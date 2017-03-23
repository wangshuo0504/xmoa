
//系统角色管理对象
var systemRoleManage = {};
systemRoleManage.createSystemRole = function () {
    layer.open({
        type: 2,
        title: '系统角色新增',
        area: ['700px', '500px'],
        // move: false,
        fixed: true,
        maxmin: true,
        content: root + '/systemRole/toAddSystemRole.do?systemRoleParentId='+systemRoleManage.queryParentId
    });
};
systemRoleManage.editSystemRole = function (fid) {
    if (!fid) {
        var checks=$('#systemRoleTable tbody input[type=checkbox]').filter(':checked');
        if(checks.length==0){
            layer.alert('需要勾选数据.(双击数据行也可编辑.)', {icon: 2});
            return;
        }
        fid=$(checks[0]).val();
    }
    layer.open({
        type: 2,
        title: '系统角色编辑',
        area: ['700px', '500px'],
        fixed: false,
        maxmin: true,
        content: root + '/systemRole/toUpdateSystemRole.do?fid=' + fid
    });

};
systemRoleManage.deleteSystemRole = function (fids) {
    if (!fids) {
        var checks=$('#systemRoleTable tbody input[type=checkbox]').filter(':checked');
        if(checks.length==0){
            layer.alert('需要勾选数据.', {icon: 2});
            return;
        }
        fids=[];
        for(var i=0;i<checks.length;i++){
            fids.push($(checks[i]).val());
        }

    }    // console.log(fids.join(","));
    layer.confirm('您确定要删除这些系统角色？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.post(root+"/systemRole/deleteSystemRole.do",{fids:typeof(fids)=='string'?fids:fids.join(",")},function (response) {
            if(response.code==1){
                layer.msg('删除成功', {time: 1500, icon: 1});
                systemRoleManage.refreshSystemRole();
            }else {
                layer.alert(response.msg);
            }
        })

    });
};
systemRoleManage.queryParentId='';
systemRoleManage.refreshSystemRole = function () {
    var page=$("#systemRole_paginator li.active").attr("page-num");
    systemRoleManage.initTable({page:page});
};

systemRoleManage.querySystemRole = function (parentId) {
    var postData={page:1};
    if(parentId!==undefined){

        systemRoleManage.queryParentId =parentId;
        postData.parentId=parentId;
    }

    systemRoleManage.initTable(postData);
};

systemRoleManage.initTable = function (postData) {
    //定义checkBoxInit
    var icheckBoxInit = function () {
        var checkAll = $('#checkAll');
        var checkboxes = $('#systemRoleTable tbody input[type=checkbox]');

        $('#systemRoleTable input[type=checkbox]').iCheck({
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
        $(document).on('dblclick', '#systemRoleTable tbody tr', function (e) {
            var id = $(this).find("input[type=checkbox]").val();
            if (id)
                systemRoleManage.editSystemRole(id);
        });
        systemRoleManage.hasBandDblclick = true;
    }

    var tableBodyDom = $("#systemRoleTable tbody");

    var url = root + '/systemRole/querySystemRole.do';

    var defaultData = {page: 1, queryStr: $("#systemRoleQuery").val(),parentId:systemRoleManage.queryParentId};
    postData=$.extend(defaultData,postData);
    $.post(url, postData, function (response) {
        tableBodyDom.empty();
        var systemRoles = response.data;
        systemRoleManage.pageData = {};
        systemRoleManage.pageData.page = response.page;
        systemRoleManage.pageData.totalNum = response.totalNum;
        systemRoleManage.pageData.totalPage = Math.ceil(response.totalNum / response.pageSize);

        for (var i in systemRoles) {
            var tr = $("<tr></tr>");
            tr.append("<td width='5%'><input type='checkbox' value='" + systemRoles[i].FID + "'/></td>");
            tr.append("<td >" + (systemRoles[i].NAME === undefined ? "" : systemRoles[i].NAME) + "</td>");
            tr.append("<td >" + (systemRoles[i].CODE === undefined ? "" : systemRoles[i].CODE) + "</td>");
            tr.append("<td >" + ( systemRoles[i].REMARK === undefined ? "" : systemRoles[i].REMARK) + "</td>");

            tableBodyDom.append(tr);
        }
        //icheck初始化
        icheckBoxInit();
        //绑定表双击事件
        if (!systemRoleManage.hasBandDblclick)
            bandDblclick();
        //初始化分页器
        systemRoleManage.pageData.refresh = function (e) {
            var page_num = $(this).attr("page-num");
            systemRoleManage.initTable({page:page_num});
        }
        $("#systemRole_paginator").xmoa_paginate(systemRoleManage.pageData);
    })
};


systemRoleManage.init = function () {
    this.initTable();
    //绑定功能按钮事件
    $(document).on('click', '.tool-button-box button', function (e) {
        var call = systemRoleManage[$(this).attr("name") + "SystemRole"];
        if (typeof(call) == 'function')
            call();
    });
    $("#systemRoleQuery").keydown(function (event) {
        if(event.keyCode==13)
            systemRoleManage.querySystemRole();
    })

}

$(function () {
    systemRoleManage.init();
})