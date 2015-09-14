<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://qtone.cn/jsp/tag/functions" prefix="qt_fn"%>
<%@ taglib uri="http://qtone.cn/jsp/tag" prefix="qt"%>
<%@ page import="cn.qtone.modules.trouble.Const"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>历史报障</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
	<!-- trouble-list -->
	<section data-role="page" data-title="历史报障" id="trouble-list">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/input-error.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-trouble-list" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">历史报障</h1>
		</header>
		<div data-role="content">	
			<c:if test="${param.msg=='ok'}">
				<div class="msg-box"></div>
				<script src="/jsp/wap/js/msg.js"></script> 
				<script>
					var type = "trouble-list";
					addMsg("${param.msg}", type);
					setTimeout(function(){
		            	$("#success-msg-"+type+"").popup("open");
		            }, 1000);
					
		            setTimeout(function(){	
		            	$("#success-msg-"+type+"").popup("close");
		            }, 4000);
				</script>
			</c:if>
	
			<ul data-role="listview" id="listviewScrolling" data-icon="false">
			<c:forEach items="${troubleWxList}" var="troubleWx">
				<li> 
					<a href="/servlet/wap/WapEnter?handle=troubleDetail&troubleId=${troubleWx.troubleId}">
						<h1 class="longContent">
							<c:out value="${qt_fn:cutString(troubleWx.content,30,'...')}"/>
						</h1>
						<h2 class="longContent">
							<c:out value="${troubleWx.creatDate}"/>
						</h2>
					</a>
				</li>
			</c:forEach>
			<c:if test="${empty troubleWxList}">
            	<li>
                	<h1 class="longContent">当前没有报障记录！</h1>
                </li>
			</c:if>
			</ul>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-trouble-list">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
		<footer data-role="footer" data-position="fixed" data-theme="b" role="contentinfo" class="ui-footer ui-bar-a ui-footer-fixed slideup footerBar">
			<div data-role="navbar" class="ui-navbar" role="navigation">
				<ul>
					<li class="ui-block-a longContent">
						<a href="/servlet/wap/WapEnter?handle=addTrouble" class="ui-btn ui-btn-icon-top ui-icon-user longContent">我要报障</a>
					</li>
					<li class="ui-block-b longContent">
						<a href="/servlet/wap/WapEnter?handle=troubleList" class="ui-btn ui-btn-active ui-btn-icon-top ui-icon-clock longContent">历史报障</a>
					</li>
				</ul>
			</div>
		</footer>
		<script src="/jsp/wap/js/basic.js"></script>
	</section>
	
</body>
</html>