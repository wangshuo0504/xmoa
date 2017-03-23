<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/view/common/common.jsp" %>
    <style type="text/css">
        .form-group .input-text {
            display: inline-block;
            width: 80%;
        }

        .form-group .control-label {
            width: 15%;
            text-align: right;
            float: left;
            margin-top: 5px;
        }

        .box-footer .btn {
            width: 150px;
        }

        .box-footer {
            padding: 10px 150px 20px 150px;
        }

        .bottom-box {
            position: absolute;
            width: 100%;
            bottom: 0px;
        }
    </style>
</head>
<body>
<div class="wrapper">
    <form class="form-horizontal" id="editForm" style="height: 100%">
        <!-- 中间内容部分 -->
        <section class="content">

            <div class="box-body">
                <div class="form-group">

                    <label for="user_userName" class="col-sm-2 control-label">登录账号:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="user_userName" name="userName" placeholder="登录账号">
                    </div>
                </div>

                <div class="form-group">
                    <label for="user_name" class="col-sm-2 control-label">姓名:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="user_name" name="name" placeholder="姓名">
                    </div>
                </div>
                <div class="form-group">
                    <label for="user_deptId" class="col-sm-2 control-label">所属单位:</label>
                    <div class="col-sm-10 input-text">
                        <input type="hidden" name="userDeptId" value="${userDeptId}">
                        <select class="form-control select2" style="width: 100%;" name="deptId" id="user_deptId">
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="user_phone" class="col-sm-2 control-label">手机:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="user_phone" name="phone" placeholder="手机号码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="user_mail" class="col-sm-2 control-label">邮箱:</label>
                    <div class="col-sm-10 input-text">
                        <input type="email" class="form-control" id="user_mail" name="mail" placeholder="邮箱">
                    </div>
                </div>

            </div>


        </section>

        <div class="bottom-box">
            <div class="box-footer">
                <button type="button" class="btn btn-primary" onclick="submitForm(${editType})">确认</button>
                <button type="button" class="btn btn-default  pull-right" onclick="cancelForm()">取消</button>

            </div>
        </div>
    </form>

</div>

<!-- 本页面管理js -->
<script type="text/javascript" src="${root}/scripts/xmoa/plugins/bootstrapvalidator/js/validator/notEmpty.js"></script>
<script type="text/javascript" src="${root}/scripts/xmoa/plugins/bootstrapvalidator/js/validator/remote.js"></script>
<script type="text/javascript" src="${root}/scripts/xmoa/system/user/userEdit.js"></script>
</body>
</html>
