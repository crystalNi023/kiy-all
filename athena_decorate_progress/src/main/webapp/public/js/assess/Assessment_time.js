//动态评估时间
	function time_Assess(time){
		time = parseInt(time);
		if(time>0){
			return '<font color="red">延期'+time+'天</font>';
		}else if(time==0){
			return '<font color="rgb(76, 174, 76)">正常</font>';
		}else if(time<0){
			return '<font color="rgb(76, 174, 76)">提前'+Math.abs(time)+'天</font>';
		}
	}
	
	function detail_time_Assess(time){
		time = parseInt(time);
		if(time>0){
			return '<font class="red_size">延期'+time+'天</font>';
		}else if(time==0){
			return '';
		}else if(time<0){
			return '<font color="#669900">提前'+Math.abs(time)+'天</font>';
		}
	}