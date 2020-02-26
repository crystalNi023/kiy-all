<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public_page.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/project/project.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/home/header_end.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js" ></script>
	</head>
	<body id="1" style="height:768px">
		
		<div class="current_position">
			<div class="nav_position">
				当前位置：<a href="${pageContext.request.contextPath}/pages/project/athena_project.jsp">在建项目</a>
			</div>			
		</div>
		<div class="page_wid top_margin">
			<div class="clearfix">
			<div class="left search1">
					<form action="" id="form">					
						<i class="fa fa-search search_icon"></i>
						<input type="text" class="search_css search_inp" name="customerName" id="" value="" placeholder="客户姓名" />
						<input type="text" class="search_css search_inp " name="address" id="" value="" placeholder="客户地址" />
						<input type="button" id="search_" class="btn btn-group btn_text" value="搜索" />
					</form>				
				</div>	
			</div>			
		</div>	
		<!----------------------------------------------------------------------->
		<style type="text/css">
				#cus_tab{
					width: 100%;
					min-width: 1224px;
					border: 2px solid rgb(227, 227, 243);
					font-size: 12px;
					margin-top: 10px;
				}
				#cus_tab .tr,td{
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
			</style>
			<div class="page_wid page_hei">
					<table id="cus_tab" class="table-responsive" border="0">
					<thead>
						<tr>
							<td>编号</td>
							<td>客户姓名</td>
							<td>工程地址</td>
							<td>装修类型</td>
							<td>装修进度</td>
							<td>启动时间</td>
							<td>计划完成时间</td>
							<td>装饰管理</td>
							<td>动态时间评估</td>
							<td>装修设计师</td>
							<td>项目负责人</td>
							<td>现场负责人</td>
							<!-- <td>视频影像</td> -->
							<td id="ttt">变更与通知</td>
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
			<!-- 完工模态框 -->
		<div class="modal fade" id="end_modal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">是否完工</h4>
					</div>
					<div class="modal-body text-center">
					项目完工后请到客户档案查看项目明细
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-default left" data-dismiss="modal">取消</button>
					<button type="button" onclick="end_project()" class="btn btn-primary">确认</button>
				</div>
				</div>
			</div>
		</div>
		<!-- ---------------------- -->
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/format.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/assess/Assessment_time.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap-paginator.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/page/page.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/iframe/iframe_url.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/layer/layer.js" ></script>
	<script type="text/javascript">	
	var page_btn_num;//分页条目显示7个按钮  就是7页
	var total_Page;//总记录页数
	var page_no;//当前页
	if(sessionStorage.getItem('pro_page_no')==null){
		page_no=1;
	}else{
		page_no=sessionStorage.getItem('pro_page_no');
	}
//---------------------------------------
var proId_list = [];//项目id集合
//默认请求分页数据
	$(function(){
		AjAx_();							
	});
	setInterval("AjAx_()",10000);
	//消息提醒弹出框返回内容d
	 function ContentMethod(num) {
		 if(num==0){
			 return '<table class="table table-bordered">'+
		     		 '<tr><td>暂无消息</td></tr>' +
		             '</table>';
		 }else if(num==1){
			 return '<table class="table table-bordered">'+
		     		 '<tr><td>装饰管理有新消息</td></tr>' +
		             '</table>';
		 }else if(num==2){
			 return '<table class="table table-bordered">'+
		     		 '<tr><td>变更与通知有新消息</td></tr>' +
		             '</table>';
		 }else if(num==3){
			 return '<table class="table table-bordered">'+
		     		 '<tr><td>装饰管理有新消息</td></tr>' +
		     		 '<tr><td>变更与通知有新消息</td></tr>' +
		             '</table>';
		 }      
     };
     
     function popover_(e,num){
    	 e.popover({
    		 trigger: 'manual',
             placement: 'left', //top, bottom, left or right
             title: '未读消息',
             html: 'true',
             content: ContentMethod(num),
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
//条件查询
$("#search_").click(function(){
		AjAx_();
})

var comId = parent.id;
//提取公共请求
function AjAx_(){
	var form = $.param({'comId':comId}) + '&' + $.param({'pageNo':page_no}) + '&' + $("#form").serialize();
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/project/queryList',
		async:false,
		dataType:'json',
		data:form,
		success:function(data){
			if(data.result==""){
				page_no=1;
				$('#pageLimit').children().remove();
				$('#cus_tab').children().first().nextAll().remove();
				$("#cus_tab").append(
				'<tr>'+
				'<td colspan="13">暂无内容</td>'+
				'</tr>')
			}else{
			add_project_con(data);
			}
			console.log(data);
		},
		error:function(){
			console.log("失败");
		}
	})
}

//完工请求
var pro_id;//项目id
function end_project(){
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/project/modifyById',
		data:{'id':pro_id,'projectStatus':3},
		success:function(data){
			window.location.reload();
		},
		error:function(){
			
		}
	})
}
//消息提示请求
function msg_ajax(){
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/project/news',
		data:{'proIdList':proId_list},	
		dataType:'json',
		async:false,
		success:function(data){
			$.each(data,function(index,item){
				$('#'+item.id).empty();
				//两个接口的newsType值不同,以当前接口的newsType值为准
				if(item.newsType!=0){
					$('#'+item.id).append('<img src="${pageContext.request.contextPath}/public/img/msg.gif" class="bell_on" data-toggle="popover" />');	
				}				
				popover_($('#'+item.id),item.newsType);
			});					 
		},
		error:function(){
		}
	});
}

function add_project_con(data){
	total_Page = data.totalPage;//将返回的总页数赋值给变量
	page_no = data.pageNo;
	sessionStorage.setItem('pro_page_no',page_no);//利用临时储存保存当前页数,刷新页面数据始终是当前页
	if(total_Page<=7){
		page_btn_num=total_Page;
	}else{
		page_btn_num=7;
	}
	//data.pageNo;//第几页
	proId_list=[];
	$('#cus_tab').children().first().nextAll().remove();
	$.each(data.result,function(index,item){
		proId_list.push(item.id);
		if(item.projectStatus!=3){
		$("#cus_tab").append(
		'<tr>'+
		'<td>'+((index+1)+(10*(page_no-1)))+'</td>'+
		'<td>'+item.customerName+'</td>'+
		'<td>'+item.address+'</td>'+
		'<td>'+sel_type(item.type)+'</td>'+
		is_process(item.process,JSON.stringify(item.id))+		
		'<td>'+getFormatDate(item.planStartTime)+'</td>'+
		'<td>'+getFormatDate(item.planEndTime)+'</td>'+
		'<td><a onclick=plan_detail('+JSON.stringify(item.id)+') href="javascript:;" class="fitment_detail">详情</a></td>'+
		'<td>'+time_Assess(item.timeAssess)+'</td>'+
		'<td>'+
		'<div class="">'+
		is_null(item,'design',item.design,item.designPhone)+
		'</div>'+
		'</td>'+
		'<td>'+
		'<div class="">'+
		is_null(item,'decoration',item.decoration,item.decorationPhone)+
		'</div>'+
		'</td>'+
		'<td>'+
		'<div class="">'+
		is_null(item,'spot',item.spot,item.spotPhone)+
		'</div>'+
		'</td>'+
		/* '<td><font color="#4CAE4C">查看</font></td>'+ */
		'<td><a onclick=change_notice('+JSON.stringify(item.id)+') href="javascript:;"><font color="#4CAE4C">详情</font></a>'+
		'<span id="'+item.id+'"></span>'+
		'</td>'+
		'</tr>'		
		);
		}
	});
	msg_ajax();
	bootstrap_paginator(page_no,total_Page);//调用分页方法		
	parent.document.getElementById('page_con').style.height=document.body.scrollHeight+'px';

}
//判断进度
function is_process(num,id){
	if(num==100){
		return '<td><a data-toggle="modal" data-target="#end_modal" onclick=set_proid('+id+') class="fitment_detail" >'+
		'<font color="#000">完工</font></a></td>';
	}else{
		return '<td>'+num+'%</td>';
	}
}
//设置项目id
function set_proid(id){
	pro_id=id;
	console.log(pro_id);
}
//判断工作人员是否为空
function is_null(e,str,e1,e2){
	if(e.hasOwnProperty(str)){
		return '<span>'+e1+'</span><br />'+
		'<span>'+e2+'</span>';
	}else{
		return '<span>无</span>';
	}
}

//变更与通知
function change_notice(e){
	loadIframe('${pageContext.request.contextPath}/pages/change/athena_change.jsp?id='+e);
}

//进度详情
function plan_detail(id){
	loadIframe("${pageContext.request.contextPath}/pages/progress/athena_plan_detail.jsp?id="+encodeURIComponent(id));
	sessionStorage.setItem('plan_detail_key',id);
}
	
//装修类型
	function sel_type(num){
		if(num=='1'){
			return '基装';
		}else if(num=='2'){
			return '整装';
		}
	}

	</script>
</html>
