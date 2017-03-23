<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/view/common/common.jsp" %>

    <link rel="stylesheet" href="${root}/styles/css/sort_list.css">
</head>
<body>
<div class="wrapper" style="padding-bottom: 55px">

        <div class="col-xs-12 sortPanel" >
            <ul id="userSortList" style="padding-right: 40px">
                <c:forEach var="user" items="${userList}" varStatus="status">
                    <li user-fid="${user.fid}">
                        <div class="info-box bg-aqua">
                            <span class="info-box-icon">${status.index+1}</span>

                            <div class="info-box-content">
                                <span class="info-box-number">${user.name}</span>
                            </div>
                            <!-- /.info-box-content -->
                        </div>

                    </li>
                </c:forEach>
            </ul>
        </div>

    <div class="bottom-box">
        <div class="box-footer" style="text-align: center">
            <button type="button" class="btn btn-warning" onclick="saveUserOrder()" style="width: 200px">保存排序</button>
        </div>
    </div>

</div>

<script type="text/javascript" src="${root}/scripts/xmoa/plugins/dragsort/jquery.dragsort-0.5.2.js"></script>
<!-- 本页面管理js -->
<script type="text/javascript" src="${root}/scripts/xmoa/system/user/userSort.js"></script>
</body>
</html>
