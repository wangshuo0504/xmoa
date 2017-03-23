
//群组管理对象
var groupManage = {};
groupManage.createGroup = function () {
    layer.open({
        type: 2,
        title: '群组新增',
        area: ['700px', '500px'],
        // move: false,
        fixed: true,
        maxmin: true,
        content: root + '/group/toAddGroup.do?groupParentId='+groupManage.queryParentId
    });
};
groupManage.editGroup = function (fid) {
    if (!fid) {
        var checks=$('#groupTable tbody input[type=checkbox]').filter(':checked');
        if(checks.length==0){
            layer.alert('需要勾选数据.(双击数据行也可编辑.)', {icon: 2});
            return;
        }
        fid=$(checks[0]).val();
    }
    layer.open({
        type: 2,
        title: '群组编辑',
        area: ['700px', '500px'],
        fixed: false,
        maxmin: true,
        content: root + '/group/toUpdateGroup.do?fid=' + fid
    });

};
groupManage.deleteGroup = function (fids) {
    if (!fids) {
        var checks=$('#groupTable tbody input[type=checkbox]').filter(':checked');
        if(checks.length==0){
            layer.alert('需要勾选数据.', {icon: 2});
            return;
        }
        fids=[];
        for(var i=0;i<checks.length;i++){
            fids.push($(checks[i]).val());
        }

    }    // console.log(fids.join(","));
    layer.confirm('您确定要删除这些群组？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        $.post(root+"/group/deleteGroup.do",{fids:typeof(fids)=='string'?fids:fids.join(",")},function (response) {
            if(response.code==1){
                layer.msg('删除成功', {time: 1500, icon: 1});
                groupManage.refreshGroup();
            }else {
                layer.alert(response.msg);
            }
        })

    });
};
groupManage.sortGroup = function () {
    layer.open({
        type: 2,
        title: '群组人员排序',
        // skin:   'layui-layer-rim',
        area: ['500px', '500px'],
        fixed: false, //不固定
        maxmin: true,
        content:root + '/group/toSortGroup.do?groupParentId='+groupManage.queryParentId
    });
};
groupManage.queryParentId='';
groupManage.refreshGroup = function () {
    var page=$("#group_paginator li.active").attr("page-num");
    groupManage.initTable({page:page});
};

groupManage.queryGroup = function (parentId) {
    var postData={page:1};
    if(parentId!==undefined){

        groupManage.queryParentId =parentId;
        postData.parentId=parentId;
    }

    groupManage.initTable(postData);
};

groupManage.initTable = function (postData) {
    //定义checkBoxInit
    var icheckBoxInit = function () {
        var checkAll = $('#checkAll');
        var checkboxes = $('#groupTable tbody input[type=checkbox]');

        $('#groupTable input[type=checkbox]').iCheck({
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
        $(document).on('dblclick', '#groupTable tbody tr', function (e) {
            var id = $(this).find("input[type=checkbox]").val();
            if (id)
                groupManage.editGroup(id);
        });
        groupManage.hasBandDblclick = true;
    }

    var tableBodyDom = $("#groupTable tbody");

    var url = root + '/group/queryGroup.do';

    var defaultData = {page: 1, queryStr: $("#groupQuery").val(),parentId:groupManage.queryParentId};
    postData=$.extend(defaultData,postData);
    $.post(url, postData, function (response) {
        tableBodyDom.empty();
        var groups = response.data;
        groupManage.pageData = {};
        groupManage.pageData.page = response.page;
        groupManage.pageData.totalNum = response.totalNum;
        groupManage.pageData.totalPage = Math.ceil(response.totalNum / response.pageSize);

        for (var i in groups) {
            var tr = $("<tr></tr>");
            tr.append("<td width='5%'><input type='checkbox' value='" + groups[i].FID + "'/></td>");
            tr.append("<td >" + (groups[i].NAME === undefined ? "" : groups[i].NAME) + "</td>");
            tr.append("<td >" + (groups[i].CODE === undefined ? "" : groups[i].CODE) + "</td>");
            tr.append("<td >" + ( groups[i].REMARK === undefined ? "" : groups[i].REMARK) + "</td>");

            tableBodyDom.append(tr);
        }
        //icheck初始化
        icheckBoxInit();
        //绑定表双击事件
        if (!groupManage.hasBandDblclick)
            bandDblclick();
        //初始化分页器
        groupManage.pageData.refresh = function (e) {
            var page_num = $(this).attr("page-num");
            groupManage.initTable({page:page_num});
        }
        $("#group_paginator").xmoa_paginate(groupManage.pageData);
    })
};


groupManage.init = function () {
    this.initTable();
    //绑定功能按钮事件
    $(document).on('click', '.tool-button-box button', function (e) {
        var call = groupManage[$(this).attr("name") + "Group"];
        if (typeof(call) == 'function')
            call();
    });
    $("#groupQuery").keydown(function (event) {
        if(event.keyCode==13)
            groupManage.queryGroup();
    })

}

$(function () {
    groupManage.init();
})