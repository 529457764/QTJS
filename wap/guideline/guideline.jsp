<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://qtone.cn/jsp/tag" prefix="qt"%>
<%@ taglib uri="http://qtone.cn/jsp/tag/functions" prefix="qt_fn"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>操作指引</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
	<!-- guideline -->
	<section data-role="page" data-title="操作指引" id="guideline">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/product-introduction.css">
		<script src="/jsp/wx/js/ajax_wap.js"></script>
	<script>
		var nextPage=2;
		$(document).ready(function(){
			$(window).scroll(function(){
				if($("#news").css('display')=='none'){
					//$("img").width(100%);
					return;
				}
				if($(window).scrollTop()==$(document).height()-$(window).height()){
					$("#loading").show();
					loadMore();
					
				}
			
			});
		});
		
		function loadMore(){
			myajax.postJSON("/servlet/wap/WapEnter?handle=newsJSON&keyword=&newsCategoryId=3&page="+nextPage,"",function(newsList){
				if(newsList!=null&&newsList.length>0) {
					nextPage++;
					for(var i=0;i<newsList.length;i++){
						var news=newsList[i];
						addRow(news);
					}
				}
				
				$("#loading").hide();
			},false);
		}
		
		function addRow(news){
			var $list = $("#listviewScrolling");
			var firstLi = $list.find('li:last');
			var row = $("<li></li>");
			row.append("<a class='ui-btn' href='/servlet/wap/WapEnter?handle=news_detail&newsId="+news.newsId+"' data-ajax='true'><h1 class='longContent'>"+news.subject+"</h1><h2 class='longContent'>"+news.creatDate+"</h2></a>");
			$list.append(row); 			
		}
	</script>	
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-guideline" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">操作指引</h1>
		</header>
		<div data-role="content">
			<ul data-role="listview" id="listviewScrolling"  data-icon="false">
				<c:forEach items="${newsList}" var="news">
					<li>
						<a href="/servlet/wap/WapEnter?handle=news_detail&newsId=${news.newsId}">
							<h1 class="longContent">
								${news.subject}
							</h1>
							<h2 class="longContent">
								${news.creatDate}
							</h2>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div id="loading" style="display:none;">
			<p>加载内容中...</p>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-guideline">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
	</section>
	
</body>
</html>