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
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/progress/plan_stage.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/home/header_end.css"/>
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
				<a href="#" id="plan_record_url">进度详情</a>&gt;
				<a href="#" id="plan_record_dta"></a>
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
			<div class="plan_dialogue clearfix" style="margin:auto;width:80%">
				<div class="right clearfix plan_record" id="plan_msg" style="">				
		
				</div>
		</div>
			<!-- <div class="right stage_fun">
				
				</div> -->
		</div>
		<div class="end"></div>
	
	</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/laydate/laydate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/format.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time_examples.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time-difference.js" ></script>
<script>
$(".plan_num").keyup(function () {
                $(this).val($(this).val().replace(/[^0-9.]/g, ''));
            }).bind("paste", function () {  //CTR+V事件处理    
                $(this).val($(this).val().replace(/[^0-9.]/g, ''));
            }).css("ime-mode", "disabled"); //CSS设置输入法不可用
</script>
<script type="text/javascript">

var proid = $.query.get("id");
proid = decodeURIComponent(proid);
var name = $.query.get("name");
name = decodeURIComponent(name);
var time = $.query.get("time");
time = decodeURIComponent(time);
var endTime = $.query.get("endTime");
$('#paln_start_time').text(getFormatDate(Number(time)));
$('#paln_end_time').text(getFormatDate(Number(endTime)));
$('#page_plan_name').text(name);
$(function(){
	show_plan_stage();
})

function show_plan_stage(){
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/progress/queryList',
		data:{'procId':proid},
		dataType:'json',
		async:true,
		success:function(data){
			console.log(data);
			$('#plan_msg').children().remove();
			$.each(data,function(index,item){
				if(item.type=="1"){
					$('#plan_msg').append(plan_a(item));
				}
				else if(item.type=="2"){
					$('#plan_msg').append(plan_b(item,item.progressRecord));
				}
				else if(item.type=="3"){
					$('#plan_msg').append(plan_c(item,item.progressRecord));
				}
			})
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
	/* '<div class="right top_margin plan_right_time">'+
	'<div class="text-right">'+
	getFormatDate(e.progressRecord.replyTime)+
	'</div>'+
	'<div class="talk_con">'+
		'<span style="color: #5e6063;">客户:</span><br />'+
		'<span>'+e.progressRecord.replyContent+'</span>'+
	'</div>'+
'</div>'+ */
'</div>';
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
//=======审核状态
function sta_(e){
	if(e=="1"){
		return "待验收";
	}else if(e=="2"){
		return "业主以审核";
	}else if(e=="3"){
		return "未通过";
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
//添加链接
$(function(){
	var url = sessionStorage.getItem('plan_record_key');
	$('#plan_record_url').attr('href','${pageContext.request.contextPath}/pages/plan_record/athena_plan_record.jsp?proid='+url);
	$('#plan_record_dta').text(name);
})
</script>
