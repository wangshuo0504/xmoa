<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${root}/scripts/traffic/plugins/bootstrap/css/bootstrap-select.css">
<div class="pull-right space-l no-print">
    <c:if test="${param.downloadBtn == true}">
        <a href="javascript:void(0)" class="btn btn-default" ng-click="downloadForm()">导出报表</a>
    </c:if>
    <a onclick="printPage()" ng-click="printPage()" class="btn btn-default space-l">打印报表</a>
</div>
<form class="form-inline no-print" id="paramForm">
    <div class="form-group" style="width: 180px">
        <select class="form-control selectpicker" data-live-search="true" id="areaCode" name="areaCode">
        <%--<c:if test="${param.allArea != false}">--%>
            <%--<option value="">所有区县</option>--%>
        <%--</c:if>--%>
            <option value="431202">鹤城区</option>

        </select>
    </div>
    <c:if test="${param.roadList == true}">
        <div class="form-group" style="width: 180px">
            <select class="form-control selectpicker" data-live-search="true" id="roadSelect" name="roadSelectName">
                <option value="">全辖区道路</option>
            </select>
        </div>
    </c:if>


    <c:if test="${param.roadCompare == true}">
        <span>VS</span>
        <div class="form-group" style="width: 180px">
            <select class="form-control selectpicker" data-live-search="true" id="roadSelectCompare" name="roadSelectNameCompare">
            </select>
        </div>
    </c:if>
    <c:if test="${param.roadJunctionList == true}">
        <div class="form-group" style="width: 180px">
            <select class="form-control selectpicker" data-live-search="true" id="roadJunctionSelect" name="roadJunctionSelectName">
                <option value="">全辖区所有路口</option>
            </select>
        </div>
    </c:if>

    <c:if test="${param.epRoadJunctionList == true}">
        <div class="form-group" style="width: 180px">
            <select class="form-control selectpicker" data-live-search="true" id="epRoadJunctionSelect" name="roadJunctionSelectName">
                <option value="">全辖区所有路口</option>
            </select>
        </div>
    </c:if>

    <c:if test="${param.areaTownSelect == true}">
        <div class="form-group" style="width: 180px">
            <select class="form-control selectpicker" data-live-search="true" id="areaTownCode" name="areaTownCode">
                <option value="">全辖区所有镇街</option>
            </select>
        </div>
    </c:if>

    <c:if test="${param.roadJunctionCompare == true}">
        <span>VS</span>
        <div class="form-group" style="width: 180px">
            <select class="form-control selectpicker" data-live-search="true" id="roadJunctionSelectCompare" name="roadJunctionSelectNameCompare">
            </select>
        </div>
    </c:if>
    <c:if test="${param.violationList == true}">
        <div class="form-group">
            <select class="form-control" id="violationCode">
            </select>
        </div>
    </c:if>
    <c:if test="${param.dateTypeInput != false}">
        <div class="form-group dateTypeSwitch">
            <select class="form-control" id="time-type" name="timeType">
                <option value="day" selected="">按日统计</option>
                <option value="week">按周统计</option>
                <option value="month">按月统计</option>
                <option value="year">按年统计</option>
            </select>
        </div>
        <div class="form-group dateTypeInput">
        </div>
    </c:if>
    <c:if test="${param.dateContrast == true}">
        <div class="form-group dateContrast" id="dateContrast"></div>
    </c:if>

    <div class="form-group">
        <button type="button" class="btn btn-primary" id="btnSearch" ng-click="dateSectionSubmit()">分析</button>
    </div>
</form>
<script type="text/javascript" src="${root}/scripts/xmoa/plugins/bootstrap/bootstrap-select.js"></script>
<script type="text/javascript" src="${root}/scripts/xmoa/paramForm.js"></script>
