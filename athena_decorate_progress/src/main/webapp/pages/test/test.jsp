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
<h4 class="color">添加用户</h4>
	<form action="/test/add" method="post">
		姓名：<input type="text" name="name"><br>
		类型：<select name="type">
				<option value="1">客户</option>
				<option selected="selected" value="2">装修队</option>
			</select><br>
			<input type="submit" value="提交">	
	</form>
<h4 class="color">用户查询</h4>
	<form action="/user/queryList" method="post">
		用户名：<input type="text" name="username"><br>
		类型：<input type="text" name="type"><br>
			<input type="submit" value="查询">	
	</form>
	<span>${jUser}</span>
<h4 class="color">添加角色</h4>	
	<form action="/role/increase" method="post">
		名称：<input type="text" name="name"><br>
		备注：<input type="text" name="remark">
			<input type="submit" value="提交">	
	</form>
<h4 class="color">移除角色</h4>	
	<form action="/role/removeById" method="post">
		角色id：<input type="text" name="id"><br>
			 <input type="submit" value="提交">	
	</form>
<h4 class="color">修改角色</h4>	
	<form action="/role/modifyById" method="post">
		角色id：<input type="text" name="id"><br>
		名称：    <input type="text" name="name"><br>
			  <input type="submit" value="提交">	
	</form>	
	<span>${count}</span>
<h4 class="color">id查询角色</h4>	
	<form action="/role/queryById" method="post">
		角色id：<input type="text" name="id"><br>
			  <input type="submit" value="提交">	
	</form>	
	<span>${jRole}</span>
<h4 class="color">分页查询角色</h4>
	<form action="/role/queryList" method="post">
		名称：<input type="text" name="name"><br>
			  <input type="submit" value="提交">	
	</form>	
	<span>${jRoles}</span>
<h4 class="color">添加短信客户</h4>
	<form action="/sms/increaseClient" method="post">
		姓名：<input type="text" name="name"><br>
		号码：<input type="text" name="phone"><br>
		备注：<input type="text" name="remark"><br>
			  <input type="submit" value="提交">	
	</form>	
	<span>${count}</span>
<h4 class="color">查询短信客户</h4>
	<form action="/sms/queryClientById" method="post">
		id： <input type="text" name="id"><br>
		    <input type="submit" value="提交">	
	</form>	
	<span>${jSMSClient}</span>	
<h4 class="color">查询多条短信客户</h4>
	<form action="/sms/queryListClient" method="post">
		姓名： <input type="text" name="name"><br>
		电话： <input type="text" name="phone"><br>
		    <input type="submit" value="提交">	
	</form>	
	<span>${jListSms}</span>			
<h4 class="color">发送短信</h4>
	<form action="/sms/sendOneMsg" method="post">
		 id：<input type="text" name="id"><br>
		姓名：<input type="text" name="name"><br>
		电话：<input type="text" name="phone"><br>
		内容：<textarea rows="3" cols="21" name="content"></textarea><br>
			  <input type="submit" value="提交">	
	</form>	
	<span>${count}</span>
<h4 class="color">增加短信模板</h4>
	<form action="/sms/increaseTemplate" method="post">
		类型：<select name="type">
				<option value="0" selected="selected">通知短信</option>
				<option value="4">验证码短信</option>
				<option value="5">会员服务短信</option>
			</select><br>
		短信签名：<input type="text" name="autograph" placeholder="建议使用公司名/APP名/网站名"><br>
		模板名称：<input type="text" name="templateName"><br>
		内容：<input type="text" name="content" placeholder="您的注册验证码是{1}"><br>
			  <input type="submit" value="提交">	
	</form>	
	<span>${resultMap}</span>	
<h4 class="color">查询短信模板</h4>
	<form action="/sms/queryListTemplate" method="post">
		签名：<input type="text" name="autograph"><br>
		模板名称：<input type="text" name="templateName"><br>
			  <input type="submit" value="提交">	
	</form>	
	<span>${jTemplateList}</span>
<h4 class="color">修改通话后结果</h4>
	<form action="/sms/updateClientByPhone" method="post">
		状态：<input type="text" name="isContact"><br>
		备注：<input type="text" name="remark1"><br>
		电话：<input type="text" name="phone"><br>
			  <input type="submit" value="提交">	
	</form>	
	<span>${count}</span>		
</body>
</html>