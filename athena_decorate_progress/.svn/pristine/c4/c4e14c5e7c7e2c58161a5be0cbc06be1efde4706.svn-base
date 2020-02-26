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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public_page.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/team/staff.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/home/header_end.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/font-awesome.min.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/hea_end.js" ></script>
</head>
<body id="3">
<div class="footer">
			
		</div>
		<div class="current_position">
			<div class="nav_position">
				当前位置：<a href="${pageContext.request.contextPath}/pages/team/athena_staff.jsp">团队管理</a>&gt;
				<a href="${pageContext.request.contextPath}/pages/team/athena_add_staff.jsp">新增员工</a>
			</div>			
		</div>
		<div class="page_wid">
			<div class="page_nav">
					新增员工
			</div>
			
			<div class="staff_panel page_hei">
			<form id='add_form' method="post">
			<input type="hidden" name="comId" id="com_id">
				<div class="row top_margin">
					<div class="col-xs-7 col-sm-7 col-md-7 col-lg-7">
						<div class="row">													
							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div class="">
									<font color="red">*</font>员工姓名
								</div>
								<input type="text" class="staff_ipt" name="name" id="add_name" value="" />
							</div>
							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div class="">
									<font color="red">*</font>联系电话
								</div>
								<input type="text" class="staff_ipt" name="phone" id="add_phone" value="" />
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
								<div class="">
									联系地址
								</div>
								<input type="text" class="staff_ipt" name="address" id="add_address" value="" />
							</div>
						</div>
					</div>
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="staff_type">
									<font color="red">*</font>员工类型
								</div>
								<label class="radio-inline">
							        <input type="radio" name="module" id="optionsRadios3" value="1" checked>工程部经理
							    </label>
								<label class="radio-inline">
							        <input type="radio" name="module" id="optionsRadios3" value="3" checked>项目经理
							    </label>
							    <label class="radio-inline">
							        <input type="radio" name="module" id="optionsRadios4"  value="4"> 现场负责人
							    </label>
							    <label class="radio-inline">
							        <input type="radio" name="module" id="optionsRadios4"  value="2"> 设计师
							    </label>
							    <label class="radio-inline">
							        <input type="radio" name="module" id="optionsRadios4"  value="5"> 施工员
							    </label>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin hidden">
								<div class="">
									<font color="red">*</font>登录账号
								</div>
								<input type="text"  class="staff_ipt" name="" id="" value="" />
								<input type="text"  class="staff_ipt" style="margin-top: 60px;" name="" id="" value="" />
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center">
						<input type="button" onclick="add_staff()" class="btn btn-success add_staff_btn" value="保存">
					</div>					
				</div>
				</form>
			</div>
		</div>
		<div class="end">
			
		</div>
	</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/layer/layer.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
<script>
var reg = /^1[0-9]{10}$/;
var str = new RegExp(reg);
$(function(){
	$('#com_id').val(sessionStorage.getItem('comId'));
})

function check_(){
	if($('#add_name').val().trim()==''){
		layer.msg('姓名不能为空',{area:['300px', '50px']});
		return false;
	}else if($('#add_phone').val().trim()==''){
		layer.msg('电话不能为空',{area:['300px', '50px']});
		return false;
	}else if($('#add_address').val().trim()==''){
		layer.msg('地址不能为空',{area:['300px', '50px']});
		return false;
	}else if(!str.test($('#add_phone').val().trim())){
		layer.msg('手机号格式不对',{area:['300px', '50px']});
		return false;
	}
	$('.add_staff_btn').attr('disabled',true);
	return true;
}

function add_staff(){
	if(check_()){
		var form = $('#add_form').serialize();
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/decorationTeam/increase',
			data:form,
			dataType:'json',
			success:function(data){
				if(data.code=='00'){
					window.location.href="${pageContext.request.contextPath}/pages/team/athena_staff.jsp";
				}else{
					layer.msg(data.msg,{area:['300px', '50px']});
					$('.add_staff_btn').removeAttr("disabled");
				}
			}
		})
	}
}
</script>