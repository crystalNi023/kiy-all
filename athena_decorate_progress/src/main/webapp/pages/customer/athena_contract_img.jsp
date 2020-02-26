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
<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public_page.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/home/header_end.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/archives/img.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/font-awesome.min.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/public/hea_end.js" ></script>
	</head>
	<style>
	.top_margin{
	margin-top:40px;
	}
	</style>
	<body id="2">
	<div class="footer">
			
		</div>
		<div class="current_position">
			<div class="nav_position">
				当前位置：<a href="${pageContext.request.contextPath}/pages/customer/athena_archives.jsp">客户档案</a>&gt;
				<a href="">装修合同</a>
			</div>			
		</div>
		<div class="page_wid top_margin bottom_margin page_hei">
		<form id="form" action="${pageContext.request.contextPath}/customer/uploadImg" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" id="cusId">
			<div class="row" id="img_dialog">
				<div id="add_img" class="col-xs-3 col-sm-3 col-md-3 col-lg-3 top_margin">
				<div id="showPic" class="" style=" width: 100%; height: auto;">
					<div id="design_img" class="">
						<i class="fa fa-upload upload_icon" aria-hidden="true"></i>
						<span class="block upload_file_text">选择图片</span>
						<input type="file" onchange="sel_file(this)" class=" add_inp selector_file" id="" name="contractImgs" value="" />
					</div>
				</div>
				</div>
			</div>
			<div class="row top_margin">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center">
			<input type="button" id="save_img" class="btn btn-success top_margin img_btn" value="保存">
			</div>
			</div>			
		</form>
		</div>
		<div class="modal fade" id="dw_modal">
			<div class="modal-dialog" style="width:80%;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
						<h4 class="modal-title" id="myModalLabel">装修合同</h4>
					</div>
					<div class="modal-body text-center img_box">
						<img id="img_modal" width="100%" src=""/>
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery.params.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/upload/upload-file.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/layer/layer.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jQuery.form.js" ></script>
	<script type="text/javascript">
		$(function(){
			var cusid = $.query.get("id");
			cusid = decodeURIComponent(cusid);
			$('#cusId').val(cusid);
			$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/customer/getImgList',
				data:{'cusId':cusid},
				async:false,
				dataType:'json',
				success:function(data){
					console.log(data);
					$.each(data.contractImgs,function(index,item){
						$('#add_img').before(
						'<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 top_margin">'+
							'<img onclick="img_src(this)" data-toggle="modal" data-target="#dw_modal" src="'+item+'"/>'+
						'</div>'
						);
					})
				},
				error:function(){
					
				}
			});
			//parent.document.getElementById('page_con').style.height=document.body.scrollHeight+'px';
		});
		
		$('#save_img').click(function(){
			options = {
					url:'${pageContext.request.contextPath}/customer/uploadImg',
					type:'post',
					async:true,
					dataType:'json',
					success:function(data){
						console.log(data.code);
						layer.close(lay_file);
						if(data.code=='00'){							
						window.location.reload();
						}else{
							//layer.msg(data.msg,{area:['400px', '50px']});
							layer.open({
								  area: ['400px', '200px'], //宽高
								  content: data.msg
								}); 
						}
					},
					error:function(){				
						console.log('获取返回数据失败');
					}
			}
			if($('input[name=contractImgs]').val()){
				var lay_file = layer.msg('文件上传中,请等待', {
					icon: 16,
					 shade: 0.4,
					 time: false
					});
				$('#form').ajaxSubmit(options);
			}else{
				layer.msg('请选择文件',{area:['400px', '50px']});
			}
		})
		
		function img_src(e){
			$('#img_modal').attr("src", $(e)[0].src);
		}
	</script>
</html>