<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/view/common/common.jsp" %>
    <style type="text/css">
        .form-group .input-text {
            display: inline-block;
            min-width: 300px;
            padding-left: 30px;
        }

        .form-group .control-label {
            min-width: 80px;
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

        .part-form .form-group {
            min-width: 400px;
            margin-left: 0px;
            margin-right: 0px;
        }

        .help-block {
            padding-left: 130px;
        }
    </style>
</head>
<body>
<form class="form-horizontal" id="editForm" style="height: 100%">
    <div class="wrapper">

        <!-- 中间内容部分 -->
        <section class="content">


            <input type="hidden" name="fid" value="${user.fid}">
            <div class="box-body" style="padding-left: -15px;padding-right: 30px">
                <div class="col-xs-6 part-form">
                    <div class="form-group">

                        <label for="user_userName" class="control-label">登录账号:</label>

                        <div class="input-text">
                            <input type="text" class="form-control" id="user_userName" name="userName"
                                   placeholder="登录账号" value="${user.userName}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="user_name" class="control-label">姓名:</label>
                        <div class="input-text">
                            <input type="text" class="form-control" id="user_name" name="name" placeholder="姓名"
                                   value="${user.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user_deptId" class="control-label">所属单位:</label>
                        <div class="input-text">
                            <input type="hidden" name="userDeptId" value="${userDeptId}">
                            <select class="form-control select2" style="width: 100%;" name="deptId"
                                    id="user_deptId">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user_phone" class="control-label">手机:</label>
                        <div class="input-text">
                            <input type="text" class="form-control" id="user_phone" name="phone" placeholder="手机号码"
                                   value="${user.phone}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user_mail" class="control-label">邮箱:</label>
                        <div class="input-text">
                            <input type="email" class="form-control" id="user_mail" name="mail" placeholder="邮箱"
                                   value="${user.mail}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label">状态:</label>
                        <div class="input-text">
                            <select class="form-control" style="width: 100%;" name="status">
                                <c:forEach var="dictData" items="${user_status}">
                                    <option value="${dictData.code}" <c:if
                                            test="${dictData.code==user.status}"> selected="selected" </c:if> >${dictData.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6 part-form">
                    <div class="form-group">

                        <label for="user_userName" class="control-label">职务:</label>

                        <div class="input-text">
                            <input type="text" class="form-control" name="duty"
                                   placeholder="职务" value="${user.duty}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="user_name" class="control-label">传真:</label>
                        <div class="input-text">
                            <input type="text" class="form-control" name="fax" placeholder="传真"
                                   value="${user.fax}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user_deptId" class="control-label">办公电话:</label>
                        <div class="input-text">
                            <input type="text" class="form-control" name="officeTel" placeholder="办公电话"
                                   value="${user.officeTel}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user_phone" class=" control-label">办公地址:</label>
                        <div class="input-text">
                            <input type="text" class="form-control" name="officeAddr" placeholder="办公地址"
                                   value="${user.officeAddr}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user_mail" class="control-label">家庭电话:</label>
                        <div class="input-text">
                            <input type="text" class="form-control" name="homeTel" placeholder="家庭电话"
                                   value="${user.homeTel}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user_mail" class="control-label">家庭地址:</label>
                        <div class="input-text">
                            <input type="text" class="form-control" name="homeAddr" placeholder="家庭地址"
                                   value="${user.homeAddr}">
                        </div>
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

    </div>
</form>
<!-- 本页面管理js -->
<script type="text/javascript" src="${root}/scripts/xmoa/plugins/bootstrapvalidator/js/validator/notEmpty.js"></script>
<script type="text/javascript" src="${root}/scripts/xmoa/plugins/bootstrapvalidator/js/validator/remote.js"></script>
<script type="text/javascript" src="${root}/scripts/xmoa/system/user/userEdit.js"></script>
</body>
</html>
