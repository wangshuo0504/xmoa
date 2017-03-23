//资源管理对象
var moduleManage = {};
moduleManage.createModule = function () {
    layer.open({
        type: 2,
        title: '资源新增',
        area: ['700px', '500px'],
        // move: false,
        fixed: true,
        maxmin: true,
        content: root + '/module/toAddModule.do?moduleParentId=' + moduleManage.queryParentId
    });
};
moduleManage.editModule = function (fid) {
    if (!fid) {
        var checks = $('#moduleTable tbody input[type=checkbox]').filter(':checked');
        if (checks.length == 0) {
            layer.alert('需要勾选数据.(双击数据行也可编辑.)', {icon: 2});
            return;
        }
        fid = $(checks[0]).val();
    }
    layer.open({
        type: 2,
        title: '资源编辑',
        area: ['700px', '500px'],
        fixed: false,
        maxmin: true,
        content: root + '/module/toUpdateModule.do?fid=' + fid
    });

};
moduleManage.deleteModule = function (fids) {
    if (!fids) {
        var checks = $('#moduleTable tbody input[type=checkbox]').filter(':checked');
        if (checks.length == 0) {
            layer.alert('需要勾选数据.', {icon: 2});
            return;
        }
        fids = [];
        for (var i = 0; i < checks.length; i++) {
            fids.push($(checks[i]).val());
        }

    }    // console.log(fids.join(","));
    layer.confirm('您确定要删除这些资源？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.post(root + "/module/deleteModule.do", {fids: typeof(fids) == 'string' ? fids : fids.join(",")}, function (response) {
            if (response.code == 1) {
                layer.msg('删除成功', {time: 1500, icon: 1});
                moduleManage.refreshModule();
            } else {
                layer.alert(response.msg);
            }
        })

    });
};
moduleManage.sortModule = function () {
    layer.open({
        type: 2,
        title: '资源排序',
        // skin:   'layui-layer-rim',
        area: ['500px', '650px'],
        fixed: false, //不固定
        maxmin: true,
        content: root + '/module/toSortModule.do?moduleParentId=' + moduleManage.queryParentId
    });
};
moduleManage.queryParentId = '';
moduleManage.refreshModule = function () {
    moduleManage.initModuleTree();
    var page = $("#module_paginator li.active").attr("page-num");
    moduleManage.initTable({page: page});
};

moduleManage.queryModule = function (parentId) {
    var postData = {page: 1};
    if (parentId !== undefined) {
        moduleManage.queryParentId = parentId;
        postData.parentId = parentId;
    }

    moduleManage.initTable(postData);
};

moduleManage.initTable = function (postData) {
    //定义checkBoxInit
    var icheckBoxInit = function () {
        var checkAll = $('#checkAll');
        var checkboxes = $('#moduleTable tbody input[type=checkbox]');

        $('#moduleTable input[type=checkbox]').iCheck({
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
        $(document).on('dblclick', '#moduleTable tbody tr', function (e) {
            var id = $(this).find("input[type=checkbox]").val();
            if (id)
                moduleManage.editModule(id);
        });
        moduleManage.hasBandDblclick = true;
    }

    var tableBodyDom = $("#moduleTable tbody");

    var url = root + '/module/queryModule.do';

    var defaultData = {page: 1, queryStr: $("#moduleQuery").val(), parentId: moduleManage.queryParentId};
    postData = $.extend(defaultData, postData);
    $.post(url, postData, function (response) {
        tableBodyDom.empty();
        var modules = response.data;
        moduleManage.pageData = {};
        moduleManage.pageData.page = response.page;
        moduleManage.pageData.totalNum = response.totalNum;
        moduleManage.pageData.totalPage = Math.ceil(response.totalNum / response.pageSize);

        for (var i in modules) {
            var tr = $("<tr></tr>");
            tr.append("<td width='5%'><input type='checkbox' value='" + modules[i].FID + "'/></td>");
            tr.append("<td >" + (modules[i].NAME === undefined ? "" : modules[i].NAME) + "</td>");
            tr.append("<td >" + (modules[i].CODE === undefined ? "" : modules[i].CODE) + "</td>");
            tr.append("<td >" + ( modules[i].URL === undefined ? "" : modules[i].URL) + "</td>");
            tr.append("<td >" + (modules[i].PARENT_NAME === undefined ? "" : modules[i].PARENT_NAME) + "</td>");
            tableBodyDom.append(tr);
        }
        //icheck初始化
        icheckBoxInit();
        //绑定表双击事件
        if (!moduleManage.hasBandDblclick)
            bandDblclick();
        //初始化分页器
        moduleManage.pageData.refresh = function (e) {
            var page_num = $(this).attr("page-num");
            moduleManage.initTable({page: page_num});
        }
        $("#module_paginator").xmoa_paginate(moduleManage.pageData);
    })
};
moduleManage.initModuleTree = function () {
    $.get(root + "/module/getAllModule.do", function (response) {

        var $checkableTree = $('#moduleTree4module').treeview({
            data: convertModuleTreeData(response.data, moduleManage.queryParentId),
            showIcon: false,
            showCheckbox: false,
            onNodeSelected: function (event, node) {
                moduleManage.needRefresh = false;
                 moduleManage.queryModule(node.id);
            },
            onNodeUnselected: function (event, node) {
                moduleManage.queryParentId='';
                moduleManage.needRefresh = true;
                setTimeout(function () {
                    if (moduleManage.needRefresh)
                        moduleManage.queryModule();
                }, 200)

            }
        });
    })
    $('#moduleTree4module').perfectScrollbar();
}

moduleManage.init = function () {
    this.initModuleTree();
    this.initTable();
    //绑定功能按钮事件
    $(document).on('click', '.tool-button-box button', function (e) {
        var call = moduleManage[$(this).attr("name") + "Module"];
        if (typeof(call) == 'function')
            call();
    });
    $("#moduleQuery").keydown(function (event) {
        if (event.keyCode == 13)
            moduleManage.queryModule();
    })

}

$(function () {
    moduleManage.init();
})