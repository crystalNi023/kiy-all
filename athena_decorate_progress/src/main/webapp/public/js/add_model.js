function  tttt(){
	if($("#add_model:input").val()!=null && $("#add_model:input").val()!=""){
		$("#add_model_btn").attr("disabled",true);
	}else{
		$("#add_model_btn").attr("disabled",false);
	}
}