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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public_page.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/date/ion.calendar.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/archives/archives.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/archives/add_cus.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/home/header_end.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/hea_end.js" ></script>	
</head>
<body id="2" style="height:768px">
<div class="footer">
			
		</div>
		<div class="current_position">
			<div class="nav_position">
				当前位置：<a href="${pageContext.request.contextPath}/pages/customer/athena_archives.jsp">客户档案</a>&gt;
				<a href="">添加客户</a>
			</div>			
		</div>
		<div class="page_wid">
			<div class="page_nav">
					添加客户
			</div>
			<form id="add_cus_form" action="${pageContext.request.contextPath}/customer/increase" method="post" enctype="multipart/form-data">
			<input type="hidden" name="comId" id="com_id">
			<div class="add_cus_panel">
				<div class="row top_margin">
					<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							<div class="">
								<font color="red">*</font>客户姓名
							</div>
							<input type="text" class="add_cus_input" name="name" id="add_name" value="" />
					</div>
					<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							<div class="">
								<font color="red">*</font>联系电话
							</div>
							<input type="text" class="add_cus_input" name="phone" id="add_phone" value="" />
					</div>
					<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							<div class="">
								<font color="red"></font>第二联系电话
							</div>
							<input type="text" class="add_cus_input" name="mobile" id="add_mobile" value="" />
					</div>
				</div>
				<div class="row top_margin">
					<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							<div class="">
								<font color="red">*</font>合同签订时间
							</div>
							<input type="text" readonly="readonly" class="add_cus_input time_ipt" name="signTime" id="add_signTime" value="" />
					</div>
					<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
							<div class="">
								<font color="red">*</font>工程地址
							</div>
							<input type="text" class="add_cus_input" name="address" id="add_address" value="" />
					</div>
				</div>
				<div class="row top_margin">
						<div class="col-xs-12 col-sm-12 col-lg-12 col-xs-12">
							<div class="">
								<font color="red"></font>装修合同
							</div>
							<div class="row" id="contract_">
							<div class="col-xs-3 col-sm-3 col-lg-3 col-xs-3 ">
							<div id="showPic" class="top_margin" style=" width: 100%; height: auto;">
									<div id="design_img" class="">
									<i class="fa fa-upload upload_icon" aria-hidden="true"></i>
									<span class="block upload_file_text">选择图片</span>
									<input onchange="file_(this)" type="file" class=" add_inp selector_file" id="" name="contractImgs" value="" />
									</div>	
							</div>	
							</div>
							</div>																
						</div>
						<div class="col-xs-12 col-sm-12 col-lg-12 col-xs-12 top_margin">
							<div class="">
								<font color="red"></font>装修设计图
							</div>
							<div class="row" id="dw_">
							<div class="col-xs-3 col-sm-3 col-lg-3 col-xs-3">
							<div id="showPic" class="top_margin" style=" width: 100%; height: auto;">
									<div id="design_img1" class="">
									<i class="fa fa-upload upload_icon" aria-hidden="true"></i>
									<span class="block upload_file_text">选择图片</span>
									<input type="file" onchange="file_(this)" class=" add_inp selector_file" id="" name="designImgs" value="" />
									</div>	
							</div>
							</div>
							</div>
						</div>
					</div>
					<div class="row top_margin">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center">
							<button type="button" id="open_add_cus" class="btn btn-success btn_add_cus_modal">添加客户</button>
						</div>
					</div>
			</div>
			<!-- ------------------------确认提交模态框--------------------------------- -->
					<div class="modal fade" id="add_cus_modal">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
										<button class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h4 class="modal-title">是否添加客户</h4>
									</div>
								<div class="modal-body">									
								</div>
								<div class="modal-footer clearfix">
									<button class="btn btn-default left" data-dismiss="modal">关闭</button>
									<input type="button" id="add_cus_btn" class="btn btn-primary" value="确认">
								</div>
							</div>							
						</div>
					</div>
				<!-- ------------------------------------------------------------------ -->
			</form>
		</div>
		<div class="end">
				
			</div>
	</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/upload/upload-file.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/format.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/laydate/laydate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time_examples.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/layer/layer.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jQuery.form.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
<script>

//验证表单格式
var comId = sessionStorage.getItem('comId');//获取公司id
$('#com_id').val(comId);
var reg = /^1[0-9]{10}$/;
var str = new RegExp(reg);
	function check(){
		if($('#add_name').val().trim()==''){
			layer.msg('姓名不能为空',{area:['300px', '50px']});
			return false;
		}
		else if($('#add_phone').val().trim()==''){
			layer.msg('电话不能为空',{area:['300px', '50px']});
			return false;
		}
		else if(!str.test($('#add_phone').val().trim())){
			layer.msg('手机号格式不对',{area:['300px', '50px']});
			return false;
		}else if($('#add_mobile').val().trim()!=''){
			if(!str.test($('#add_mobile').val().trim())){
				layer.msg('第二联系方式格式不对',{area:['300px', '50px']});
				return false;
			}
		}else if($('#add_signTime').val().trim()==''){
			layer.msg('时间不能为空',{area:['300px', '50px']});
			return false;
		}
		else if($('#add_address').val().trim()==''){
			layer.msg('地址不能为空',{area:['300px', '50px']});
			return false;
		}
		return true;
	}
	//是否打开模态框
	$('#open_add_cus').click(function(){
		if(check()){
			$('#add_cus_modal').modal('show');
		}
	})
	//添加客户请求
	$('#add_cus_btn').click(function(){
		var this_ = $(this);
			if(check()){
			options = {
					url:'${pageContext.request.contextPath}/customer/increase',
					type:'post',
					async:true,
					dataType:'json',
					resetForm:true,
					beforeSend: function () {
				        // 禁用按钮防止重复提交
						 this_.attr({ disabled: "disabled" });
				    },
					success:function(data){
						console.log(data);
						layer.close(add_cus);
						$('#contract_').children("div:last-child").siblings().remove();
						$('#dw_').children("div:last-child").siblings().remove();
						if(data.code=='00'){	
							$('#add_cus_modal').modal('hide');
							layer.msg('添加成功',{area:['300px', '50px']});
						}else{
							layer.msg(data.msg,{area:['300px', '50px']});
						}
					},
					complete: function () {
				        this_.removeAttr("disabled");
				    },
					error:function(){				
						console.log('获取返回数据失败');
					}
			}
			var add_cus = layer.msg('正在添加客户,请等待', {
				icon: 16,
				 shade: 0.4,
				 time: false
				});
			$('#add_cus_form').ajaxSubmit(options);
			}
		});
	$("#add_cus_btn").click(function(){
		file_con =[];
		file_val=[];
	})
</script>