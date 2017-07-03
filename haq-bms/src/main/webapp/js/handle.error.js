/**
 *  错误处理handle add by sunxushe
 */
var errorHandle = {
		
		deal : function(data){
			
			var r = errorHandle.parse(data);
			
			$.showMsg(r[2]);
			
			switch (parseInt(r[0], 10)) {
			case 4011:
				errorHandle.login.call(null);
				break;
			case 404:
			//	errorHandle.notfund.call(null);
				break;
			default:
				break;
			}
		} ,
		parse : function(json){
			if(!json) return;
			
			var status = json.status;
			
			var result	= $.toJSON(json.responseText);
			
			return [status, result.result,result.msg ];
			
		} ,
		login : function(){
			
			var $div = $.loadHtml('登录','',['450px','270px'])
			
			$div.template('temp-user-login',{}).removeClass('iframe');
			
			$div.on('click','img',function(){
				var $t  = $(this),
					src = $t.attr('src');
				
				$t.attr('src',src + new Date().getTime() );
				
			}) , 
			errorHandle.ajaxSubmit.call(this,$div.find('form'),function(data){
				// $div.remove();
				if(data.result) {
					$.closeLayer();
				}
			})
		} ,
		notfund: function(){
			$.showMsg('服务器没有该资源');
		} ,
		ajaxSubmit : function(form,arg){
			if(!form) return;
			$btn = form.find('.fm-submit');
			form.validationEngine();
			$btn.click(function(){
				if(!form.validationEngine('validate')) return;
				form.ajaxSubmit(function(data){
					$.showMsg(data.msg);
					if($.isFunction(arg)) {
						arg.call(this,data)
					} else {
						if(data.result && arg) $.reload();
					}
				})
			});
		}
		
}

