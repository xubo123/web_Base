var cookie = function(key, value, options) {
	if (arguments.length > 1 && (value === null || typeof value !== "object")) {
		options = $.extend({}, options);
		if (value === null) {
			options.expires = -1;
		}
		if (typeof options.expires === 'number') {
			var days = options.expires, t = options.expires = new Date();
			t.setDate(t.getDate() + days);
		}
		return (document.cookie = [
				encodeURIComponent(key),
				'=',
				options.raw ? String(value) : encodeURIComponent(String(value)),
				options.expires ? '; expires=' + options.expires.toUTCString()
						: '', options.path ? '; path=' + options.path : '',
				options.domain ? '; domain=' + options.domain : '',
				options.secure ? '; secure' : '' ].join(''));
	}
	options = value || {};
	var result, decode = options.raw ? function(s) {
		return s;
	} : decodeURIComponent;
	return (result = new RegExp('(?:^|; )' + encodeURIComponent(key)
			+ '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};

var setIframeHeight = function(iframe, height) {
	iframe.height = height;
};

var serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		try {
			parent.$.messager.progress('close');
			if(XMLHttpRequest.responseText!=undefined&&XMLHttpRequest.responseText!=''){
				parent.$.messager.alert('错误', XMLHttpRequest.responseText, 'error');
			}else{
				parent.$.messager.alert('错误', 'ajax加载数据异常', 'error');
			}
		} catch (e) {
			alert(XMLHttpRequest.responseText);
		}
	}
});
