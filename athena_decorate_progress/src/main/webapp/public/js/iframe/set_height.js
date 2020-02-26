//function reinitIframe(e){
//var iframe = document.getElementById("test");
//try{
//var bHeight = iframe.contentWindow.document.body.scrollHeight;
//var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
//var height = Math.max(bHeight, dHeight);
////iframe.height = height;
//console.log("a"+bHeight);
//console.log("b"+dHeight);
//$("iframe").height(height);
//console.log($("iframe").contentWindow.document.body.scrollHeight);
//}catch (ex){}
//}

function reinitIframe(e){
		var body_height = e.contentWindow.document.body.scrollHeight;//窗口对象body高度
		var scroll_height = e.contentWindow.document.documentElement.scrollHeight;//可见区域高度
		var height_ = Math.max(body_height,scroll_height);
		e.height = height_+'px';
}




var iframe = parent.document.getElementById('page_con');

	 iframe.onload = function(){
	 	reinitIframe(this);	 
	 };
	
	 function iframe_src(num,e){
		 if(num==4){
			 $(e).addClass('hea_border');
			 $('#1,#2,#3').removeClass('hea_border');
		 }else{
			 $(e).addClass('hea_border');
			 $(e).parent().siblings().children().removeClass('hea_border');
			 $('#4').removeClass('hea_border');
		 }	 
		 if(num==1){
			 //iframe.src="/pages/project/athena_project.jsp";
			 loadIframe('/pages/project/athena_project.jsp');
		 }
		 if(num==2){
			 //iframe.src="/pages/customer/athena_archives.jsp";
			 loadIframe('/pages/customer/athena_archives.jsp');
		 }	
		 if(num==3){
			 //iframe.src="/pages/team/athena_staff.jsp";
			 loadIframe('/pages/team/athena_staff.jsp');
		 }	
		 if(num==4){
			 //iframe.src="/pages/team/athena_staff.jsp";
			 loadIframe('/pages/feedback/feedback.jsp');
		 }
	 }

//	 iframe[1].onload = function(){
//	 	reinitIframe(this);
//
//	 }
	

//var $iFrame=$("#test");
//if (!/*@aijquery@*/0) { //如果不是IE，IE的条件注释  
//  $iFrame[0].onload = function(){     
//      reinitIframe();
//      console.log("1");
//  };  
//}else{  
//  $iFrame[0].onreadystatechange = function(){ // IE下的节点都有onreadystatechange这个事件  
//      if (iframe.readyState == "complete"){  
//          reinitIframe();
//          console.log("2");
//      }  
//  };  
//}
