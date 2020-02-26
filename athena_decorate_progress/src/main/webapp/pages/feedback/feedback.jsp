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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/home/header_end.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/feedback/feedback.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/layer/layer.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/hea_end.js" ></script>
</head>
<body id="4" style="height:768px">
<div class="footer">
			
		</div>
		<div class="current_position">
			<div class="nav_position">
				当前位置：<a href="">意见反馈</a>
			</div>			
		</div>
		<div class="feed_con">
		<form action="" id="feed_form" method="post" enctype="multipart/form-data">
		<input type="hidden" id="user_id" value="" name="userId">
			<div class="">
				问题反馈
			</div>
			<div class="">
				<textarea placeholder="请输入遇到的问题和建议" id="information" name="information" class="form-control" style="resize: none;height: 80px;"></textarea>
			</div>
			<div class="top_margin">
				上传图片
			</div>
			<div class="file_box">
				<div class="row">
					<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 feed_margin">
					 <div class="feed_img_box">
					 <i class="fa fa-upload upload_icon" aria-hidden="true"></i>
					 <span class="back_upload_text">选择图片</span>
					 <input onchange="feed_file(this)" type="file" class=" add_inp selector_file" id="feedbackImage" name="feedbackImage" value="">
					 </div>
					</div>
				</div>
			</div>
			<div class="top_margin">
				快速反馈
			</div>
			<div>
			<label class="checkbox-inline">
		        <input type="checkbox" name="types" value="21"> 系统崩溃
		    </label>
		    <label class="checkbox-inline">
		        <input type="checkbox" name="types" value="22"> 操作卡顿
		    </label>
		    <label class="checkbox-inline">
		        <input type="checkbox" name="types" value="23"> 操作复杂
		    </label>
		    <label class="radio-inline">
		        <input type="checkbox" name="types" value="24"> 网络延时
		    </label>
		    <label class="radio-inline">
		        <input type="checkbox" name="types" value="25"> 其它
		    </label>
			</div>
			
			<div class="top_margin text-center">
				<button type="button" style="width: 200px;" id="sub_feed" class="btn btn-primary">提交</button>
			</div>
			</form>
		</div>
		<div class="end">
			
		</div>
	</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/upload/upload-file.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jQuery.form.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
<script>
$(function(){
	var user_id = parent.user_id;
	$('#user_id').val(user_id);
})

$('#sub_feed').click(function(){
	if($('#information').val().trim()=='' && $('#information').val().trim()=='' && $('input[name="types"]:checked').length==0){
		layer.msg('请填写内容',{area:['300px', '50px']});
	}else{
	var form = $('#feed_form').serialize();
	var this_ = $(this);
	options = {
			url:'${pageContext.request.contextPath}/feedback/increase',
			type:'post',
			data:form,
			async:true,
			clearForm:true,
			beforeSend: function () {
		        // 禁用按钮防止重复提交
				 this_.attr({ disabled: "disabled" });
		    },
			success:function(data){
				layer.msg('反馈成功',{area:['300px', '50px']});
			},
			complete: function () {
		        this_.removeAttr("disabled");
		    },
			error:function(){
				
			}
	}
	$('#feed_form').ajaxSubmit(options);
	}
})
</script>