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
				<a href="#">变更与通知</a>
			</div>			
		</div>
		<div class="page_wid">
		<div class="plan_header">
			<span id="page_plan_name" class="plan_stage_nav">
				变更与通知
			</span>
		</div>
		</div>
		<div class="clearfix page_wid top_margin">
			<div class="plan_change_dialogue clearfix">
				<div class="right clearfix plan_record" id="plan_msg" style="border:none">
					<!--  --------------------------------------------------  -->
			
				<!-- ------------------------------------------------------- -->


		
				</div>
		</div>
		</div>
		
<div class="modal fade">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h4 class="modal-title">提示信息</h4>
							</div>
							<div class="modal-body">
								
							</div>
							<div class="modal-footer">
									<button class="btn btn-default left" data-dismiss="modal">关闭</button>
									<button class="btn btn-primary">确认</button>
							</div>
						</div>
					</div>
				</div>

<!-- =================================== -->
		<div class="modal fade" id="change_modal">
			<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">回复内容</h4>
				</div>
				<div class="modal-body">
					<div class="row top_margin">
						<div class="col-xs-4 col-sm-4 col-lg-4 col-md-4">
						输入内容：
						</div>
						<div class="col-xs-8 col-sm-8 col-lg-8 col-md-8">
						<input type="text" class="form-control" id="changeReply">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="change_btn"  class="btn btn-primary">确认</button>
				</div>
			</div>
		</div>
		</div>
		<!-- 图片预览 -->
		<div class="modal fade" id="change_img_modal">
			<div class="modal-dialog" style="width:60%;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
						<h4 class="modal-title" id="myModalLabel">图片预览</h4>
					</div>
					<div class="modal-body text-center img_box">
						<img id="change_img" width="100%" src=""/>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
				
			</div>
		</div>
		<div class="end">
				
		</div>
	</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/laydate/laydate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/format.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time_examples.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time-difference.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/space/space.js" ></script>
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

var change_id;
setInterval("change_text()",10000);
$(function(){
	change_text();
});
function change_text(){
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/changeRecord/queryByProId',
		data:{'proId':proid},
		async:false,
		dataType:'json',
		success:function(data){
			console.log(data);
			if(data!=null && data!=""){
				$('#plan_msg').children().remove();
				var whether_null=true;
				$.each(data,function(index,item){	
					$.each(item.changeRecordList,function(index,change_list){
						whether_null=false;
						$('#plan_msg').append(
								'<div class="clearfix">'+
									'<div class=" top_margin plan_left_time">'+
										'<div class="">'+
											'<i class="fa fa-calendar"></i>'+
											'<font color="">'+item.name+'阶段需求变更</font>'+
											getFormatDate(change_list.changeTime)+
										'</div>'+
										'<div class="talk_con">'+
											'<span style="color: #5e6063;">客户:</span><br />'+
											'<span>'+change_list.changeContent+'</span>'+
											is_cus_img(change_list.changeRecordUrls)+
										'</div><br />'+										
									json_has(change_list,item)+
								'</div>'		
								);
					})										
				})	
				if(whether_null){
					$('#plan_msg').append('<div class="none_con">暂无内容</div>');
				}
			}else{
				$('#plan_msg').append('<div class="none_con">暂无内容</div>');
			}
		},
		error:function(){
			
		}
	})
}

//判断是否返回有图片
function is_cus_img(arry){
	if(arry.length>0){
		var img_record = '';
		$.each(arry,function(index,item){
			img_record = img_record + '<img onclick=change_img_src('+JSON.stringify(item)+') data-toggle="modal" data-target="#change_img_modal" src="'+item+'" class="record_img">';
		})
		return img_record;
	}else{
		return '';
	}
}

//预览图片
function change_img_src(url){
	console.log(url);
	$('#change_img').attr('src',url);
}
//判断数据是否有装修公司返回的字段
function json_has(e,item){
	if(e.hasOwnProperty("changeReply")){
		return '<div class="right top_margin plan_right_time">'+
		'<div class="text-right">'+
		getFormatDate(e.replyTime)+
	'</div>'+
	'<div class="talk_con">'+
		'<span style="color: #5e6063;">'+judge_role(e.replyType)+':</span><br />'+
		'<span>'+
		e.changeReply+
		'</span>'+
	'</div>'+
'</div>';
	}else{
		return '<button data-toggle="modal" onclick=reply_('+JSON.stringify(e.id,item.id)+') data-target="#change_modal" class="btn btn-success top_margin">回复</button>'+
		'</div><br />';
	}
}

$('#change_btn').click(function(){
	var changeReply = $('#changeReply').val();
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/changeRecord/modify',
		data:{'replyType':JSON.parse(sessionStorage.getItem('SRC_user_msg')).type,'procId':procId,'changeReply':changeReply,'id':change_id},
		async:false,
		success:function(data){
			$('#change_modal').modal('hide');
			change_text();
		},
		error:function(){
			
		}
	})
});
//判断执行 角色 
function judge_role(num){
	if(num==0 || num==''){
		return '公司';
	}else{
		if(num==1){
			return '公司';
		}else if(num==4){
			return '工程部经理';
		}else if(num==6){
			return '项目经理';
		}
	}		
	}
//更改消息是否已读状态
 $(function(){
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/changeRecord/readByProId',
		data:{'notice':2,'proId':proid},
		async:true,
		success:function(data){
			
		},
		error:function(){
			
		}
	});
 })

//================
var procId;//进程id
function reply_(e,procid){
		change_id=e;
		procId = procid;
}

</script>
