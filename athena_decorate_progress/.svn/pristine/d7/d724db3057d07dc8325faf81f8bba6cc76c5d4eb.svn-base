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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public_page.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/login/register_ret.css"/>
	</head>
	<body>
		<div class="reg_nav">
			<span>装修进度管理后台</span>
		</div>
		<div class="register_size">
			<form action="${pageContext.request.contextPath}/user/register" id="register_form" method="post" enctype="multipart/form-data">
				<span class="register_text form_margin">帐号注册</span>
				<span><font color="#ff7800">装修团队</font></span><br />
				<span><font color="#01a7c1" size="3">ACCOUNT REGISTRATION</font></span>
				<!-- ---------------------- -->
				<div class="row step ret_mar">
				<div class="col-sm-4 col-md-4 col-lg-4 col-xs-4 text-center">
					<span>帐号信息</span>
				</div>
				<div class="col-sm-4 col-md-4 col-lg-4 col-xs-4 text-center">
					<span>公司信息</span>
				</div>
				<div class="col-sm-4 col-md-4 col-lg-4 col-xs-4 text-center">
					<span>注册成功</span>
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
			
			<!-- ---------------------- -->
			<div class="" id="one_box">
				<div class="input_">
					<i class="fa fa-phone fa-2x"></i>
					<input id="phone" type="text" name="username" class="" placeholder="手机号" value="" />
				</div>
				<div class="input_code clearfix">
						<i class="fa fa-envelope fa-2x"></i>
						<input type="text" id="code_ph" name="code" placeholder="验证码" />
						<input type="button" id="sendV" class="btn btn-default right" value="获取验证码">	
				</div>
				<div id="code_tip"></div>
				<div class="input_">
					<i class="fa fa-key fa-2x"></i>
					<input type="password" id="pwd" placeholder="登录密码" name="password" />
				</div>
				
				<div class="register_sub">
					<input id="next_one" type="button" class="btn btn-group" value="下一步">
				</div>
				</div>
				<!--  ========================  -->
				<div class="row hide" id="two_box">
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
							<input type="text" class="form-control" placeholder="公司名称" id="com_name" name="name" value="" />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
							<input type="text" class="form-control" placeholder="经营地址" id="com_address" name="address" value="" />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
							<input type="text" class="form-control" placeholder="企业法人" id="com_person" name="legalPerson" value="" />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
							<input type="text" class="form-control" placeholder="法人电话" id="com_personPhone" name="legalPersonPhone" value="" />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
							<input type="text" class="form-control" placeholder="业务负责人" id="com_business" name="business"  value="" />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
							<input type="text" class="form-control" placeholder="业务电话" id="com_businessPhone" name="businessPhone"  value="" />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
							<input type="text" class="form-control" placeholder="财务" id="com_financialOfficer" name="financialOfficer"  value="" />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
							<input type="text" class="form-control" placeholder="财务电话" id="com_financialPhone" name="financialOfficerPhone"  value="" />
						</div>
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
							<input type="text" class="form-control" placeholder="电子邮箱" id="com_email" name="email"  value="" />
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 top_margin" style="height:440px">
					<div class="upload_img_box" style="">	
					<span class="img_text">营业执照</span>	
						<i class="fa fa-upload preview_icon" aria-hidden="true"></i>
    					<img id="img_preview" class="img-responsive" />
						<input type="file" name="businessLicense" id="file_prev" value=""/>	
					</div>
					<div>
					<font color="red">* 所有信息均为必填项</font>
					</div>
				</div>
				
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center top_margin">
					<input type="button" id="next_two"  class="btn btn-primary complete_btn" value="下一步">
				</div>
			</div>
				<!-- =========================== -->
				<!-- ============================== -->
			<div class="row hide thr_box" id="three_box">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center top_margin">
					<i class="fa fa-check-circle-o"></i>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center top_margin">
					信息提交成功！
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center">
					请等待工作人员审核，通过后将以短信形式通知你。
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center top_margin">
					<button type="button" id="next_thr" class="btn btn-primary complete_btn">完成</button>
				</div>
			</div>
			<!-- ==================================== -->
			</form>
		</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/note/note_send.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jQuery.form.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/layer/layer.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
<script>
//通过form.js提交表单
//单击事件提交表
var reg = /^1[0-9]{10}$/;
var str = new RegExp(reg);
function register_form(){
	if($('#com_address').val().trim()==""){
		layer.msg('地址不能为空',{area:['300px', '50px']});
		return false;
	}else if($('#com_name').val().trim()==""){
		layer.msg('姓名不能为空',{area:['300px', '50px']});
		return false;
	}else if($('#com_person').val().trim()==""){
		layer.msg('法人不能为空',{area:['300px', '50px']});
		return false;
	}else if($('#com_personPhone').val().trim==""){
		layer.msg('法人电话不能为空',{area:['300px', '50px']});
		return false;
	}else if(!str.test($('#com_personPhone').val().trim())){
		layer.msg('法人电话格式不对',{area:['300px', '50px']});
		return false;
	}else if($('#com_business').val().trim==""){
		layer.msg('业务负责人不能为空',{area:['300px', '50px']});
		return false;
	}else if($('#com_financialOfficer').val().trim()==""){
		layer.msg('财务不能为空',{area:['300px', '50px']});
		return false;
	}else if($('#com_businessPhone').val().trim()==""){
		layer.msg('业务负责人电话不能为空',{area:['300px', '50px']});
		return false;
	}else if(!str.test($('#com_businessPhone').val().trim())){
		layer.msg('业务负责人电话格式不对',{area:['300px', '50px']});
		return false;
	}else if($('#com_financialPhone').val().trim()==""){
		layer.msg('财务电话不能为空',{area:['300px', '50px']});
		return false;
	}else if(!str.test($('#com_financialPhone').val().trim())){
		layer.msg('财务电话格式不对',{area:['300px', '50px']});
		return false;
	}else if($('#com_email').val().trim()==""){
		layer.msg('邮箱不能为空',{area:['300px', '50px']});
		return false;
	}else if($('#file_prev').val()==""){
		layer.msg('请选择图片',{area:['300px', '50px']});
		return false;
	}else{
		return true;
	}
}
$('#next_two').click(function(){
	var bool = register_form();
	if(bool){
	options = {
			url:'${pageContext.request.contextPath}/user/register',
			type:'post',
			async:true,
			data:$('#register_form').serialize(),
			dataType:'json',
			success:function(data){
				console.log(data);				
				if(data=='1'){
					two_click('step_thr');
				}else{
					//失败弹窗提示
					layer.msg('注册失败',{area:['300px', '50px']});
					return false;
				}
				
			},
			error:function(){				
				console.log('获取返回数据失败');
			}
		}
	$('#register_form').ajaxSubmit(options);
	}
})		
//验证短信验证码是否正确
$("#sendV").click(function(){
	var phone=$("#phone").val();
	var code=$("#code_ph").val();
	var reg = /^1[0-9]{10}$/;
	var str = new RegExp(reg);
	if(!str.test(phone)){
		layer.msg('手机号格式不对',{area:['300px', '50px']});
		return false;
	}else{
	time_(this,60);
	$.ajax({
		url : "${pageContext.request.contextPath}/user/sendMobileCode",
		type : "POST",
		datatype : "json",
		data : {'phone':phone,'type':1},
		async : false,
		success : function(data) {
			console.log(data);
			if(data.code=="01"){
				layer.msg('手机号已注册',{area:['300px', '50px']});
			}
		},
		error : function(data) {
			alert("请求服务器失败！");
		}
	}); 
}
});
//-----------------------------------------------
$('#next_one').click(function(){
	var phone = $('#phone').val();
	var code = $('#code_ph').val();
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/user/codeCheck',
		datatype : "json",
		data : {'username':phone,'code':code},
		success : function(data) {
			console.log(data);
			if(data=='00'){
				one_click('step_two');
			}else{
				//验证码弹窗提示
				layer.msg('验证码错误',{area:['300px', '50px']});
				return false;
			}
		},
		error : function(data) {
			
		}
	})	
})
//-----------------------------------------------
$(function(){
		if($.trim($("#phone").val())=="" || $.trim($("#code_ph").val())=="" || $.trim($("#pwd").val())==""){
			$("#next_one").attr("disabled",true);
		}else{
			$("#next_one").attr("disabled",false);
		}
})
//监测input值变化
		$("#phone,#code_ph,#pwd").on("input propertychange",function(){	
			var phone_val = $("#phone").val();
			var code_val = $("#code_ph").val();
			var pwd_val = $("#pwd").val();
		if($.trim(phone_val)!="" && $.trim(code_val)!="" && $.trim(pwd_val)!=""){
			$("#next_one").attr("disabled",false);
		}else{
			$("#next_one").attr("disabled",true);
		}
		})
		function one_click(e){
			$('#one_box').hide();
			$('#two_box').removeClass('hide');
			step(e);
			$('.register_size').width('60%');
		}
		function two_click(e){
			var form = $('#register_form').serialize();
			console.log(form);
			$('#two_box').hide();
			$('#three_box').removeClass('hide');
			step(e);
			$('.register_size').width('40%');
		}
		
		$('#next_thr').click(function(){
			window.location.href='${pageContext.request.contextPath}/pages/login/login.jsp';
		})
		
		function step(e){
	 	$('#'+e).addClass("step_line");
	 }
//-----------------------------------------------
$('#register_btn').click(function(){
	var phone=$("#phone").val();
	var code=$("#code_ph").val();
	var form = $('#register_form').serialize();
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/user/codeCheck',
		datatype : "json",
		async:false,
		data : {'username':phone,'code':code},
		success : function(data) {
			if(data=='00'){		
				$('#code_tip').text("");
				$.ajax({
					type:'post',
					url:'${pageContext.request.contextPath}/user/register',
					datatype : "json",
					async:false,
					data : form,
					success : function(data) {
						window.location.href="${pageContext.request.contextPath}/pages/login/login.jsp";
					},
					error : function(data) {
						
					}
				})
			}else{
				$('#code_tip').text('验证码错误');
			}
		},
		error : function(data) {
			
		}
	})
});
//自定义上传图片样式
var fileTypes = [".jpg", ".png"];
$('#file_prev').change(function(){
	var file_arr = $(this).get(0);
	var files = file_arr.files[0];
	var file_name = files.name;
	var file_type = file_name.substring(file_name.indexOf("."));
	var isNext = false;
	for (var i = 0; i < fileTypes.length; i++) {
    if (fileTypes[i] == file_type) {
        isNext = true;
        break;
    }
}
if (!isNext){
    alert('不接受此文件类型');
    return false;
}
	var file_src = window.URL.createObjectURL(files);
	$('#img_preview').attr('src',file_src);
	$('#img_preview').height($(this).height());
	$('#img_preview').width($(this).width());
})
</script>
</html>
