/**
 * 弹出层
 */
function addMsg(msg, type) {
	var $box = $(".msg-box");
	var msgDialog = "";
	var msgBtn = '<a href="#success-msg-'+type+'" data-rel="popup" data-position-to="window" data-role="button" data-inline="true" style="display: none;" id="success-btn">弹出窗口</a>';
	
	if (msg == "ok") {
		msgDialog = '<div data-role="popup" id="success-msg-'+type+'" data-theme="c" class="ui-corner-all success-msg" data-transition="pop" data-overlay-theme="a">'+
			'<header data-role="header" data-theme="b" class="ul-corner-top orange">'+
				'<h2 class="longContent">消息</h2>'+
			'</header>'+
			'<div data-role="content" data-theme="a" class="ui-corner-bottom ui-content">'+
				'<p class="longContent">报障提交成功，我们会尽快安排人员跟进。</p>'+
				'<a href="#" data-role="button" data-transition="flow" data-theme="b" data-rel="back" class="ok">确认</a>'+
			'</div>'+
		'</div>';
	} else if (msg == "faqOk") {
		msgDialog = '<div data-role="popup" id="success-msg-'+type+'" data-theme="c" class="ui-corner-all success-msg" data-transition="pop" data-overlay-theme="a">'+
			'<header data-role="header" data-theme="b" class="ul-corner-top orange">'+
				'<h2 class="longContent">消息</h2>'+
			'</header>'+
			'<div data-role="content" data-theme="a" class="ui-corner-bottom ui-content">'+
				'<p class="longContent">咨询提交成功，我们会尽快安排人员跟进。</p>'+
				'<a href="#" data-role="button" data-transition="flow" data-theme="b" data-rel="back" class="ok">确认</a>'+
			'</div>'+
		'</div>';
	} else {
		msgDialog = '<div data-role="popup" id="success-msg-'+type+'" data-theme="c" class="ui-corner-all success-msg" data-transition="pop" data-overlay-theme="a">'+
			'<header data-role="header" data-theme="b" class="ul-corner-top">'+
				'<h2 class="longContent">消息</h2>'+
			'</header>'+
			'<div data-role="content" data-theme="a" class="ui-corner-bottom ui-content">'+
				'<p class="longContent">'+msg+'</p>'+
				'<a href="#" data-role="button" data-transition="flow" data-theme="b" data-rel="back" class="ok">确认</a>'+
			'</div>'+
		'</div>';
	}
	$box.html(msgBtn+msgDialog);
}