
//用户管理对象
var userManage = {};
userManage.createUser = function () {
    layer.open({
        type: 2,
        title: '用户新增',
        area: ['700px', '500px'],
        // move: false,
        fixed: true,
        maxmin: true,
        content: root + '/user/toAddUser.do?userDeptId='+userManage.queryDeptId
    });
};
userManage.editUser = function (fid) {
    if (!fid) {
        var checks=$('#userTable tbody input[type=checkbox]').filter(':checked');
        if(checks.length==0){
            layer.alert('需要勾选数据.(双击数据行也可编辑.)', {icon: 2});
            return;
        }
        fid=$(checks[0]).val();
    }
    layer.open({
        type: 2,
        title: '用户编辑',
        area: ['850px', '500px'],
        fixed: false,
        maxmin: true,
        content: root + '/user/toUpdateUser.do?fid=' + fid
    });

};
userManage.deleteUser = function (fids) {
    if (!fids) {
        var checks=$('#userTable tbody input[type=checkbox]').filter(':checked');
        if(checks.length==0){
            layer.alert('需要勾选数据.', {icon: 2});
            return;
        }
        fids=[];
        for(var i=0;i<checks.length;i++){
            fids.push($(checks[i]).val());
        }

    }    // console.log(fids.join(","));
    layer.confirm('您确定要删除这些用户？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.post(root+"/user/deleteUser.do",{fids:typeof(fids)=='string'?fids:fids.join(",")},function (response) {
            if(response.code==1){
                layer.msg('删除成功', {time: 1500, icon: 1});
                userManage.refreshUser();
            }else {
                layer.alert(response.msg);
            }
        })

    });
};
userManage.sortUser = function () {
    if (!userManage.queryDeptId) {
            layer.alert('需要先选择一个部门.', {icon: 2});
            return;
    }

    layer.open({
        type: 2,
        title: '用户排序',
        // skin:   'layui-layer-rim',
        area: ['500px', '500px'],
        fixed: false, //不固定
        maxmin: true,
        content:root + '/user/toSortUser.do?deptId='+userManage.queryDeptId
    });
};
userManage.queryDeptId='';
userManage.refreshUser = function () {
    userManage.initDeptTree();
    var page=$("#user_paginator li.active").attr("page-num");
    userManage.initTable({page:page});
};

userManage.queryUser = function (deptId) {
    var postData={page:1};
    if(deptId!==undefined){
        userManage.queryDeptId =deptId;
        postData.deptId=deptId;
    }

    userManage.initTable(postData);
};

userManage.initTable = function (postData) {
    //定义checkBoxInit
    var icheckBoxInit = function () {
        var checkAll = $('#checkAll');
        var checkboxes = $('#userTable tbody input[type=checkbox]');

        $('#userTable input[type=checkbox]').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });

        checkAll.off('ifChecked ifUnchecked');
        checkAll.on('ifChecked ifUnchecked', function (event) {
            if (event.type == 'ifChecked') {
                checkboxes.iCheck('check');
            } else {
                checkboxes.iCheck('uncheck');
            }
        });
        var refreshCheckAll = function () {
            if(checkboxes.length==0)
                return;
            if (checkboxes.filter(':checked').length == checkboxes.length) {
                checkAll.prop('checked', 'checked');
            } else {
                checkAll.prop('checked', false);
            }
            checkAll.iCheck('update');
        }
        checkboxes.on('ifChanged', function (event) {
            refreshCheckAll();
        });
        refreshCheckAll();
    }

    //定义绑定table行的双击事件(dblclick)
    var bandDblclick = function () {
        $(document).on('dblclick', '#userTable tbody tr', function (e) {
            var id = $(this).find("input[type=checkbox]").val();
            if (id)
                userManage.editUser(id);
        });
        userManage.hasBandDblclick = true;
    }

    var tableBodyDom = $("#userTable tbody");

    var url = root + '/user/queryUser.do';

    var defaultData = {page: 1, queryStr: $("#userQuery").val(),deptId:userManage.queryDeptId};
    postData=$.extend(defaultData,postData);
    $.post(url, postData, function (response) {
        tableBodyDom.empty();
        var users = response.data;
        userManage.pageData = {};
        userManage.pageData.page = response.page;
        userManage.pageData.totalNum = response.totalNum;
        userManage.pageData.totalPage = Math.ceil(response.totalNum / response.pageSize);

        for (var i in users) {
           var status_class=''
            if (users[i].STATUS=='NORMAL')
                status_class='label label-success';
           else if(users[i].STATUS=='FREEZE')
                status_class='label label-warning';
           else if (users[i].STATUS=='CANCEL')
                status_class='label label-danger';
            var tr = $("<tr></tr>");
            tr.append("<td width='5%'><input type='checkbox' value='" + users[i].FID + "'/></td>");
            tr.append("<td >" + (users[i].USER_NAME ? users[i].USER_NAME : "" ) + "</td>");
            tr.append("<td >" + (users[i].NAME ? users[i].NAME : "" ) + "</td>");
            tr.append("<td >" + (users[i].DEPT_NAME ? users[i].DEPT_NAME : "" ) + "</td>");
            tr.append("<td ><span class='"+status_class+"'> " + (users[i].STATUS_NAME ? users[i].STATUS_NAME : "" ) + "</span></td>");
            tableBodyDom.append(tr);
        }
        //icheck初始化
        icheckBoxInit();
        //绑定表双击事件
        if (!userManage.hasBandDblclick)
            bandDblclick();
        //初始化分页器
        userManage.pageData.refresh = function (e) {
            var page_num = $(this).attr("page-num");
            userManage.initTable({page:page_num});
        }
        $("#user_paginator").xmoa_paginate(userManage.pageData);
    })
};
userManage.initDeptTree = function () {
    $.get(root + "/dept/getAllDept.do", function (response) {

        var $checkableTree = $('#deptTree4user').treeview({
            data: convertDeptTreeData(response.data,userManage.queryDeptId),
            showIcon: false,
            showCheckbox: false,

            onNodeSelected: function (event, node) {
                userManage.needRefresh = false;
                userManage.queryUser(node.id);
            },
            onNodeUnselected: function (event, node) {
                userManage.queryDeptId='';
                userManage.needRefresh = true;
                setTimeout(function () {
                    if (userManage.needRefresh)
                        userManage.queryUser();
                }, 200)

            }
        });
    })
    $('#deptTree4user').perfectScrollbar();
}

userManage.init = function () {
    this.initDeptTree();
    this.initTable();
    //绑定功能按钮事件
    $(document).on('click', '.tool-button-box button', function (e) {
        var call = userManage[$(this).attr("name") + "User"];
        if (typeof(call) == 'function')
            call();
    });
    $("#userQuery").keydown(function (event) {
        if(event.keyCode==13)
            userManage.queryUser();
    })

}

$(function () {
    userManage.init();
})