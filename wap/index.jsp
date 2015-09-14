<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>中山市全通金税信息服务有限公司</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>	
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
</head>
<body>
	<section data-role="page" id="home" data-title="中山市全通金税信息服务有限公司">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/index.css">
		<link rel="stylesheet" href="/jsp/wap/css/owl.carousel.css">
		<link rel="stylesheet" href="/jsp/wap/css/owl.theme.css">
		<link rel="stylesheet" href="/jsp/wap/css/owl.css">
		<header data-role="header" class="header">
			<div class="header-left" id="logo">
				<a href="index.html"><img class="logo" src="/jsp/wap/images/logo2.png" alt="中山市全通金税信息服务有限公司"></a>
			</div>
			<!--
			<div class="header-right longContent">
				<h1>中山市全通金税信息服务有限公司</h1>
			</div>
			-->
		</header>
		<div class="navBar-home" role="main">
			<div id="owl" class="owl-carousel owl-theme">
		      	<div class="item">
		      		<a href="/servlet/wap/WapEnter?handle=showProduct&productTypeId=3"><img id="slides-pic1" src="/jsp/wap/images/572x250.jpg" alt="1"></a>
		      	</div>
		      	<div class="item">
		      		<a href="consultation.html"><img id="slides-pic2" src="/jsp/wap/images/572x250d.jpg" alt="2"></a>
		      	</div>
		      	<!-- <img id="slides-pic1" src="../images/572x250d.jpg" alt="3"> -->
		    </div>
			<div class="nav nav1">
				<!-- <a href="news.html"> -->
				<a href="/servlet/wap/WapEnter?handle=news&newsCategoryId=1">
					<i class="icon message-icon">&#xe602;</i>
					<span class="nav-title">最新通知</span>
				</a>
			</div>
			<div class="nav-line">
				<div class="nav nav2">
					<a href="/servlet/wap/WapEnter?handle=service">
						<i class="icon service-icon">&#xe60a;</i>
						<span class="nav-title">服务中心</span>
					</a>
				</div>
				<div class="nav nav3">
					<a href="/servlet/wap/WapEnter?handle=product">
						<i class="icon product-icon">&#xe60e;</i>
						<span class="nav-title">税控产品</span>
					</a>
				</div>
			</div>
			<div class="nav nav4">
				<a href="/servlet/wap/WapEnter?handle=news&newsCategoryId=3">
					<i class="icon guideline-icon">&#xe60c;</i>
					<span class="nav-title">操作指引查询</span>
				</a>
			</div>
			<div class="nav nav5">
				<a href="/servlet/wap/WapEnter?handle=addFaq">
					<i class="icon consultation-icon">&#xe60b;</i>
					<span class="nav-title">在线咨询</span>
				</a>
			</div>
			<div class="nav nav6">
				<a href="/servlet/wap/WapEnter?handle=addTrouble">
					<i class="icon report-icon">&#xe60d;</i>
					<span class="nav-title">在线报障</span>
				</a>
			</div>
		</div>
		<footer data-role="footer" class="footer">
			<p class="ft-info longContent">&copy;2015中山市全通金税信息服务有限公司</p>
			<h1>${curCustomerWx.openid}</h1>
		</footer>
		<script src="/jsp/wap/js/index.js"></script>
		<script src="/jsp/wap/js/owl.carousel.min.js"></script>
		<script src="/jsp/wap/js/owl.js"></script>
	</section>
</body>
</html>
