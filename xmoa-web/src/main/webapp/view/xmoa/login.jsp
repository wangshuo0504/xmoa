<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/view/common/common.jsp"%>
</head>
<body class="hold-transition login-page">
<div class="login-box" style="font-family:  arial, 'Microsoft Yahei', 微软雅黑;">
	<div class="login-logo">
		<a href="#"><b>办公自动化系统</b></a>
	</div>

	<div class="login-box-body" style="margin: 0px ">
		<p class="login-box-msg">用户登录</p>

		<form action="${root}/login/login.do" method="post">
			<div class="form-group has-feedback">
				<input type="text" class="form-control" placeholder="账号" name="userName">
				<span class="glyphicon glyphicon-user form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<input type="password" class="form-control" placeholder="密码" name="password">
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<input type="text" class="form-control" placeholder="验证码" name="password" style="width: 180px">
				<span class="glyphicon glyphicon-th form-control-feedback" style="right: 140px; "></span>
				<span class="form-control-feedback" style="    height: 34px; line-height: 34px;background-color: #ff9d02;color: #fff;width: 130px;text-align: center;font-size: 20px;">XD34F</span>
			</div>


			<div class="row" style="margin-top: 40px">
				<div class="col-xs-8">
					<div class="checkbox icheck">
						<label>
							<input type="checkbox"> 记住密码
						</label>
					</div>
				</div>
				<!-- /.col -->
				<div class="col-xs-4">
					<button type="submit" class="btn btn-primary btn-block btn-flat">登 录</button>
				</div>
				<!-- /.col -->
			</div>
		</form>

		<!-- /.social-auth-links -->

		<a href="#">忘记密码?</a><br>


	</div>
	<!-- /.login-box-body -->
</div>

<script>
    $(function () {
        $('input[type=checkbox]').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
</script>
</body>
</html>
