<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="cn.qtone.modules.product.Const"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://qtone.cn/jsp/tag" prefix="qt"%>
<%@ taglib uri="http://qtone.cn/jsp/tag/functions" prefix="qt_fn"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>${productType}</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>	
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
</head>
<body>
	<!-- product-detail -->
	<section data-role="page" data-title="${productType}" id="product-detail">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/product-introduction.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-product-detail" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title product-title" role="heading" aria-level="1">${productType}</h1>
		</header>
		<div data-role="content" class="product-content">
			<ul>
			<c:forEach items="${productList}" var="product">
				<li class="product-item">
					<h1 class="product-name longContent"><c:out value="${product.productName}"/><c:out value="${product.std}"/></h1>
					<c:choose>
						<c:when test='${(empty product.photo||product.photo=="null")&&empty product.readPicPath(0)}'><img src="/jsp/wa/images/service.jpg" width="250"/></c:when><c:otherwise><img src="/${appConfig.uploadFold}/${qt_fn:getConfigValue(currentModule,'uploadFold')}/${(empty product.readPicPath(1))?product.photo:product.readPicPath(1)}"></c:otherwise>
					</c:choose>
					<p class="product-introduction longContent">${product.detail}</p>
					<c:forEach var="productPrice" items="${product.productPriceList}">
						<c:if test="${productPrice.priceType==1}">
							<p class="product-price">${productPrice.priceName}:<span class="orange">${qt_fn:formatFloat(productPrice.price)}</span><qt:hashtableValue table="<%=Const.getPriceUnit()%>">${productPrice.priceUnit}</qt:hashtableValue></p>
						</c:if>
					</c:forEach>
				</li>
			</c:forEach>
				
			</ul>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-product-detail">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
		<footer data-role="footer" data-position="fixed" data-theme="b" role="contentinfo" class="ui-footer ui-bar-a ui-footer-fixed slideup footerBar">
			<div data-role="navbar" class="ui-navbar" role="navigation">
				<ul>
					<li class="ui-block-a">
						<a href="/servlet/wap/WapEnter?handle=showProduct&productTypeId=3" class="ui-btn ui-btn-icon-top ui-icon-grid longContent">电脑计算机</a>
					</li>
					<li class="ui-block-b longContent">
						<a href="/servlet/wap/WapEnter?handle=showProduct&productTypeId=2" class="ui-btn ui-btn-icon-top ui-icon-star longContent">打印机</a>
					</li>
					<li class="ui-block-c longContent">
						<a href="/servlet/wap/WapEnter?handle=showProduct&productTypeId=1" class="ui-btn ui-btn-icon-top ui-icon-camera longContent">扫描仪</a>
					</li>
					<li class="ui-block-d longContent">
						<a href="/servlet/wap/WapEnter?handle=showProduct&productTypeId=5" class="ui-btn ui-btn-icon-top ui-icon-shop longContent">软件</a>
					</li>
				</ul>
			</div>
		</footer>
		<!-- <script src="/jsp/wap/js/basic.js"></script> -->
		<script>
		$(document).ready(function(){
			function addActive($block) {
				if($block.html() == "${productType}") {
					$block.addClass("ui-btn-active");
				} else {
					$block.removeClass("ui-btn-active");
				} 
			}
			addActive($(".ui-block-a a"));
			addActive($(".ui-block-b a"));
			addActive($(".ui-block-c a"));
			addActive($(".ui-block-d a"));
		});
		</script>
	</section>
</body>
</html>

