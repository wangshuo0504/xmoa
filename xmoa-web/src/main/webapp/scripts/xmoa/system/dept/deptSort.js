var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

var deptSortInit = function () {

    $('.sortPanel').perfectScrollbar();
    $("#deptSortList").dragsort({
        dragSelector: "div",
        dragBetween: false,
        dragEnd: function () {
        },
        placeHolderTemplate: "<li class='placeHolder'><div></div></li>"
    });
}

$(function () {
    deptSortInit();
})


var saveDeptOrder = function () {
    var getPostData = function () {
        var liList = $("#deptSortList li");
        var sortList = [];
        for (var i = 0; i < liList.length; i++) {
            var id = $(liList[i]).attr("dept-fid");
            sortList.push(( i + 1) + ":" + id);
        }
        return sortList.join(",");
    }
    var url = root + "/dept/saveDeptOrder.do";
    var postData = {'resultList': getPostData()};
    $.post(url, postData, function (response) {
        if (response.code == "1") {
            parent.layer.msg('操作成功', {time: 1500, icon: 1});
            parent.deptManage.refreshDept();
            parent.layer.close(index);
        } else {
            if (response.msg)
                parent.layer.msg(response.msg, {time: 1500, icon: 2});
            else
                parent.layer.msg("保存时发生错误.", {time: 1500, icon: 5});
        }
    })
}