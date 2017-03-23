<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/view/common/common.jsp" %>
</head>
<body class="sidebar-mini skin-blue-light" style="min-width: 400px !important;">
<div class="wrapper">
    <!--载入头部-->
    <%@ include file="/view/xmoa/header.jsp" %>
    <!--载入菜单-->
    <%@ include file="/view/xmoa/menu.jsp" %>
    <!-- 中间内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <%--<section class="content-header">--%>
        <%--</section>--%>

        <section class="content">
            <div class="row">
                <div class="col-sm-12">
                    <div style="    height: 56px;border: 1px solid #ddd; margin-bottom: 15px;background-color: #fff;">
                        <div class="box-tools">
                            <div class="input-group" style="width: 350px;margin: 10px 15px;">
                                <input type="text" name="table_search" class="form-control"
                                       placeholder="搜索关键字" style="z-index: 3;" id="systemRoleQuery">
                                <div class="input-group-btn">
                                    <button type="button" class="btn  bg-teal" onclick="systemRoleManage.querySystemRole()"><i class="fa fa-search"></i></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Small boxes (Stat box) -->
            <div class="row">

                <div class="col-sm-12">
                    <div class="box" style="border-top: 3px solid #428BCA;margin-bottom: 0px">
                        <div class="box-header" style="padding-bottom: 0px">
                            <div class="columns columns-left btn-group pull-left tool-button-box">
                                <button class="btn btn-default" type="button" name="create" title="新增">
                                    <i class="fa fa-plus">&nbsp;新增</i>
                                </button>
                                <button class="btn btn-default" type="button" name="edit" title="编辑">
                                    <i class="fa fa-edit">&nbsp;编辑</i>
                                </button>
                                <button class="btn btn-default" type="button" name="delete" title="删除">
                                    <i class="fa fa-trash-o">&nbsp;删除</i>
                                </button>
                                <button class="btn btn-default" type="button" name="refresh" title="排序">
                                    <i class="fa fa-refresh">&nbsp;刷新</i>
                                </button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="tab-container" style="min-height: 431px">
                                <table id="systemRoleTable" class="table table-bordered table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" id="checkAll"/></th>
                                        <th width="25%">系统角色名称</th>
                                        <th>系统角色编码</th>
                                        <th>备注</th>

                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                            <div class="row" style="margin-top: 10px">
                                <div class="col-sm-5">
                                    <div class="dataTables_info" id="systemRole_paginator_info">

                                    </div>
                                </div>
                                <div class="col-sm-7">
                                    <div class="dataTables_paginate" id="systemRole_paginator" style="text-align: right">

                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>
        </section>

    </div>

    <!--载入尾部-->
    <%@ include file="/view/xmoa/footer.jsp" %>
</div>

<!-- AdminLTE App -->
<script type="text/javascript" src="${root}/scripts/xmoa/plugins/adminLet/js/app.js"></script>
<!-- 本页面管理js -->
<script type="text/javascript" src="${root}/scripts/xmoa/system/role/systemRoleManage.js"></script>
</body>
</html>
