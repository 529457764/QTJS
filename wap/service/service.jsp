<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>服务中心</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>	
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
</head>
<body>
	<!-- service -->
	<section data-role="page" data-title="服务中心" id="service">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/4blocks.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-service" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">服务中心</h1>
		</header>
		<div data-role="content">
			<ul data-role="listview" class="navsBar">
				<li>
					<a href="/servlet/wap/WapEnter?handle=service&type=operating" class="navs nav1">
						<i class="icon message-icon">&#xe60f;</i>
						<h2 class="longContent icon-title">防伪开票办理流程</h2>
					</a>
				</li>
				<li>
					<a href="/servlet/wap/WapEnter?handle=service&type=time" class="navs nav2">
					<!-- a href="/servlet/wap/WapEnter?handle=news_detail&newsId=1351" class="navs nav2"-->
						<i class="icon service-icon">&#xe604;</i>
						<h2 class="longContent icon-title">培训时间</h2>
					</a>
				</li>
				<li>
					<a href="/servlet/wap/WapEnter?handle=service&type=standard" class="navs nav3">
						<i class="icon product-icon">&#xe603;</i>
						<h2 class="longContent icon-title">业务收费标准</h2>
					</a>
				</li>
				<li>
					<a href="/servlet/wap/WapEnter?handle=service&type=connection" class="navs nav4">
						<i class="icon search-icon">&#xe609;</i>
						<h2 class="longContent icon-title">联系我们</h2>
					</a>
				</li>
			</ul>
		</div>

		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-service">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
	</section>
</body>
</html>

