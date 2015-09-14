<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://qtone.cn/jsp/tag" prefix="qt"%>
<%@ taglib uri="http://qtone.cn/jsp/tag/functions" prefix="qt_fn"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>咨询内容</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
	<section data-role="page" data-title="咨询内容" id="consultation-detail">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/consultation-detail.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-consultation-detail" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">咨询内容</h1>
		</header>
		<div data-role="content" class="detail-content">
			<div class="dialog question">
				<div class="dialog-header">
					<h1 class="questioner longContent name">
						<c:out value='${faq.companyName}'/>
					</h1>
					<p class="time">发表于：<c:out value='${faq.askDate}'/></p>
				</div>
				<div class="dialog-content longContent">
					${qt_fn:formatData(faq.question)}
				</div>
			</div>
			<div class="dialog response">
				<div class="dialog-header">
					<h1 class="respondent longContent name">客服  ${faq.number}</h1>
					<p class="time">回复于：<c:out value='${faq.answerDate}'/></p>
				</div>
				<div class="dialog-content longContent">
					${qt_fn:formatData(faq.answer)}
				</div>
			</div>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-consultation-detail">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
	</section>
	
</body>
</html>