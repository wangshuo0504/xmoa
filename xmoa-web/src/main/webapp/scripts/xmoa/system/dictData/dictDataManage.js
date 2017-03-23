//基础代码管理对象
var dictDataManage = {};
dictDataManage.createDictData = function () {
    layer.open({
        type: 2,
        title: '基础代码新增',
        area: ['700px', '500px'],
        // move: false,
        fixed: true,
        maxmin: true,
        content: root + '/dictData/toAddDictData.do?dictDataParentId=' + dictDataManage.queryParentId
    });
};
dictDataManage.editDictData = function (fid) {
    if (!fid) {
        var checks = $('#dictDataTable tbody input[type=checkbox]').filter(':checked');
        if (checks.length == 0) {
            layer.alert('需要勾选数据.(双击数据行也可编辑.)', {icon: 2});
            return;
        }
        fid = $(checks[0]).val();
    }
    layer.open({
        type: 2,
        title: '基础代码编辑',
        area: ['700px', '500px'],
        fixed: false,
        maxmin: true,
        content: root + '/dictData/toUpdateDictData.do?fid=' + fid
    });

};
dictDataManage.deleteDictData = function (fids) {
    if (!fids) {
        var checks = $('#dictDataTable tbody input[type=checkbox]').filter(':checked');
        if (checks.length == 0) {
            layer.alert('需要勾选数据.', {icon: 2});
            return;
        }
        fids = [];
        for (var i = 0; i < checks.length; i++) {
            fids.push($(checks[i]).val());
        }

    }    // console.log(fids.join(","));
    layer.confirm('您确定要删除这些基础代码？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.post(root + "/dictData/deleteDictData.do", {fids: typeof(fids) == 'string' ? fids : fids.join(",")}, function (response) {
            if (response.code == 1) {
                layer.msg('删除成功', {time: 1500, icon: 1});
                dictDataManage.refreshDictData();
            } else {
                layer.alert(response.msg);
            }
        })

    });
};
dictDataManage.sortDictData = function () {
    layer.open({
        type: 2,
        title: '基础代码排序',
        // skin:   'layui-layer-rim',
        area: ['500px', '650px'],
        fixed: false, //不固定
        maxmin: true,
        content: root + '/dictData/toSortDictData.do?dictDataParentId=' + dictDataManage.queryParentId
    });
};
dictDataManage.queryParentId = '';
dictDataManage.refreshDictData = function () {
    dictDataManage.initDictDataTree();
    var page = $("#dictData_paginator li.active").attr("page-num");
    dictDataManage.initTable({page: page});
};

dictDataManage.queryDictData = function (parentId) {
    var postData = {page: 1};
    if (parentId !== undefined) {
        dictDataManage.queryParentId = parentId;
        postData.parentId = parentId;
    }

    dictDataManage.initTable(postData);
};

dictDataManage.initTable = function (postData) {
    //定义checkBoxInit
    var icheckBoxInit = function () {
        var checkAll = $('#checkAll');
        var checkboxes = $('#dictDataTable tbody input[type=checkbox]');

        $('#dictDataTable input[type=checkbox]').iCheck({
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
        $(document).on('dblclick', '#dictDataTable tbody tr', function (e) {
            var id = $(this).find("input[type=checkbox]").val();
            if (id)
                dictDataManage.editDictData(id);
        });
        dictDataManage.hasBandDblclick = true;
    }

    var tableBodyDom = $("#dictDataTable tbody");

    var url = root + '/dictData/queryDictData.do';

    var defaultData = {page: 1, queryStr: $("#dictDataQuery").val(), parentId: dictDataManage.queryParentId};
    postData = $.extend(defaultData, postData);
    $.post(url, postData, function (response) {
        tableBodyDom.empty();
        var dictDatas = response.data;
        dictDataManage.pageData = {};
        dictDataManage.pageData.page = response.page;
        dictDataManage.pageData.totalNum = response.totalNum;
        dictDataManage.pageData.totalPage = Math.ceil(response.totalNum / response.pageSize);

        for (var i in dictDatas) {
            var tr = $("<tr></tr>");
            tr.append("<td width='5%'><input type='checkbox' value='" + dictDatas[i].FID + "'/></td>");
            tr.append("<td >" + (dictDatas[i].NAME === undefined ? "" : dictDatas[i].NAME) + "</td>");
            tr.append("<td >" + (dictDatas[i].CODE === undefined ? "" : dictDatas[i].CODE) + "</td>");
            tr.append("<td >" + ( dictDatas[i].URL === undefined ? "" : dictDatas[i].URL) + "</td>");
            tr.append("<td >" + (dictDatas[i].PARENT_NAME === undefined ? "" : dictDatas[i].PARENT_NAME) + "</td>");
            tableBodyDom.append(tr);
        }
        //icheck初始化
        icheckBoxInit();
        //绑定表双击事件
        if (!dictDataManage.hasBandDblclick)
            bandDblclick();
        //初始化分页器
        dictDataManage.pageData.refresh = function (e) {
            var page_num = $(this).attr("page-num");
            dictDataManage.initTable({page: page_num});
        }
        $("#dictData_paginator").xmoa_paginate(dictDataManage.pageData);
    })
};
dictDataManage.initDictDataTree = function () {
    $.get(root + "/dictData/getAllDictType.do", function (response) {

    })
    $('#dictDataTree4dictData').perfectScrollbar();
}

dictDataManage.init = function () {
    this.initDictDataTree();
    this.initTable();
    //绑定功能按钮事件
    $(document).on('click', '.tool-button-box button', function (e) {
        var call = dictDataManage[$(this).attr("name") + "DictData"];
        if (typeof(call) == 'function')
            call();
    });
    $("#dictDataQuery").keydown(function (event) {
        if (event.keyCode == 13)
            dictDataManage.queryDictData();
    })

}

$(function () {
    dictDataManage.init();
})