<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>您访问的页面不存在</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<%@ include file="/view/common/common.jsp"%>
	<META http-equiv=refresh content=3;URL=${root}>
	<link type="text/css" rel="stylesheet" media="all" href="${root}/view/common/404/styles/404.css" />
</head>
<body>
<DIV class=bg>
	<DIV class=cont>
		<DIV class=c1><IMG class=img1 src="${root}/view/common/404/image/01.png"></DIV>
		<H2>Sorry…系统出了点问题，请联系管理员</H2>
		<DIV class=c2>
			<A class=home href="${root}">返回首页</A>
		</DIV>
		<DIV class=c3>您可能输入了错误的网址，或者该网页已删除或移动</DIV>
	</DIV>
</DIV>
</body>
</html>