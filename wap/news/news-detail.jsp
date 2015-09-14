<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://qtone.cn/jsp/tag" prefix="qt"%>
<%@ taglib uri="http://qtone.cn/jsp/tag/functions" prefix="qt_fn"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>${news.subject}</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>	
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
</head>
<body>
	<section data-role="page" data-title="新闻详情" id="news-detail">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/news-detail.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-news-detail" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">新闻详情</h1>
		</header>
		<div data-role="content" data-title="${news.subject}">
			<ul data-role="listview" data-dividertheme="e">
				<li data-role="list-divider">
					<h1 class="news-title longContent">${news.subject}</h1>
					<p class="news-time">${news.createDate}</p>
				</li>
				<div class="news-text longContent">
					${news.content}
				</div>
			</ul>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-news-detail">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
	</section>
</body>
</html>