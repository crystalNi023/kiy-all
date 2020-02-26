<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style>
	.color{background: #DEDEBE;color: black}
	span {background: #F2E6E6}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>		
</head>
<body>
<h1>login   register</h1>
<h4 class="color">注册账号</h4>
	<form action="/user/register" method="post">
		手机号：<input id="phone" type="text" name="username"><br>
		密码：   <input type="text" name="password"><br>
		验证码：   <input  type="text" name="code"><input type="button" id="sendV" value="发送验证码"/><br>
			  <input type="submit" value="提交">	
	</form>	
	<span>${msg}</span>
<h4 class="color">密码重置</h4>
	<form action="/user/resetPassword" method="post">
		手机号：<input type="text" name="username"><br>
		密码：   <input type="text" name="password"><br>
		验证码：   <input  type="text" name="code"><br>
			  <input type="submit" value="重置密码">	
	</form>	
	<span>${msg}</span>
<h4 class="color">登录</h4>
	<form action="/user/login" method="post">
		手机号：<input type="text" name="username"><br>
		密码：   <input type="text" name="password"><br>
			  <input type="submit" value="登录">	
	</form>	
	<span>${msg}</span>	
<h4 class="color">登出</h4>
	<form action="/user/logout" method="post">
			  <input type="submit" value="登出">	
	</form>	
	<span>${msg}</span>		
</body>
<script type="text/javascript">
$("#sendV").click(function(){
	var  phone=$("#phone").val();
	$.ajax({
		url : "/user/sendMobileCode",
		type : "POST",
		datatype : "json",
		data : {'phone':phone},
		async : false,
		success : function(data) {
		
		},
		error : function(data) {
			alert("请求服务器失败！");
		}
	});
});

</script>
</html>