var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

//让层自适应iframe
// parent.layer.iframeAuto(index);
//在父层弹出一个层
// parent.layer.msg('Hi, man', {shade: 0.3});
//给父页面传值
// parent.$('#parentIframe').text('我被改变了');
// parent.layer.tips('Look here', '#parentIframe', {time: 5000});
// parent.layer.close(index);

var actionRightEditInit = function () {
    $.get(root + "/module/getAllModule.do", function (response) {
        var actionRightList = response.data;
        var parentSelect = $("#editForm select[name=moduleId]");

        var fid = $("#editForm input[name=fid]").val();
        var actionRightModuleId = $("#editForm input[name=actionRightModuleId]").val();
        for (var i in actionRightList) {
            var selected = '';

            if (actionRightList[i].FID == actionRightModuleId)
                selected = ' selected="selected"';

            parentSelect.append('<option ' + selected  + ' value="' + actionRightList[i].FID + '">' + actionRightList[i].NAME + '(' + actionRightList[i].CODE + ')' + '</option>')
        }

        $(".select2").select2({language: "zh-CN"});
    })

}


var submitForm = function (type) {

    var bootstrapValidator = $("#editForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid())
        return;

    var actionRightData = $('#editForm').serialize();
    var url = root + "/actionRight/" + type + "ActionRight.do";
    $.post(url, actionRightData, function (response) {
        if (response.code == 1) {
            parent.layer.msg('操作成功', {time: 1500, icon: 1});
            parent.actionRightManage.refreshActionRight();
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
    actionRightEditInit();
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
                        message: '动作权限名不能为空.'
                    },
                    remote: {
                        message: "动作权限名已存在.",
                        url: root + "/actionRight/checkActionRightNameUnique.do",
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
                        message: '动作权限编码不能为空'
                    },
                    remote: {
                        message: "动作权限编码已存在.",
                        url: root + "/actionRight/checkActionRightCodeUnique.do",
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