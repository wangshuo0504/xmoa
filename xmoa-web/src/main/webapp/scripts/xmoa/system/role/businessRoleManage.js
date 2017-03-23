
//业务角色管理对象
var businessRoleManage = {};
businessRoleManage.createBusinessRole = function () {
    layer.open({
        type: 2,
        title: '业务角色新增',
        area: ['700px', '500px'],
        // move: false,
        fixed: true,
        maxmin: true,
        content: root + '/businessRole/toAddBusinessRole.do?businessRoleParentId='+businessRoleManage.queryParentId
    });
};
businessRoleManage.editBusinessRole = function (fid) {
    if (!fid) {
        var checks=$('#businessRoleTable tbody input[type=checkbox]').filter(':checked');
        if(checks.length==0){
            layer.alert('需要勾选数据.(双击数据行也可编辑.)', {icon: 2});
            return;
        }
        fid=$(checks[0]).val();
    }
    layer.open({
        type: 2,
        title: '业务角色编辑',
        area: ['700px', '500px'],
        fixed: false,
        maxmin: true,
        content: root + '/businessRole/toUpdateBusinessRole.do?fid=' + fid
    });

};
businessRoleManage.deleteBusinessRole = function (fids) {
    if (!fids) {
        var checks=$('#businessRoleTable tbody input[type=checkbox]').filter(':checked');
        if(checks.length==0){
            layer.alert('需要勾选数据.', {icon: 2});
            return;
        }
        fids=[];
        for(var i=0;i<checks.length;i++){
            fids.push($(checks[i]).val());
        }

    }    // console.log(fids.join(","));
    layer.confirm('您确定要删除这些业务角色？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.post(root+"/businessRole/deleteBusinessRole.do",{fids:typeof(fids)=='string'?fids:fids.join(",")},function (response) {
            if(response.code==1){
                layer.msg('删除成功', {time: 1500, icon: 1});
                businessRoleManage.refreshBusinessRole();
            }else {
                layer.alert(response.msg);
            }
        })

    });
};

businessRoleManage.queryParentId='';
businessRoleManage.refreshBusinessRole = function () {
    var page=$("#businessRole_paginator li.active").attr("page-num");
    businessRoleManage.initTable({page:page});
};

businessRoleManage.queryBusinessRole = function (parentId) {
    var postData={page:1};
    if(parentId!==undefined){

        businessRoleManage.queryParentId =parentId;
        postData.parentId=parentId;
    }

    businessRoleManage.initTable(postData);
};

businessRoleManage.initTable = function (postData) {
    //定义checkBoxInit
    var icheckBoxInit = function () {
        var checkAll = $('#checkAll');
        var checkboxes = $('#businessRoleTable tbody input[type=checkbox]');

        $('#businessRoleTable input[type=checkbox]').iCheck({
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
        $(document).on('dblclick', '#businessRoleTable tbody tr', function (e) {
            var id = $(this).find("input[type=checkbox]").val();
            if (id)
                businessRoleManage.editBusinessRole(id);
        });
        businessRoleManage.hasBandDblclick = true;
    }

    var tableBodyDom = $("#businessRoleTable tbody");

    var url = root + '/businessRole/queryBusinessRole.do';

    var defaultData = {page: 1, queryStr: $("#businessRoleQuery").val(),parentId:businessRoleManage.queryParentId};
    postData=$.extend(defaultData,postData);
    $.post(url, postData, function (response) {
        tableBodyDom.empty();
        var businessRoles = response.data;
        businessRoleManage.pageData = {};
        businessRoleManage.pageData.page = response.page;
        businessRoleManage.pageData.totalNum = response.totalNum;
        businessRoleManage.pageData.totalPage = Math.ceil(response.totalNum / response.pageSize);

        for (var i in businessRoles) {
            var tr = $("<tr></tr>");
            tr.append("<td width='5%'><input type='checkbox' value='" + businessRoles[i].FID + "'/></td>");
            tr.append("<td >" + (businessRoles[i].NAME === undefined ? "" : businessRoles[i].NAME) + "</td>");
            tr.append("<td >" + (businessRoles[i].CODE === undefined ? "" : businessRoles[i].CODE) + "</td>");
            tr.append("<td >" + ( businessRoles[i].REMARK === undefined ? "" : businessRoles[i].REMARK) + "</td>");

            tableBodyDom.append(tr);
        }
        //icheck初始化
        icheckBoxInit();
        //绑定表双击事件
        if (!businessRoleManage.hasBandDblclick)
            bandDblclick();
        //初始化分页器
        businessRoleManage.pageData.refresh = function (e) {
            var page_num = $(this).attr("page-num");
            businessRoleManage.initTable({page:page_num});
        }
        $("#businessRole_paginator").xmoa_paginate(businessRoleManage.pageData);
    })
};


businessRoleManage.init = function () {
    this.initTable();
    //绑定功能按钮事件
    $(document).on('click', '.tool-button-box button', function (e) {
        var call = businessRoleManage[$(this).attr("name") + "BusinessRole"];
        if (typeof(call) == 'function')
            call();
    });
    $("#businessRoleQuery").keydown(function (event) {
        if(event.keyCode==13)
            businessRoleManage.queryBusinessRole();
    })
}

$(function () {
    businessRoleManage.init();
})