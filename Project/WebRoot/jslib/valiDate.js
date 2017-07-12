var Validate = function(){

};


/**--是否为空--**/
Validate.isNull = function(str){
	//$.trim(str) str.trim()
	if(str == null){
		return true;
	}else if($.trim(str) == ""){
		return true;
	}else{
		return false;
	}
}


Validate.isNotBlank = function(str){
	if(!str || null == str || str.length == 0) return false;
	str = str.replace(/^[\s\u3000]+|[\s\u3000]+$/g,"");
	if(!str || null == str || str.length == 0) return false;
	return true;
};

/**--是否为数字--**/
Validate.isNum = function(str){
	var regular = /^[1-9][0-9]*([.]?[1-9][0-9]*)?$/;
	return regular.test(str);
};

/**--是否为邮箱--**/
Validate.isEmail = function(str){
	var regular  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return regular.test(str);
};

/**--是否含有非法字符--**/
Validate.haveSpecial = function(str) {
//    var pattern = new RegExp("[`~!@#$^&*=|{}'\":;',\\[\\]<>?~！@#￥……&*（）|{}【】‘；：”“'。，、？]");
	var pattern = new RegExp("[`~!@#$^*=|{}'\":;',\\[\\]<>?~！@#*|{}]");
    if (pattern.test(str)) {
        return true;
    }
    return false;  
};

/**--是否为URL--**/
Validate.isURL = function(str_url){
	//var strRegex ="^(\b[\w-]+://)?((([\w-]){1,61}\.){0,2}(([\w-]){1,61}\.)(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|me|asia|hk)(\.cn)?|(25[0-5]|2[0-4]\d|1\d\d|\d{1,2})(\.(25[0-5]|2[0-4]\d|1\d\d|\d{1,2})){3})(:\d{0,5})?(/?[\w\.\,\?\'\\\+&amp;%\$#\=~_\-\?\&\@]+)*$";
	
	var strRegex ="^(\\b[\\w-]+://)?" +//网站前缀
			"((([\\w-]){1,61}\\.){0,2}(([\\w-]){1,61}\\.)" + //普通域名
			"(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|me|asia|hk|cn|cc|tw|web|rec)" + //顶级域名
			"|(25[0-5]|2[0-4]\\d|1\\d\\d|\\d{1,2})(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|\\d{1,2})){3})" + //IP域名
			"(:\\d{0,5})?" + //端口号
			"(/?[\\w\\.\\,\\?\\'\\\\\\+&amp;%\\$#\\=~_\\-\\?\\&\\@]?)*$";
	var re = new RegExp(strRegex);
	if (re.test(str_url)){
		return true;
	}else{
		return false;
	}
}


/**--是否为手机号--**/
Validate.isPhone= function(str){
	var strRegex ="^1[3|4|5|7|8][0-9]\\d{8}$";
	var re = new RegExp(strRegex);
	return re.test(str);
}

/**--电话号码--**/
Validate.isTelephoneNum= function(str){
	var strRegex=	"^1[3|4|5|7|8][0-9]\\d{8}$" +
					"|^\\d{3}[\\-]\\d{7,8}([\\-]\\d{0,4})?$" +
					"|^\\d{4}[\\-]\\d{7,8}([\\-]\\d{0,4})?$" +
					"|^\\d{5}$";
	var re = new RegExp(strRegex);
	return re.test(str);
}



/*Date format is "YYYY-mm" */
Validate.dateIsAfter = function(dateStr1,dateStr2,isAllowEquals){
    if(!Validate.isNotBlank(dateStr1) || !Validate.isNotBlank(dateStr2)){
        return false;
    }
    if(isAllowEquals && dateStr1==dateStr2){
        return false;
    }
    var yearMonth1 = dateStr1.split("-");
    var year1 = parseInt(yearMonth1[0]);
    var month1 = parseInt(yearMonth1[1]);
    
    var yearMonth2 = dateStr2.split("-");
    var year2 = parseInt(yearMonth2[0]);
    var month2 = parseInt(yearMonth2[1]);
    
    if(year1 != year2){
        return year1 > year2;
    }
    if(month1 != month2){
        return month1 > month2;
    }
    return true;
    
};


Validate.containBlank=function(str){
	if(null==(/[\u3000\s]/g.exec(str))){
		return false;
	}else{
		return true;
	}
	
}

/**--用户名--**/
Validate.isUserName= function(str){
	//中文英文限制
	var strRegex=	"^[\\u4e00-\\u9fa5]{2,6}$" +
					"|^[A-Za-z]{1}[A-Za-z0-9\\-\\_]{3,11}$" +
					"|^[A-Za-z\\u4e00-\\u9fa5]{1}[\\u4e00-\\u9fa5A-Za-z0-9\\-\\_]{2,10}$" 
					;
	var re = new RegExp(strRegex);
	return re.test(str);
};
Validate.isSymbolPass= function(str){
	var pattern=new RegExp("[\\s]");
	if (pattern.test(str)) {
	        return true;
	    }
	return false;  
};

/**--密码--**/
Validate.isPassWord=function(str){
//	var strRegex=new RegExp(	
//	"[A-Za-z0-9\\-\\_~!@#$^*=,?~！@#*$:;]{6,16}")
//	;
	//(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*])(?=.*[A-Z]).{6,16}
	//var pattern=new RegExp("[\\S]{6,16}");
	//(?=.*[a-zA-Z])(?=.*[\d])[\w\W]{6,16}
	//str =str.toUpperCase();

	var pattern = new RegExp("^(?![0-9]+$)(?![a-zA-Z-_~\\!@#\\$\\^\\*\\=,\\?]+$)"+
									"[0-9A-Za-z-_~\\!@#\\$\\^\\*\\=,\\?~]{6,16}$");
	//var pattern = new RegExp("[]/g");
	return pattern.test(str);
};
Validate.isMaxLength= function(str,maxLength){
	//判断字符是否超长
	 var realLength = 0, 
	 	 len = str.length, 
	 	 charCode = -1;
	    for (var i = 0; i < len; i++) {
	        charCode = str.charCodeAt(i);
	        if (charCode >= 0 && charCode <= 128) realLength += 1;
	        else realLength += 2;
	    }
	    return realLength<=maxLength?true:false;
};

/**--是否为sql字符--**/
Validate.isSql= function(str){
//	alert(str);
	str =str.toUpperCase();
	//判断是否是sql写法
	var strRegex=	  "^(SELECT)\\s*.*\\s*(FROM)\\s+[0-9a-zA-Z\\-\\_]+\\s*(WHERE)?$" +
					  "|^(INSERT)\\s+(INTO)\\s+[0-9a-zA-Z\\-\\_]+(\\(.*\\))?\\s+(VALUES)\\s*\\(.*\\)\\s*;+$"+
					  "|^(UPDATE)\\s+[0-9a-zA-Z\\-\\_]+\\s+(SET)\\s+[0-9a-zA-Z\\-\\_]+\\s*\\=[0-9a-zA-Z\\-\\_]+\\s*(,\\s*[0-9a-zA-Z\\-\\_]+\\s*\\=[0-9a-zA-Z\\-\\_]+)*\\s+(WHERE)\\s*$"+
					  "|^(DELETE)\\s+(FROM)\\s+[0-9a-zA-Z\\-\\_]+\\s+(WHERE)?$"+
					  "|^((CREATE)|(DROP)|(ALTER)){1}\\s+(UNIQUE\\s+)?((TABLE|INDEX|TRIGGER|JOB|PROCEDURE)){1}\\s[0-9a-zA-Z\\-\\_]*$"
					;
	var re = new RegExp(strRegex);
	return re.test(str);
};

/**--是否为html字符--**/
Validate.isHtml= function(str){
	str = str.toLowerCase();
	//判断是否是sql写法
	var strRegex= "(<(\\/)?\\s*)(html|script|h[0-6]|span|link|a|div|from|strong|em|i|fram|iframe|body|head|footer|style|)\\s*.*>|(function\\s*\\(+.*\\))|(alert\\s*\\(+.*\\))";
	var re = new RegExp(strRegex);
	return re.test(str);
};


//中文姓名正则[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*
Validate.isName= function(str){
	str = str.toLowerCase();
	//判断是否是sql写法
	var strRegex= "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*";
	var re = new RegExp(strRegex);
	return re.test(str);
};



