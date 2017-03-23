<%@ page import="com.zkxy.xmoa.util.PropertiesUtils" %>
<%@ page import="com.zkxy.xmoa.util.Tools" %>
<%@ page language="java" pageEncoding="utf-8"%>
<%  String root = request.getContextPath();
	request.setAttribute("root", root);
    request.setAttribute("mapRoot", PropertiesUtils.getConfigString("mapRoot"));//公司
    request.setAttribute("jsVersion", "0.0.1");
    request.setAttribute("isProd", (Boolean)Tools.isProductEnv());
%>
<script type="text/javascript">
var root='<%=root%>';
</script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>办公自动化系统</title>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">


<link type="text/css" rel="stylesheet" media="all" href="${root}/scripts/xmoa/plugins/bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" media="all" href="${root}/scripts/xmoa/plugins/bootstrapvalidator/css/bootstrapValidator.css" />
<link rel="stylesheet" href="${root}/scripts/xmoa/plugins/bootstrap-treeview/css/bootstrap-treeview.css">
<link rel="stylesheet" href="${root}/scripts/xmoa/plugins/font-awesome/css/font-awesome.min.css">
<!-- select2 css 需要放在AdminLTE.css之前 -->
<link rel="stylesheet" href="${root}/scripts/xmoa/plugins/select2/select2.css">
<!-- Theme style -->
<link rel="stylesheet" href="${root}/scripts/xmoa/plugins/adminLet/css/AdminLTE.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="${root}/scripts/xmoa/plugins/adminLet/css/skins/_all-skins.css">
<link rel="stylesheet" href="${root}/styles/css/head_nav.css">



<script type="text/javascript" src="${root}/scripts/xmoa/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="${root}/scripts/xmoa/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${root}/scripts/xmoa/plugins/bootstrapvalidator/js/bootstrapValidator.js"></script>
<script type="text/javascript" src="${root}/scripts/xmoa/plugins/bootstrapvalidator/js/language/zh_CN.js"></script>
<script type="text/javascript" src="${root}/scripts/xmoa/plugins/bootstrap-treeview/js/bootstrap-treeview.js"></script>

<!-- perfect-scrollbar -->
<link rel="stylesheet" href="${root}/scripts/xmoa/plugins/perfect-scrollbar/css/perfect-scrollbar.css">
<script src="${root}/scripts/xmoa/plugins/perfect-scrollbar/js/perfect-scrollbar.jquery.js"></script>

<!-- iCheck -->
<link rel="stylesheet" href="${root}/scripts/xmoa/plugins/iCheck/square/blue.css">
<script src="${root}/scripts/xmoa/plugins/iCheck/icheck.min.js"></script>

<!-- layer -->
<link rel="stylesheet" href="${root}/scripts/xmoa/plugins/layer2.1/layer/skin/layer.css">
<script src="${root}/scripts/xmoa/plugins/layer2.1/layer/layer.js"></script>

<!-- select2 -->
<script src="${root}/scripts/xmoa/plugins/select2/select2.js"></script>
<script src="${root}/scripts/xmoa/plugins/select2/i18n/zh-CN.js"></script>


<script type="text/javascript" src="${root}/scripts/xmoa/xmoa-paginate.js"></script>
<script type="text/javascript" src="${root}/scripts/xmoa/xmoa-common.js"></script>
<!-- 针对ie8的HTML5修复，且必须在这个位置 -->
<!--[if lt IE 9]>
<script src="../assets/js/ieFix/html5shiv.js"></script>
<script src="../assets/js/ieFix/respond.min.js"></script>
<![endif]-->


