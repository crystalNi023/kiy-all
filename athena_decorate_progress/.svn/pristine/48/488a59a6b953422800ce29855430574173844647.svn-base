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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/progress/plan_stage.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/hea_end.js" ></script>
	</head>
<body id="1">
<div class="footer">
			
		</div>
		<div class="current_position">
			<div class="nav_position">
				当前位置：<a href="${pageContext.request.contextPath}/pages/project/athena_project.jsp">在建项目</a>&gt;
				<a href="#" id="detail_url">进度详情</a>&gt;
				<a href="#" id="plan_stage_name"></a>
			</div>			
		</div>
		<div class="page_wid">
			<div class="plan_header">
				<span id="page_plan_name" class="plan_stage_nav">
				
				</span>
				<span style="margin:20px;">
					<span>计划启动时间：</span>
					<span id="paln_start_time"></span>
				</span>	
				<span style="margin:20px;">		
					<span>计划完成时间：</span>
					<span id="paln_end_time"></span>
				</span>
			</div>			
		</div>
		<div class="clearfix page_wid top_margin">
			<div class="left plan_dialogue clearfix">
				<div class="right clearfix plan_record" id="plan_msg" style="">
					<!--  --------------------------------------------------  -->
				<!-- 	<div class="clearfix">
					<div class="left plan_than">
						0%
					</div>
					<div class="left top_margin plan_left_time">
						<div class="">
							<font color="">进度提示</font> 2018-01-01
						</div>
						<div class="talk_con">
							<span style="color: #5e6063;">客户:</span><br />
							<span>因装修材料不够，计划延期3天，预计01月06日完成。</span>
						</div>
						<span> <font color="#0171C5">业主已审核</font></span>
					</div><br />
					<div class="right top_margin plan_right_time">
						<div class="text-right">
							2018-01-01
						</div>
						<div class="talk_con">
							<span style="color: #5e6063;">客户:</span><br />
							<span>因装修材料不够，计划延期3天，预计01月06日完成。</span>
						</div>
					</div>
				</div> -->
				<!-- ------------------------------------------------------- -->


		
				</div>
		</div>
			<div  id="operation_btn" class="right stage_fun">
				<button class="btn btn-default" data-toggle="modal" data-target="#plan_tip_modal">进度提示</button><br />
				<button class="btn btn-default" data-toggle="modal" data-target="#plan_delay_modal">整改/延期申请</button><br />
				<button class="btn btn-default" data-toggle="modal" data-target="#plan_confirm_modal ">验收申请</button>
			</div>
		</div>
		
		<!-- -------- -->
		<div class="modal fade" id="plan_tip_modal">
			<div class="modal-dialog">
				<div class="modal-content">
				<form action="" id="pro_form">
				<input type="text" class="hidden"/>
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">进度</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-lg-4 col-md-4 text-right">
								完成度：
							</div>
							<div class="col-lg-4 col-md-4 ">
								<input type="text" class="form-control plan_num"  name="schedule" id="schedule_con" value="" />
							</div>
							<div class="col-lg-4 col-md-4 ">
								%
							</div>
							<div class="col-lg-12 col-md-12 text-center">
								<font color="red"><span id="tip_msg"></span></font>
							</div>
						</div>
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<input type="button" id="add_pro" class="btn btn-primary" value="确认">
				</div>
				</form>
				</div>
			</div>
		</div>
		<!-- -------- -->
		<!-- -------- -->
		<div class="modal fade" id="plan_delay_modal">
			<div class="modal-dialog" style="width: 40%;">
				<div class="modal-content">
				<form id="delay_form">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">整改/延期申请</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-lg-4 col-md-4 text-right">
								完成度：
							</div>
							<div class="col-lg-4 col-md-4 ">
								<input type="text" class="form-control plan_num" name="schedule" id="apply_sch" value="" />
							</div>
							<div class="col-lg-4 col-md-4 ">
								%
							</div>
						</div>
						<div id="sch_apply_msg" style="color:red" class="row top_margin text-center">
						<!--  提示信息  -->
						</div>
						<div class="row top_margin">
							<div class="col-lg-4 col-md-4 text-right">
								项目计划完成时间：
							</div>
							<div class="col-lg-8 col-md-8 text-left">								
								<input id="stat_time" type="text" readonly="readonly" class="form-control">
							</div>
						</div>
						<div class="row top_margin">
							<div class="col-lg-4 col-md-4 text-right">
								项目实际完成时间：
							</div>
							<div class="col-lg-8 col-md-8 text-left">
							<input type="text" id="end_time" readonly="readonly" class="form-control">							
							</div>
						</div>
						<div class="row top_margin">
							<div class="col-lg-4 col-md-4 text-right">
								是否延期：
							</div>
							<div class="col-lg-4 col-md-4">
								<input type="text" name="progressRecord.timeAssess" id="timeAssess" readonly="readonly" class="form-control">	
							</div>
							<div class="col-lg-4 col-md-4">
								天	
							</div>
						</div>
						<div class="row top_margin">
							<div class="col-lg-4 col-md-4 text-right">
								整改/延期原因：
							</div>
							<div class="col-lg-8 col-md-8 text-left">
								<textarea name="progressRecord.promptMsg" class="form-control"  style="resize:none" ></textarea>
							</div>
						</div>
						<div id="apply_msg" style="color:red" class="row top_margin text-center">
						<!--  提示信息  -->
						</div>
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-default left" data-dismiss="modal">关闭</button>
					<button id="com_delay" type="button" class="btn btn-primary">确认</button>
				</div>
				</form>
				</div>
			</div>
		</div>
		<!-- -------- -->
		<!-- -------- -->
		<div class="modal fade" id="plan_confirm_modal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">验收申请</h4>
					</div>
					<div class="modal-body">
					即将提交业主审核,请确认当前项目是否完成？
				</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-default left" data-dismiss="modal">关闭</button>
					<button type="button" id="end_com" class="btn btn-primary">确认</button>
				</div>
				</div>
			</div>
		</div>
		<!-- -------- -->
		<div class="end">
				
			</div>
	</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/laydate/laydate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/format.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time-difference.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
<script>
var min_schedule = 1;//可填进度区间最小值
$(".plan_num").keyup(function () {
    $(this).val($(this).val().replace(/[^0-9]/g, ''));             
}).bind("paste", function () {  //CTR+V事件处理    
    $(this).val($(this).val().replace(/[^0-9]/g, ''));
}).css("ime-mode", "disabled"); //CSS设置输入法不可用

var myDate = new Date();
var my_yeat = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
var my_month = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
var my_day = myDate.getDate();        //获取当前日(1-31)
var to_day = my_yeat+'-'+my_month+'-'+my_day;
</script>
<script type="text/javascript">

var proid = $.query.get("id");
proid = decodeURIComponent(proid);
var time = $.query.get("time");
time = decodeURIComponent(time);
var name = $.query.get("name");
name = decodeURIComponent(name);
var endTime = $.query.get("endTime");
var code  =$.query.get('code');
$('#page_plan_name').text(name);
$('#stat_time').val(getFormatDate(Number(endTime)));
$('#paln_start_time').text(getFormatDate(Number(time)));
$('#paln_end_time').text(getFormatDate(Number(endTime)));
//=================================
	$(function(){
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/procedure/modifyById',
			data:{'id':proid,'notice':2},
			async:true,
			success:function(data){
				console.log(data);
			},
			error:function(){
				
			}
		})
	})
//=================================
	//修改/延期申请时间限制
laydate.render({
	elem: '#end_time',
	//min: time,
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		time_cha();//回调 计算时间天数函数
		}
});
//=======进度提示
	$('#add_pro').click(function(){
		if(min_schedule==100){
			$('#tip_msg').text('只能填写：100');
			return false;
		}else if($('#schedule_con').val()<min_schedule || $('#schedule_con').val()>99){
	    	$('#tip_msg').text('填写范围在：'+min_schedule+'-99');
	    	return false;
	    }else{   
	    $('#tip_msg').text('');
		$('#plan_tip_modal').modal('hide');
		var form = $.param({"progressRecord.promptType":JSON.parse(sessionStorage.getItem('SRC_user_msg')).type}) + "&" + $.param({"procId":proid}) + "&" + $.param({"type":"1"}) + "&" + $.param({"progressRecord.promptMsg":""}) + "&" + $('#pro_form').serialize();
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/progress/increase',
			async:true,
			data:form,
			success:function(data){				
				show_plan_stage();
			},
			error:function(){
				
			}
		})
	   }
	})
	$('#apply_sch').change(function(){
		if(min_schedule==100 && $(this).val()!=100){
			$('#sch_apply_msg').text('只能填写：100');
			$('#com_delay').attr('disabled',true);
		}else if($(this).val()<min_schedule || $(this).val()>99){
	    	$('#sch_apply_msg').text('填写范围在：'+min_schedule+'-99');
	    	$('#com_delay').attr('disabled',true);
	    }else{
	    	$('#com_delay').attr('disabled',false);
	    }
	})
	//=======整改/延期申请请
	$('#com_delay').click(function(){
		//判断填写的值大小是否合理
		if($('#apply_sch').val().trim()==''){
			$('#sch_apply_msg').text('请填写进度');
			return false;
		}else if($('#end_time').val()==''){
	    	$('#apply_msg').text('请选择时间');
	    	return false;	
	    }else{
	    	$('#sch_apply_msg').text('');
	    	$('#apply_msg').text('');
		$('#plan_delay_modal').modal('hide');
		var form = $.param({"progressRecord.promptType":JSON.parse(sessionStorage.getItem('SRC_user_msg')).type}) + "&" + $.param({"procId":proid}) + "&" + $.param({"type":"2"}) + "&" + $('#delay_form').serialize();
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/progress/postponeAndAcceptance',
			async:true,
			data:form,
			success:function(data){		
				show_plan_stage();
			},
			error:function(){
				console.log('no');
			}
		})
	    }
	})
	//=======验收申请
	$('#end_com').click(function(){
		$('#plan_confirm_modal').modal('hide');
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/progress/postponeAndAcceptance',
			async:true,
			data:{"progressRecord.promptType":JSON.parse(sessionStorage.getItem('SRC_user_msg')).type,"procId":proid,"type":"3","progressRecord.promptMsg":"","progressRecord.timeAssess":getDaysByDateString(getFormatDate(Number(time)),to_day)},
			success:function(data){			
				show_plan_stage();
			},
			error:function(){
				
			}
		})
	})
	
//计算时间差
	function time_cha(){
		if($('#end_time').val()!=""){
			var num = getDaysByDateString($('#stat_time').val(),$('#end_time').val());
			$('#timeAssess').val(num);
		}
	};
	

$(function(){
	if(code==3){
		$('#operation_btn').children('button').attr('disabled',true);
	}
	show_plan_stage();
});
setInterval("show_plan_stage()",10000);
function show_plan_stage(){
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/progress/queryList',
		data:{'procId':proid},
		dataType:'json',
		async:true,
		success:function(data){
			console.log(data);
			state = true;//每次刷新时重置状态1
			$('#plan_msg').children().remove();
			$.each(data,function(index,item){				
				if(index == data.length-1){
					min_schedule = item.schedule;
					/* if(item.schedule!=0){
						$('#schedule_con').val(item.schedule);
						$('#apply_sch').val(item.schedule);
					}else{
						$('#schedule_con').val(1);
						$('#apply_sch').val(1);
					} */
				}
				if(item.type=="1"){
					$('#plan_msg').append(plan_a(item));
				}
				else if(item.type=="2"){
					$('#plan_msg').append(plan_b(item,item.progressRecord));
				}
				else if(item.type=="3"){				
					$('#plan_msg').append(plan_c(item,item.progressRecord));
				}
			});
			if(data[data.length-1].type=="3" && data[data.length-1].progressRecord.status!=3){
				$('#operation_btn').children('button').attr('disabled',true);
			}else if(data[data.length-1].type=="3" && data[data.length-1].progressRecord.status==3){
				$('#operation_btn').children('button').last().prevAll().attr('disabled',true);
			}
			
		},
		error:function(){
			console.log('请求失败');
		}
	})
}

//=====进度提示
function plan_a(e){
	return	'<div class="clearfix bottom_margin">'+
			is_start(e)+
		'</div>';	
}
//判断是否是项目开始启动
function is_start(e){
	//判断是否有集合
	if(!e.hasOwnProperty("progressRecord")){
		return '<div class="left plan_than">'+
		e.schedule+
		'%</div>'+
		'<div class="left top_margin plan_left_time">'+
			'<div class="">'+
				'<font color="#009933">进度提示</font>'+
				'<span class="role">'+getFormatDate(e.created)+'</span>'+
				judge_role(e.progressRecord.promptType)+
			'</div>'+
		'</div><br />';
	}
	else if(e.progressRecord.promptMsg!=''){
		return '<div class="left plan_than">'+
					e.schedule+
					'%</div>'+
					'<div class="left top_margin plan_left_time">'+
						'<div class="">'+
							'<font color="#009933">进度提示</font>'+
							'<span class="role">'+getFormatDate(e.created)+'</span>'+
							judge_role(e.progressRecord.promptType)+
						'</div>'+
						'<div class="talk_con">'+
						'<span>'+e.progressRecord.promptMsg+'</span>'+
						'</div>'+
					'</div><br />';		
	}else{
		return '<div class="left plan_than">'+
		e.schedule+
		'%</div>'+
		'<div class="left top_margin plan_left_time">'+
			'<div class="">'+
				'<font color="#009933">进度提示</font>'+
				'<span class="role">'+getFormatDate(e.created)+'</span>'+
				judge_role(e.progressRecord.promptType)+
			'</div>'+
		'</div><br />';
	}
	}
//=====延期申请
function plan_b(e,c){
	return	'<div class="clearfix bottom_margin">'+
	'<div class="left plan_than">'+
		e.schedule+
	'%</div>'+
	'<div class="left top_margin plan_left_time">'+
		'<div class="">'+
			'<font color="#1288F3">整改/延期申请</font>'+
			'<span class="role">'+getFormatDate(e.created)+'</span>'+
			judge_role(e.progressRecord.promptType)+
		'</div>'+		
		'<div class="talk_con">'+
		'<span style="color: #5e6063;">装修公司:</span><br />'+
		'<span>'+e.progressRecord.promptMsg+'</span>'+
	'</div>'+
	'<span> <font color="#0171C5">'+sta_(e.progressRecord.status)+'</font></span>'+
	'</div><br />'+show_reply(c)+	
'</div>';
}

//=====验收申请
function plan_c(e,c){
	return	'<div class="clearfix bottom_margin">'+
	'<div class="left plan_than">'+
		e.schedule+
	'%</div>'+
	'<div class="left top_margin plan_left_time">'+
		'<div class="">'+
			'<font color="#D89B05">验收申请</font>'+
			'<span class="role">'+getFormatDate(e.created)+'</span>'+
			judge_role(e.progressRecord.promptType)+
		'</div>'+		
		'<div class="talk_con">'+
		'<span style="color: #5e6063;">装修公司:</span><br />'+
		'<span>'+e.progressRecord.promptMsg+'</span>'+
	'</div>'+
	'<span> <font color="#0171C5">'+sta_(e.progressRecord.status)+'</font></span>'+
	'</div><br />'+	show_reply(c)+
'</div>';
}
//=======审核状态
function sta_(e){
	if(e=="1"){
		return "待验收";
	}else if(e=="2"){
		return "业主已审核";
	}else if(e=="3"){
		return "未通过";
	}
}

//判断执行 角色 
function judge_role(num){
	if(num==0 || num==''){
		return '';
	}else{
		if(num==1){
			return '<span class="role">公司</span>';
		}else if(num==4){
			return '<span class="role">工程部经理</span>';
		}else if(num==6){
			return '<span class="role">项目经理</span>';
		}
	}		
	}
//========判断是否有某个字段
function show_reply(c){
	if(c.hasOwnProperty("replyContent")){
		return '<div class="right top_margin plan_right_time">'+
		'<div class="text-right">'+
		getFormatDate(c.replyTime)+
		'</div>'+
		'<div class="talk_con">'+
			'<span style="color: #5e6063;">客户:</span><br />'+
			'<span>'+c.replyContent+'</span>'+
		'</div>'+
	'</div>';
	}
	else{
		return "";
	}
}
//设置链接路径
$(function(){
	var url = sessionStorage.getItem('plan_detail_key');
	$('#detail_url').attr('href','${pageContext.request.contextPath}/pages/progress/athena_plan_detail.jsp?id='+url);
	$('#plan_stage_name').text(name);
})
</script>
