//动作权限管理对象
var actionRightManage = {};
actionRightManage.createActionRight = function () {
    layer.open({
        type: 2,
        title: '动作权限新增',
        area: ['700px', '500px'],
        // move: false,
        fixed: true,
        maxmin: true,
        content: root + '/actionRight/toAddActionRight.do?actionRightModuleId=' + actionRightManage.queryModuleId
    });
};
actionRightManage.editActionRight = function (fid) {
    if (!fid) {
        var checks = $('#actionRightTable tbody input[type=checkbox]').filter(':checked');
        if (checks.length == 0) {
            layer.alert('需要勾选数据.(双击数据行也可编辑.)', {icon: 2});
            return;
        }
        fid = $(checks[0]).val();
    }
    layer.open({
        type: 2,
        title: '动作权限编辑',
        area: ['700px', '500px'],
        fixed: false,
        maxmin: true,
        content: root + '/actionRight/toUpdateActionRight.do?fid=' + fid
    });

};
actionRightManage.deleteActionRight = function (fids) {
    if (!fids) {
        var checks = $('#actionRightTable tbody input[type=checkbox]').filter(':checked');
        if (checks.length == 0) {
            layer.alert('需要勾选数据.', {icon: 2});
            return;
        }
        fids = [];
        for (var i = 0; i < checks.length; i++) {
            fids.push($(checks[i]).val());
        }

    }    // console.log(fids.join(","));
    layer.confirm('您确定要删除这些动作权限？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.post(root + "/actionRight/deleteActionRight.do", {fids: typeof(fids) == 'string' ? fids : fids.join(",")}, function (response) {
            if (response.code == 1) {
                layer.msg('删除成功', {time: 1500, icon: 1});
                actionRightManage.refreshActionRight();
            } else {
                layer.alert(response.msg);
            }
        })

    });
};
actionRightManage.giveActionRight = function () {
    layer.open({
        type: 2,
        title: '动作权限排序',
        // skin:   'layui-layer-rim',
        area: ['500px', '650px'],
        fixed: false, //不固定
        maxmin: true,
        content: root + '/actionRight/toSortActionRight.do?actionRightParentId=' + actionRightManage.queryModuleId
    });
};
actionRightManage.queryModuleId = '';
actionRightManage.refreshActionRight = function () {
    actionRightManage.initModuleTree();
    var page = $("#actionRight_paginator li.active").attr("page-num");
    actionRightManage.initTable({page: page});
};

actionRightManage.queryActionRight = function (moduleId) {
    var postData = {page: 1};
    if (moduleId !== undefined) {
        actionRightManage.queryModuleId = moduleId;
        postData.moduleId = moduleId;
    }

    actionRightManage.initTable(postData);
};

actionRightManage.initTable = function (postData) {
    //定义checkBoxInit
    var icheckBoxInit = function () {
        var checkAll = $('#checkAll');
        var checkboxes = $('#actionRightTable tbody input[type=checkbox]');

        $('#actionRightTable input[type=checkbox]').iCheck({
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
            if (checkboxes.length == 0)
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
        $(document).on('dblclick', '#actionRightTable tbody tr', function (e) {
            var id = $(this).find("input[type=checkbox]").val();
            if (id)
                actionRightManage.editActionRight(id);
        });
        actionRightManage.hasBandDblclick = true;
    }

    var tableBodyDom = $("#actionRightTable tbody");

    var url = root + '/actionRight/queryActionRight.do';

    var defaultData = {page: 1, queryStr: $("#actionRightQuery").val(), moduleId: actionRightManage.queryModuleId};
    postData = $.extend(defaultData, postData);
    $.post(url, postData, function (response) {
        tableBodyDom.empty();
        var actionRights = response.data;
        actionRightManage.pageData = {};
        actionRightManage.pageData.page = response.page;
        actionRightManage.pageData.totalNum = response.totalNum;
        actionRightManage.pageData.totalPage = Math.ceil(response.totalNum / response.pageSize);

        for (var i in actionRights) {
            var tr = $("<tr></tr>");
            tr.append("<td width='5%'><input type='checkbox' value='" + actionRights[i].FID + "'/></td>");
            tr.append("<td >" + (actionRights[i].NAME === undefined ? "" : actionRights[i].NAME) + "</td>");
            tr.append("<td >" + (actionRights[i].CODE === undefined ? "" : actionRights[i].CODE) + "</td>");
            tr.append("<td >" + ( actionRights[i].MODULE_NAME === undefined ? "" : actionRights[i].MODULE_NAME) + "</td>");
            tr.append("<td >" + (actionRights[i].DESCRIPTION === undefined ? "" : actionRights[i].DESCRIPTION) + "</td>");
            tableBodyDom.append(tr);
        }
        //icheck初始化
        icheckBoxInit();
        //绑定表双击事件
        if (!actionRightManage.hasBandDblclick)
            bandDblclick();
        //初始化分页器
        actionRightManage.pageData.refresh = function (e) {
            var page_num = $(this).attr("page-num");
            actionRightManage.initTable({page: page_num});
        }
        $("#actionRight_paginator").xmoa_paginate(actionRightManage.pageData);
    })
};
actionRightManage.initModuleTree = function () {
    $.get(root + "/module/getAllModule.do", function (response) {

        var $checkableTree = $('#moduleTree4actionRight').treeview({
            data: convertModuleTreeData(response.data, actionRightManage.queryModuleId),
            showIcon: false,
            showCheckbox: false,
            onNodeSelected: function (event, node) {
                actionRightManage.needRefresh = false;
                actionRightManage.queryActionRight(node.id);
            },
            onNodeUnselected: function (event, node) {
                actionRightManage.queryModuleId='';
                actionRightManage.needRefresh = true;
                setTimeout(function () {
                    if (actionRightManage.needRefresh)
                        actionRightManage.queryActionRight();
                }, 200)

            }
        });
    })
    $('#moduleTree4actionRight').perfectScrollbar();
}

actionRightManage.init = function () {
    this.initModuleTree();
    this.initTable();
    //绑定功能按钮事件
    $(document).on('click', '.tool-button-box button', function (e) {
        var call = actionRightManage[$(this).attr("name") + "ActionRight"];
        if (typeof(call) == 'function')
            call();
    });
    $("#actionRightQuery").keydown(function (event) {
        if (event.keyCode == 13)
            actionRightManage.queryActionRight();
    })

}

$(function () {
    actionRightManage.init();
})