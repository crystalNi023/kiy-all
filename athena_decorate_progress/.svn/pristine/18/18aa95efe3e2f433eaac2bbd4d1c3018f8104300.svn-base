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
<h4 class="color">添加客户</h4>
<!-- 	<form action="/athena_decorate_progress/customer/increase" method="post">
		客户名称：<input type="text" name="name"><br>
		联系电话：   <input type="text" name="phone"><br>
		第二联系方式：<input type="text" name="mobile"><br>
		工程地址：   <input type="text" name="address"><br>
		合同签订时间：   <input  id="txt_calendar" type="text" name="signTime" class="repayDate form-control pro_time" ><br>
			  <input type="submit" value="确认">	
	         上传图片：<input type="file" name="file"><br>
	</form>	  -->
 	<form  action="/customer/increase" method="post" enctype="multipart/form-data">
		客户名称：<input type="text" name="name"><br>
		联系电话：   <input type="text" name="phone"><br>
		第二联系方式：<input type="text" name="mobile"><br>
		工程地址：   <input type="text" name="address"><br>
		合同签订时间：   <input  id="txt_calendar" type="text" name="signTime" class="repayDate form-control pro_time" ><br>
		file 1：<input type="file" id="file1" name="contractImgs" multiple="multiple">
<!-- 		file 2：<input type="file" name="contractImgs"> -->
		file 3：<input type="file" name="designImgs" multiple="multiple">
<!-- 		file 4：<input type="file" name="designImgs"> -->
			   <input type="submit" value="确定" />
			   <input type="button" onclick="file_()" value="点击">
	</form> 
	<script>
	function file_(){
		console.log($('#file1').get(0).files[0].name);
		var text = window.URL.createObjectURL($('#file1').get(0).files[0]);
		console.log(text);
	}	
	</script>
	<span>${count}</span>
<h4 class="color">修改客户</h4>
	<form action="/customer/modifyById" method="post">
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
	<form action="/customer/queryById" method="post">
		客户编号：<input type="text" name="id"><br>
					  <input type="submit" value="确认">	
	</form>	
	<span>${customer}</span>
<h4 class="color">移除客户</h4>
	<form action="/customer/removeById" method="post">
	客户编号：<input type="text" name="id"><br>
			  <input type="submit" value="确认">	
	</form>	
	<span>${count}</span>
<h4 class="color">分页查询</h4>
	<form action="/customer/queryList" method="post">
	客户姓名：<input type="text" name="name"><br>
	客户电话：<input type="text" name="phone"><br>
			  <input type="submit" value="确认">	
	</form>	
	<span>${jCustomers}</span>
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