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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/progress/plan_detail.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/swiper/swiper.min.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/hea_end.js" ></script>
	</head>
	<style>
	.swiper-container {
      width: 100%;
      height: 100%;
      display:inline-block;
      border-radius:4px;
    }
    .swiper-slide {
      text-align: center;
      font-size: 18px;
      background: #fff;

      /* Center slide text vertically */
      display: -webkit-box;
      display: -ms-flexbox;
      display: -webkit-flex;
      display: flex;
      -webkit-box-pack: center;
      -ms-flex-pack: center;
      -webkit-justify-content: center;
      justify-content: center;
      -webkit-box-align: center;
      -ms-flex-align: center;
      -webkit-align-items: center;
      align-items: center;
      width: 60%;
    }
    .swiper-slide:nth-child(n) {
      width: 150px;
    }
	</style>
	 <style>
  .list-name-input{
   color: #333;
   font-size: 15px;
   font-weight: bold;
   height: 50px;
   margin: 0px;
   padding: 0px;
   position: relative;
   width: 530px;
  }
  .list-name-for-select{
   border: 0;
   color: #555;
   height: 20px;
   lighting-color: rgb(255, 255, 255);
   line-height: 20px;
   margin:0 0 10px 0;
 
   outline-color: #555;
   outline-offset: 0px;
   outline-style: none;
   outline-width: 0px;
 
   padding: 4px 6px;
   position: absolute;
   top: 1px;
   left: 3px;
   vertical-align: middle;
   width: 486px;
  }
  .list-name-input-for-select:focus{
   border: 0;
   border-radius: 0;
  }
  .list-select{
   background-color: #FFF;
   border:1px #ccc solid;
   border-radius: 4px;
   color: #555;
   cursor: pointer;
   margin:0 0 10px 0;
   padding: 4px 6px;
   position: absolute;
   top: 0px;
   vertical-align: middle;
   white-space: pre;
   width: 90%;
   height: 34px;
  }
  #plan_detail_name{
  position: relative;
    left: 0;
    width: 90%;
}
#edit_plan_detail_name{
position: relative;
    left: 0;
    width: 90%;
}
 </style>
	<body id="1">
	<div class="footer">
			
		</div>
		<div class="current_position">
			<div class="nav_position">
				当前位置：<a href="${pageContext.request.contextPath}/pages/project/athena_project.jsp">在建项目</a>&gt;
				<a href="#">进度详情</a>
			</div>			
		</div>
		<div class="page_wid">
			<div class="plan_con">
				<div class="plan_copyreader">
					<button class="btn btn-primary" id="plan_edit">编辑</button>
					<button id="back" class="btn btn-primary" style="display: none;">返回</button>
				</div>				
				<div id="plan_traverse" class="plan_box clearfix">
					<!-- --------------------------------------------------- -->
					<div class="swiper-container left" id="swiper_wid">
						<div class="swiper-wrapper">
							<!-- <div class="swiper-slide">
					 <a href="">
						<div class="plan_msg">
							<div class="plan_num">
								01
							</div>						
							<div class="plan_sta_1">
								<div class="plan_name">
									墙体改造
								</div>
								<div class="bfb">
									100%
								</div>
								<div class="progress progress_height">
								    <div class="progress-bar" role="progressbar" aria-valuenow="60" 
								        aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
								        <span class="sr-only">40% 完成</span>
								    </div>
								</div>
								<div class="plan_result">
									完成
								</div>
							</div>	
							<div class="pro_hide plan_sta_2">
								<div class="plan_name">
									<input type="text" class="block_force form-control " name="" id="" value="墙体改造" />
								</div>
								<div class="top_margin">
									<span>启动时间</span><br />
									<span class="time_css">2019-1-10</span>
								</div>
								<div class="top_margin">
									<span>计划时间</span><br />
									<span class="time_css">2019-1-10</span>
								</div>
							</div>
						</div>
					</a> 
					</div>	-->				
				</div>
				</div>	
				<div class="swiper-button-next" style="right:10%;top:50%"></div>
   				<div class="swiper-button-prev" style="left:10%;top:50%"></div>				
					<!-- --------------------------------------------------- -->
					<!-- --------------------------------------------------- -->
					<div id="add_plan_btn" class="add_plan_box" data-toggle="modal" data-target="#add_plan_modal" style="display: none;">
						<div class="plan_msg">
							<i class="fa fa-plus-square-o add_plan_icon" style=""></i>
							<div class="top_margin">
								添加进度
							</div>
						</div>						
					</div>
					<!-- --------------------------------------------------- -->
				</div>
				<div class="pro_hide save_plan hide">
					<input type="button" class="btn btn-success" value="保存" />
				</div>				

			<!-- --------------------------------------- -->
			<div class="modal fade" id="add_plan_modal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h4 class="modal-title">添加进度</h4>
						</div>
						<div class="modal-body">
						<form action="" id="add_plan_form">
						<input type="hidden" name="proId" id="proId">
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									流程名：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
								 <select type="text" class="list-select" id="list-select">
								 <option value=""></option>
     							</select>
								<input type="text" class="form-control" name="name" id="plan_detail_name" value="" />
								 <script>
								  var listName = document.getElementById('plan_detail_name');
								  document.getElementById('list-select').onchange = function(e){
								   if(this.value){
								    listName.value = this.options[this.selectedIndex].text;
								   }else{
								    listName.value = ''
								   }
								  };
								 </script>
								</div>
							</div>
							<input type="hidden" name="status" value="1">
							
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									启动时间：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<input type="text" readonly="readonly" class="form-control" name="procStartTime" id="proc_StartTime" value="" />
								</div>
							</div>
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									计划结束时间：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<input type="text" readonly="readonly" class="form-control" name="procEndTime" id="proc_EndTime" value="" />
								</div>
							</div>
							</form>
						</div>
						<div class="modal-footer">
							<button class="btn btn-default" data-dismiss="modal">关闭</button>
							<button class="btn btn-primary" id="sub_add_plan">确认</button>
						</div>
					</div>
				</div>
			</div>
			<!-- ------------------------------------------------ -->
			<!-- ------------------------------------------------ -->
			<div class="modal fade" id="edit_plan_modal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h4 class="modal-title">修改进度</h4>
						</div>
						<div class="modal-body">
						<form action="" id="edit_plan_form">
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									流程名：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
								<select type="text" class="list-select" id="list-select1">
								<option value=""></option>
     							</select>
									<input type="text" class="form-control" name="name" id="edit_plan_detail_name" value="" />
								</div>
							</div>
							<script>
								  var listName1 = document.getElementById('edit_plan_detail_name');
								  document.getElementById('list-select1').onchange = function(e){
								   if(this.value){
								    listName1.value = this.options[this.selectedIndex].text;
								   }else{
								    listName1.value = ''
								   }
								  };
								 </script>
							<input type="hidden" name="status" value="1">
							<input type="hidden" name="accept" value="1">
							<input type="hidden" id="edit_id" name="id" value="">
							
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									启动时间：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<input type="text" readonly="readonly" class="form-control" name="procStartTime" id="edit_proc_StartTime" value="" />
								</div>
							</div>
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									计划结束时间：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<input type="text" readonly="readonly" class="form-control" name="procEndTime" id="edit_proc_EndTime" value="" />
								</div>
							</div>
							</form>
						</div>
						<div class="modal-footer">
							<button class="btn btn-default" data-dismiss="modal">关闭</button>
							<button class="btn btn-primary" id="sub_edit_plan">确认修改</button>
						</div>
					</div>
				</div>
			</div>
			<!-- ------------------------------------------------ -->
			</div>
		</div>
		
		<!-- 启动进度模态框 -->
		<div class="modal fade" id="start_plan_modal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">是否启动进度</h4>
					</div>
					<div class="modal-body text-center">
					将会告知业主流程已启动
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-default left" data-dismiss="modal">取消</button>
					<button type="button" id="start_plan_com" class="btn btn-primary">确认</button>
				</div>
				</div>
			</div>
		</div>
		<!-- ---------------------- -->
		<!-- 启动进度模态框 -->
		<div class="modal fade" id="msg_modal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">消息提醒</h4>
					</div>
					<div id="msg_text" class="modal-body text-center">
					
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-default left" data-dismiss="modal">取消</button>
					<button type="button" data-dismiss="modal" class="btn btn-primary">确认</button>
				</div>
				</div>
			</div>
		</div>
		<!-- ---------------------- -->
		<div class="end">
				
			</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/format.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/laydate/laydate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time_examples.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assess/Assessment_time.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/layer/layer.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time-difference.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
	<script>
	
	var proid = $.query.get("id");
	proid = decodeURIComponent(proid);
	
	//=================================

	//=================================
	$(function(){
		//滑块数据
	queryList_ajax();
	//流程名集合
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/procedureTemplate/queryList',
		async:true,
		success:function(data){
			console.log(data);
			$.each(JSON.parse(data),function(index,item){
				$('#list-select').append('<option value="0">'+
			             item.name+
			             '</option>');
				$('#list-select1').append('<option >'+
			             item.name+
			             '</option>');
			})
		},
		error:function(){
			
		}
	})
	});
	
	//====滑块数据
		function queryList_ajax(){
			$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/procedure/queryList',
				async: true,
				data: {'proId':proid},
				dataType:'json',
				success:function(data){
					console.log(data);
					$('.swiper-wrapper').children().remove();
					if(data.result.length>0){
					$.each(data.result,function(index,item){
						$('.swiper-wrapper').append(
								'<div class="swiper-slide">'+
								div_show(item)+
							'<div class="plan_msg">'+
									'<div class="plan_num">'+
									swiper_num(index)+
									'</div>'+
									'<div class="plan_name">'+
									item.name+
									'</div>'+
								'<div class="plan_sta_1">'+							
									'<div class="bfb">'+
											item.process+
										'%</div>'+
										'<div class="progress progress_height">'+
									    '<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: '+item.process+'%;">'+
									        '<span class="sr-only"></span>'+
									    '</div>'+
									'</div>'+
									'<div class="plan_result">'+
									plan_status(item,item.status,item.id,item.accept)+
									'</div>'+
									msg_unread(item)+
									'<div class="plan_result">'+detail_time_Assess(item.timeAssess)+'</div>'+
								'</div>'+
								'<div class="pro_hide plan_sta_2">'+
								/* '<div class="plan_name">'+
								'<input type="text" class="block_force form-control " name="" id="" value="'+item.name+'" />'+
								'</div>'+ */
								'<div class="top_margin">'+
								'<span>启动时间</span><br />'+
								'<span class="time_css">'+getFormatDate(item.procStartTime)+'</span>'+
								'</div>'+
								'<div class="top_margin">'+
								'<span>计划时间</span><br />'+
								'<span class="time_css">'+getFormatDate(item.procEndTime)+'</span>'+
								'</div>'+
								'</div>'+	
								'</div>'+
							'</a>'+
							'</div>'
						);
						
						});			
				}else{
					$('.swiper-wrapper').append('<div class="none_con">暂无内容</div>');
				}
					swiper_();
					random_color();
				},
				error:function(){
					console.log('请求失败');
				}
			})		
	}
//添加流程请求
$('#sub_add_plan').click(function(){	
	var this_ = $(this);
	if(check_add_plan($('#plan_detail_name'),$('#proc_StartTime'),$('#proc_EndTime'))){	
		$('#proId').val(proid);
		var form = $('#add_plan_form').serialize();
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/procedure/increase',
			data:form,
			async: true,
			beforeSend: function () {
		        // 禁用按钮防止重复提交
				 this_.attr({ disabled: "disabled" });
		    },
			success:function(){
				window.location.reload();
			},
			error:function(){
				console.log('请求添加流程失败');
			}
		})
	}
});

var start_plan_id;//保存进度id
//弹出确认框
function start_modal(e,id){
	stopDefault(e);
	start_plan_id=id;//将进度d赋给全局变量
}
//启动流程
$('#start_plan_com').click(function(){
	var this_ = $(this);
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/progress/increase',
		data:{"progressRecord.promptType":JSON.parse(sessionStorage.getItem('SRC_user_msg')).type,"procId":start_plan_id,"type":"1","progressRecord.promptMsg":"项目已启动"},
		async:false,
		beforeSend: function () {
	        // 禁用按钮防止重复提交
			 this_.attr({ disabled: "disabled" });
	    },
		success:function(){
			window.location.reload();
		},
		error:function(){
			console.log('启动流程失败');
		}
})
})

//消息提醒弹出框返回内容
	 function ContentMethod(con) {
		 if(con!=''){
			   /* return '<table class="table table-bordered">'+
		     		 '<tr><td>'+con+'</td></tr>' +
		             '</table>';  */
		          return '<div style="width:200px;">12312</div>'
		 }
     };
     
     function popover_(e,con){
    	 e.popover({
    		 trigger: 'manual',
             placement: 'top', //top, bottom, left or right
             title: '原因',
             html: true,
             content: ContentMethod(con),
    	 }).on("mouseenter", function () {
             var _this = this;
             $(this).popover("show");
             $(this).siblings(".popover").on("mouseleave", function () {
                 $(_this).popover('hide');
             });
         }).on("mouseleave", function () {
             var _this = this;
             setTimeout(function () {
                 if (!$(".popover:hover").length) {
                     $(_this).popover("hide")
                 }
             }, 100);
         });
     }

//根据状态码返回状态
	function plan_status(item){
	if(item.accept=="3"){
		return '<font class="red_size">未通过原因</font>'+
		msg_img(item)+
		'<button data-toggle="modal" data-target="#edit_plan_modal" onclick=edit_modal(this,'+JSON.stringify(item)+') class="btn btn-primary">修改信息</button>';
	}else if(item.accept=="1"){
		return "<font color='#000'>待审核</font>";
	}else if(item.accept=="2"){
		if(item.status=="1"){
			return '<font color="#669900">已审核</font>'+
			msg_img(item)+
			'<button data-toggle="modal" data-target="#start_plan_modal" onclick=start_modal(this,"'+item.id+'") class="btn btn-primary">启动进度</button>';
		}else if(item.status=="2"){
			return "装修中";
		}else if(item.status=="3"){
			return "已完成";
		}
	}		
	}

//根据notice值追加相应状态的图片
function msg_img(item){
	if(item.notice==2){
		return '<img id="'+item.id+'" onclick=pass('+JSON.stringify(item)+',this) src="${pageContext.request.contextPath}/public/img/read.png" class="bell_on" data-toggle="modal" data-target="#msg_modal" /><br>';
	}else{
		return '<img id="'+item.id+'" onclick=pass('+JSON.stringify(item)+',this) src="${pageContext.request.contextPath}/public/img/unread.png" class="bell_on" data-toggle="modal" data-target="#msg_modal"/><br>';
	}
}
	
//消息提醒模态框
function pass(item,e){
	$('#msg_text').text('');
	if(item.accept=="3"){
		if(item.hasOwnProperty("refuseMsg")){
			$('#msg_text').text(item.refuseMsg);
		}else{
			$('#msg_text').text('无');
		}
	}else if(item.status=="1"){
		$('#msg_text').text('客户审核通过');
	}
	
	edit_notice(item,e);//更改消息状态
}

//提取更改消息状态的请求
function edit_notice(item,e){
	if(item.notice!=2){//为2的时候表示消息已读,此时就不需要发起请求
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/procedure/modifyById',
			data:{'id':item.id,'notice':2},
			async:true,
			success:function(data){
				queryList_ajax();
				console.log(data);
			},
			error:function(){
				
			}
		})
	}
}



//消息未读
function msg_unread(item){
	if(item.notice==1 && item.accept!="3"){
		return '<div class="plan_result"><font class="red_size">有消息未读</font></div>';
	}else{
		return '';
	}
}
//修改流程信息
function edit_modal(e,item){
	$('#edit_id').val(item.id);
	$('#edit_plan_detail_name').val(item.name);
	$('#edit_proc_StartTime').val(getFormatDate(item.procStartTime));
	$('#edit_proc_EndTime').val(getFormatDate(item.procEndTime));
}
//修改流程后提交请求,返回给用户重新审核
$('#sub_edit_plan').click(function(){
	var this_ = $(this);
	if(check_add_plan($('#edit_plan_detail_name'),$('#edit_proc_StartTime'),$('#edit_proc_EndTime'))){			
	var form = $.param({'proId':proid}) + '&' + $('#edit_plan_form').serialize();
	$.ajax({
		type: 'post',
		url: '${pageContext.request.contextPath}/procedure/reLaunch',
		data: form,
		async:true,
		beforeSend: function () {
	        // 禁用按钮防止重复提交
			 this_.attr({ disabled: "disabled" });
	    },
		success:function(data){
			if(data.code=='00'){
				window.location.reload();
			}
			else
				layer.msg(data.msg,{area:['300px', '50px']});
		},
		complete: function () {
	        this_.removeAttr("disabled");
	    },
		error:function(){
			console.log('no');
		}
	})
}
})
//滑块编号处理
function swiper_num(e){
	e = parseInt(e);
	e=e+1;
	if(e<10){
		e='0'+e;
	}
	return e+"";
}

//根据assess判断元素是否置灰
function div_show(item){
	if(item.accept=="1"){
		return '<a class="disable" href="'+a_href(item.id,item.name,item.procStartTime,item.status,item.procEndTime)+'">';
	}else{
		//href="'+a_href(item.id,item.name,item.procStartTime,item.status)+'"
		//onclick='+a_href(item.id,item.name,item.procStartTime,item.status)+'
		return '<a href="'+a_href(item.id,item.name,item.procStartTime,item.status,item.procEndTime)+'">';
	}
}

	$("#plan_edit").click(function(){
		$('#swiper_wid').css('width','80%');
		swiper_();
		$('.plan_sta_1').hide();
		$('.plan_sta_2').show();
		$('.save_plan').show();
		$('#add_plan_btn').show();
		$('#back').show();
		$(this).hide();
		$('.swiper-slide').children('a').click(function(e){
			e.preventDefault();
		});		
	});
	$('#back').click(function(){
		$('#swiper_wid').css('width','100%');
		$('#plan_edit').show();
		$(this).hide();
		$('.plan_sta_1').show();
		$('.plan_sta_2').hide();
		$('.save_plan').hide();
		$('#add_plan_btn').hide();
		$('.swiper-slide').children('a').unbind("click");
		swiper_();
	});
	
	//a标签链接处理
	function a_href(id,name,time,code,endTime){
		if(code!="1"){
			 /* loadIframe("${pageContext.request.contextPath}/pages/progress/athena_plan_stage.jsp?id="+
					id+"&name="+name+"&time="+time);  */
			  return '${pageContext.request.contextPath}/pages/progress/athena_plan_stage.jsp?id='+
			id+'&name='+name+'&time='+time+'&endTime='+endTime+'&code='+code;  
		}else{
			return 'javascript:;';
			//return false;
		}		
		//${pageContext.request.contextPath}/pages/progress/athena_plan_stage.jsp?
	//id='+item.id+'&name='+item.name+'&time='+item.procStartTime+'
	}
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/swiper/swiper.min.js" ></script>
<script>
    function swiper_(){
    	var swiper = new Swiper('.swiper-container', {
    	      slidesPerView: 'auto',//auto表示自动显示滑块数量,也可以自定义数字显示数量    	      centeredSlides: false,//true表示第一个滑块局中,默认靠左对齐
    	      spaceBetween: 30,
    	      pagination: {
    	        el: '.swiper-pagination',
    	        clickable: true,
    	      },
    	        navigation: {
    	        nextEl: '.swiper-button-next',
    	        prevEl: '.swiper-button-prev',
    	      },
    	    });
    }
    //遍历滑块，给下边框设置不同颜色色
    function random_color(){
    	var num = $('.plan_msg').length;
    	for(var i=0; i<num; i++){
    		$('.plan_msg:eq('+i+')').css('border-bottom-color',bg());
    	}
    }
//随机生成颜色
    function bg(){
        return '#'+Math.floor(Math.random()*0xffffff).toString(16);
    }
    
    //阻止启动进度按钮默认事件跟冒泡事件
    function stopDefault( e ) { 
    //阻止默认浏览器动作(W3C) 
    if ( e && e.preventDefault ) 
        e.preventDefault(); 
    //IE中阻止函数器默认动作的方式 
    else 
        window.event.returnValue = false; 
    return false; 
}
  </script>
</html>
