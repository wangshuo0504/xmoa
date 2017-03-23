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
            <input type="hidden" name="fid" value="${dictData.fid}">
            <div class="box-body">
                <div class="form-group">
                    <label for="dictDataName" class="col-sm-2 control-label">基础代码名:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="dictDataName" name="name" placeholder="基础代码名"
                               value="${dictData.name}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="dictDataCode" class="col-sm-2 control-label">基础代码编码:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="dictDataCode" name="code" placeholder="基础代码编码"
                               value="${dictData.code}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">父级基础代码:</label>
                    <div class="col-sm-10 input-text">
                        <input type="hidden" name="dictDataParentId" value="${dictDataParentId}">
                        <select class="form-control select2" style="width: 100%;" name="parentId">

                        </select>

                    </div>
                </div>
                <div class="form-group">
                    <label for="dictDataUrl" class="col-sm-2 control-label">基础代码URL:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="dictDataUrl" name="url" placeholder="基础代码URL"
                               value="${dictData.url}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="dictDataIocn" class="col-sm-2 control-label">基础代码图标:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="dictDataIocn" name="icon" placeholder="基础代码图标"
                               value="${dictData.icon}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="remark" class="col-sm-2 control-label">备注:</label>
                    <div class="col-sm-10 input-text">
                        <input type="text" class="form-control" id="remark" name="remark" placeholder="备注信息"
                               value="${dictData.remark}">
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
<script type="text/javascript" src="${root}/scripts/xmoa/system/dictData/dictDataEdit.js"></script>
</body>
</html>
