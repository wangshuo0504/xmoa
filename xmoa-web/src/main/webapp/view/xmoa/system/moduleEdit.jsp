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
            <input type="hidden" name="fid" value="${module.fid}">
            <div class="box-body">
                <div class="form-group">
                    <label for="moduleName" class="col-sm-2 control-label">资源名:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="moduleName" name="name" placeholder="资源名"
                               value="${module.name}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="moduleCode" class="col-sm-2 control-label">资源编码:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="moduleCode" name="code" placeholder="资源编码"
                               value="${module.code}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">父级资源:</label>
                    <div class="col-sm-10 input-text">
                        <input type="hidden" name="moduleParentId" value="${moduleParentId}">
                        <select class="form-control select2" style="width: 100%;" name="parentId">

                        </select>

                    </div>
                </div>
                <div class="form-group">
                    <label for="moduleUrl" class="col-sm-2 control-label">资源URL:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="moduleUrl" name="url" placeholder="资源URL"
                               value="${module.url}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="moduleIocn" class="col-sm-2 control-label">资源图标:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="moduleIocn" name="icon" placeholder="资源图标"
                               value="${module.icon}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="remark" class="col-sm-2 control-label">备注:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="remark" name="remark" placeholder="备注信息"
                               value="${module.remark}">
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
<script type="text/javascript" src="${root}/scripts/xmoa/system/module/moduleEdit.js"></script>
</body>
</html>
