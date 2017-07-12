$.fn.panel.defaults.loadingMessage = '数据加载中....';
$.fn.datagrid.defaults.loadMsg = '数据加载中....';

// panel关闭回收内存
$.extend($.fn.panel.defaults, {
	onBeforeDestroy : function() {
		var frame = $('iframe', this);
		try {
			if (frame.length > 0) {
				for ( var i = 0; i < frame.length; i++) {
					frame[i].src = '';
					frame[i].contentWindow.document.write('');
					frame[i].contentWindow.close();
				}
				frame.remove();
				if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
					try {
						CollectGarbage();
					} catch (e) {
					}
				}
			}
		} catch (e) {
		}
	}
});
// 防止屏幕跑偏
var easyuiPanelOnMove = function(left, top) {
	var l = left;
	var t = top;
	if (l < 1) {
		l = 1;
	}
	if (t < 1) {
		t = 1;
	}
	var width = parseInt($(this).parent().css('width')) + 14;
	var height = parseInt($(this).parent().css('height')) + 14;
	var right = l + width;
	var buttom = t + height;
	var browserWidth = $(window).width();
	var browserHeight = $(window).height();
	if (right > browserWidth) {
		l = browserWidth - width;
	}
	if (buttom > browserHeight) {
		t = browserHeight - height;
	}
	$(this).parent().css({/* 修正面板位置 */
		left : l,
		top : t
	});
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;

// 通用错误
var easyuiErrorFunction = function(XMLHttpRequest) {
	if (parent.$ && parent.$.messager) {
		parent.$.messager.progress('close');
		if(XMLHttpRequest.responseText!=undefined&&XMLHttpRequest.responseText!=''){
			parent.$.messager.alert('错误', XMLHttpRequest.responseText, 'error');
		}else{
			parent.$.messager.alert('错误', 'easyui加载数据异常', 'error');
		}
	} else {
		$.messager.progress('close');
		if(XMLHttpRequest.responseText!=undefined&&XMLHttpRequest.responseText!=''){
			$.messager.alert('错误', XMLHttpRequest.responseText, 'error');
		}else{
			$.messager.alert('错误', 'easyui加载数据异常', 'error');
		}
	}
}
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

/**
 * 扩展combobox在自动补全模式时，检查用户输入的字符是否存在于下拉框中，如果不存在则清空用户输入
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.combobox.defaults, {
	onShowPanel : function() {
		var _options = $(this).combobox('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _value = $(this).combobox('textbox').val();
			var _combobox = $(this);
			if (_value.length > 0) {
				$.post(_options.url, {
					q : _value
				}, function(result) {
					if (result && result.length > 0) {
						_combobox.combobox('loadData', result);
					}
				}, 'json');
			}
		}
	},
	onHidePanel : function() {
		var _options = $(this).combobox('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _data = $(this).combobox('getData');/* 下拉框所有选项 */
			var _value = $(this).combobox('getValue');/* 用户输入的值 */
			var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
			for (var i = 0; i < _data.length; i++) {
				if (_data[i][_options.valueField] == _value) {
					_b = true;
				}
			}
			if (!_b) {/* 如果在下拉列表中没找到用户输入的字符 */
				$(this).combobox('setValue', '');
			}
		}
	}
});

/**
 * 扩展combogrid在自动补全模式时，检查用户输入的字符是否存在于下拉框中，如果不存在则清空用户输入
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.combogrid.defaults, {
	onShowPanel : function() {
		var _options = $(this).combogrid('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _value = $(this).combogrid('textbox').val();
			if (_value.length > 0) {
				$(this).combogrid('grid').datagrid("load", {
					q : _value
				});
			}
		}
	},
	onHidePanel : function() {
		var _options = $(this).combogrid('options');
		if (_options.mode == 'remote') {/* 如果是自动补全模式 */
			var _data = $(this).combogrid('grid').datagrid('getData').rows;/* 下拉框所有选项 */
			var _value = $(this).combogrid('getValue');/* 用户输入的值 */
			var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */
			for (var i = 0; i < _data.length; i++) {
				if (_data[i][_options.idField] == _value) {
					_b = true;
				}
			}
			if (!_b) {/* 如果在下拉列表中没找到用户输入的字符 */
				$(this).combogrid('setValue', '');
			}
		}
	}
});

/**
 * 扩展tree和combotree，使其支持平滑数据格式
 * 
 * @requires jQuery,EasyUI
 * 
 */
var loadFilter = function(data, parent) {
	var opt = $(this).data().tree.options;
	var idField, textField, parentField;
	if (opt.parentField) {
		idField = opt.idField || 'id';
		textField = opt.textField || 'text';
		parentField = opt.parentField || 'pid';
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idField]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]]
					&& data[i][idField] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textField];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textField];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
}
$.extend($.fn.combotree.defaults, loadFilter);
$.extend($.fn.tree.defaults, loadFilter);

/**
 * 扩展treegrid，使其支持平滑数据格式
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.extend($.fn.treegrid.defaults, {
	loadFilter : function(data, parentId) {
		var opt = $(this).data().treegrid.options;
		var idField, treeField, parentField;
		if (opt.parentField) {
			idField = opt.idField || 'id';
			treeField = opt.textField || 'text';
			parentField = opt.parentField || 'pid';
			var i, l, treeData = [], tmpMap = [];
			for (i = 0, l = data.length; i < l; i++) {
				tmpMap[data[i][idField]] = data[i];
			}
			for (i = 0, l = data.length; i < l; i++) {
				if (tmpMap[data[i][parentField]]
						&& data[i][idField] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children'])
						tmpMap[data[i][parentField]]['children'] = [];
					data[i]['text'] = data[i][treeField];
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					data[i]['text'] = data[i][treeField];
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
		return data;
	}
});

var changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for (var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			try {
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			} catch (e) {
				try {
					ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
				} catch (e) {
				}
			}
		}
	}

	cookie('easyuiTheme', themeName, {
		expires : 7
	});
};

var modalDialog = function(options) {
	var opts = $.extend({
		title : '&nbsp;',
		width : 1000,
		height : 600,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
	if (options.url) {
		opts.content = '<iframe id="" src="' + options.url + '" allowTransparency="true" scrolling="auto" width="100%" height="98%" frameBorder="0" name=""></iframe>';
	}
	return $('<div/>').dialog(opts);
};

var WidescreenModalDialog = function(options) {
	var opts = $.extend({
		title : '&nbsp;',
		width : 1000,
		height : 600,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
	if (options.url) {
		opts.content = '<iframe id="" src="' + options.url + '" allowTransparency="true" scrolling="auto" width="100%" height="98%" frameBorder="0" name=""></iframe>';
	}
	return $('<div/>').dialog(opts);
};

$.extend($.fn.validatebox.defaults.rules, {
	eqPassword : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致,请重新输入!'
	},
	userName : {// 验证姓名，可以是中文或英文 
           validator : function(value) { 
               return /^[\u4e00-\u9fa5]{2,4}$/.test(value); 
           }, 
           message : '请输入中文,2-4位' 
   },
   userAccount : {// 验证用户名 
       validator : function(value) { 
           return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value); 
       }, 
       message : '用户帐号不合法（字母开头，字数6-16位，允许字母数字下划线）' 
   },
   ip : {// 验证IP地址
       validator : function(value) {
           return /^(\d|[1-9]\d|[1]\d{2}|2[0-5]{2})(\.(\d|[1-9]\d|[1]\d{2}|2[0-5]{2})){3}$/.test(value);
       },
       message : 'IP地址格式不正确'
   },
   passWord: {  
       validator: function(value, param){  
           return value.length >= param[0];  
       },  
       message: '密码长度不能低于{0}个字符'  
   },
   telePhone : {// 验证手机号码
       validator : function(value) { 
           return /^(13[0-9]|14[5|7]|15[0-9]|18[0-9])\d{8}$/i.test(value); 
       }, 
       message : '请输入正确的手机号码' 
   },
   email:{
	   validator : function(value) { 
           return  /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g.test(value); 
       }, 
       message : '请输入正确的电子邮箱' 
	  
   },
   customRequired:{
	   validator : function(value) { 
           return value.trim(); 
       }, 
       message : '请输入非空字符串' 
   },
   price:{
	   validator:function(value){
		   return /^(\d+\.\d{1,2}|\d+)$/.test(value);
	   },
	   message:'请输入正整数或者小数,小数点后保留1位或者2位小数'
   },tel:{
	   validator:function(value){
		   return /^(\d+)$/.test(value);
	   },
	   message:'请输入非负整数'
   },
   deptNo:{
	   validator:function(value){
		   return /^(\d{5})0$/.test(value);
	   },
	   message:'请输入6位数,末位必须是0'
   }
});

$.extend($.fn.tree.methods, {   
    /**
     * 激活复选框  
     * @param {Object} jq  
     * @param {Object} target  
     */  
    enableCheck : function(jq, target) {    
        return jq.each(function(){   
            var realTarget;   
            if(typeof target == "string" || typeof target == "number"){   
                realTarget = $(this).tree("find",target).target;   
            }else{   
                realTarget = target;   
            }   
            var ckSpan = $(realTarget).find(">span.tree-checkbox");   
            if(ckSpan.hasClass('tree-checkbox-disabled0')){   
                ckSpan.removeClass('tree-checkbox-disabled0');   
            }else if(ckSpan.hasClass('tree-checkbox-disabled1')){   
                ckSpan.removeClass('tree-checkbox-disabled1');   
            }else if(ckSpan.hasClass('tree-checkbox-disabled2')){   
                ckSpan.removeClass('tree-checkbox-disabled2');   
            }   
        });   
    },   
    /**
     * 禁用复选框  
     * @param {Object} jq  
     * @param {Object} target  
     */  
    disableCheck : function(jq, target) {   
        return jq.each(function() {   
            var realTarget;   
            var that = this;   
            var state = $.data(this,'tree');   
            var opts = state.options;   
            if(typeof target == "string" || typeof target == "number"){   
                realTarget = $(this).tree("find",target).target;   
            }else{   
                realTarget = target;   
            }   
            var ckSpan = $(realTarget).find(">span.tree-checkbox");   
            ckSpan.removeClass("tree-checkbox-disabled0").removeClass("tree-checkbox-disabled1").removeClass("tree-checkbox-disabled2");   
            if(ckSpan.hasClass('tree-checkbox0')){   
                ckSpan.addClass('tree-checkbox-disabled0');   
            }else if(ckSpan.hasClass('tree-checkbox1')){   
                ckSpan.addClass('tree-checkbox-disabled1');   
            }else{   
                ckSpan.addClass('tree-checkbox-disabled2')   
            }   
            if(!state.resetClick){   
                $(this).unbind('click').bind('click', function(e) {   
                    var tt = $(e.target);   
                    var node = tt.closest('div.tree-node');   
                    if (!node.length){return;}   
                    if (tt.hasClass('tree-hit')){   
                        $(this).tree("toggle",node[0]);   
                        return false;   
                    } else if (tt.hasClass('tree-checkbox')){   
                        if(tt.hasClass('tree-checkbox-disabled0') || tt.hasClass('tree-checkbox-disabled1') || tt.hasClass('tree-checkbox-disabled2')){   
                            $(this).tree("select",node[0]);   
                        }else{   
                            if(tt.hasClass('tree-checkbox1')){   
                                $(this).tree('uncheck',node[0]);   
                            }else{   
                                $(this).tree('check',node[0]);   
                            }   
                            return false;   
                        }   
                    } else {   
                        $(this).tree("select",node[0]);   
                        opts.onClick.call(this, $(this).tree("getNode",node[0]));   
                    }   
                    e.stopPropagation();   
                });   
            }   
               
        });   
    }   
});  
