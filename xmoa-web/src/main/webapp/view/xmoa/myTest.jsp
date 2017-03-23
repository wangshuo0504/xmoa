<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <script type="text/javascript"
            src="${root}/scripts/xmoa/plugins/jQuery/jquery-1.11.1/jquery.min.js"></script>
    <style TYPE="text/css">
        .wrap {
            padding: 10px 0 0 10px;
            width: 460px;
            overflow: hidden;
        }

        .wrap .item {

            position: relative;
            float: left;
            margin-left: -2px;
            margin-top: -2px;
            width: 149px;
            height: 149px;

            text-align: center;
            border: 3px solid #2e2e2e;
        }

        .wrap .item .num {
            position: relative;
            float: left;
            width: 45px;
            height: 45px;
            line-height: 45px;
            text-align: center;
            border: 2px solid #ccc;
        }

        .wrap .item .num:hover {
            z-index: 2;
            border-color: #f00;
        }

        .form-control {
            width: 43px;
            height: 43px;
            font-size: 22px;
            text-align: center;
        }

        .original {
            color: red;
        }
    </style>
</head>
<body>
<form id="sudoku" style=" float: left;width: 600px">
    <div class="wrap">
        <%--1--%>
        <div class="item" >
            <div class="num"><input type="text" class="form-control" name="name11" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name12" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name13" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name21" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name22" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name23" value="2"></div>
            <div class="num"><input type="text" class="form-control" name="name31" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name32" value="6"></div>
            <div class="num"><input type="text" class="form-control" name="name33" value=""></div>
        </div>
            <%--2--%>
        <div class="item">
            <div class="num"><input type="text" class="form-control" name="name14" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name15" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name16" value="6"></div>
            <div class="num"><input type="text" class="form-control" name="name24" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name25" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name26" value="7"></div>
            <div class="num"><input type="text" class="form-control" name="name34" value="5"></div>
            <div class="num"><input type="text" class="form-control" name="name35" value="3"></div>
            <div class="num"><input type="text" class="form-control" name="name36" value=""></div>
        </div>
            <%--3--%>
        <div class="item ">
            <div class="num"><input type="text" class="form-control" name="name17" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name18" value="7"></div>
            <div class="num"><input type="text" class="form-control" name="name19" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name27" value="3"></div>
            <div class="num"><input type="text" class="form-control" name="name28" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name29" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name37" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name38" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name39" value="1"></div>
        </div>
            <%--4--%>
        <div class="item">
            <div class="num"><input type="text" class="form-control" name="name41" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name42" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name43" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name51" value="5"></div>
            <div class="num"><input type="text" class="form-control" name="name52" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name53" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name61" value="3"></div>
            <div class="num"><input type="text" class="form-control" name="name62" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name63" value="9"></div>
        </div>
            <%--5--%>
        <div class="item ">
            <div class="num"><input type="text" class="form-control" name="name44" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name45" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name46" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name54" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name55" value="4"></div>
            <div class="num"><input type="text" class="form-control" name="name56" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name64" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name65" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name66" value=""></div>
        </div>
            <%--6--%>
        <div class="item ">
            <div class="num"><input type="text" class="form-control" name="name47" value="8"></div>
            <div class="num"><input type="text" class="form-control" name="name48" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name49" value="7"></div>
            <div class="num"><input type="text" class="form-control" name="name57" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name58" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name59" value="3"></div>
            <div class="num"><input type="text" class="form-control" name="name67" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name68" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name69" value=""></div>
        </div>
            <%--7--%>
        <div class="item ">
            <div class="num"><input type="text" class="form-control" name="name71" value="9"></div>
            <div class="num"><input type="text" class="form-control" name="name72" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name73" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name81" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name82" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name83" value="4"></div>
            <div class="num"><input type="text" class="form-control" name="name91" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name92" value="2"></div>
            <div class="num"><input type="text" class="form-control" name="name93" value=""></div>
        </div>
            <%--8--%>
        <div class="item ">
            <div class="num"><input type="text" class="form-control" name="name74" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name75" value="2"></div>
            <div class="num"><input type="text" class="form-control" name="name76" value="5"></div>
            <div class="num"><input type="text" class="form-control" name="name84" value="1"></div>
            <div class="num"><input type="text" class="form-control" name="name85" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name86" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name94" value="6"></div>
            <div class="num"><input type="text" class="form-control" name="name95" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name96" value=""></div>
        </div>
            <%--9--%>
        <div class="item">
            <div class="num"><input type="text" class="form-control" name="name77" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name78" value="1"></div>
            <div class="num"><input type="text" class="form-control" name="name79" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name87" value="2"></div>
            <div class="num"><input type="text" class="form-control" name="name88" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name89" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name97" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name98" value=""></div>
            <div class="num"><input type="text" class="form-control" name="name99" value=""></div>
        </div>

    </div>

<div class="form-group">
    <button type="button" class="btn btn-primary" id="btnSearch" onclick="calculateResult();">计算结果</button>
    <button type="reset" class="btn btn-primary" >重置</button>
    <button type="button" class="btn btn-primary" onclick="clearData();">清空</button>
    <button type="button" class="btn btn-primary" onclick="clearDataNotOriginal();">清空非原始数据</button>
    <button type="button" class="btn btn-primary" onclick="markData();">标记为原始数据</button>

    <button type="button" class="btn btn-primary" onclick="generateSudoku();">生成一题</button>
    <button type="button" class="btn btn-primary" onclick="checkAnswerUniqueButton();">检测答案唯一性</button>

</div>

</form>
<div id="msg">信息框</div>
<script type="text/javascript" src="${root}/scripts/xmoa/sudoku.js"></script>
</body>
</html>
