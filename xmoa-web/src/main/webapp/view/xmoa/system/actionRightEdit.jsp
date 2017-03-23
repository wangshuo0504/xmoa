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
            <input type="hidden" name="fid" value="${actionRight.fid}">
            <div class="box-body">
                <div class="form-group">
                    <label for="actionRightName" class="col-sm-2 control-label">权限名称:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="actionRightName" name="name" placeholder="动作权限名"
                               value="${actionRight.name}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="actionRightCode" class="col-sm-2 control-label">权限编码:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="actionRightCode" name="code" placeholder="动作权限编码"
                               value="${actionRight.code}">
                    </div>
                </div>

                <div class="form-group">
                    <label  class="col-sm-2 control-label">所属资源:</label>
                    <div class="col-sm-10 input-text">
                        <input type="hidden" name="actionRightModuleId" value="${actionRightModuleId}">
                        <select class="form-control select2" style="width: 100%;" name="moduleId">

                        </select>

                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-2 control-label">权限描述:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="description" name="description" placeholder="权限描述"
                               value="${actionRight.description}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="actionRightCode" class="col-sm-2 control-label">备注:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="remark" name="remark" placeholder="备注信息"
                               value="${actionRight.remark}">
                    </div>
                </div>
            </div>
            <!-- /.box-footer -->
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
<script type="text/javascript" src="${root}/scripts/xmoa/system/actionRight/actionRightEdit.js"></script>
</body>
</html>
