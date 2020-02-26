$(function(){
	$('input').keyup(trimkeyup);
	//失去焦点时将控件的值做去空格处理  同时过滤掉文件选择器
	$('input[type!="file"]').blur(function(){
		$(this).val($(this).val().replace(/\s+/g,""));
	})
})

//空格监听d
function trimkeyup(e) {
    lucene_objInput = $(this);
     if(e.keyCode != 38 && e.keyCode != 40 && e.keyCode != 13) {
       var im = $.trim(lucene_objInput.val());
       lucene_objInput.val(im);
    }
}