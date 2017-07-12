seajs.config({
	alias : {
		"jquery" : "jquery-1.10.2.js"
	}
});


function dialogAlert(title,content){
	seajs.use(['jquery', basePath+'/jslib/artDialog/src/dialog'], function ($, dialog) {
		var alert = dialog({
			title:title,
			content: content,
			width:200,
			okValue: '确定',
		    ok: function () {
		    	alert.close().remove();
		    }
		});
		alert.showModal();
	});
}

function dialogAlert(title,content,isCallback,type){
	seajs.use(['jquery', basePath+'/jslib/artDialog/src/dialog'], function ($, dialog) {
		var alert = dialog({
			title:title,
			content: content,
			width:200,
			okValue: '确定',
		    ok: function () {
		    	alert.close().remove();
		    	if(isCallback)
		    	{
		    		callback(type);
		    	}
		    }
		});
		alert.showModal();
	});
}


function dialogConfirm(title,content,isCallback,type){
	seajs.use(['jquery', basePath+'/jslib/artDialog/src/dialog'], function ($, dialog) {
		var alert = dialog({
			title:title,
			content: content,
			width:200,
			okValue: '确定',
			cancelValue:'取消',
		    ok: function () {
		    	if(isCallback)
		    	{
		    		callback(type);
		    	}
		    },
		    cancel: function () {
		    	alert.close().remove();
		    }
		});
		alert.showModal();
	});
}