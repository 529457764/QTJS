<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>培训时间</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>	
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
</head>
<body>
	<section data-role="page" data-title="培训时间" id="service-time">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/standard.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-service-time" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">培训时间</h1>
		</header>
		<div data-role="content">
			<h1 class="longContent service-title">2015年防伪开票子系统培训时间</h1>
			<ul data-role="listview" data-inset="true">
				<li>
					<h1 class="longContent service-list-title">2015年04月培训安排</h1>
				</li>
				<li class="standard-row">
					<div class="row-left row-item">
						<h1 class="longContent">课程</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent">防伪开票系统</h1>
					</div>
				</li>
				<li class="standard-row">
					<div class="row-left row-item">
						<h1 class="longContent">地点</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent">全通三楼</h1>
					</div>
				</li>
				<li class="standard-row">
					<div class="row-left row-item">
						<h1 class="longContent">时间</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent">9日（星期四）</h1>
					</div>
				</li>
				<li class="standard-row">
					<div class="row-left row-item">
						<h1 class="longContent">时间</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent">23日（星期四）</h1>
					</div>
				</li>
			</ul>
			<ul data-role="listview" data-inset="true">
				<li>
					<h1 class="longContent service-list-title">2015年05月培训安排</h1>
				</li>
				<li class="standard-row">
					<div class="row-left row-item">
						<h1 class="longContent">课程</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent">防伪开票系统</h1>
					</div>
				</li>
				<li class="standard-row">
					<div class="row-left row-item">
						<h1 class="longContent">地点</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent">全通三楼</h1>
					</div>
				</li>
				<li class="standard-row">
					<div class="row-left row-item">
						<h1 class="longContent">时间</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent">7日（星期四）</h1>
					</div>
				</li>
				<li class="standard-row">
					<div class="row-left row-item">
						<h1 class="longContent">时间</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent">21日（星期四）</h1>
					</div>
				</li>
			</ul>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-service-time">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
		<footer data-role="footer" data-position="fixed" data-theme="b" role="contentinfo" class="ui-footer ui-bar-a ui-footer-fixed slideup footerBar">
			<div data-role="navbar" class="ui-navbar" role="navigation">
				<ul>
					<li class="ui-block-a longContent">
						<a href="/servlet/wap/WapEnter?handle=service&type=operating" class="ui-btn ui-btn-icon-top ui-icon-grid longContent">办理流程</a>
					</li>
					<li class="ui-block-b longContent">
						<a href="/servlet/wap/WapEnter?handle=service&type=time" class="ui-btn ui-btn-icon-top ui-icon-clock longContent ui-btn-active">培训时间</a>
					</li>
					<li class="ui-block-c longContent">
						<a href="/servlet/wap/WapEnter?handle=service&type=standard" class="ui-btn ui-btn-icon-top ui-icon-bullets longContent">收费标准</a>
					</li>
					<li class="ui-block-d longContent">
						<a href="/servlet/wap/WapEnter?handle=service&type=connection" class="ui-btn ui-btn-icon-top ui-icon-user longContent">联系我们</a>
					</li>
				</ul>
			</div>
		</footer>
		<script src="/jsp/wap/js/basic.js"></script>
	</section>
</body>
</html>

