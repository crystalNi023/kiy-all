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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/archives/sponsor.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/date/ion.calendar.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/home/header_end.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/hea_end.js" ></script>		
	</head>
	<body id="2">
	<div class="footer">
				
			</div>
		<div class="current_position">
			<div class="nav_position">
				当前位置：<a href="${pageContext.request.contextPath}/pages/customer/athena_archives.jsp">客户档案</a>&gt;
				<a href="${pageContext.request.contextPath}/pages/customer/athena_sponsor_project.jsp">发起项目</a>
			</div>			
		</div>
		<div class="page_wid">
				<div class="project_data">
					项目资料
				</div>
				
				<form id="add_pro_form">
				<div class="project_form">
					<div class="row top_padding">
					
					<input type="hidden" name="cusId" id="cId" />
						<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
							<div class="">
								<font color="red">*</font>客户
							</div>
							<!--<select name="" class=" project_ipt">
								<option value="">请选择</option>
							</select>-->
							<input type="text" readonly="readonly" class="project_ipt" name="customerName" id="cus_name" value="" />
							<input type="text" readonly="readonly" class="project_ipt" name="" id="cus_phone" value="" />							
						</div>
						<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
							<div class="">
								<font color="red">*</font>项目经理	
							</div>
							<select name="decoration" class="project_ipt" id="decoration">
								<option>请选择</option>
							</select>
							<input readonly="readonly" type="text" class="project_ipt" name="decorationPhone" id="decoration_phone" value="" />
							<br><font color="red" id="decoration_msg"></font>
							<input type="hidden" class="project_ipt" name="decorationId" id="decoration_id" value="" />
						</div>
						<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
							<div class="">
								<font color="red">*</font>项目装修设计师							
							</div>
							<select name="design" class="project_ipt" id="design">
								<option>请选择</option>
							</select>
							<input readonly="readonly" type="text" class="project_ipt" name="designPhone" id="design_phone" value="" />
							<input type="hidden" class="project_ipt" name="designId" id="design_id" value="" />
							<br><font color="red" id="design_msg"></font>
						</div>
						<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
							<div class="">
								<font color="red">*</font>现场负责人								
							</div>
							<select name="spot" class="project_ipt" id="spot">
								<option>请选择</option>
							</select>
							<input readonly="readonly" type="text" class="project_ipt" name="spotPhone" id="spot_phone" value="" />
							<input type="hidden" class="project_ipt" name="spotId" id="spot_id" value="" />
							<br><font color="red" id="spot_msg"></font>
						</div>
						<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
							<div class="">
								<font color="red">*</font>装修类型
							</div>
							<div class="row top_margin">
								<div class="radio-inline col-xs-5 col-sm-5 col-md-5 col-lg-5">
							    <label>
							        <input type="radio" name="type" id="optionsRadios1" value="2" checked> 整装
							    </label>
							</div>
							<div class="radio-inline col-xs-5 col-sm-5 col-md-5 col-lg-5">
							    <label>
							        <input type="radio" name="type" id="optionsRadios1" value="1" checked> 基装
							    </label>
							</div>
							</div>						
							<!--<button class="btn btn-primary type_btn">整装</button>
							<button class="btn btn-default type_btn">基装</button>-->
						</div>
					</div>
					<!-- -------------------------------------------------------------- -->
					<div class="row top_margin">
						<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
							<div class="">
								<font color="red">*</font>计划开工时间
							</div>
							<input type="text" name="planStartTime" placeholder="点击选择时间" class="project_ipt" readonly="readonly" id="add_plan_start" />
						</div>
						<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
							<div class="">
								<font color="red">*</font>计划完成时间
							</div>
							<input type="text" name="planEndTime" placeholder="点击选择时间" class="project_ipt" readonly="readonly" id="add_plan_end" />
						</div>
					</div>					
					<div class="row top_margin">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
							<div class="">
								装修流程
							</div>
							<!-- <div class="top_margin">工期：<span></span></div> -->
							<div class="add_process">
								<div  class="process_name_par">
								<div class="block">
									<i id="flow_find" data-toggle="modal" data-target="#add_process_modal" class="fa fa-plus-square-o" aria-hidden="true"></i>
									<span class="add_plan_text">添加流程</span>
								</div>
								</div>		
								<!-- ------------------------------------------------- -->							
						</div>
						</div>						
					</div>
					<div class="row top_margin">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center">
							<input type="button" id="whether_start" class="btn btn-success sponsor_btn" value="发起项目">
						</div>
					</div>		
				<!-- ------------------------确认提交模态框--------------------------------- -->
					<div class="modal fade" id="sub_modal">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
										<button class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h4 class="modal-title">是否发起项目</h4>
									</div>
								<div class="modal-body">									
								</div>
								<div class="modal-footer clearfix">
									<button class="btn btn-default left" data-dismiss="modal">关闭</button>
									<input type="button" id="sub_add_pro" class="btn btn-primary" value="确认">
								</div>
							</div>							
						</div>
					</div>
				<!-- ------------------------------------------------------------------ -->		
				</div>	
			</form>					
			</div>	
			<!-- --------------------------------------------------------------------- -->
			<div class="modal fade"  id="add_process_modal">
				<div class="modal-dialog" style="width: 40%;">
					<div class="modal-content">
						<div class="modal-body">
							<div class="modal-header">
								<button class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								<h4 class="modal-title">添加流程</h4>
							</div>
							<div class="modal-body">
								<div class="row">
									<div class="row top_margin">
										<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
											流程名：
										</div>
										<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
											<input type="text" id="flow_name" class="form-control" name=""  value="" placeholder="请输入流程名" />
										</div>	
									</div>
									<div class="row top_margin">
										<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
											计划启动时间：
										</div>
										<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 ">
											<input type="text"  class="form-control" name="" id="start_time" value="" placeholder="点击选择启动时间" readonly="readonly" />
										</div>
									</div>	
									<div class="row top_margin">
										<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
											计划结束时间：
										</div>									
										<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
											<input type="text"  class="form-control" name="" id="end_time" value="" placeholder="点击选择结束时间" readonly="readonly" />
										</div>
									</div>													
								</div>														
							</div>
							<div class="modal-footer">
								<button class="btn btn-default" data-dismiss="modal">关闭</button>
								<button onclick="add_flow_name()" class="btn btn-primary">确认添加</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- ------------------------------------- -->
			<div class="modal fade"  id="edit_process_modal">
				<div class="modal-dialog" style="width: 40%;">
					<div class="modal-content">
						<div class="modal-body">
							<div class="modal-header">
								<button class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								<h4 class="modal-title">添加流程</h4>
							</div>
							<div class="modal-body">
								<div class="row">
									<div class="row top_margin">
										<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
											流程名：
										</div>
										<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
											<input type="text" id="edit_flow_name" class="form-control" name=""  value="" placeholder="请输入流程名" />
										</div>	
									</div>
									<div class="row top_margin">
										<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
											计划启动时间：
										</div>
										<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 ">
											<input type="text"  class="form-control" name="" id="edit_start_time" value="" placeholder="点击选择启动时间" readonly="readonly" />
										</div>
									</div>	
									<div class="row top_margin">
										<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
											计划结束时间：
										</div>									
										<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
											<input type="text"  class="form-control" name="" id="edit_end_time" value="" placeholder="点击选择结束时间" readonly="readonly" />
										</div>
									</div>													
								</div>														
							</div>
							<div class="modal-footer">
								<button class="btn btn-default" data-dismiss="modal">关闭</button>
								<button id="edit_flow" class="btn btn-primary">确认添加</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="end">
				
			</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/laydate/laydate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time_examples.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time-difference.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/layer/layer.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/format.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
<script>
//parent.document.getElementById('page_con').style.height=document.body.scrollHeight+'px';	
var cusid;
var comId;
$(function(){
	
	comId = sessionStorage.getItem('comId');
	
	//====================
	cusid = $.query.get("id");
	cusid = decodeURIComponent(cusid);
	$('#cId').val(cusid);
	
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/customer/queryById',
		async:true,
		data:{'id':cusid},
		dataType:'json',
		success:function(data){
			console.log(data);
			$('#cus_name').val(data.name);
			$('#cus_phone').val(data.phone);
		},
		error:function(){
			
		}
	})
	
	//==========================
		$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/decorationTeam/queryList',
		async:true,
		data:{'comId':comId},
		dataType:'json',
		success:function(data){
			console.log(data);
			$.each(data.result,function(index,item){
				 if(item.module=='2'){
					$('#design').append(
							'<option value="'+item.name+'">'+item.name+'</option>'		
							);					
					design.push(item);
				}else if(item.module=='3'){
					$('#decoration').append(
							'<option value="'+item.name+'">'+item.name+'</option>'		
							);
					decoration.push(item);
				}
				else if(item.module=='4'){
					$('#spot').append(
							'<option value="'+item.name+'">'+item.name+'</option>'		
							);
					spot.push(item);
				}
				//=============================
					
			});
			if(design.length==0){
				$('#design_msg').text('请到团队管理页面添加装修设计师');
			}
			if(decoration.length==0){
				$('#decoration_msg').text('请到团队管理页面添加项目负责人');
			}
			if(spot.length==0){
				$('#spot_msg').text('请到团队管理页面添加现场负责人');
			}
		},
		error:function(){
			
		}
	})
});
var design=[]//设计师
var decoration=[]//项目经理
var spot=[]//现场负责人

//------------------------------------
 
$('#design').change(function(){
	var num = $(this).get(0).selectedIndex;
	if(num==0){
		$('#design_phone').val('');
		$('#design_id').val('');
	}else{
		$('#design_phone').val(design[num-1].phone);
		$('#design_id').val(design[num-1].id);
	}
})  
$('#decoration').change(function(){ 
	var num = $(this).get(0).selectedIndex;
	if(num==0){
		$('#decoration_phone').val('');
		$('#decoration_id').val('');
	}else{
		$('#decoration_phone').val(decoration[num-1].phone);
		$('#decoration_id').val(decoration[num-1].id);
	}
})  
$('#spot').change(function(){ 
	var num = $(this).get(0).selectedIndex;
	if(num==0){
		$('#spot_phone').val('');
		$('#spot_id').val('');
	}else{
		$('#spot_phone').val(spot[num-1].phone);
		$('#spot_id').val(spot[num-1].id);
	}
})  
//-------------------------------------

$('#add_process_modal').modal({backdrop: 'static', keyboard: false,show:false});

	
var list = [];
function add_flow_name(){
	if(check_add_plan($('#flow_name'),$('#start_time'),$('#end_time'))){		
		$('#add_process_modal').modal('hide');
		var obj={};
		obj["name"]=$('#flow_name').val();
		obj["procStartTime"]=$('#start_time').val();
		obj["procEndTime"]=$('#end_time').val();
		list.push(obj);
		var flow_val = $('#flow_name').val();
		$('#flow_find').parent().before(
			'<div onclick=process_click(this) id="'+(list.length-1)+'" data-toggle="modal" data-target="#edit_process_modal" class="add_process_name">'+flow_val+'</div>'+
			'<div id="" class="add_process_name_icon">'+
					'<i class="fa fa-long-arrow-right" aria-hidden="true"></i>'+
			'</div>'
		);
		$('#flow_name').val('');
		$('#start_time').val('');
		$('#end_time').val('');
	}
};
//流程名点击事件
function process_click(e){
	parent.scrollTo(0,0);
	var num = e.id;
	$('#edit_flow_name').val(list[num].name);
	$('#edit_start_time').val(list[num].procStartTime);
	$('#edit_end_time').val(list[num].procEndTime);
	//这里给按钮绑定函数是先解除上一次的绑定,不然会重复绑定点击事件
	$('#edit_flow').off("click").on("click",function(){edit_flow_name(e,num)});
}
//编辑流程
function edit_flow_name(e,num){
	if(check_add_plan($('#edit_flow_name'),$('#edit_start_time'),$('#edit_end_time'))){
		list[num].name=$('#edit_flow_name').val();
		list[num].procStartTime=$('#edit_start_time').val();
		list[num].procEndTime=$('#edit_end_time').val();
		$(e).text($('#edit_flow_name').val());
		$('#edit_process_modal').modal('hide');
	}
}

//发起项目前判断填写规则决定是否打开模态框
$('#whether_start').click(function(){
	if($('#add_plan_start').val()==""){
		layer.msg('选择开始时间',{area:['300px', '50px']});
		return false;
	}else if($('#add_plan_end').val()==""){
		layer.msg('选择结束时间',{area:['300px', '50px']});
		return false;
	}else if($('#decoration_phone').val()==""){
		layer.msg('选择项目经理',{area:['300px', '50px']});
		return false;
	}else if($('#design_phone').val()==""){
		layer.msg('选择项目装修设计师',{area:['300px', '50px']});
		return false;
	}else if($('#spot_phone').val()==""){
		layer.msg('选择现场负责人',{area:['300px', '50px']});
		return false;
	}
	$('#sub_modal').modal('show');
	parent.scrollTo(0,0);
})
//发起项目请求
$('#sub_add_pro').click(function(){
	//console.log(list[0].name);
	var project={};
	project['cusId']=$('#cId').val();
	project['customerName']=$('#cus_name').val();
	//project['decoration']=$('#decoration').val();
	//project['decorationPhone']=$('#decoration_phone').val();
	//project['design']=$('#design').val();
	//project['designPhone']=$('#design_phone').val();
	//project['spot']=$('#spot').val();
	//project['spotPhone']=$('#spot_phone').val();
	project['type']=$('input[name="type"]:checked').val(); 
	project['planStartTime']=$('#add_plan_start').val();
	project['planEndTime']=$('#add_plan_end').val();
	project['designId']=$('#design_id').val();
	project['decorationId']=$('#decoration_id').val();
	project['spotId']=$('#spot_id').val();
	//project={'cusId':$('#cId').val(),'planStartTime':$('#planStartTime').val(),'planEndTime':$('#planEndTime').val()};
	project['listProcedure']=list; 
	project['comId'] = comId;
	console.log("请求开始");
	//var form =  $.param({"listProcedure":JSON.stringify(list)}) + "&" +  $("#add_pro_form").serialize() + "";
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/project/increase',
		contentType: "application/json; charset=utf-8",
		async: true,
		data: JSON.stringify(project),
		success:function(data){
			list = [];
			console.log(data);
			if(data.code=='01'){
				layer.msg(data.msg,{area:['300px', '50px']});
				return false;
			}else{
				window.location.href="${pageContext.request.contextPath}/pages/customer/athena_archives.jsp";	
			}			
		},
		error:function(){
			layer.msg('添加项目失败',{area:['300px', '50px']});
		}
	})
})

</script>
</html>