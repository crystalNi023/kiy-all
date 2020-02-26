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
<title>进度详情</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public_page.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/progress/plan_detail.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/swiper/swiper.min.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>		
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js" ></script>
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
	<body>
		<div class="current_position">
			<div class="nav_position">
				当前位置：<a href="${pageContext.request.contextPath}/pages/project/athena_project.jsp">在建项目</a>&gt;
				<a href="#">进度详情</a>
			</div>			
		</div>
		<div class="page_wid">
			<div class="plan_con">
				<div class="plan_copyreader">
				</div>				
				<div id="plan_traverse" class="plan_box">
					<!-- --------------------------------------------------- -->
					<div class="swiper-container">
						<div class="swiper-wrapper">
						<!-- swiper滑块 -->				
				</div>
				</div>	
				<div class="swiper-button-next" style="right:10%;top:40%"></div>
   				<div class="swiper-button-prev" style="left:10%;top:40%"></div>				
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
				<div class="pro_hide save_plan">
					<input type="button" class="btn btn-success" value="保存" />
				</div>				

			<!-- --------------------------------------- -->
			<div class="modal fade" id="add_plan_modal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h4 class="modal-title">添加流程</h4>
						</div>
						<div class="modal-body">
						<form action="" id="add_plan_form">
						<input type="hidden" name="proId" id="proId">
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									流程名：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<input type="text" class="form-control" name="name" id="" value="" />
								</div>
							</div>
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									流程状态：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<select name="status" class="form-control">
										<option value="1" selected="selected">未启动</option>
										<option value="2">已启动</option>
										<option value="3">已完成</option>
									</select>
								</div>
							</div>
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									启动时间：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<input type="text" readonly="readonly" class="form-control time_ipt" name="procStartTime" id="" value="" />
								</div>
							</div>
							<div class="row top_margin">
								<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 text-right">
									计划结束时间：
								</div>
								<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
									<input type="text" readonly="readonly" class="form-control time_ipt" name="procEndTime" id="" value="" />
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
			</div>
		</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/format.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/laydate/laydate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/time_examples.js" ></script>
	<script>
	
	var proid = $.query.get("id");
	proid = decodeURIComponent(proid);
	$(function(){
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}/procedure/queryList',
		async: true,
		data: {'proId':proid},
		dataType:'json',
		success:function(data){
			console.log(data);
			$.each(data.result,function(index,item){
				$('.swiper-wrapper').append(
						'<div class="swiper-slide">'+
					'<a href="${pageContext.request.contextPath}/pages/change/athena_change.jsp?id='+item.id+'&name='+item.name+'">'+
						'<div class="plan_msg">'+
							'<div class="plan_num">'+
								index+
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
							        '<span class="sr-only">40% 完成</span>'+
							    '</div>'+
							'</div>'+
							'<div class="plan_result">'+
							plan_status(item.status)+
							'</div>'+
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
			swiper_();
		},
		error:function(){
			console.log('请求失败');
		}
	})				
	});

	
	function plan_status(code){
		if(code=="1"){
			return "未启动";
		}else if(code=="2"){
			return "装修中";
		}else if(code=="3"){
			return "已完成";
		}
	}
	
	
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/swiper/swiper.min.js" ></script>
<script>
    function swiper_(){
    	var swiper = new Swiper('.swiper-container', {
    	      slidesPerView: 'auto',
    	      centeredSlides: false,
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
  </script>
</html>
