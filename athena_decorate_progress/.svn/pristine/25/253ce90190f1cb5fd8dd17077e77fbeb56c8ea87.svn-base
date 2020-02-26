
function time_(e,out_time){
		if(out_time==0){
			$(e).attr('disabled',false);
			out_time = 10;
			$(e).val('重新发送');
			return false;
		}
		else{
			$(e).attr('disabled',true);
			out_time--;
			$(e).val('重新发送'+out_time+'s');
		}
		setTimeout(function(){
			time_(e,out_time);
		},1000);
			
	}