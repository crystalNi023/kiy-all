var comId;

//=========================================================================
function sel_com(){
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/decorationCompany/queryById',
			async:true,
			data:{'id':id},
			dataType:'json',
			success:function(data){
				$('#show_com_name').html(data.name);
				/* $('#com_name').val(data.name);					
				$("#com_phone").val(data.phone);
				$('#com_email').val(data.email);
				$('#com_address').val(data.address); */
			},
			error:function(){
				
			}
		})
	}
	
	$('#edit_com').click(function(){
		var form = $('#user_form').serialize();
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/decorationCompany/modifyById',
			async:true,
			data:form,
			dataType:'json',
			success:function(data){
				
			},
			error:function(){
				
			}
		})
	})