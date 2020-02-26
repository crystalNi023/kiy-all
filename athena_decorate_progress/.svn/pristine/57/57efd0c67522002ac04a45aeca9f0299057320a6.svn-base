//bootstarp分页插件的封装
function bootstrap_paginator(page_no,total_Page){	
	$('#pageLimit').bootstrapPaginator({    
    currentPage: page_no,    //当前页
    totalPages: total_Page,    //总页数
    size:"normal",    //设置控件显示的页码数
    bootstrapMajorVersion: 3,   //如果是bootstrap3版本需要加此标识，并且设置包含分页内容的DOM元素为UL,如果是bootstrap2版本，则DOM包含元素是DIV 
    alignment:"right",    
    numberOfPages:5,    
    itemTexts: function (type, page, current) {        
        switch (type) {            
        case "first": return "首页";            
        case "prev": return "上一页";            
        case "next": return "下一页";            
        case "last": return "末页";            
        case "page": return page;
        }
    },
    //onPageClicked为分页按钮的回调函数
    onPageClicked: function(event,originalEvent,type,page){   	
    	currentPage = page;
    	var form = $.param({"pageNo":currentPage })+ "&" + $.param({'comId':comId}) + '&' + $("#form").serialize();
    	$.ajax({
    		type:'post',
    		url:'/project/queryList',
    		data:form,
    		dataType:'json',
    		success:function(data){
    			add_project_con(data);
    		},
    		error:function(){
    			
    		}
    	})
    }
    
});
}