<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>产品分类</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>	
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
</head>
<body>
	<!-- product -->
	<section data-role="page" data-title="产品分类" id="product">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/4blocks.css">
		<link rel="stylesheet" href="/jsp/wap/css/product-introduction.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-product" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">产品分类</h1>
		</header>
		<div data-role="content">
			<ul data-role="listview" class="navsBar">
				<li>
					<a href="/servlet/wap/WapEnter?handle=product&type=computer" class="navs nav1">
						<i class="icon message-icon">&#xe606;</i>
						<h2 class="longContent icon-title">电脑计算机</h2>
					</a>
				</li>
				<li>
					<a href="/servlet/wap/WapEnter?handle=product&type=printer" class="navs nav2">
						<i class="icon service-icon">&#xe605;</i>
						<h2 class="longContent icon-title">打印机</h2>
					</a>
				</li>
				<li>
					<a href="/servlet/wap/WapEnter?handle=product&type=scanner" class="navs nav3">
						<i class="icon product-icon">&#xe601;</i>
						<h2 class="longContent icon-title">扫描仪</h2>
					</a>
				</li>
				<li>
					<a href="/servlet/wap/WapEnter?handle=product&type=software" class="navs nav4">
						<i class="icon search-icon">&#xe611;</i>
						<h2 class="longContent icon-title">软件</h2>
					</a>
				</li>
			</ul>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-product">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
	</section>
</body>
</html>










