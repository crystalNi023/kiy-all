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
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/moment.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/moment.zh-cn.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/ion.calendar.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/format.js" ></script>
<body>
<h1>login   register</h1>
<h4 class="color">添加项目</h4>
	<form action="/project/increase" method="post">
		客户ID：<input type="text" name="cusId"><br>
		类型：  <select name="type">
				<option value="1" selected="selected">基装</option>
				<option value="2">整装</option>
			</select><br>
		计划开工时间：<input type="text" name="planStartTime"><br>
		计划完成时间：<input type="text" name="planEndTime"><br>
		  <input type="submit" value="确认">	
	</form>	
	<span>${count}</span>
<h4 class="color">修改项目</h4>
	<form action="/project/modifyById" method="post">
		客户编号：<input type="text" name="id"><br>
		客户名称：<input type="text" name="name"><br>
		联系电话：   <input type="text" name="phone"><br>
		第二联系方式：<input type="text" name="mobile"><br>
		工程地址：   <input type="text" name="address"><br>
		合同签订时间：   <input  id="txt_calendar" type="text" name="signTime" class="repayDate form-control pro_time" ><br>
			  <input type="submit" value="确认">	
	</form>	
	<span>${count}</span>
<h4 class="color">查询客户</h4>
	<form action="/project/queryById" method="post">
		客户编号：<input type="text" name="id"><br>
					  <input type="submit" value="确认">	
	</form>	
	<span>${customer}</span>
<h4 class="color">移除客户</h4>
	<form action="/project/removeById" method="post">
	客户编号：<input type="text" name="id"><br>
			  <input type="submit" value="确认">	
	</form>	
	<span>${count}</span>
<h4 class="color">分页查询</h4>
	<form action="/project/queryList" method="post">
	客户姓名：<input type="text" name="customerName"><br>
	客户地址：<input type="text" name="address"><br>
			  <input type="submit" value="确认">	
	</form>	
	<span>${jProjects}</span>
</body>
<script type="text/javascript">
//日历控件
$(function(){
	$('#txt_calendar').each(function(){
		$(this).ionDatePicker({
			lang: 'zh-cn',
			format: 'YYYY-MM-DD'
		});
	});
});
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