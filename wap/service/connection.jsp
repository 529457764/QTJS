<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>联系我们</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
	<section data-role="page" data-title="联系我们" id="connection">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/connection.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-connection" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">联系我们</h1>
		</header>
		<div data-role="content">
			<h1>服务通道</h1>
			<ul data-role="listview" data-inset="true">
				<li>
					<h1 class="longContent red">通道一：致电我们的客服中心</h1>
					<p class="longContent">服务热线 ：0760-89912366（15条线）</p>
					<p class="longContent">服务传真 ：0760-88385377</p>
					<p class="longContent">服务邮箱 ：qt89912366@163.com</p>
				</li>
				<li>
					<h1 class="longContent red">通道二：网上在线咨询及报障</h1>
						<div class="clearfix connection-box">
							<a href="/servlet/wap/WapEnter?handle=addFaq" class="connection-icon c-icon1">
								<i class="c-list-icon">&#xe60b;</i>
								<span class="list-title">在线咨询</span>
							</a>
							<a href="/servlet/wap/WapEnter?handle=addTrouble" class="connection-icon c-icon2">
								<i class="c-list-icon">&#xe60d;</i>
								<span class="list-title">在线报障</span>
							</a>
						</div>
					<p class="longContent">我们的客服人员将尽快为您解答。</p>
				</li>
				<li>
					<h1 class="longContent red">通道三：直接到我们服务大厅咨询或办理业务</h1>
					<p class="longContent">全通金税服务时间：</p>
					<p class="longContent">周一至周五上班时间：早上8:00~12:00  下午14:00~18:00</p> 
					<p class="longContent">周六值班时间：早上8:30~12:00  下午14:00~17:00(电话客服+前台维护)</p>	
					<p class="longContent">总部地址 ：中山市东区东裕路8号二楼（市国税局旁）</p>
				</li>
				<li>
					<h1 class="longContent red">航天信息（广东）有限公司全省呼叫中心电话：020-83931588</h1>
				</li>
			</ul>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-connection">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
		<footer data-role="footer" data-position="fixed" data-theme="b" role="contentinfo" class="ui-footer ui-bar-a ui-footer-fixed slideup footerBar">
			<div data-role="navbar" class="ui-navbar" role="navigation">
				<ul>
					<li class="ui-block-a longContent">
						<a href="/servlet/wap/WapEnter?handle=service&type=operating" class="ui-btn ui-btn-icon-top ui-icon-grid longContent">办理流程</a>
					</li>
					<li class="ui-block-b longContent">
						<a href="/servlet/wap/WapEnter?handle=service&type=time" class="ui-btn ui-btn-icon-top ui-icon-clock longContent">培训时间</a>
					</li>
					<li class="ui-block-c longContent">
						<a href="/servlet/wap/WapEnter?handle=service&type=standard" class="ui-btn ui-btn-icon-top ui-icon-bullets longContent">收费标准</a>
					</li>
					<li class="ui-block-d longContent">
						<a href="/servlet/wap/WapEnter?handle=service&type=connection" class="ui-btn ui-btn-icon-top ui-icon-user longContent ui-btn-active">联系我们</a>
					</li>
				</ul>
			</div>
		</footer>
		<script src="/jsp/wap/js/basic.js"></script>
	</section>
	
</body>
</html>