<%@page pageEncoding="utf-8" %>
<%@ include file="/view/common/common.jsp"%>
<style type="text/css">
#errorContent{
    top:-200px;
    position: relative;
    font-size: 30px;
    color:red;
    text-align: center;
}
.errorContentContainer img{
    width: 100%;
    height: 100%;
    max-width: 900px;
    max-height: 400px;
}
</style>
<div align="center" class="errorContentContainer">
    <img src="${root}/view/common/404/image/01.png">
    <div id="errorContent" class='test'>
        ${errorMsg}!
    </div>
</div>