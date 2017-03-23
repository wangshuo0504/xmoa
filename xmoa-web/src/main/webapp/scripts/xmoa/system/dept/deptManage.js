
//单位管理对象
var deptManage = {};
deptManage.createDept = function () {
    layer.open({
        type: 2,
        title: '单位新增',
        area: ['700px', '550px'],
        // move: false,
        fixed: true,
        maxmin: true,
        content: root + '/dept/toAddDept.do?deptParentId='+deptManage.queryParentId
    });
};
deptManage.editDept = function (fid) {
    if (!fid) {
        var checks=$('#deptTable tbody input[type=checkbox]').filter(':checked');
        if(checks.length==0){
            layer.alert('需要勾选数据.(双击数据行也可编辑.)', {icon: 2});
            return;
        }
        fid=$(checks[0]).val();
    }
    layer.open({
        type: 2,
        title: '单位编辑',
        area: ['700px', '550px'],
        fixed: false,
        maxmin: true,
        content: root + '/dept/toUpdateDept.do?fid=' + fid
    });

};
deptManage.deleteDept = function (fids) {
    if (!fids) {
        var checks=$('#deptTable tbody input[type=checkbox]').filter(':checked');
        if(checks.length==0){
            layer.alert('需要勾选数据.', {icon: 2});
            return;
        }
        fids=[];
        for(var i=0;i<checks.length;i++){
            fids.push($(checks[i]).val());
        }

    }    // console.log(fids.join(","));
    layer.confirm('您确定要删除这些单位？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.post(root+"/dept/deleteDept.do",{fids:typeof(fids)=='string'?fids:fids.join(",")},function (response) {
            if(response.code==1){
                layer.msg('删除成功', {time: 1500, icon: 1});
                deptManage.refreshDept();
            }else {
                layer.alert(response.msg);
            }
        })

    });
};
deptManage.sortDept = function () {
    layer.open({
        type: 2,
        title: '单位排序',
        // skin:   'layui-layer-rim',
        area: ['500px', '600px'],
        fixed: false, //不固定
        maxmin: true,
        content:root + '/dept/toSortDept.do?deptParentId='+deptManage.queryParentId
    });
};
deptManage.queryParentId='';
deptManage.refreshDept = function () {
    deptManage.initDeptTree();
    var page=$("#dept_paginator li.active").attr("page-num");
    deptManage.initTable({page:page});
};

deptManage.queryDept = function (parentId) {
    var postData={page:1};
    if(parentId!==undefined){

        deptManage.queryParentId =parentId;
        postData.parentId=parentId;
    }

    deptManage.initTable(postData);
};

deptManage.initTable = function (postData) {
    //定义checkBoxInit
    var icheckBoxInit = function () {
        var checkAll = $('#checkAll');
        var checkboxes = $('#deptTable tbody input[type=checkbox]');

        $('#deptTable input[type=checkbox]').iCheck({
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
        $(document).on('dblclick', '#deptTable tbody tr', function (e) {
            var id = $(this).find("input[type=checkbox]").val();
            if (id)
                deptManage.editDept(id);
        });
        deptManage.hasBandDblclick = true;
    }

    var tableBodyDom = $("#deptTable tbody");

    var url = root + '/dept/queryDept.do';

    var defaultData = {page: 1, queryStr: $("#deptQuery").val(),parentId:deptManage.queryParentId};
    postData=$.extend(defaultData,postData);
    $.post(url, postData, function (response) {
        tableBodyDom.empty();
        var depts = response.data;
        deptManage.pageData = {};
        deptManage.pageData.page = response.page;
        deptManage.pageData.totalNum = response.totalNum;
        deptManage.pageData.totalPage = Math.ceil(response.totalNum / response.pageSize);

        for (var i in depts) {
            var tr = $("<tr></tr>");
            tr.append("<td width='5%'><input type='checkbox' value='" + depts[i].FID + "'/></td>");
            tr.append("<td >" + (depts[i].NAME === undefined ? "" : depts[i].NAME) + "</td>");
            tr.append("<td >" + (depts[i].CODE === undefined ? "" : depts[i].CODE) + "</td>");
            tr.append("<td >" + ( depts[i].SHORT_NAME === undefined ? "" : depts[i].SHORT_NAME) + "</td>");
            tr.append("<td >" + (depts[i].PARENT_NAME === undefined ? "" : depts[i].PARENT_NAME) + "</td>");
            tableBodyDom.append(tr);
        }
        //icheck初始化
        icheckBoxInit();
        //绑定表双击事件
        if (!deptManage.hasBandDblclick)
            bandDblclick();
        //初始化分页器
        deptManage.pageData.refresh = function (e) {
            var page_num = $(this).attr("page-num");
            deptManage.initTable({page:page_num});
        }
        $("#dept_paginator").xmoa_paginate(deptManage.pageData);
    })
};
deptManage.initDeptTree = function () {
    $.get(root + "/dept/getAllDept.do", function (response) {

        var $checkableTree = $('#deptTree4dept').treeview({
            data: convertDeptTreeData(response.data,deptManage.queryParentId),
            showIcon: false,
            showCheckbox: false,
            onNodeSelected: function (event, node) {
                deptManage.needRefresh = false;
                deptManage.queryDept(node.id);
            },
            onNodeUnselected: function (event, node) {
                deptManage.queryParentId='';
                deptManage.needRefresh = true;
                setTimeout(function () {
                    if (deptManage.needRefresh)
                        deptManage.queryDept();
                }, 200)
            }
        });
    })
    $('#deptTree4dept').perfectScrollbar();
}

deptManage.init = function () {
    this.initDeptTree();
    this.initTable();
    //绑定功能按钮事件
    $(document).on('click', '.tool-button-box button', function (e) {
        var call = deptManage[$(this).attr("name") + "Dept"];
        if (typeof(call) == 'function')
            call();
    });
    $("#deptQuery").keydown(function (event) {
        if(event.keyCode==13)
            deptManage.queryDept();
    })

}

$(function () {
    deptManage.init();
})