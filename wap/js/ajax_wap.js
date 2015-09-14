var myajax={
	
	//没登陆时要执行的函数
	doOnLogin:"",
	
	//访问允许的最长时间
	timeout:650000,

	//通过ajax提交，不直接调用
	post:function(url,data,callback,dataType,isHiddeTips,isHideErr){
		if(!isHiddeTips) myajax.hidTips();//隐藏提示
		$.ajax({
			type: "POST",
			url: url,
			//async:false,
			async:true,
			dataType: dataType,
			data: data,
			cache: false,
			timeout:myajax.timeout,
			beforeSend: function(){
				if(!isHiddeTips){
					myajax.showTips();//显示提示：数据加载中
				}
			},
			complete:function(){

			},
			error: function(e,error){
				if(!isHiddeTips) myajax.hidTips();
				if(!isHideErr){
					alert("error="+error);
				}
			}, 
			success:function(html){
				if(!isHiddeTips) myajax.hidTips();//隐藏提示
				flag=html;
				if(flag==-2){
					alert("你没有操作此功能的权限！");
					//html="没有权限";
				}else if(flag==-1){
					if(document.getElementById("_DialogDiv_login")==null){//没有登陆框才弹出登陆框
						//boLogin.loginBox();	//利用闭包调用，弹出登陆框,待修改
						myajax.doOnLogin=function(){//利用闭包调用，更新登录后执行的函数
							myajax.post(url,data,callback,dataType,isHiddeTips);
						}
					}
				}else{			 
					//if(callback!=null&&callback!='') {
						callback(html);
					//}else{
						//alert(html);
						return html;	
					//}
				}
			}
		});

	},
	
	/**
	表单提交，不直接调用
	*/
	submitform:function (formId,callback,dataType,isHiddeTips,isHideErr){
		if(document.getElementById(formId)==null) return;
		if(!isHiddeTips) myajax.hidTips();//隐藏提示
		//if(!isHiddeTips) this.showTips();
		var actionURL=$("#"+formId).attr("action");
		actionURL+=actionURL.indexOf("?")==-1?"?isAjax=1":"&isAjax=1";
	
		var options = { 
			//target:        '#output1',   // target element(s) to be updated with server response 
			//beforeSubmit:  showRequest,  // pre-submit callback 
			cache: false,
			beforeSend: function(){
				if(!isHiddeTips) myajax.showTips();//显示提示：数据加载中
			},
			complete:function(){
				//if(!isHiddeTips) myajax.hidTips();//隐藏提示
			},			
			error: function(e,error){
				if(!isHiddeTips) myajax.hidTips();
				if(!isHideErr) alert(error);
			}, 
			timeout:myajax.timeout,
			success:function(html){
				if(!isHiddeTips) myajax.hidTips();//隐藏提示
				flag=html;
				if(flag==-2){
					alert("你没有操作此功能的权限！");
					//html="没有权限";
				}else if(flag==-1){
					if(document.getElementById("_DialogDiv_login")==null){//没有登陆框才弹出登陆框					
						//boLogin.loginBox();//待修改
						myajax.doOnLogin=function(){
							myajax.submitform(formId,callback,dataType,isHiddeTips);
						}
					}
				}else{			 
					if(callback!=null&&callback!='') callback(html,formId);
				}
			}, 
			// other available options: 
			url:       actionURL,         // override for form's 'action' attribute 
			//type:      type        // 'get' or 'post', override for form's 'method' attribute 
			dataType:  dataType        // 'xml', 'script', or 'json' (expected server response type) 
			//clearForm: true        // clear all form fields after successful submit 
			//resetForm: true        // reset the form after successful submit 
			//,iframe:true
	 
			// $.ajax options can be used here too, for example: 
			//timeout:   3000 
		};
	
//			alert($('#'+formId));

		$('#'+formId).ajaxSubmit(options);
		//if(!isHiddeTips) this.hidTips();
	},

	/**
	表单提交，返回json数据
	*/
	submitformJSON:function(formId,callback,isHiddeTips,isHideErr){
		this.submitform(formId,callback,"json",isHiddeTips,isHideErr);
	},

	/**
	表单提交，返回json数据
	*/
	submitformHTML:function(formId,callback,isHiddeTips,isHideErr){
		this.submitform(formId,callback,"html",isHiddeTips,isHideErr);
	},

	//通过ajax提交改变目标div的html,使用form提交
	ajaxlinkForm:function(formId,target,isHideErr){
		if(document.getElementById(formId)==null) return;

		if(target==null) target=this.mainTarget;
		
		//如果没有放置内容的容器则不用ajax提交
		if(document.getElementById(target)==null){
			document.getElementById(formId).submit();
			return;			
		}

		var $form=$("#"+formId);
		data=$form.formSerialize();
		url=$form.attr("action");
		this.setBackUrl(url,data);

		this.submitformHTML(
			formId,
			function(html){
				myajax.changeHtmlById(html,target);
			},
			false,
			isHideErr
		);
	},

	/**
	*通过ajax提交改变目标div的html
	target 省略的话使用默认的target
	*/
	ajaxlink:function(url,data,target,isHideErr){
		//if(target==null) target=this.mainTarget;

//		this.setBackUrl(url,data);
		
		this.postHTML(
			url,
			data,
			function(html){
					myajax.changeHtmlById(html,target);
			},
			false,
			isHideErr
		);
	},
		
	//通过ajax提交，返回html代码
	postHTML:function(url,data,callback,isHiddeTips,isHideErr){
		this.post(url,data,callback,'html',isHiddeTips,isHideErr);
	},

	//通过ajax提交，返回json代码
	postJSON:function(url,data,callback,isHiddeTips,isHideErr){
		this.post(url,data,callback,'json',isHiddeTips,isHideErr);
	},

	//更改目标的html
	changeHtmlById:function(html,target){
		$('#'+target).empty();
		$('#'+target).html(html);
		//this.replaceHtml(target,html);
		//document.getElementById(target).innerHTML=html;

	},
	
	replaceHtml:function(el, html) { 
     var oldEl = typeof el === "string" ? document.getElementById(el) : el; 
     var newEl = oldEl.cloneNode(false); 
     newEl.innerHTML = html; 
     oldEl.parentNode.replaceChild(newEl, oldEl); 
     return newEl; 
	},
	
	
	showTips:function(){
        $.mobile.loading('show', {  
            text: '数据加载中...', //加载器中显示的文字  
            textVisible: true, //是否显示文字  
            theme: 'a',        //加载器主题样式a-e  
            textonly: false,   //是否只显示文字  
            html: ""           //要显示的html内容，如图片等  
        }); 
		//alert($.mobile.showPageLoadingMsg);
	},
	
	hidTips:function(){
		$.mobile.loading('hide'); 
	}	
	
};