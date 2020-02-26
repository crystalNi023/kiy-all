//执行一个laydate实例		
$('.time_ipt').each(function(){
	var lay = laydate.render({
	elem: this,
	position: 'fixed',
	format: 'yyyy-MM-dd',
});
})
//开始时间
//获取当前时间
var timestamp = '2017-08-18';
//定义一个变量保存开始时间值,默认是当前时间
var public_start_time = timestamp;
$('.time_ipt_start').each(function(){
	laydate.render({
	elem: this,
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		public_start_time = value; //得到日期生成的值，如：2017-08-18
		console.log(public_start_time);
	    //console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
	    //console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
	}
});
})
//结束时间
//定义变量保存结束时间值
var public_end_time = '2017-08-18';
$('.time_ipt_end').each(function(){
	laydate.render({
	elem: this,
	min: public_start_time,
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		console.log(public_start_time);
		public_end_time = value; //得到日期生成的值，如：2017-08-18
		//---------------------
		}
});
})
//=====================================
//发起项目流程开始时间
laydate.render({
	elem: '#start_time',
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		checkout_time(value,$('#end_time').val(),$('#start_time'));
	}
})
//发起项目流程结束时间
laydate.render({
	elem: '#end_time',
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		checkout_time($('#start_time').val(),value,$('#end_time'));
	}
})
//更改流程开始时间
laydate.render({
	elem: '#edit_start_time',
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		checkout_time(value,$('#edit_end_time').val(),$('#edit_start_time'));
	}
})
//更改流程结束时间
laydate.render({
	elem: '#edit_end_time',
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		checkout_time($('#edit_start_time').val(),value,$('#edit_end_time'));
	}
})
//发起项目限定开始时间可选日期
laydate.render({
	elem: '#add_plan_start',
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		checkout_time(value,$('#add_plan_end').val(),$('#add_plan_start'));
	}
})
//判断时间
function checkout_time(start_time,end_time,e){
	var num = getDaysByDateString(start_time,end_time);
	if(num<0){
		layer.msg('开始时间不能大于结束时间',{area:['300px', '50px']})
		$(e).val('');
	}
}
//发起项目限定结束时间可选日期
laydate.render({
	elem: '#add_plan_end',
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		checkout_time($('#add_plan_start').val(),value,$('#add_plan_end'));
	}
});
//进度详情开始时间
laydate.render({
	elem: '#proc_StartTime',
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		checkout_time(value,$('#proc_EndTime').val(),$('#proc_StartTime'));
	}
});
//进度详情结束时间
laydate.render({
	elem: '#proc_EndTime',
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		checkout_time($('#proc_StartTime').val(),value,$('#proc_EndTime'));
	}
});
//进度详情修改流程信息开始时间
laydate.render({
	elem: '#edit_proc_StartTime',
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		checkout_time(value,$('#edit_proc_EndTime').val(),$('#edit_proc_StartTime'));
	}
});
//进度详情修改流程信息结束时间
laydate.render({
	elem: '#edit_proc_EndTime',
	position: 'fixed',
	format: 'yyyy-MM-dd',
	done: function(value, date, endDate){
		checkout_time($('#edit_proc_StartTime').val(),value,$('#edit_proc_EndTime'));
	}
});


//验证添加流程填写规则
function check_add_plan(name,start,end){
	if(name.val().trim()==''){
		layer.msg('流程名不能为空',{area:['300px', '50px']})
		return false;
	}else if(start.val()==''){
		layer.msg('开始时间不能为空',{area:['300px', '50px']})
		return false;
	}else if(end.val()==''){
		layer.msg('结束时间不能为空',{area:['300px', '50px']})
		return false;
	}else{
		return true;
	}
	}