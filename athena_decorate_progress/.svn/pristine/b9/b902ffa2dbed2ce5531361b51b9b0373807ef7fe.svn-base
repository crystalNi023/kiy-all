/**
 * 
 */
var reg= /[,]/g;            //正则表达式
var model_text_copy;        //短信模版下拉框值
var ipt_len;                //"{}"出现的次数
var inp_arr=[];             //短信参数下标数组
var inp_arr1=[];
var input_val;              //将短信参数数组转换为字符串赋给变量量
var sta;                    //判断输入框中是否有英文逗号
var ipu_val=[];             //短信参数数组


$("#sms_model").change(function () {
	$("#sel_first").remove();
	$("#model_msg").empty();
    var text = $(this).children('option:selected').html(); 
    model_text_copy=text;                  //每次改变下拉框的值重新赋给变量
    ipt_len=(text.split('{')).length-1;    //获取{}出现的次数
    if(ipt_len!=0){
    	$("#send").attr("disabled", true); 
    	for(var i=0; i<ipt_len;i++){
        	inp_arr[i]=find(model_text_copy,"{",i)+1;
        	inp_arr1[i]=find(model_text_copy,"}",i);
        	model_text_copy=sel_sub(model_text_copy,inp_arr[i],inp_arr1[i]); //将{}里面的类容清空
        	$("#model_msg").append('<div class="right pra">'+							
			'<input id="model_area" placeholder="请填写短信类容" name="pra" class="form-control model_area">'+
			'</div>');   	
        }
    }else{
    	$("#send").attr("disabled", false); 
    }
    
    $("#model_text").html(model_text_copy);
});


$("body").on("input propertychange","input[name='pra']",function(e){
	sta=null;
	inp_arr=[]; //数组里面已经保存了一组下标，清空一下数组
	var model_text=model_text_copy;
	for(var i=0;i<ipt_len;i++){	
		inp_arr[i]=find(model_text,"{",i)+1;//重新将{的下标存入数组
		ipu_val[i]=$("input[name='pra']:eq("+i+")").val();//遍历获取参数输入框的值
		sta=ipu_val[i].match(reg);
		if(sta!=null){
			$("input[name='pra']:eq("+i+")").val(($("input[name='pra']:eq("+i+")").val()).replace(/[,]/g,"，"));
			ipu_val[i]=ipu_val[i].replace(/[,]/g,"，");
		}
		model_text=insert_flg(model_text,ipu_val[i],inp_arr[i]); //输入框值发生改变后重新得到模版类容
		$("#model_text").html(model_text);		
	}
	input_val=ipu_val.join(",");            //将短信参数类容以字符串形式输出，以","隔开
	btn_show();
});

function search(){
	$(".sel_all").removeClass('hidden');
	$(".operation").addClass('hidden');
	$("#confirm").removeClass('hidden');
	$('#send_msg').addClass('hidden');
}

function sel(){
	$(".sel_all").addClass('hidden');
	$(".operation").removeClass('hidden');
	$('#send_msg').removeClass('hidden');	
	$("#confirm").addClass('hidden');
}

$("#confirm").click(function(){
	if($('input[name=box]:checked').length<=0){
		alert("请选择客户！");
		return false;
	}else{
		$('#msg_modal').modal({backdrop: 'static', keyboard: false,show:false});
	}
})

//获取字符在字符串的位置 ,str需要检索的字符串,cha出现在str中的子串,num第几次出现，
  function find(str,cha,num){
    var x=str.indexOf(cha);
    for(var i=0;i<num;i++){
        x=str.indexOf(cha,x+1);
    }
    return x;
    }
//在指定长度处插入字符串,str等待插入的父字符串,flg要插入的类容,sn要插入的位置下标,
function insert_flg(str,flg,sn){
	    var newstr="";
	    var i=0;
        var tmp=str.substring(i, i+sn);
        var tmp1=str.substring(sn, str.length);
		newstr+=tmp+flg+tmp1;        	
	    return newstr;
	}
//删除指定的两个字符串之间的类容
function sel_sub(str,s1,sn){
	    var newstr="";
	    var i=0;
        var tmp=str.substring(i, i+s1);
        var tmp1=str.substring(sn, str.length);
		newstr+=tmp+tmp1;        	
	    return newstr;
	}

//判断输入框值是否为空  若为空  确认按钮为不可点击状态
function btn_show(){
	if (ipu_val.indexOf(null) != -1 || ipu_val.indexOf("") != -1){
		$("#send").attr("disabled", true); 
	}else {
		$("#send").attr("disabled", false); 
	}
}
//判断是否已通话
function contect(sta){
	if(sta=="1"){
		return "未通话";
	}else{
		return "已通话";
	}
}