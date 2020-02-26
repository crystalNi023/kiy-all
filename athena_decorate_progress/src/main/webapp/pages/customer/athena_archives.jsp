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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/home/header_end.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/date/ion.calendar.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/archives/archives.css"/>
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
				当前位置：<a href="${pageContext.request.contextPath}/pages/customer/athena_archives.jsp">客户档案</a>
			</div>			
		</div>
		<div class="page_wid top_margin">
			<div class="clearfix">
			<div class="left search1">
					<form action="" id="sel_cus_form" method="post">					
						<i class="fa fa-search search_icon"></i>
						<input type="text" class="search_css search_inp" name="name" id="" value="" placeholder="姓名" />
						<input type="text" class="search_css search_inp " name="phone" id="" value="" placeholder="手机号" />
						<input type="button" id="sel_cus_btn" class="btn btn-group btn_text" value="搜索" />
					</form>				
				</div>	
				<div class="right">
					<button id="new_add_cus" class="btn btn-primary">新增客户</button>
				</div>
			</div>			
		</div>	
		<!----------------- 添加客户模态框 --------------------------------->
		<div class="modal fade" id="add_user">
			<div class="modal-dialog add_user_modal">
				<form id="add_cus_form" action="${pageContext.request.contextPath}/customer/increase" method="post" enctype="multipart/form-data">
				<input type="hidden" name="comId" id="com_id">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加客户</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4">
							<div class="">
								<font color="red">*</font>客户姓名
							</div>
							<input type="text" class=" add_inp" name="name" id="add_name" value="" />
						</div>
						<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4">
							<div class="">
								<font color="red">*</font>联系电话
							</div>
							<input type="text" class=" add_inp" name="phone" id="add_phone" value="" />
						</div>
						<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4">
							<div class="">
								<font color="red"></font>第二联系方式
							</div>
							<input type="text" class=" add_inp" name="mobile" id="add_mobile" value="" />							
						</div>
					</div>
					<div class="row top_margin">
						<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4">
							<div class="">
								<font color="red">*</font>合同签订时间
							</div>
							<input readonly="readonly" type="text" class="add_inp time_ipt" name="signTime" id="add_signTime" value="" />
						</div>
						<div class="col-xs-8 col-sm-8 col-lg-8 col-md-8">
							<div class="">
								<font color="red">*</font>工程地址
							</div>
							<input type="text" class=" add_inp" name="address" id="add_address" value="" />
						</div>
					</div>					
					<div class="row top_margin">
						<div class="col-xs-12 col-sm-12 col-lg-12 col-xs-12">
							<div class="">
								<font color="red"></font>装修合同
							</div>
							<div class="row">
							<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4 text-center">
							<div id="showPic" class="top_margin" style=" width: 100%; height: auto;">
									<div id="design_img" class="">
									<i class="fa fa-upload upload_icon" aria-hidden="true"></i>
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
							<div class="row">
							<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4 text-center">
							<div id="showPic" class="top_margin" style=" width: 100%; height: auto;">
									<div id="design_img1" class="">
									<i class="fa fa-upload upload_icon" aria-hidden="true"></i>
									<input type="file" onchange="file_(this)" class=" add_inp selector_file" id="" name="designImgs" value="" />
									</div>	
							</div>
							</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<input type="button" id="add_cus_btn" class="btn btn-primary" value="确认添加">
				</div>
				</form>
			</div>
		</div>
		<!----------------------------------------------------------------------->
		<style type="text/css">
				#customer_tab{
					width: 100%;
					min-width: 1224px;
					border: 2px solid rgb(227, 227, 243);
					font-size: 12px;
					margin-top: 10px;
				}
				#customer_tab tr,td{
					width:auto;
					text-align: center;
					vertical-align: middle;
					border-bottom: 1px solid #CECBCB;
					height: 50px;
					background: #FFFFFF;
				}
				#customer_tab thead{
					border-bottom: 2px solid #0171C5;background-color: rgb(236, 236, 246);
				}
				#customer_tab thead tr,td{
					background: none;
				}
				.check_wid{
					width: 50px;					
				}
				.ch_box{
					vertical-align: top;
				}
			</style>
			<div class="page_wid page_hei">
					<table id="customer_tab" class="table-responsive" border="0">
					<thead>
						<tr>
							<td>编号</td>
							<td>姓名</td>
							<td>联系方式</td>
							<td>第二联系方式</td>
							<td>合同签订时间</td>
							<td>工程地址</td>
							<td>项目进度</td>
							<td>操作</td>
						</tr>
					</thead>
									
				</table>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center">
						<div id="Paginator" style="text-align: center"> <ul id="pageLimit"></ul> </div>
					</div>
				</div>
			</div>	
			<!-- ----------------------------------------------------- -->
			
			<div class="end">
				
			</div>
			<!--------------------------------- 修改客户模态框------------------------------ -->
			<div class="modal fade" id="edit_user">
			<div class="modal-dialog add_user_modal">
				<form id="edit_cus_form">
				<input type="hidden" id="cus_id" name="id">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">修改客户</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4">
							<div class="">
								<font color="red">*</font>客户姓名
							</div>
							<input type="text" class=" add_inp" name="name" id="cus_name" value="" />
						</div>
						<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4">
							<div class="">
								<font color="red">*</font>联系电话
							</div>
							<input type="text" class=" add_inp" name="phone" id="cus_phone" value="" />
						</div>
						<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4">
							<div class="">
								<font color="red"></font>第二联系方式
							</div>
							<input type="text" class=" add_inp" name="mobile" id="cus_mobile" value="" />							
						</div>
					</div>
					<div class="row top_margin">
						<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4">
							<div class="">
								<font color="red">*</font>合同签订时间
							</div>
							<input type="text" readonly="readonly" class="add_inp time_ipt" name="signTime" id="cus_signTime" value="" />
						</div>
						<div class="col-xs-8 col-sm-8 col-lg-8 col-md-8">
							<div class="">
								<font color="red">*</font>工程地址
							</div>
							<input type="text" class=" add_inp" name="address" id="cus_address" value="" />
						</div>
					</div>
					</form>
					<div class="row top_margin hidden">
						<div class="col-xs-12 col-sm-12 col-lg-12 col-xs-12">
							<div class="">
								<font color="red"></font>装修设计图
							</div>
							<div class="row">
							<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4">
								<div id="showPic" class="top_margin" style=" width: 100%; height: auto;">
										<div id="design_img" class="">
										<i class="fa fa-upload upload_icon" aria-hidden="true"></i>
										<input type="file" multiple="multiple" class="fileTag add_inp selector_file" id="chooseImage" name="file" value="" />
										</div>	
								</div>
							</div>	
							</div>																	
						</div>
						<div class="col-xs-12 col-sm-12 col-lg-12 col-xs-12 top_margin">
							<div class="">
								<font color="red"></font>装修合同
							</div>
							<div class="row">
							<div class="col-xs-4 col-sm-4 col-lg-4 col-xs-4">
							<div id="showPic1" class="top_margin" style=" width: 100%; height: auto;">
									<div id="design_img1" class="">
									<i class="fa fa-upload upload_icon" aria-hidden="true"></i>
									<input type="file" multiple="multiple" class="fileTag add_inp selector_file" id="chooseImage" name="file" value="" />
									</div>	
							</div>
							</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="edit_sub" class="btn btn-primary">确认修改</button>
				</div>
				
			</div>
		</div>
		<!----------------------------------------------------------------------->
		<!-- =================================== -->
		<div class="modal fade" id="touch_modal">
			<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">联系客户</h4>
				</div>
				<div class="modal-body">
					<div class="row top_margin">
						<div class="col-xs-4 col-sm-4 col-lg-4 col-md-4">
						电话：
						</div>
						<div class="col-xs-8 col-sm-8 col-lg-8 col-md-8">
						<input type="text" class="form-control" id="touch_ph">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button"  class="btn btn-primary">确认</button>
				</div>
			</div>
		</div>
		</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/upload/upload-file.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/format.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/laydate/laydate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time_examples.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap-paginator.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/page/page_cus.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/layer/layer.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jQuery.form.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
	<script>
	
	var comId = sessionStorage.getItem('comId');//从临时会话中获取公司id	
	$('#com_id').val(comId);
	var reg = /^1[0-9]{10}$/;
	var str = new RegExp(reg);		
	</script>
<script type="text/javascript">	
var page_btn_num;//分页条目显示7个按钮  就是7页
var total_Page;//总记录页数
var page_no;//当前页
if(sessionStorage.getItem('cus_page_no')==null){
	page_no=1;
}else{
	page_no=sessionStorage.getItem('cus_page_no');
}
	$(function(){
		//请求默认分页数据
		cus_ajax();
	});
	
	//条件分页查询
	$('#sel_cus_btn').click(function(){
		cus_ajax();
	})
	//封装分页查询的请求
	function cus_ajax(){
		var form =$.param({'userId':JSON.parse(sessionStorage.getItem('SRC_user_msg')).id})+ "&" + $.param({"pageNo":page_no })+ "&" + $.param({'comId':comId}) + '&' + $('#sel_cus_form').serialize();
		$.ajax({
			type:"post",
			url:'${pageContext.request.contextPath}/customer/queryList',
			dataType:'json',
			data:form,
			async:true,
			success:function(data){
				if(data.result=="" || data.result==null){
					page_no=1;
					$('#pageLimit').children().remove();
					$('#customer_tab').children().first().nextAll().remove();
					$("#customer_tab").append(
					'<tr>'+
					'<td colspan="8">暂无内容</td>'+
					'</tr>')
				}else{					
				customer_show(data);
				//parent.document.getElementById('page_con').style.height=document.body.scrollHeight+'px';
				}
			},
			error:function(){
				console.log('no');
			}
		})
	}
	
//生成客户数据
function customer_show(data){
	total_Page = data.totalPage;//将返回的总页数赋值给变量
	page_no = data.pageNo;
	sessionStorage.setItem('cus_page_no',page_no);
	if(total_Page<=7){
		page_btn_num=total_Page;
	}else{
		page_btn_num=7;
	}
	console.log(data);
	$('#customer_tab').children().first().nextAll().remove();
	$.each(data.result,function(index,item){
		$('#customer_tab').append(
		'<tr>'+
		'<td>'+((index+1)+(10*(page_no-1)))+'</td>'+
		'<td>'+item.name+'</td>'+
		'<td>'+item.phone+'</td>'+
		'<td>'+item.mobile+'</td>'+
		'<td>'+getFormatDate(item.signTime)+'</td>'+
		'<td>'+item.address+'</td>'+
		'<td>'+item.process+'%</td>'+
		'<td>'+
			'<a class="file_btn" href="javascript:;" onclick=dw_img('+JSON.stringify(item)+')>'+'设计图'+'</a>'+
			'<a class="file_btn" href="javascript:;" onclick=contract_img('+JSON.stringify(item)+')>'+'合同'+'</a>'+
			'<span class="file_btn"><a href="#touch_modal" data-toggle="modal" onclick=touch('+item.phone+')><font color="black">'+'联系业主'+'</font></a></span>'+
			'<span class="file_text"><a href="#edit_user" data-toggle="modal" onclick=edit_cus('+JSON.stringify(item)+')><font color="#0171C5">'+'编辑'+'</font></a></span>'+
			is_sponsor(JSON.stringify(item))+
			'<span class="file_text"><a href="javascript:;" onclick=plan_record('+JSON.stringify(item)+')><font color="#449D44">'+'进度记录'+'</font></a></span>'+
			'</td>'+
			'</tr>'		
		);
	});
	bootstrap_paginator(page_no,total_Page);//调用分页方法	
}

//判断用户是否有权限发起项目
function is_sponsor(item){
	if(JSON.parse(sessionStorage.getItem('SRC_user_msg')).type != 6){
		return '<span class="file_text"><a href="javascript:;" onclick=start_project('+item+')><font color="#EEA236">'+'发起项目'+'</font></a></span>';
	}else{
		return '';
	}
}
//新增客户
$('#new_add_cus').click(function(){
	//loadIframe('${pageContext.request.contextPath}/pages/customer/athena_add_cus.jsp?id='+comId);
	window.location.href='${pageContext.request.contextPath}/pages/customer/athena_add_cus.jsp?id='+comId;
})
//合同
function contract_img(item){
	//loadIframe('${pageContext.request.contextPath}/pages/customer/athena_contract_img.jsp?id='+item.id);
	window.location.href='${pageContext.request.contextPath}/pages/customer/athena_contract_img.jsp?id='+item.id;
}
//设计图
function dw_img(item){
	//loadIframe('${pageContext.request.contextPath}/pages/customer/athena_dw_img.jsp?id='+item.id);
	window.location.href='${pageContext.request.contextPath}/pages/customer/athena_dw_img.jsp?id='+item.id;
}
//发起项目
function start_project(item){
	//loadIframe('${pageContext.request.contextPath}/pages/customer/athena_sponsor_project.jsp?id='+item.id);
	window.location.href='${pageContext.request.contextPath}/pages/customer/athena_sponsor_project.jsp?id='+item.id;
}
//进度记录
function plan_record(item){
	//loadIframe('${pageContext.request.contextPath}/pages/plan_record/athena_plan_record.jsp?proid='+item.proId);
	sessionStorage.setItem('plan_record_key',item.proId);
	window.location.href='${pageContext.request.contextPath}/pages/plan_record/athena_plan_record.jsp?proid='+item.proId;
}
	
	//修改客户================================================
		$("#edit_sub").click(function(){
			if($('#cus_name').val().trim()==''){
				layer.msg('姓名不能为空',{area:['300px', '50px']});
				return false;
			}
			else if($('#cus_phone').val().trim()==''){
				layer.msg('电话不能为空',{area:['300px', '50px']});
				return false;
			}
			else if(!str.test($('#cus_phone').val().trim())){
				layer.msg('手机号格式不对',{area:['300px', '50px']});
				return false;
			}else if($('#cus_mobile').val().trim()!='' && !str.test($('#cus_mobile').val().trim())){
					layer.msg('第二联系方式格式不对',{area:['300px', '50px']});
					return false;
				
			}else if($('#cus_signTime').val().trim()==''){
				layer.msg('时间不能为空',{area:['300px', '50px']});
				return false;
			}
			else if($('#cus_address').val().trim()==''){
				layer.msg('地址不能为空',{area:['300px', '50px']});
				return false;
			}else if(true){
			var form  = $("#edit_cus_form").serialize();
			$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/customer/modifyById',
				async:true,
				data:form,
				success:function(data){
					window.location.reload();
					//$('#edit_user').modal('hide');
					//cus_ajax();
					//parent.document.getElementById('cus_iframe').src="${pageContext.request.contextPath}/pages/customer/athena_archives.jsp";
				},
				error:function(){
					layer.msg('修改失败！',{area:['300px', '50px']});
				}
			})
			}
		});
	
	function edit_cus(e){
		$('#cus_name').val(e.name);
		$('#cus_phone').val(e.phone);
		$('#cus_mobile').val(e.mobile);
		$('#cus_address').val(e.address);
		$('#cus_signTime').val(getFormatDate(e.signTime));
		$('#cus_id').val(e.id);
	}
	//=============
		function touch(e){
		$('#touch_ph').val(e);
	}
</script>
</html>
