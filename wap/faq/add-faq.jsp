<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="cn.qtone.modules.faq.Const"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://qtone.cn/jsp/tag/functions" prefix="qt_fn"%>
<%@ taglib uri="http://qtone.cn/jsp/tag" prefix="qt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>我要咨询</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
	<!-- my-faq -->
	<section data-role="page" data-title="我要咨询" id="my-consultation">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/input-error.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-my-consultation" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">我要咨询</h1>
		</header>
		<div data-role="content" class="content">
			<c:if test="${msg != null}">
				<div class="msg-box"></div>
				<script src="/jsp/wap/js/msg.js"></script> 
				<script>
					var type="add-faq";
					addMsg("${msg}", type);
					setTimeout(function(){
		            	$("#success-msg-"+type+"").popup("open");
		            }, 1500);
		            setTimeout(function(){	
		            	$("#success-msg-"+type+"").popup("close");
		            }, 4000);
				</script>
			</c:if>
			<form id="faqForm" name="addFaqForm" action="/servlet/wap/WapEnter?handle=addFaqPost" method="POST" data-ajax="true" >
				<div data-role="fieldcontain">
					<div class="row">
						<label>税号:</label>
						<input type="text" class="require" name="taxNO" id="taxNOFaq" placeholder="请输入税号" data-clear-btn="true" value="${customerWx.taxNO != 'null'? customerWx.taxNO : ''}" onblur="boxScriptFaq.getInfoByTaxNO();">
					</div>
					<div class="row">
						<label>公司:</label>
						<input type="text" class="required" readonly="readonly" name="companyName" id="companyName" placeholder="请输入您的公司名" value="${customerWx.companyName != 'null' ? customerWx.companyName : ''}">
						<input type="hidden" name="customerId" id="customerId" value="${customerWx.customerId != 'null' ? customerWx.customerId : ''}">
					</div>
					<div class="row">
						<label>联系人:</label>
						<input type="text" class="ch_en" name="linkman" id="linkman" placeholder="请输入您的姓名" data-clear-btn="true" value="${param.linkman}">
					</div>
					<div class="row">
						<label>联系电话:</label>
						<input type="text" class="requiredNum ismobile" name="tel" id="tel" placeholder="请输入您的电话号码" data-clear-btn="true" value="${param.tel}">
					</div>
					<div class="row">
						<label>问题:</label>
						<input type="text" class="required" name="questionTitle" id="questionTitle" placeholder="请输入您的问题" data-clear-btn="true" value="${param.questionTitle}">
					</div>
					<div class="row">
						<label>问题类型:</label>
						
						<select name="faqCategoryId">
			  			<c:forEach var="category" items="<%=Const.getFaqCategory()%>">
                			<option value="${category.key}" ${param.faqCategoryId==category.key?'selected':''}>${category.value}</option>
			  			</c:forEach>
              			</select>
					</div>
					<div class="row">
						<label>问题详述:</label>
						<textarea cols="40" class="required" rows="8" name="question" id="question">${param.question}</textarea>
					</div>
					<div class="row" id="submit-row">
						<input type="submit" name="faqSubmit" class="faqSubmit" id="faqSubmit" value="提交问题">
					</div>
				</div>
			</form>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-my-consultation">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
	<!--
		<div data-role="popup" id="success-msg" data-theme="c" class="ui-corner-all" data-transition="pop" data-overlay-theme="a">
			<header data-role="header" data-theme="b" class="ul-corner-top">
				<h1 class="longContent">消息</h1>
			</header>
			<div data-role="content" data-theme="a" class="ui-corner-bottom ui-content">
				<p class="longContent">${msg}</p>
				<a href="#" data-role="button" data-transition="flow" data-theme="b" id="faq-ok" class="ok">确认</a>
			</div>
		</div>
	 -->
		<footer data-role="footer" data-position="fixed" data-theme="b" role="contentinfo" class="ui-footer ui-bar-a ui-footer-fixed slideup footerBar">
			<div data-role="navbar" class="ui-navbar" role="navigation">
				<ul>
					<li class="ui-block-a longContent">
						<a href="/servlet/wap/WapEnter?handle=addFaq" class="ui-btn ui-btn-active ui-btn-icon-top ui-icon-user longContent">我要咨询</a>
					</li>
					<li class="ui-block-b longContent">
						<a href="/servlet/wap/WapEnter?handle=faqList" class="ui-btn ui-btn-icon-top ui-icon-clock longContent">历史咨询</a>
					</li>
				</ul>
			</div>
		</footer>
		<script src="/jsp/wap/js/jquery.validate.min.js"></script>
		<script src="/jsp/wap/js/basic.js"></script>
		<script src="/jsp/wap/js/input-reg.js"></script>
		<script src="/jsp/wap/js/ajax_wap.js"></script>
		<script>
		var $taxNOFaq = $("#taxNOFaq");
		var submitFlag = 1;
		//$(".troubleTypeId").first().attr("checked", "checked");
		$(document).ready(function (){
			$("#faqForm").validate({
				submitHandler: function ( form ) {
					if($taxNOFaq.hasClass("taxNOError")){
						$taxNOFaq.focus();
						if($(".warnText").length<1) {
							var warn = "<span class='warnText' style='color:red;'>请输出正确的税号</span>";
							$taxNOFaq.parent().append(warn);	
						}
						return;
					}
					
					$("#faqSubmit").attr("disabled", "true");
					//console.log("咨询提交成功");
					form.submit();
				}
			});
		});
		var boxScriptFaq={
			theform:document.addFaqForm,
			getInfoByTaxNO: function() {
				//console.log(this.theform.taxNO.value);
				myajax.postJSON("/servlet/wap/WapEnter?handle=getInfoByTaxNO","taxNO="+this.theform.taxNO.value,boxScriptFaq.getVisitByIdCB);
			},
			
			getVisitByIdCB:function(jsonData){
				if(jsonData.flag == 1) {//成功
					boxScriptFaq.theform.customerId.value=jsonData.customer.customerId;
					boxScriptFaq.theform.companyName.value=jsonData.customer.companyName;
					if(boxScriptFaq.theform.linkman.value == "") {
						boxScriptFaq.theform.linkman.value=jsonData.customer.linkman;	
					}
					if(boxScriptFaq.theform.tel.value == "") {
						boxScriptFaq.theform.tel.value=jsonData.customer.tel;	
					}
					//boxScriptFaq.theform.submit.disabled="true";
					//$("#submit").removeClass("ui-disabled");
					
					
				} else if(jsonData.flag == 2) {
					if($taxNOFaq.val() !== "" && $(".warnText").length<1) {
						var warn = "<span class='warnText' style='color:red;'>请输出正确的税号</span>";
						$taxNOFaq.parent().append(warn);
						//$("#submit").addClass("ui-disabled");
					}
					$taxNOFaq.addClass("taxNOError");
					boxScriptFaq.theform.customerId.value="";
					boxScriptFaq.theform.companyName.value="";
					//boxScriptFaq.theform.linkman.value="";
					//boxScriptFaq.theform.tel.value="";
					
					//$taxNO.focus();
					//boxScriptFaq.theform.submit.disabled="false";
					//$("#submit").addClass("ui-disabled");
				}
			}
		};
		
		$taxNOFaq.focus(function(){
			$taxNOFaq.removeClass("taxNOError");
			if($(".warnText").length>0) {
				$(".warnText").remove();	
			}
		});
		
		</script>
	</section>
	
</body>
</html>