<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>打印机</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>	
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
</head>
<body>
	<!-- printer -->
	<section data-role="page" data-title="打印机" id="printer">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/product-introduction.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-printer" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">打印机</h1>
		</header>
		<div data-role="content" class="product-content">
			<ul>
				<li class="product-item">
					<h1 class="product-name  longContent">江裕 针式打印机FP-8400KIII 128G</h1>
					<img class="product-img" src="/jsp/wap/images/printer.jpg" alt="">
					<p class="product-introduction longContent"></p>
					<p class="product-price">含税单价:<span class="orange">2950元</span></p>
				</li>
				<li class="product-item">
					<h1 class="product-name  longContent">江裕 针式打印机FP-8400KIII 128G</h1>
					<img class="product-img" src="/jsp/wap/images/printer.jpg" alt="">
					<p class="product-introduction longContent"></p>
					<p class="product-price">含税单价:<span class="orange">2950元</span></p>
				</li>
				<li class="product-item">
					<h1 class="product-name  longContent">江裕 针式打印机FP-8400KIII 128G</h1>
					<img class="product-img" src="/jsp/wap/images/printer.jpg" alt="">
					<p class="product-introduction longContent"></p>
					<p class="product-price">含税单价:<span class="orange">2950元</span></p>
				</li>
			</ul>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-printer">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
		<footer data-role="footer" data-position="fixed" data-theme="b" role="contentinfo" class="ui-footer ui-bar-a ui-footer-fixed slideup footerBar">
			<div data-role="navbar" class="ui-navbar" role="navigation">
				<ul>
					<li class="ui-block-a">
						<a href="/servlet/wap/WapEnter?handle=product&type=computer" class="ui-btn ui-btn-icon-top ui-icon-grid longContent">电脑计算机</a>
					</li>
					<li class="ui-block-b longContent">
						<a href="/servlet/wap/WapEnter?handle=product&type=printer" class="ui-btn ui-btn-icon-top ui-icon-star longContent ui-btn-active">打印机</a>
					</li>
					<li class="ui-block-c longContent">
						<a href="/servlet/wap/WapEnter?handle=product&type=scanner" class="ui-btn ui-btn-icon-top ui-icon-camera longContent">扫描仪</a>
					</li>
					<li class="ui-block-d longContent">
						<a href="/servlet/wap/WapEnter?handle=product&type=software" class="ui-btn ui-btn-icon-top ui-icon-shop longContent">软件</a>
					</li>
				</ul>
			</div>
		</footer>
		<script src="/jsp/wap/js/basic.js"></script>
	</section>
</body>
</html>










