jQuery.extend(jQuery.validator.messages, {
    required: "请填写相关信息",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
    minlength: jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
    rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
    range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: jQuery.validator.format("请输入一个最大为{0} 的值"),
    min: jQuery.validator.format("请输入一个最小为{0} 的值")
});

//非数字
jQuery.validator.addMethod("noNum", function(value, element) {  
    var length = value.length;
    var noNum = /^[^0-9]+$/;     
    return ((length > 0 && noNum.exec(value)))? true:false;  
}, "请填写非数字内容"); 

//中文验证
jQuery.validator.addMethod("chinese", function(value, element) {
    var length = value.length;
    var chinese = /^[\u4e00-\u9fa5]+$/;
    return ((length > 0 && chinese.exec(value)))? true:false;     
},"请填写中文信息");

//中文或英文
jQuery.validator.addMethod("ch_en", function(value, element) {
    var length = value.length;
    var chinese = /^[\u4e00-\u9fa5]+$/;
    var english = /^[a-zA-Z]+$/;
    var noNum = /^[^0-9]+$/; 
    return ( (english.exec(value) || chinese.exec(value)) && noNum.exec(value) )? true:false;     
},"请填写中文或英文");

//号码非空
jQuery.validator.addMethod("requiredNum", function(value, element) {  
    var length = value.length;     
    return ( length > 0 )? true:false;  
}, "请填写您的手机号码或固定电话"); 

//手机号码或固定号码，手机号码格式：13, 15, 18 开头+8位； 固定电话格式： 前4或3位区号-8到7位号码
jQuery.validator.addMethod("ismobile", function(value, element) {  
    var length = value.length;     
    // var mobileRule = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
    var mobileRule = /^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))+\d{8}$/;
    // var longTelephoneRule = /^\d{3,4}-?\d{7,9}$/;
    var longTelephoneRule = /^\d{3,4}-\d{7,9}$/;
    var shortTelephoneRule = /^\d{7,9}$/     
    // return ((length == 11 && mobileRule.exec(value)) || (longTelephoneRule.exec(value)) || (shortTelephoneRule.exec(value)))? true:false;
    return ((length == 11 && mobileRule.exec(value)) || ( (length>=11) && longTelephoneRule.exec(value)) || (((length>=7) && (length<=8)) && shortTelephoneRule.exec(value)))? true:false; 

}, "请正确填写您的电话号码");  


/*
$(document).ready(function (){
    $("#trouble-ok").on("click", function(){
        $.mobile.changePage("/servlet/wap/WapEnter?handle=myTrouble", "pop");
    });
    $("#faq-ok").on("click", function(){
        //$.mobile.changePage("report.html", "pop");
    	$.mobile.changePage("/servlet/wap/WapEnter?handle=faqList", "pop");
    });
    $("#form").validate({
        submitHandler: function ( form ) {
            console.log("提交成功");
            $(".headerBar").hide();
            $(".footerBar").hide();
            function submitTimeout() {
                $("#success-btn").click();
            }
            setTimeout(submitTimeout, 1000);
            function okTimeout () {
                $(".ok").click();
                $(".headerBar").show();
                $(".footerBar").show();
            }
            setTimeout(okTimeout, 3000);
        }
    });
});
*/
/*
$(document).ready(function (){
	$("#form").validate({
		submitHandler: function ( form ) {
			console.log("提交成功");
			form.submit();
        }
	});
});
*/