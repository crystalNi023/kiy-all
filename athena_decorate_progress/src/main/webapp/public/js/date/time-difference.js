function  getDaysByDateString(dateString1,dateString2){
     //获取起始时间的毫秒数  	 
    //其中dateString1.replace(/-/g,'/')是将日期格式为yyyy-mm-dd转换成yyyy/mm/dd  
    //Date.parse()静态方法的返回值为1970年1月1日午时到当前字符串时间的毫秒数，返回值为整数  
	//如果传入的日期只包含年月日不包含时分秒，则默认取的毫秒数为yyyy/mm/dd 00:00:00  
	//取的是0时0分0秒的毫秒数，如果传入的是2015/07/03 12:20:12则取值为该时间点的毫秒数  
    var  startDate=Date.parse(dateString1.replace(/-/g,'/'));  
    var  endDate=Date.parse(dateString2.replace(/-/g,'/'));  
	//因为不传时分秒的时候 默认取值到dateString2的0时0分0秒时的毫秒数，这样就不包含当前天数的毫秒数  
	//如果计算时要包含日期的当前天，就要加上一天的毫秒数，我的业务需要，将加上了。  
    // var diffDate=(endDate-startDate)+1*24*60*60*1000;  
    var diffDate=(endDate-startDate);
	 //计算出两个日期字符串之间的相差的天数  
      var days=diffDate/(1*24*60*60*1000);  
      //alert(diffDate/(1*24*60*60*1000));  
      //console.log(days);
       return  days;  
}