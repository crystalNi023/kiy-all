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
<h4 class="color">添加流程</h4>
	<form action="/procedure/increase" method="post">
		项目ID：<input type="text" name="proId"><br>
		流程名称：<input type="text" name="name"><br>
		流程状态 <select name="status">
				<option value="1" selected="selected">未启动</option>
			</select><br>
		计划启动时间：<input type="text" name="procStartTime"><br>
		计划结束时间：<input type="text" name="procEndTime"><br>
		  <input type="submit" value="确认">	
	</form>	
	<span>${count}</span>
<h4 class="color">修改流程</h4>
	<form action="/procedure/modifyById" method="post">
		流程编号：<input type="text" name="id"><br>
		流程名称：<input type="text" name="name"><br>
					  <input type="submit" value="确认">	
	</form>	
	<span>${count}</span>
<h4 class="color">查询流程</h4>
	<form action="/procedure/queryById" method="post">
		流程编号：<input type="text" name="id"><br>
					  <input type="submit" value="确认">	
	</form>	
	<span>${procedure}</span>
<h4 class="color">移除流程</h4>
	<form action="/procedure/removeById" method="post">
	流程编号：<input type="text" name="id"><br>
			  <input type="submit" value="确认">	
	</form>	
	<span>${count}</span>
<h4 class="color">分页查询</h4>
	<form action="/procedure/queryList" method="post">
	项目id：<input type="text" name="proId"><br>
			  <input type="submit" value="确认">	
	</form>	
	<span>${jProcedures}</span>
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