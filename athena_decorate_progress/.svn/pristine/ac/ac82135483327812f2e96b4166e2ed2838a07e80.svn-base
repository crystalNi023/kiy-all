<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>装修进度管理平台</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public_page.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/home/header_end.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/hea_end.js" ></script>
	</head>
	<body>
		<div class="footer">
			
		</div>
		<div class="tab-content">
				<div class="tab-pane fade in active" >
					<iframe src="${pageContext.request.contextPath}/pages/project/athena_project.jsp" id="page_con" frameborder="0"  width="100%" ></iframe>
				</div>
<%-- 				<div class="tab-pane fade" id="archives_">
					<iframe id="cus_iframe" src="${pageContext.request.contextPath}/pages/customer/athena_archives.jsp"  frameborder="0"  width="100%" ></iframe>
				</div> --%>
		</div>
		
		<div class="end">
				
			</div>
			<input type="hidden" id="com_id">
			<!-- <div class="modal fade" id="user_name">
				<div class="modal-dialog" style="width: 40%;">
				<form action="" id="user_form">
				<input type="hidden" name="id" id="com_id">
					<div class="modal-content">
						<div class="modal-header">
								<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
						<h4 class="modal-title" id="myModalLabel">公司信息</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									公司名称：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<input type="text" class="form-control" name="name" id="com_name" value="" />
								</div>
							</div>
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									固定电话：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<input type="text" class="form-control" name="phone" id="com_phone" value="" />
								</div>
							</div>
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									公司邮箱：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<input type="text" class="form-control" name="email" id="com_email" value="" />
								</div>
							</div>
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									公司地址：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<input type="text" class="form-control" name="address" id="com_address" value="" />
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							<button type="button" id="edit_com" class="btn btn-primary" data-dismiss="modal">确认修改</button>
						</div>
					</div>
					</form>
				</div>
			</div> -->
	</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/iframe/set_height.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/iframe/iframe_url.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
</html>
<script>
var id;
var user_id;
$(function(){
	id = $.query.get("id");
	id = decodeURIComponent(id);
	$('#com_id').val(id);
	user_id = $.query.get("userId");
	user_id = decodeURIComponent(user_id);
	sel_com();
})
function sel_com(){
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/decorationCompany/queryById',
			async:true,
			data:{'id':id},
			dataType:'json',
			success:function(data){
				$('#show_com_name').html(data.name);
				/* $('#com_name').val(data.name);					
				$("#com_phone").val(data.phone);
				$('#com_email').val(data.email);
				$('#com_address').val(data.address); */
			},
			error:function(){
				
			}
		})
	}
	
	$('#edit_com').click(function(){
		var form = $('#user_form').serialize();
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/decorationCompany/modifyById',
			async:true,
			data:form,
			dataType:'json',
			success:function(data){
				
			},
			error:function(){
				
			}
		})
	})
</script>