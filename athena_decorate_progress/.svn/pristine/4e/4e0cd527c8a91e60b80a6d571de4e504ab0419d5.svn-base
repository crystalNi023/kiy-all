/*   $("input.fileTag").change(function(){
   	var oFiles = $(this).get(0);
   	var formData = new FormData();
   	if(oFiles.files.length >=10){alert('最多上传10张图片.'); oFiles.value=''; return;}
        for (var i = 0, file; i < oFiles.files.length; i++) {
            file = oFiles.files[i];
            if (file.type.indexOf("image") == 0) {
				if (file.size >= 512000) {
					alert('"'+ file.name +'" 图片文件过大，已跳过，应小于5M');
				} else {
					formData.append('files', file);
					console.log("file"+file.name);
				}
			} else {
				alert('文件 "' + file.name + '" 不是图片，已跳过。');	
			}
		}
        console.log("文件"+formData.getAll("files"));
        console.log("====================");
        for (key in formData.getAll("files")) {
        console.log(formData.getAll("files")[key].name);        
        var text = formData.getAll("files")[key].name;
        text = window.URL.createObjectURL(oFiles.files[key]);
        //window.url.revokeObjectURL("释放的对象");
        console.log("test:"+text.toString());
         $(this).parent().before('<div class="smallImgCnt"><img onclick="img_(this)" src="' + text + '" class="smallImg" />'+
         '<button onclick="remove_img(this)">移除</button>'
         +'</div>');
        }
      $.ajax({
      	type:"post",
      	url:"",
      	async:true,
      	processData: false, 
      	contentType: false,
      	data:formData,
      	success:function(data){
      		
      	},
      	error:function(){
      		
      	}
      });  
        
   });*/
  /* $('input.fileTag').change(function(){
    	var oFiles = $(this).get(0);
        var formData = new FormData();
        if(oFiles.files.length >=10){alert('最多上传10张图片.'); oFiles.value=''; return;}
        for (var i = 0, file; i < oFiles.files.length; i++) {
            file = oFiles.files[i];
            if (file.type.indexOf("image") == 0) {
				if (file.size >= 512000) {
					alert('"'+ file.name +'" 图片文件过大，已跳过，应小于5M');
				} else {
					formData.append('files', file);
				}
			} else {
				alert('文件 "' + file.name + '" 不是图片，已跳过。');	
			}
		}
        if(!formData.has('files')){ return}  //如果控件为空
		$.ajax({
			url: "/admin/upLoad/upPic.ashx",
			type: "POST",
			processData: false,
			contentType: false,
			data: formData
		}).done(function (d) {
            if (d.toString() != ''){  doResult(d); } else { alert('返回参数错误'); }
        }).fail(function (jqXHR, textStatus, errorThrown) { alert(errorThrown); });
    });
 */
    function doResult(jsnObj){
        if(jsnObj.status == '0'){alert(jsnObj.result.toString());}
        else if(jsnObj.status == '1'){
            var picArr = new Array();
            picArr = jsnObj.result.toString().split(',');
            if(picArr.length > 0){
                $.each(picArr, function(i,v){
                    if(v.indexOf('.') >= 0){
                        $('#showPic').append('<div class="smallImgCnt"><img src="' + v + '" class="smallImg" /></div>');
                    }
                });
            }
        }
    }
    function getPicsSrc(){
        var pics = new Array();
        $.each($('#showPic img.smallImg'), function(){
            pics.push($(this).attr('src'));
        });
        //console.log(pics);
        $('#picsSrc').val(pics.join(','));
    }
    $(document).on('mouseenter', '.smallImgCnt',function(){$(this).prepend('<i class="delPic"></i>').addClass('smallImgCntBg');});
    $(document).on('mouseleave', '.smallImgCnt',function(){$(this).find('i.delPic').remove().end().removeClass('smallImgCntBg');});
    $(document).on('click', 'i.delPic',function(){$(this).parent('.smallImgCnt').remove();});
    $('.fileTag').hover(function(){$(this).siblings('.btn4').addClass('btn4_')},function(){$(this).siblings('.btn4').removeClass('btn4_')});


function img_(e){
	console.log(e.src);
}
function remove_img(e,num){
	$(e).parent().parent().parent().parent().remove();
	//file_con.remove($(e).parent().next());
	var file_rem = $(e).parent().next();
	file_val.remove(file_rem.val());
	file_con.remove(num);//移除数组元素
}

function remove_feed_img(e,num){
	$(e).parent().parent().parent().remove();
	//file_con.remove($(e).parent().next());
	var file_rem = $(e).parent().next();
	file_val.remove(file_rem.val());
	file_con.remove(num);//移除数组元素
}

//========================================
var fileTypes = [".jpg", ".png"];
var file_con =[];
var file_val=[];

Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
//===========
//添加客户时添加图片片
function file_(e){
	var fileInput = $(e).val();
	if(fileInput!='' && fileInput){
		 if(!isInArr(file_val,fileInput)){
			 return false;
		 }
		 			 
		 var isNext = false;
	     var fileEnd = fileInput.substring(fileInput.indexOf("."));
        for (var i = 0; i < fileTypes.length; i++) {
            if (fileTypes[i] == fileEnd) {
                isNext = true;
                break;
            }
        }
        if (!isNext){
            layer.msg('不接受此文件类型',{area:['300px', '50px']});
            return false;
        }
        
        var file_sel_size = $(e).get(0).files[0].size;
        file_con.push(file_sel_size);//将文件大小存入数组
		if(file_size(file_con)){
        file_val.push(fileInput);
		var img_name = $(e).get(0).files[0];		
		var img_src = window.URL.createObjectURL(img_name);
		$(e).prev().prev().remove();
		$(e).prev().remove();
		$(e).hide();
		$(e).before('<img onclick="img_(this)" src="' + img_src + '" class="smallImg" />'+
				'<span class="btn_remove"><button onclick=remove_img(this,'+ file_sel_size +')>移除</button></span>');
		  $(e).parent().parent().parent().after(
				  '<div class="col-xs-3 col-sm-3 col-lg-3 col-xs-3">'+
				'<div id="showPic" class="top_margin" style=" width: 100%; height: auto;">'+
				  '<div id="design_img" class="">'+
					'<i class="fa fa-upload upload_icon" aria-hidden="true"></i>'+
					'<span class="block upload_file_text">选择图片</span>'+
					'<input type="file" onchange="file_(this)" class="fileTag add_inp selector_file" name="'+$(e).prop("name")+'" value="" />'+
					'</div>'+
					'</div>'+
					'</div>'
				  );		  
	}
	}
}

//=================================
//设计图跟合同单独添加图片
function sel_file(e){	
	var fileInput = $(e).val();
	if(fileInput!='' && fileInput){
		 if(!isInArr(file_val,fileInput)){
			 return false;
		 }
		 			 
		 var isNext = false;
	     var fileEnd = fileInput.substring(fileInput.indexOf("."));
        for (var i = 0; i < fileTypes.length; i++) {
            if (fileTypes[i] == fileEnd) {
                isNext = true;
                break;
            }
        }
        if (!isNext){
            layer.msg('不接受此文件类型',{area:['300px', '50px']});
            return false;
        }
        var file_sel_size = $(e).get(0).files[0].size;
        file_con.push(file_sel_size);
        console.log(file_con);
		if(file_size(file_con)){
			
        file_val.push(fileInput);
		var img_name = $(e).get(0).files[0];		
		var img_src = window.URL.createObjectURL(img_name);
		$(e).prev().prev().remove();
		$(e).prev().remove();
		$(e).hide();
		
		$(e).before('<img onclick="img_(this)" src="' + img_src + '" class="smallImg" />'+
	'<span class="btn_remove"><button onclick=remove_img(this,'+ file_sel_size +')>移除</button></span>');
		  $(e).parent().parent().parent().after(
				  '<div class="col-xs-3 col-sm-3 col-lg-3 col-xs-3 top_margin">'+
				'<div id="showPic" class="" style=" width: 100%; height: auto;">'+
				  '<div id="design_img" class="">'+
					'<i class="fa fa-upload upload_icon" aria-hidden="true"></i>'+
					'<span class="block upload_file_text">选择图片</span>'+
					'<input type="file" onchange="sel_file(this)" class="fileTag add_inp selector_file" name="'+$(e).prop("name")+'" value="" />'+
					'</div>'+
					'</div>'+
					'</div>'
				  );
	}
	}
}
//===================
//意见反馈上传图片
function feed_file(e){	
	var fileInput = $(e).val();
	if(fileInput!='' && fileInput){
		 if(!isInArr(file_val,fileInput)){
			 return false;
		 }
		 			 
		 var isNext = false;
	     var fileEnd = fileInput.substring(fileInput.indexOf("."));
        for (var i = 0; i < fileTypes.length; i++) {
            if (fileTypes[i] == fileEnd) {
                isNext = true;
                break;
            }
        }
        if (!isNext){
            layer.msg('不接受此文件类型',{area:['300px', '50px']});
            return false;
        }
        var file_sel_size = $(e).get(0).files[0].size;
        file_con.push(file_sel_size);
		if(file_size(file_con)){
        file_val.push(fileInput);
		var img_name = $(e).get(0).files[0];		
		var img_src = window.URL.createObjectURL(img_name);
		$(e).prev().prev().remove();
		$(e).prev().remove();
		$(e).hide();
		$(e).before('<img onclick="img_(this)" src="' + img_src + '" class="smallImg" />'+
				'<span class="btn_remove"><button onclick=remove_feed_img(this,'+ file_sel_size +')>移除</button></span>');
		  $(e).parent().parent().after(
				  '<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 feed_margin">'+
				  '<div class="feed_img_box">'+
				  '<i class="fa fa-upload upload_icon" aria-hidden="true"></i>'+
				  '<span class="back_upload_text">选择图片</span>'+
				  '<input onchange="feed_file(this)" type="file" class=" add_inp selector_file" id="" name="feedbackImage" value="">'+
				  '</div></div>'
				  );
	}
	}
}



//文件查重
function isInArr(arr,val){
	 for(var i = 0; i < arr.length; i++){
	        if(val == arr[i]){
	        	layer.msg('文件已选择',{area:['300px', '50px']});
	            return false;
	        }
	    }
	 return true;
}

//监测文件大小
function file_size(arr){
	var f_size=0;
	for(var i = 0;i < arr.length; i++){
		//f_size += $(arr[i]).get(0).files[0].size;
		f_size +=arr[i];//计算文件大小总和
	}
	if(f_size/(1024*1024)>30){
		file_con.pop();//如果超出大小将最后选择的去除掉
		layer.msg('上传图片大小不能超过30M',{area:['300px', '50px']});
		return false;
	}else{
		return true;
	}
}
		