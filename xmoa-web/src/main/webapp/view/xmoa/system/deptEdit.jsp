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
    <form class="form-horizontal" id="editForm">
        <!-- 中间内容部分 -->
        <section class="content">

            <input type="hidden" name="fid" value="${dept.fid}">
            <div class="box-body">
                <div class="form-group">

                    <label for="deptName" class="col-sm-2 control-label">单位名:</label>

                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="deptName" name="name" placeholder="单位名"
                               value="${dept.name}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="deptCode" class="col-sm-2 control-label">单位编码:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="deptCode" name="code" placeholder="单位编码"
                               value="${dept.code}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="deptCode" class="col-sm-2 control-label">单位简称:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="deptShortName" name="shortName" placeholder="单位简称"
                               value="${dept.shortName}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="deptCode" class="col-sm-2 control-label">父级单位:</label>
                    <div class="col-sm-10 input-text">
                        <input type="hidden" name="deptParentId" value="${deptParentId}">
                        <select class="form-control select2" style="width: 100%;" name="parentId">

                        </select>

                    </div>
                </div>
                <div class="form-group">
                    <label for="deptCode" class="col-sm-2 control-label">单位邮箱:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="deptMail" name="deptMail" placeholder="单位邮箱"
                               value="${dept.deptMail}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="deptCode" class="col-sm-2 control-label">单位领导:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="deptLeadId" name="leadId" placeholder="单位领导"
                               value="${dept.leadId}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="deptCode" class="col-sm-2 control-label">管理员:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="deptAdminId" name="adminId" placeholder="单位管理员"
                               value="${dept.adminId}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="deptCode" class="col-sm-2 control-label">备注:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="remark" name="remark" placeholder="备注信息"
                               value="${dept.remark}">
                    </div>
                </div>
            </div>
            <!-- /.box-body -->

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
<script type="text/javascript" src="${root}/scripts/xmoa/system/dept/deptEdit.js"></script>
</body>
</html>
