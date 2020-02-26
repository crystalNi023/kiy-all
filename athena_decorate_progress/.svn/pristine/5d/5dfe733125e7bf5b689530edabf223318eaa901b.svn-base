<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/public_page.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/note.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/date/ion.calendar.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/font-awesome.min.css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/css/bootstrap.min.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/jquery-3.3.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/bootstrap.js" ></script>
	</head>
	<body>
		<div class="current_position">
			<div class="nav_position">
				当前位置：信息中心
			</div>			
		</div>
		<div class="page_wid top_margin">
			<div class="clearfix">
				<div class="left search1">
					<form action="" id="form" method="post">					
						<i class="fa fa-search search_icon"></i>
						<input type="tel" class="search_css search_inp" name="name" id="" value="" placeholder="姓名" />
						<input type="text" class="search_css search_inp " name="phone" id="" value="" placeholder="手机号" />
						<input type="text" name="repayDate" placeholder="日期" class="repayDate form-control pro_time" id="txt_calendar" readonly="readonly" />
						<select id="" name="isContect" class="search_css search_inp" style="vertical-align: top;">
									<option value="0">状态选择...</option>
									<option value="1">未通话</option>
									<option value="2">已通话</option>
						</select>
						<input type="button" id="btn" class="btn btn-group btn_text" value="搜索" />											
					</form>				
				</div>
				<!-- <div class="right">
					<button id="send_msg" class="btn btn-group btn_confirm hidden">发送短信</button>
				</div>
				<div class="right" >
					<button id="confirm"  data-toggle="modal" data-target="#msg_modal" class="btn btn-group btn_confirm hidden">确认选择</button>
				</div>
				<div class="right">
					<button id="" data-toggle="modal" data-target="#add_modal" class="btn btn-group btn_confirm hidden">添加模版</button>
				</div> -->
			</div>	
			<style type="text/css">
				table{
					width: 100%;
					margin-top: 10px;
				}
				tr,td{
					width:auto;
					text-align: center;
					vertical-align: middle;
					border-bottom: 1px solid #CECBCB;
					height: 50px;
					background: #FFFFFF;
				}
				.check_wid{
					width: 50px;					
				}
				.ch_box{
					vertical-align: top;
				}
			</style>
			<table id="tab" class="table-responsive">
				<thead style="border-bottom: 2px solid #0171C5;">
					<tr>
						<td class="check_wid hidden sel_all"><input class="ch_box" type="checkbox" name="" id="all" value="" />全选</td>
						<td>编号</td>
						<td>姓名</td>
						<td>性别</td>
						<td>年龄</td>					
						<td>日期</td>
						<td>联系方式</td>
						<td>其它</td>
						<td>通话状态</td>
						<td>通话备注</td>
						<td class="operation">操作</td>
					</tr>
				</thead>	
			</table>
<!----------------------------------------编辑短信-------------------------------------------------->			
			<div class="modal fade" id="msg_modal">
				<div class="modal-dialog send_model">
					<div class="modal-header send_model_header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">编辑信息</h4>
					</div>
					<div class="modal-body clearfix">
						<div class="clearfix">
							<div class="left sel_left">
								<span>选择模版：</span>
							</div>
							<div class="right sel_right">							
								<select id="sms_model" class="form-control">
									<option id="sel_first" value="">请选择...</option>
								</select>
							</div>
						</div>	
						<div class="clearfix" style="margin-top:20px;">
								<div class="left sel_left model_con">
									<span>模版类容：</span>
								</div>
							<div class="left sel_right">							
								<span id="model_text">
									
								</span>
							</div>
						</div>		
						<div class="clearfix" style="margin-top:20px">
								<div class="left sel_left">
									<span>填写类容：</span>
								</div>
							  <div  id="model_msg" class="left sel_right">							
								
							</div>
						</div>	
						<div>
						温馨提醒：<font color="red">所有输入框不能为空！</font>
						</div>										
					</div>					
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" id="send" disabled="disabled" class="btn btn-primary">
					确认发送
				</button>
			</div>
				</div>
			</div>
			<!------------------------------------添加模版------------------------------------------>
			<form id="add_model_form" action="">			
			<div class="modal fade" id="add_modal">
				<div class="modal-dialog send_model">
					<div class="modal-header send_model_header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">添加模版</h4>
					</div>
					<div class="modal-body clearfix">
						<div class="clearfix top_margin">
							<div class="left sel_left">
							<span>模版Id：</span>
						</div>
						<div class="right sel_right">				
							<input type="text" class="form-control" placeholder="请输入id" name="TemplateId" id="" value="" />
						</div>
					</div>
					<div class="clearfix top_margin">
							<div class="left sel_left">
							<span>模版类型：</span>
						</div>
						<div class="right sel_right">				
							<select id="sms_model" name="type" class="form-control">
									<option id="sel_first" value="0">通知短信</option>
									<option id="sel_first" value="5">会员服务短信（需企业认证）</option>
									<option id="sel_first" value="4">验证码短信(此类型content内必须至少有一个参数{1}</option>
							</select>
						</div>
					</div>
					<div class="clearfix top_margin">
							<div class="left sel_left">
							<span>签名：</span>
						</div>
						<div class="right sel_right">				
							<input type="text" class="form-control" placeholder="请输入签名" name="autograph" id="" value="" />
						</div>
					</div>
					<div class="clearfix top_margin">
							<div class="left sel_left">
							<span>模版名称：</span>
						</div>
						<div class="right sel_right">				
							<input type="text" class="form-control" placeholder="请输入模版名称" name="TemplateName" id="" value="" />
						</div>
					</div>
					<div class="clearfix top_margin">
							<div class="left sel_left">
							<span>模版类容：</span>
						</div>
						<div class="right sel_right">				
							<input type="text" class="form-control" placeholder="请输入模版类容" name="content" id="" value="" />
						</div>
					</div>
					</div>					
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button id="add_model_btn" type="button" class="btn btn-primary">
					确认添加
				</button>
			</div>
				</div>
			</div>
			</form>
			<!-- ----------------------------------------------------------------------------- -->
			
			<!------------------------------------------------------------------------------>
			<form action="" id="update_user_form">
			<div class="modal fade" id="live_modal">
				<div class="modal-dialog send_model" style="width: 40% !important;">
					<div class="modal-header send_model_header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">通话备注</h4>
					</div>
					<div class="modal-body clearfix">
						<div class="">
							<input type="hidden" name="phone" id="user_phone" />
							状态：<label><input type="radio" value="1" name="isContect">未通话</label>
								<label><input type="radio" value="2" name="isContect">已通话</label>
						</div>
						<div class="">
							备注：<textarea name="explain" id="explain" style="resize:none;width: 70%;height: 100px;vertical-align: middle;">
								
							</textarea>
						</div>
					</div>					
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" id="user_update" class="btn btn-primary">
					确认添加
				</button>
			</div>
				</div>
			</div>
			</form>
			<!------------------------------------------------------------------------------>
		</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/select.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/moment.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/moment.zh-cn.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/ion.calendar.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/date/format.js" ></script>
	<script>
	//日历控件
	$(function(){
		$('#txt_calendar').each(function(){
			$(this).ionDatePicker({
				lang: 'zh-cn',
				format: 'YYYY-MM-DD'
			});
		});
	});
		$(function(){
			$('#all').click(function(){
				var allcheck=this.checked;
				$('input[name=box]').each(function(){
					this.checked=allcheck;
				})
			});
			
			$('#tab').on("click","input[name=box]",function(){
				var sel_len = $('input[name=box]:checked').length;
				var len = $('input[name=box]').length;
				if(sel_len==len){
					$('#all').get(0).checked=true;
				}else{
					$('#all').get(0).checked=false;
				}
			});
			
			$('#send_msg').click(function(){
				search();		
			})
		})
	</script>
	<script>
//添加短信模版
$('#add_model_btn').click(function(){
	var form = $('#add_model_form').serialize();
	$.ajax({
		type:'post',
		url:"${pageContext.request.contextPath}/sms/increaseTemplate",
		data:form,
		async:true,
		success:function(data){
			window.location.href="${pageContext.request.contextPath}/pages/note.jsp";
		},
		error:function(){
			console.log("no");
		}
	})
})
//条件查询客户数据
			$('#btn').click(function(){					
			var form = $('#form').serialize();
			sel();
			$.ajax({
				type:"post",
				//contentType: 'application/json;charset=utf-8',
				url:"${pageContext.request.contextPath}/sms/queryListClient",				
				dataType:'json',			
				data:form,
				async:true,				
				success:function(data){	
					$(".repayDate").val("");
					$('#tab').children().first().nextAll().remove();
					$.each(data, function(index,item) {					
						$('#tab').append(
							'<tr>'+
								'<td class="hidden sel_all">'+
								'<input type="checkbox" class="ch_box" name="box" id="" value="" />'+'</td>'+
								'<td id='+item.id+'>'+index+'</td>'+
								'<td>'+item.name+'</td>'+
								'<td>'+item.sex+'</td>'+
								'<td>'+item.age+'</td>'+								
								'<td>'+getFormatDate(item.repayDate)+'</td>'+
								'<td>'+item.phone+'</td>'+
								'<td>'+item.used+'</td>'+
								'<td>'+contect(item.isContect.toString())+'</td>'+
								'<td>'+item.explain+'</td>'+						
								'<td class="operation">'+
								'<button class="btn btn-group" data-toggle="modal" data-target="#live_modal" onclick=live_user('+item.phone+','+'\"'+item.explain.toString()+'\"'+','+item.isContect+')>'+'备注'+'</button>'+
								'</td>'+
						'</tr>'
						)
					});
				},
				error:function(){
					console.log('失败');
				}
			});
		});
			
//发送短信
		$("#send").click(function(){
			//if(sta!=null){
			//	alert("不能输入英文逗号!");
			//	return null;
		//	}
			var user_arr=[];//保存传递参数的集合
			$('input[name=box]:checked').each(function(index,e){
				var user_obj={};//一个用户对象
				user_obj["id"]=$(e).closest('tr').find('td:eq(1)').attr("id");
				user_obj["name"]=$(e).closest('tr').find('td:eq(2)').map(function(){
					return this.innerHTML
					}).get().join();
				user_obj["phone"]=$(e).closest('tr').find('td:eq(6)').map(function(){
					return this.innerHTML
					}).get().join();
				user_obj["content"]=input_val;
				user_obj["templateId"]=$("#sms_model").val();
				console.log("---------------------------------");
				user_arr.push(user_obj);//将对象存入集合象
				console.log("---------------------------------");
			});
			var list = user_arr;
			ipu_val=[];//每次请求发送短信后就将短信参数集合清空
			$.ajax({
				type:"post",
				contentType: 'application/json;charset=utf-8',
				url:"${pageContext.request.contextPath}/sms/sendListMsg",
				async:true,
				//dataType:"json",
				data:JSON.stringify(list),
				success:function(data){					
					window.location.href="${pageContext.request.contextPath}/pages/note.jsp";
				},
				error:function(){
					console.log("no");
				}
			})
		});

//页面加载成功后,请求默认客户数据，
			$(function(){
				var form = $('#form').serialize();
				$.ajax({
					type:"post",
					//contentType:"multipart/form-data",
					contentType: 'application/json;charset=utf-8',
					url:"${pageContext.request.contextPath}/sms/queryListClient",
					async:true,
					dataType:"json",
					data:form,
					success:function(data){
						$('#tab').children().first().nextAll().remove();//移除元素内里面的类容
						$.each(data,function(index,item){							
							$('#tab').append(
							'<tr>'+
								'<td class="hidden sel_all">'+
								'<input type="checkbox" class="ch_box" name="box" id="" value="" />'+'</td>'+
								'<td id='+item.id+'>'+index+'</td>'+
								'<td>'+item.name+'</td>'+
								'<td>'+item.sex+'</td>'+
								'<td>'+item.age+'</td>'+								
								'<td>'+getFormatDate(item.repayDate)+'</td>'+
								'<td>'+item.phone+'</td>'+
								'<td>'+item.used+'</td>'+
								'<td>'+contect(item.isContect.toString())+'</td>'+
								'<td>'+item.explain+'</td>'+
								'<td class="operation">'+
								'<button class="btn btn-group" data-toggle="modal" data-target="#live_modal" onclick=live_user('+item.phone+','+'\"'+item.explain.toString()+'\"'+','+item.isContect+')>'+'备注'+'</button>'+
								'</td>'+
						'</tr>'
						)
						})
					},
					error:function(data){
						console.log("no");
					}
				});
			});	
		
//页面加载成功后请求接口，得到下拉框的模版样式
			$(function(){
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/sms/queryListTemplate",
					async:true,
					dataType:'json',			
					success:function(data){
						$.each(data,function(index){
							var sms_header=data[index].autograph; //下拉框文本类容
							var sms_content=data[index].content;//下拉框值
							$("#sms_model").append('<option value='+data[index].templateId+'>'+sms_header+"，"+sms_content+'</option>')
						})
					},
					error:function(){
						console.log("no");
					}
				})
			});
			
			function live_user(phone,explain,isContect){
				$("#user_phone").val(phone);
				$('input[value='+isContect+']').prop("checked", true);
				$("#explain").val(explain);
			}
			
			$("#user_update").click(function(){
				var form = $("#update_user_form").serialize();		
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/sms/updateClientByPhone",
					async:true,
					data:form,
					dataType:'text',
					success:function(){
						window.location.href="${pageContext.request.contextPath}/pages/note.jsp";
					},
					error:function(){
						console.log("no");
					}
				});
			})
	</script>
</html>
