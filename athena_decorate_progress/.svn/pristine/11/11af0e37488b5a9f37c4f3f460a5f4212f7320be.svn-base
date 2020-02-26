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
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>Athena装修进度管理平台</title>
 	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public_page.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/team/staff.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/home/header_end.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/font-awesome.min.css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/hea_end.js" ></script>	
</head>
<body id="3" style="height:768px">
<div class="footer">
			
		</div>
		<div class="current_position">
			<div class="nav_position">
				当前位置：<a href="${pageContext.request.contextPath}/pages/team/athena_staff.jsp">团队管理</a>
			</div>		
		</div>	
			<div class="clearfix page_wid staff_options">			
				<div class="left search1">				
					<form action="" id="sea_form" method="post">					
						<i class="fa fa-search search_icon"></i>
						<input type="text" class="search_css search_inp" name="name" id="" value="" placeholder="姓名" />
						<input type="text" class="search_css search_inp " name="phone" id="" value="" placeholder="手机号" />
						<input type="button" id="sta_btn" class="btn btn-group btn_text right" value="搜索" />
					</form>	
				</div>
				<a onclick="add_staff()" href="javascript:;" class="btn btn-primary add_staff">新增员工</a>
			</div>
			<style type="text/css">
				table{
					width: 100%;
					border: 2px solid rgb(227, 227, 243);
				}
				tr,td{
					width:auto;
					text-align: center;
					vertical-align: middle;
					border-bottom: 1px solid #CECBCB;
					height: 50px;
					background: #FFFFFF;
				}
				thead{
					border-bottom: 2px solid #0171C5;background-color: rgb(236, 236, 246);
				}
				thead tr,td{
					background: none;
				}
				.check_wid{
					width: 50px;					
				}
				.ch_box{
					vertical-align: top;
				}
				.staff_sel_project{
				margin:5px;
				}
			</style>
			<div class="page_wid top_margin page_hei">
					<table id="staff_tab" class="table-responsive" border="0">
					<thead>
						<tr>
							<td>编号</td>
							<td>姓名</td>
							<td>联系方式</td>
							<td>员工类型</td>
							<td>地址</td>
							<td>操作</td>
						</tr>
					</thead>										
				
				</table>
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center">
						<!-- <div id="" class="page_box clearfix">
							<a onclick="prev_page()" class="page_turn left" href="javascript:;">&laquo;</a>
							<ul id="page_num" class="pagination"></ul>
							<a onclick="next_page()" class="page_turn right" href="javascript:;">&raquo;</a>
						</div> -->
						<div id="Paginator" style="text-align: center"> <ul id="pageLimit"></ul> </div>
					</div>
				</div>
			</div>				
		<div class="end">
				
			</div>
		<!-- -------------------------------------------- -->
		<div class="modal fade" id="add_staff_modal">
			<div class="modal-dialog" style="width: 50%;">
				<div class="modal-content">
				<form id="edit_form">
				<input type="hidden" name="module" id="edit_module" value="" />
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">修改员工</h4>
					</div>
					<div class="modal-body">
						<div class="row top_margin">
						<input type="hidden" name="id" id="sta_id" />
							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div class="">
									<font color="red">*</font>员工姓名
								</div>
								<input type="text" class="staff_ipt" name="name" id="username" value="" />
							</div>
							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div class="">
									<font color="red">*</font>联系电话
								</div>
								<input type="text" class="staff_ipt" name="phone" id="sta_phone" value="" />
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
								<div class="">
									<font color="red">*</font>地址
								</div>
								<input type="text" class="staff_ipt" name="address" id="sta_ads" value="" />
							</div>
						</div>
						<div class="row hidden">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
								分配帐号
							</div>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<div class="">
									<font color="red">*</font>登录密码
								</div>
								<input type="text" class="staff_ipt" name="" id="" value="" />
							</div>
							<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
								<div class="">
									<font color="red">*</font>登录账号
								</div>
								<input type="text" class="staff_ipt" name="" id="" value="" />
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button onclick="edit_ajax()" type="button" class="btn btn-primary">确认修改</button>
					</div>
				</div>
				</form>
			</div>
		</div>
		<!-- -------------------管理账号--------------------------- -->
		<div class="modal fade" id="admin_staff_modal">
			<div class="modal-dialog" style="width: 50%;">
				<div class="modal-content">
				<form id="admin_form">
				<input type="hidden" name="comId" id="com_Id" value="" />
				<input type="hidden" name="id" id="admin_Id" value="" />
				<input type="hidden" name="userId" id="user_Id" value="" />
				<input type="hidden" name="module" id="admin_module" value="" />
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">管理帐号</h4>
					</div>
					<div class="modal-body">	
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 top_margin">
								分配帐号
							</div>
						</div>					
						<div class="row">							
							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 top_margin">
								<div class="">
									<font color="red">*</font>登录账号
								</div>
								<input type="text" class="staff_ipt" name="username" id="user_name" value="" />
							</div>
							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 top_margin">
								<div class="">
									<font color="red">*</font>登录密码
								</div>
								<input type="text" class="staff_ipt" name="password" id="pass_word" value="" />
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button onclick="del_staff()" type="button" class="btn btn-primary">删除</button>
						<button onclick="admin_ajax()" type="button" class="btn btn-primary">确认</button>
					</div>
				</div>
				</form>
			</div>
		</div>
		
		<!-- -------------------施工员关联项目--------------------------- -->
		<div class="modal fade" id="staff_project">
			<div class="modal-dialog" style="width: 50%;">
				<div class="modal-content">
				
				
				<input type="hidden" name="userId" id="user_Id" value="" />
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
						<h4 class="modal-title">关联项目</h4>
					</div>
					<div class="modal-body">		
					<div class="row">							
							<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 top_margin">
								<div class="">
									<font color="red"></font>已关联项目:
								</div>								
							</div>
							<div class="col-xs-9 col-sm-9 col-md-9 col-lg-9 top_margin">
								<div class="" id="">
								</div>						
								<table id="in_stock" class="table-responsive" border="0">
									<thead>
										<tr>
											<td>姓名</td>
											<td>电话</td>
											<td>地址</td>
											<td>操作</td>
										</tr>
									</thead>										
								
								</table>		
							</div>
							
						</div>			
						<div class="row">							
							<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 top_margin">
								<div class="">
									<font color="red">*</font>请选择:
								</div>								
							</div>
							<form id="add_contact_form">
							<input type="hidden" name="decorationTeamId" id="add_constructor" value="" />
							<div class="col-xs-9 col-sm-9 col-md-9 col-lg-9 top_margin">
								<select name="projectId" class="form-control" id="add_project">
									<option>请选择</option>
								</select>
							</div>
							</form>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button onclick="contact_project_ajax()" type="button" class="btn btn-primary">确认</button>
					</div>
				</div>
				
			</div>
		</div>
	</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap-paginator.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/page/page_team.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/layer/layer.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
<script>
var page_btn_num;//分页条目显示7个按钮  就是7页
var total_Page;//总记录页数
var page_no;//当前页
if(sessionStorage.getItem('staff_page_no')==null){
	page_no=1;
}else{
	page_no=sessionStorage.getItem('staff_page_no');
}
$(function(){
	staff_ajax();
})
$('#sta_btn').click(function(){
	staff_ajax();
})

//得到公司id
var comId = sessionStorage.getItem('comId');
function staff_ajax(){
	var form = $.param({"pageNo":page_no })+ "&" + $.param({'comId':comId}) + '&' + $("#sea_form").serialize();
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/decorationTeam/queryList',
		async:true,
		data:form,
		dataType:'json',
		success:function(data){
			console.log(data);
			if(data.result=='' || data.result==null){
				page_no=1;
				$('#pageLimit').children().remove();
				$('#staff_tab').children().first().nextAll().remove();
				$("#staff_tab").append(
				'<tr>'+
				'<td colspan="6">暂无内容</td>'+
				'</tr>')
			}else{
			team_show(data);
			//parent.document.getElementById('page_con').style.height=document.body.scrollHeight+'px';
			}
		},
		error:function(){
			
		}
	})
}
//生成员工数据
function team_show(data){
	total_Page = data.totalPage;//将返回的总页数赋值给变量
	page_no = data.pageNo;
	sessionStorage.setItem('staff_page_no',page_no);//利用临时储存保存当前页数,刷新页面数据始终是当前页	
	if(total_Page<=7){
		page_btn_num=total_Page;
	}else{
		page_btn_num=7;
	}
	$('#staff_tab').children().first().nextAll().remove();
	console.log(data);
	$.each(data.result,function(index,item){
		$('#staff_tab').append(
		'<tr>'+
				'<td>'+((index+1)+(10*(page_no-1)))+'</td>'+
				'<td>'+item.name+'</td>'+
				'<td>'+item.phone+'</td>'+
				'<td>'+staff_type(item.module)+'</td>'+
				'<td>'+item.address+'</td>'+
				'<td>'+
					'<a class="btn staff_copyreader" href="#add_staff_modal" data-toggle="modal" onclick=edit_staff('+JSON.stringify(item)+')>编辑</a>'+
					'<a class="btn staff_copyreader"  data-toggle="modal" onclick=admin_init('+JSON.stringify(item)+')>管理</a>'+
					add_project(item)+
				'</td>'+
			'</tr>'		
		);
	});
	bootstrap_paginator(page_no,total_Page);//调用分页方法
	
}
//修改员工
function edit_staff(e){
	$('#sta_id').val(e.id);
	$('#username').val(e.name);
	$('#sta_phone').val(e.phone);
	$('#sta_ads').val(e.address);
	$('#edit_module').val(e.module);
}

//判断员工类型
function staff_type(e){
	if(e==5){
		return '施工员';
	}else if(e==2){
		return '装修设计师';
	}else if(e==3){
		return '项目经理';
	}else if(e==4){
		return '现场负责人';
	}else if(e==1){
		return '工程部经理';
	}
}
//如果是施工员则增加加关联项目的操作
function add_project(e){
	if(e.module==5){
		return '<a class="btn staff_copyreader"  data-toggle="modal" onclick=sel_project("'+e.id+'")>项目关联</a>';
	}else{
		return '';
	}
}

function sel_project(id){
	$('#add_constructor').val(id);
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/project/queryList',
		async:false,
		dataType:'json',
		data:{'userId':JSON.parse(sessionStorage.getItem('SRC_user_msg')).id,'comId':sessionStorage.getItem('comId')},
		success:function(data){
			$('#in_stock').children().first().nextAll().remove();
			$('#add_project').children().remove();
			if(data.result=="" || data.result==null){
				console.log(data);
			}else{
				$.each(data.result,function(index,item){
					$('#in_stock').append(
					is_constructor(item,'constructor',id)
					);
					$('#add_project').append(
							is_staff(item,'constructor')
					);
				})
				if($('#in_stock').children('tr').length<1){
					$('#in_stock').append('<tr>'+
							'<td colspan="4">暂无关联项目</td>'+					
							'</tr>');
				}
			}
			console.log(data);
		},
		error:function(){
			console.log("失败");
		}
	});
	$('#staff_project').modal();
}

//施工员关联项目联
function contact_project_ajax(){
	
	var form = $('#add_contact_form').serialize();
	if($('#add_project').val()==null || $('#add_project').val()==''){
		layer.msg('请选择项目',{area:['300px', '50px']});
		return false;
	}
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/decorationTeam/relationProject',
		data:form,
		dataType:'json',
		success:function(data){
			if(data.code=='00'){
				layer.msg('操作成功',{area:['300px', '50px']});
				sel_project($('#add_constructor').val())
				//$('#staff_project').modal('hide');
			}else{
				layer.msg('操作失败',{area:['300px', '50px']});
			}
			console.log(data);
			
		},
		error:function(){
			
		}
	})
}
//判断项目是否关联施工员
function is_constructor(e,str,id){
		if(e.hasOwnProperty(str)){
			if(id==e.constructorId){
			$('#in_stock').append('<tr>'+
			'<td>'+e.customerName+'</td>'+
			'<td>'+e.customerPhone+'</td>'+
			'<td>'+e.address+'</td>'+
			'<td><a class="btn staff_copyreader" onclick=del_contact("'+e.id+'","'+id+'",this)>删除</a></td>'+
			'</tr>');
			}
		}else{
			return '';
		}
	}
	
function del_contact(projectId,decorationTeamId,e){
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/decorationTeam/deleteRelation',
			data:{'decorationTeamId':decorationTeamId,'projectId':projectId},
			dataType:'json',
			success:function(data){
				console.log(data);
				if(data.code=='00'){
					layer.msg('操作成功',{area:['300px', '50px']});
					/* var this_ = $(e);
					if(this_.parent().parent().parent().children('tr').length-1<1){
						$('#in_stock').append('<tr>'+
								'<td colspan="4">暂无关联项目</td>'+					
								'</tr>');
					}
					$(e).parent().parent().remove(); */
					sel_project(decorationTeamId);
				}else{
					layer.msg('操作失败',{area:['300px', '50px']});
				}
			},
			error:function(){
				
			}
		})
	}
function is_staff(e,str){
	if(!e.hasOwnProperty(str)){
		return '<option value='+e.id+'>'+'<span class="staff_sel_project">'+e.customerName+'</span>&nbsp;&nbsp;&nbsp;'+'<span>'+e.customerPhone+'</span>'+'</span>&nbsp;&nbsp;&nbsp;'+'<span class="staff_sel_project">'+e.address+'</span>'+'</option>';
	}else{
		return '';
	}
}

var reg = /^1[0-9]{10}$/;
var str = new RegExp(reg);
function check_edit(){
	if($('#username').val().trim()==''){
		layer.msg('姓名不能为空',{area:['300px', '50px']});
		return false;
	}else if($('#sta_phone').val().trim()==''){
		layer.msg('电话不能为空',{area:['300px', '50px']});
		return false;
	}else if($('#sta_ads').val().trim()==''){
		layer.msg('地址不能为空',{area:['300px', '50px']});
		return false;
	}else if(!str.test($('#sta_phone').val().trim())){
		layer.msg('手机号格式不对',{area:['300px', '50px']});
		return false;
	}
	return true;
}
//删除数据
function del_staff(){
	if(add_modify){
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/user/removeById',
			data:{'id':$('#user_Id').val()},
			dataType:'json',
			success:function(data){
				console.log(data);
				if(data.code=='00'){
					layer.msg('删除成功',{area:['300px', '50px']});
				}else{
					layer.msg('删除失败',{area:['300px', '50px']});
				}
			}
		})
	}
	$('#admin_staff_modal').modal('hide');
}
//提交修改请求
function edit_ajax(){
	if(check_edit()){
	var form = $('#edit_form').serialize();
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/decorationTeam/modifyById',
		async:true,
		data:form,
		success:function(data){
			window.location.href="${pageContext.request.contextPath}/pages/team/athena_staff.jsp";
		},
		error:function(){
			
		}
	})
	}
	}
	
	var add_modify;//添加一个标识,判断是调用添加账号还是修改账号接口
	var user_val,pass_val;
	//管理账号
	function admin_ajax(){
		var form = $("#admin_form").serialize();
		if($('#user_name').val()=='' || $('#pass_word').val()==''){
			layer.msg('输入框不能为空',{area:['300px', '50px']});
			return false;
		}
		if(add_modify){			
			if($('#user_name').val()!=user_val || $('#pass_word').val()!=pass_val){				
				$.ajax({
					type:'post',
					url:'${pageContext.request.contextPath}/user/modifyById',
					data:form,
					dataType:'json',
					success:function(data){
						console.log(data);
						if(data.code=='00'){
							layer.msg('修改成功',{area:['300px', '50px']});
						}else{
							layer.msg(data.msg,{area:['300px', '50px']});
						}
					}
				})
			};
		}else{
			$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/decorationTeam/distributionUser',
				data:form,
				dataType:'json',
				success:function(data){
					console.log(data);
					if(data.code=='00'){
						layer.msg('添加成功',{area:['300px', '50px']});
						staff_ajax();
					}else{
						layer.msg(data.msg,{area:['300px', '50px']});
					}
				}
			})
		}	
		$('#admin_staff_modal').modal('hide');
	}
	//初始化管理账号表单
	function admin_init(item){
		if(item.module == '0' || item.module == '2' || item.module == '4'){
			layer.msg('该员工暂不能分配账号',{area:['300px', '50px']});
			return false;
		}
		$('#admin_staff_modal').modal();
		$('#admin_Id').val(item.id);
		$('#com_Id').val(item.comId);
		$('#admin_module').val(item.module);
		$('#user_Id').val(item.userId);
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/user/queryById',
			data:{'id':item.userId},
			dataType:'json',
			success:function(data){
				console.log(data);
				if(data!='' && data!=null){
					add_modify=true;
					$('#admin_Id').val(data.id);
					$('#user_name').val(data.username);
					user_val = data.username;
					$('#pass_word').val(data.password);
					pass_val = data.password;
				}else{
					add_modify=false;
					$('#user_name').val('');
					$('#pass_word').val('');
				}
			}
		})
	}
	//添加员工
	function add_staff(){
		//loadIframe('${pageContext.request.contextPath}/pages/team/athena_add_staff.jsp');
		window.location.href='${pageContext.request.contextPath}/pages/team/athena_add_staff.jsp';
	}
</script>
