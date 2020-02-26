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
</head>
<body>
<h1>test server!</h1>
<h4 class="color">角色查询</h4>
	<form action="/test/add" method="post">
		姓名：<input type="text" name="name"><br>
		类型：<select name="type">
				<option value="1">客户</option>
				<option selected="selected" value="2">装修队</option>
			</select><br>
			<input type="submit" value="提交">	
	</form>
</body>
</html>