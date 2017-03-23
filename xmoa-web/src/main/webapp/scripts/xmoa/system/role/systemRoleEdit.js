var index = parent.layer.getFrameIndex(window.name); //获取窗口索引

//让层自适应iframe
// parent.layer.iframeAuto(index);
//在父层弹出一个层
// parent.layer.msg('Hi, man', {shade: 0.3});
//给父页面传值
// parent.$('#parentIframe').text('我被改变了');
// parent.layer.tips('Look here', '#parentIframe', {time: 5000});
// parent.layer.close(index);

var systemRoleEditInit = function () {

}


var submitForm = function (type) {

    var bootstrapValidator = $("#editForm").data('bootstrapValidator');
    bootstrapValidator.validate();
    if (!bootstrapValidator.isValid())
        return;

    var systemRoleData = $('#editForm').serialize();
    var url = root + "/systemRole/" + type + "SystemRole.do";
    $.post(url, systemRoleData, function (response) {
        if (response.code == 1) {
            parent.layer.msg('操作成功', {time: 1500, icon: 1});
            parent.systemRoleManage.refreshSystemRole();
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
    systemRoleEditInit();
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
                        message: '系统角色名不能为空.'
                    },
                    remote: {
                        message: "系统角色名已存在.",
                        url: root + "/systemRole/checkSystemRoleNameUnique.do",
                        data: function (validator, $field, value) {
                            return {
                                fid: $('#editForm input[name="fid"]').val(),
                                name: $('#editForm input[name="name"]').val()
                            }
                        },
                        delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'
                    }
                }
            },
            code: {
                validators: {
                    notEmpty: {
                        message: '系统角色编码不能为空'
                    },
                    remote: {
                        message: "系统角色编码已存在.",
                        url: root + "/systemRole/checkSystemRoleCodeUnique.do",
                        data: function (validator, $field, value) {
                            return {
                                fid: $('#editForm input[name="fid"]').val(),
                                code: $('#editForm input[name="code"]').val()
                            }
                        },
                        delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置1秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST'
                    }
                }
            }
        }
    })
})