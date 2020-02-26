window.onhashchange = function(){
    var hash = location.hash;
    hash = hash.substring(1,hash.length);
    if(hash!=''){
    	document.getElementById('page_con').src = hash;
    }
};
function loadIframe(url){
  parent.window.location.href = parent.window.location.href + "#";
  var u = parent.window.location.href;
  var end = u.indexOf("#");
  var rurl = u.substring(0,end);
  //设置新的锚点
  parent.window.location.href = rurl + "#" + url;
/*  var hash = location.hash;
  hash = hash.substring(1,hash.length);
  $("#page_con").attr("src",hash);*/
};
document.addEventListener('DOMContentLoaded', function () {	
     var hash = location.hash;     
     if(hash!=''){
    	 var url = hash.substring(1,hash.length);
    	 parent.document.getElementById('page_con').src = url;
     }   
}, false);

window.onload = function(){
	var children_body = parent.document.getElementById('page_con').contentWindow.document.getElementsByTagName('body')[0];
	if(children_body.id == '1'){
		 $('#1').addClass('hea_border');
		 $('#1').parent().siblings().children().removeClass('hea_border');
     }
     if(children_body.id == '2'){
    	 $('#2').addClass('hea_border');
		 $('#2').parent().siblings().children().removeClass('hea_border');
     }
     if(children_body.id == '3'){
    	 $('#3').addClass('hea_border');
		 $('#3').parent().siblings().children().removeClass('hea_border');
     }
     if(children_body.id == '4'){
    	 $('#4').addClass('hea_border');
		 $('#1,#2,#3').removeClass('hea_border');
     }
}