<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
            <%   
      // 将过期日期设置为一个过去时间  
    //response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");  
  
      // 设置 HTTP/1.1 no-cache 头  
    response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");  
  
      // 设置 IE 扩展 HTTP/1.1 no-cache headers， 用户自己添加  
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
  
      // 设置标准 HTTP/1.0 no-cache header.  
      response.setHeader("Pragma", "no-cache");  
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0">  
<title>Athena装修进度管理平台</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public_page.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/font-awesome.min.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/register_ret.css" />
	</head>

	<body>
		<div class="reg_nav">
			<span>Athena装修进度管理后台</span>
		</div>
		<div class="ret_size">
			<span class="register_text form_margin">找回密码</span><br />
			<span><font color="#01a7c1"; size="3">ACCOUNT REGISTRATION</font></span>
			<div class="row step ret_mar">
				<div class="col-sm-4 col-md-4 col-lg-4 col-xs-4 text-center">
					<span>第一步：验证</span>
				</div>
				<div class="col-sm-4 col-md-4 col-lg-4 col-xs-4 text-center">
					<span>第二步：重置密码</span>
				</div>
				<div class="col-sm-4 col-md-4 col-lg-4 col-xs-4 text-center">
					<span>第三步：完成</span>
				</div>
			</div>
			<div class="row step_bg">
				<div class="col-sm-4 col-md-4 col-lg-4 col-xs-4 step_padding step_line">
					<div class="step_yuan">1</div>
				</div>
				<div class="col-sm-4 col-md-4 col-lg-4 col-xs-4 step_padding" id="step_two">
					<div class="step_yuan">2</div>
				</div>
				<div class="col-sm-4 col-md-4 col-lg-4 col-xs-4 step_padding" id="step_thr">
					<div class="step_yuan">3</div>
				</div>
			</div>
			<div class="">
			<form id="form_ret" action="/user/resetPassword" method="post">
				<div class="">
					<div class="input_">
						<i class="fa fa-phone fa-2x"></i>
						<input type="tel" placeholder="手机号" class="" name="username" id="tel" value="" />
					</div>
					<div class="input_code clearfix">
						<i class="fa fa-key fa-2x"></i>
						<input type="text" name="code" placeholder="验证码" id="code" value="" />
						<input type="button" id="send_msg" name="code" class="btn btn-default right" value="获取验证码">
					</div>
					<div class="register_sub">
						<input type="button" class="btn btn-group" id="next" onclick="" value="下一步" />
					</div>
				</div>
				<div class="pro_hide">
					<div class="input_">
						<i class="fa fa-key fa-2x"></i>
						<input type="password" placeholder="新密码" name="password" id="pwd" value="" />
					</div>
					<div class="input_">
						<i class="fa fa-key fa-2x"></i>
						<input type="password" placeholder="确认密码" name="" id="confirm_pwd" value="" />
					</div>
					<div>
						<span id="warn" style="color: red;"></span>
					</div>
					<div class="register_sub">
						<input type="button" class="btn btn-group" id="next1" value="提交" />
					</div>
				</div>
				<div class="pro_hide">
					<div class="ret_success">
						<i class="fa fa-check-circle-o"></i>
					</div>
					<div style="text-align:center">
						<span>操作成功，</span><span id="count">5</span><span>秒后跳转到登陆页面</span>
					</div>
				</div>
			</form>
			</div>
		</div>
	</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/note/note_send.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/layer/layer.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
<script>
$("#send_msg").click(function(){
	var reg = /^1[0-9]{10}$/;
	var str = new RegExp(reg);
	var phone = $('#tel').val();
	if(!str.test(phone)){
		layer.msg('手机号格式不对',{area:['300px', '50px']});
		return false;
	}else{
		time_(this,60);
		$.ajax({
			url : "${pageContext.request.contextPath}/user/sendMobileCode",
			type : "POST",
			datatype : "json",
			data : {'phone':phone,'type':2},
			async : false,
			success : function(data) {
				
			},
			error : function(data) {
				alert("请求服务器失败！");
			}
		}); 		
	}	
})

$('#next').click(function(){
	var phone = $('#tel').val();
	var code = $('#code').val();
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/user/codeCheck',
		datatype : "json",
		data : {'username':phone,'code':code},
		success : function(data) {
			console.log(data);
			if(data=='00'){
				step_('step_two');
				$("#next").parent().parent().next().show();
				$("#next").parent().parent().next().siblings().hide();
			}else{
				layer.msg('验证码错误',{area:['300px', '50px']});
				return false;
			}
		},
		error : function(data) {
			
		}
	})
	
})

//=============================================
	$("#next1").click(function(){
		var form = $('#form_ret').serialize();
		$.ajax({
			url : "${pageContext.request.contextPath}/user/resetPassword",
			type : "POST",
			datatype : "json",
			data : form,
			async : false,
			success : function(data) {
				time(5);
				step_('step_thr');
			},
			error : function(data) {
				alert("请求服务器失败！");
			}
		})
	})
</script>
<script>
	$("#next1").click(function(){
		$(this).parent().parent().next().show();
		$(this).parent().parent().next().siblings().hide();
	})
	
	function time(i){
		setTimeout(function(){
			if(i>1){
				i--;
				$("#count").text(i);
				time(i);
			}else{
				window.location.href="${pageContext.request.contextPath}/pages/login/login.jsp";
			}
		},1000)
	}
	 function step_(e){
	 	$('#'+e).addClass("step_line");
	 }
</script>
<script>
	$(function(){
		//页面加载后就提示输入密码
		$("#warn").text("请输入密码！");	
		//判断input是否为空  更改按钮状态
		if($("#tel").val()=="" || $("#code").val()==""){
			$("#next").attr("disabled",true);
		}else{
			$("#next").attr("disabled",false);
		}
		if($("#pwd").val()=="" || $("#confirm_pwd").val()==""){
			$("#next1").attr("disabled",true);
		}else{
			$("#next1").attr("disabled",false);
		}
		$("#tel,#code").on("input propertychange",function(){			
			var tel_val = $("#tel").val();
			var code_val = $("#code").val();
		if(tel_val=="" || code_val==""){
			$("#next").attr("disabled",true);
		}else{
			$("#next").attr("disabled",false);
		}
		})	
		
		$("#pwd,#confirm_pwd").on("input propertychange",function(){			
			var pwd_val = $("#pwd").val();
			var con_pwd_val = $("#confirm_pwd").val();
		if(pwd_val=="" || con_pwd_val==""){
			$("#warn").text("请输入密码！");			
		}else{
			if(pwd_val!=con_pwd_val){
				$("#next1").attr("disabled",true);
				$("#warn").text("密码不一致，请重新输入！");
			}else{			
				$("#warn").text("");
				$("#next1").attr("disabled",false);
			}		
		}
		})	
	})
</script>
</html>