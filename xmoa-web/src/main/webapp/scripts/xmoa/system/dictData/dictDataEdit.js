var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

//让层自适应iframe
// parent.layer.iframeAuto(index);
//在父层弹出一个层
// parent.layer.msg('Hi, man', {shade: 0.3});
//给父页面传值
// parent.$('#parentIframe').text('我被改变了');
// parent.layer.tips('Look here', '#parentIframe', {time: 5000});
// parent.layer.close(index);

var dictDataEditInit = function () {
    $.get(root + "/dictData/getAllDictData.do", function (response) {
        var dictDataList = response.data;
        var parentSelect = $("#editForm select[name=parentId]");
        parentSelect.append("<option value=''>(无父级)</option>");
        var fid = $("#editForm input[name=fid]").val();
        var dictDataParentId = $("#editForm input[name=dictDataParentId]").val();
        for (var i in dictDataList) {
            var selected = '';
            var disabled = '';
            if (dictDataList[i].FID == dictDataParentId)
                selected = ' selected="selected"';
            else if (dictDataList[i].FID == fid)
                disabled = ' disabled="disabled"';
            parentSelect.append('<option ' + selected + disabled + ' value="' + dictDataList[i].FID + '">' + dictDataList[i].NAME + '(' + dictDataList[i].CODE + ')' + '</option>')
        }

        $(".select2").select2({language: "zh-CN"});
    })

}


var submitForm = function (type) {

    var bootstrapValidator = $("#editForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid())
        return;

    var dictDataData = $('#editForm').serialize();
    var url = root + "/dictData/" + type + "DictData.do";
    $.post(url, dictDataData, function (response) {
        if (response.code == 1) {
            parent.layer.msg('操作成功', {time: 1500, icon: 1});
            parent.dictDataManage.refreshDictData();
            parent.layer.close(index);
        } else {
            if (response.msg)
                parent.layer.msg(response.msg, {time: 1500, icon: 2});
            else
                parent.layer.msg("保存时发生错误.", {time: 1500, icon: 5});
        }
    })

}

var cancelForm = function () {
    parent.layer.close(index);
}

$(function () {
    dictDataEditInit();
    $('#editForm').bootstrapValidator({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: '基础代码名不能为空.'
                    },
                    remote: {
                        message: "基础代码名已存在.",
                        url: root + "/dictData/checkDictDataNameUnique.do",
                        data: function (validator, $field, value) {
                            return {
                                fid: $('#editForm input[name="fid"]').val(),
                                name: $('#editForm input[name="name"]').val()
                            }
                        },
                        delay: 2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'
                    }
                }
            },
            code: {
                validators: {
                    notEmpty: {
                        message: '基础代码编码不能为空'
                    },
                    remote: {
                        message: "基础代码编码已存在.",
                        url: root + "/dictData/checkDictDataCodeUnique.do",
                        data: function (validator, $field, value) {
                            return {
                                fid: $('#editForm input[name="fid"]').val(),
                                code: $('#editForm input[name="code"]').val()
                            }
                        },
                        delay: 2000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'
                    }
                }
            }
        }
    })
})