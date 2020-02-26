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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/login.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css"/>
	</head>
	<body>
	
		<div class="logo">
			<span>
				Athena装修进度管理后台 
			</span>
		</div>
		<div class="contend clearfix">
			<%-- <div class="left login_img">
				<img src="${pageContext.request.contextPath}/public/img/1.png" class="img-responsive" width="100%"/>
			</div> --%>
			<div class="login_register">
				<div class="login_margin">
					<!-- ==================================================== -->
					<form action="" id="login_form" method="post">										
						<div class="user_tip">
							<span class="tip_zh">用户登录</span><br />
							<span class="tip-en">USER LOGIN</span>
						</div>
						<div class="user_name">
							<i class="fa fa-user-o fa-2x" style=""></i>
							<input type="text" name="username" id="user_name" value="" placeholder="请输入手机号码" />
						</div>
						<div class="user_pwd">
							<i class="fa fa-key fa-2x" style=""></i>
							<input type="password" name="password" id="user_pwd" value="" placeholder="请输入密码" />
						</div>
						<div class="y_or_n clearfix">
							<div class="left">
									<input type="checkbox" checked="checked" />
									<span>记住用户名和密码</span>
							</div>
							<div class="right login_a">
								<a href="${pageContext.request.contextPath}/pages/login/retrieve.jsp" class="forget_pwd">忘记密码？</a>
							</div>
						</div>
						
						<div id="prompt_msg">
							<!-- 提示信息 -->
						</div>
						
						<div class="login_btn">
							<input type="button" id="login_sub" class="btn login_sub_btn" value="登录">
						</div>
						
						<div class="login_a register">
							<a class="without_user" href="${pageContext.request.contextPath}/pages/login/register.jsp">没有帐号?立即注册</a>
						</div>
					</form>
				</div>		
		</div>
		</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/login/login_checkout.js" ></script>
</html>
<script>
$(function(){
	$(document).keydown(function(event){ 
		if(event.keyCode==13){ 
		$("#login_sub").click(); 
		} 
	})
})
$('#login_sub').click(function(){
	var form = $('#login_form').serialize();
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/user/login',
		async:true,
		data:form,
		dataType:'json',
		success:function(data){
			if(data.code=="01"){
				$("#prompt_msg").text(data.msg);
			}else{
				sessionStorage.setItem('comId',data.result.comId);//将公司id、用户id保存在本地存储 临时会话
				sessionStorage.setItem('userId',data.result.id);	
				sessionStorage.setItem('SRC_user_msg',JSON.stringify(data.result));
				//window.location.href="${pageContext.request.contextPath}/pages/project/athena_project.jsp?id="+data.result.comId +'&userId='+data.result.id;
				//登录成功后执行查询公司信息请求
				$.ajax({
					type:'post',
					url:'${pageContext.request.contextPath}/decorationCompany/queryById',
					async:false,
					data:{'id':data.result.comId},
					dataType:'json',
					success:function(data){
						localStorage.setItem("com_name", data.name);//将公司名称保存在本地存储
					},
					error:function(){
						
					}
				})
				//=====================
				window.location.href="${pageContext.request.contextPath}/pages/project/athena_project.jsp?";
			} 			
		},
		error:function(){
			
		}
	})
})
</script>
