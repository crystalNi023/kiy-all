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
<h4 class="color">添加流程进度</h4>
	<form action="/progress/increase" method="post">
		流程ID：   <input type="text" name="procId"><br>
		流程进度：<input type="text" name="schedule"><br>
		流程类型：<input type="text" name="type"><br>
		备注：        <input type="text" name="remark"><br>
		  <input type="submit" value="确认">	
	</form>	
	<span>${count}</span>
<h4 class="color">修改流程进度</h4>
	<form action="/progress/modifyById" method="post">
		流程进度编号：<input type="text" name="id"><br>
		流程进度：<input type="text" name="schedule"><br>
		流程进度：<input type="text" name="remark"><br>
					  <input type="submit" value="确认">	
	</form>	
	<span>${count}</span>
<h4 class="color">查询流程进度</h4>
	<form action="/progress/queryById" method="post">
		流程进度编号：<input type="text" name="id"><br>
					  <input type="submit" value="确认">	
	</form>	
	<span>${progress}</span>
<h4 class="color">移除流程进度</h4>
	<form action="/progress/removeById" method="post">
	流程进度编号：<input type="text" name="id"><br>
			  <input type="submit" value="确认">	
	</form>	
	<span>${count}</span>
<h4 class="color">分页查询</h4>
	<form action="/progress/queryList" method="post">
	项目流程id：<input type="text" name="procId"><br>
			  <input type="submit" value="确认">	
	</form>	
	<span>${jProgresss}</span>
<h4 class="color">整改、延期申请/验收申请</h4>
	<form action="/progress/postponeAndAcceptance" method="post">
	流程ID：：<input type="text" name="procId"><br>
	流程进度：<input type="text" name="schedule"><br>
	流程类型：<input type="text" name="type"><br>
	提示消息： <input type="text" name="progressRecord.promptMsg"><br>
	延期/提前完成时间： <input type="text" name="progressRecord.assessDate"><br>
	延期/提前完成天数： <input type="text" name="progressRecord.timeAssess"><br>
			<input type="submit" value="确认">	
	</form>	
	<span>${count}</span>
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