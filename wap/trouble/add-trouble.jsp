<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://qtone.cn/jsp/tag" prefix="qt"%>
<%@ taglib uri="http://qtone.cn/jsp/tag/functions" prefix="qt_fn"%>
<%@ page import="cn.qtone.modules.trouble.Const"%>
<%@ page import="cn.qtone.utils.StringFunction" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>我要报障</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
</head>
<body>
	<!-- add-trouble -->
	<section data-role="page" data-title="我要报障" id="add-trouble">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/input-error.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-add-trouble" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">我要报障</h1>
		</header>
		<div data-role="content" class="content">
			<c:if test="${msg != null}">
				<div class="msg-box"></div>
				<script src="/jsp/wap/js/msg.js"></script> 
				<script>
					var type = "add-trouble";
					addMsg("${msg}", type);
					setTimeout(function(){
		            	$("#success-msg-"+type+"").popup("open");
		            }, 1500);
		            setTimeout(function(){	
		            	$("#success-msg-"+type+"").popup("close");
		            }, 4000);
				</script>
			</c:if>
			<form id="form" name="addTroubleForm" method="post" action="/servlet/wap/WapEnter?handle=addTroublePost" method="POST" data-ajax="true">
				<div data-role="fieldcontain">
					<div class="row">
						<label>税号:</label>
						<input type="text" class="required" name="taxNO" id="taxNO" data-clear-btn="true" placeholder="请输入税号" value="${customerWx.taxNO != 'null'? customerWx.taxNO : ''}" onblur="boxScript.getInfoByTaxNO();">
					</div>
					<div class="row">
						<label>公司名:</label>
						<input type="text" class="required" readonly="readonly" name="companyName" id="companyName" placeholder="请输入公司名" value="${customerWx.companyName != 'null' ? customerWx.companyName : ''}">
						<input type="hidden" name="customerId" id="customerId" value="${customerWx.customerId != 'null' ? customerWx.customerId : ''}">			
					</div>
					<div class="row">
						<label>上门地址:</label>
						<input type="text" class="required" name="address" id="address" placeholder="例如：东裕路8号" data-clear-btn="true" value="${param.address}">
					</div>
					<div class="row">
						<label>公司所在镇区:</label>
					    <select name="cityId" id="cityId">
                  			<c:forEach var="city" items="${cityList}">
	  							<option value="${city.cityId}" ${param.cityId==city.cityId?"selected":""}>${city.cityName}</option>
                  			</c:forEach>
               			</select>       
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
						<label>故障类型:</label>
				        <c:forEach var="type" items="<%=Const.getTroubleType()%>" >  
			  				<input type="radio" class="troubleTypeId" name="troubleTypeId"  id="troubleTypeId${type.key}" value="${type.key}"  ${param.troubleTypeId==type.key?'checked':''}><label for="troubleTypeId${type.key}">${type.value}</label>
                  		</c:forEach> 
					</div>
					<div class="row">
						<label>问题详述:</label>
						<textarea cols="40" rows="8" class="required" name="content" id="content">${param.content}</textarea>
					</div>
					<div class="row">
						<input type="submit" name="submit" id="submit" value="提交问题">
					</div>
				</div>
			</form>
		
		<!-- 
			<div data-role="popup" id="success-msg" data-theme="c" class="ui-corner-all" data-transition="pop" data-overlay-theme="a">
				<header data-role="header" data-theme="b" class="ul-corner-top">
					<h1 class="longContent">消息</h1>
				</header>
				<div data-role="content" data-theme="a" class="ui-corner-bottom ui-content">
					<p class="longContent">${msg}</p>
					<a href="#" data-role="button" data-transition="flow" data-theme="b" id="trouble-ok" class="ok">确认</a>
				</div>
			</div>
		 -->
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-add-trouble">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
		<footer data-role="footer" data-position="fixed" data-theme="b" role="contentinfo" class="ui-footer ui-bar-a ui-footer-fixed slideup footerBar">
			<div data-role="navbar" class="ui-navbar" role="navigation">
				<ul>
					<li class="ui-block-a longContent">
						<a href="/servlet/wap/WapEnter?handle=addTrouble" class="ui-btn ui-btn-active ui-btn-icon-top ui-icon-user longContent">我要报障</a>
					</li>
					<li class="ui-block-b longContent">
						<a href="/servlet/wap/WapEnter?handle=troubleList" class="ui-btn ui-btn-icon-top ui-icon-clock longContent">历史报障</a>
					</li>
				</ul>
			</div>
		</footer>
		<script src="/jsp/wap/js/jquery.validate.min.js"></script>
		<script src="/jsp/wap/js/basic.js"></script>
		<script src="/jsp/wap/js/input-reg.js"></script>
		<script src="/jsp/wap/js/ajax_wap.js"></script>
		<script>
			var $taxNO = $("#taxNO");
			$(".troubleTypeId").first().attr("checked", "checked");
			$(document).ready(function (){
				$("#form").validate({
					submitHandler: function ( form ) {
						if($taxNO.hasClass("taxNOError")){
							$taxNO.focus();
							if($(".warnText").length<1) {
								var warn = "<span class='warnText' style='color:red;'>请输出正确的税号</span>";
								$taxNO.parent().append(warn);	
							}
							return;
						}
						
						$("#submit").attr("disabled", "true");
						form.submit();
					}
				});
			});
			var boxScript={
				theform:document.addTroubleForm,
				getInfoByTaxNO: function() {
					//console.log(this.theform.taxNO.value);
					myajax.postJSON("/servlet/wap/WapEnter?handle=getInfoByTaxNO","taxNO="+this.theform.taxNO.value,boxScript.getVisitByIdCB);
				
				},
				
				getVisitByIdCB:function(jsonData){
					if(jsonData.flag == 1) {//成功
						boxScript.theform.customerId.value=jsonData.customer.customerId;
						boxScript.theform.companyName.value=jsonData.customer.companyName;
						if(boxScript.theform.linkman.value == "") {
							boxScript.theform.linkman.value=jsonData.customer.linkman;	
						}
						if(boxScript.theform.tel.value == "") {
							boxScript.theform.tel.value=jsonData.customer.tel;	
						}
						
						//boxScript.theform.submit.disabled="true";
						//$("#submit").removeClass("ui-disabled");
						
						
					} else if(jsonData.flag == 2) {
						if($taxNO.val() !== "" && $(".warnText").length<1) {
							var warn = "<span class='warnText' style='color:red;'>请输出正确的税号</span>";
							$taxNO.parent().append(warn);
							//$("#submit").addClass("ui-disabled");
						}
						$taxNO.addClass("taxNOError");
						boxScript.theform.customerId.value="";
						boxScript.theform.companyName.value="";
						//boxScript.theform.linkman.value="";
						//boxScript.theform.tel.value="";
						
						//$taxNO.focus();
						//boxScript.theform.submit.disabled="false";
						//$("#submit").addClass("ui-disabled");
					}
				}
			};
			
			$taxNO.focus(function(){
				$taxNO.removeClass("taxNOError");
				if($(".warnText").length>0) {
					$(".warnText").remove();	
				}
			});
			
		</script>
	</section>
	
</body>
</html>