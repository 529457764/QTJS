<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://qtone.cn/jsp/tag" prefix="qt"%>
<%@ taglib uri="http://qtone.cn/jsp/tag/functions" prefix="qt_fn"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>最新通知</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>	
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
</head>
<body>
	<!-- news -->
	<section data-role="page" data-title="全通金税-${categoryName}" id="news">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/news.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-news" data-icon="bars" data-iconpos="notext">目录</a>
			<!--h1 class="ui-title" role="heading" aria-level="1">${categoryName}</h1-->
			<h1 class="ui-title" role="heading" aria-level="1">${categoryName}</h1>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
		</header>
		<div data-role="content" class="content">
			<div class="search-box">
				<form action="/servlet/wap/WapEnter?handle=news&newsCategoryId=${param.newsCategoryId}" method="POST">
					<div class="search-text-box search">
						<input type="search" placeholder="可以搜索关键字哦" name="keyword" class="search-text" value="${param.keyword}">
					</div>
					<div class="search-btn-box search">
						<input type="submit" value="搜索" name="search_btn" class="search-btn"> 
					</div>
				</form> 
			</div>
			<ul data-role="listview" id="listviewScrolling"  data-icon="false">
			<c:forEach items="${newsList}" var="news">
				<li>
					<a href="/servlet/wap/WapEnter?handle=news_detail&newsId=${news.newsId}" data-ajax="true">
						<h1 class="longContent">
							<c:out value="${news.subject}"/>
						</h1>
						<h2 class="longContent">
							<!--<c:out value="${news.creatDate}"/>-->
							<c:out value="${fn:substringBefore(news.creatDate,' ')}"/>
						</h2>
					</a>
				</li>
			</c:forEach>
			</ul>
		</div>
		<div id="loading" style="display:none;">
			<p>加载内容中...</p>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-news">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
		<script src="/jsp/wap/js/ajax_wap.js"></script>
		
		<script>
		
		var nextPage=2;	
		$(document).ready(function(){
			$(window).scroll(function(){
				
				if($("#news").css('display')=='none' || $("#news").css('display')==null){
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
			//myajax.postJSON("/servlet/wap/WapEnter?handle=newsJSON&keyword=&newsCategoryId="+${param.newsCategoryId}+"&page="+nextPage,"",function(newsList){
			//myajax.postJSON("/servlet/wap/WapEnter? handle=newsJSON&newsCategoryId="+${param.newsCategoryId}+"&page="+nextPage,"keyword=${param.keyword}",function (newsList){
			var keyword = $(".search-text").val();
			myajax.postJSON("/servlet/wap/WapEnter?handle=newsJSON&keyword="+keyword+"&newsCategoryId="+${param.newsCategoryId}+"&page="+nextPage,"",function(newsList){
				if(newsList!=null&&newsList.length>0) {
					nextPage++;
					for(var i=0;i<newsList.length;i++){
						var news=newsList[i];
						addRow(news);
					}
				}
				if(newsList == null || newsList.length == 0) {
					if(!$("#listviewScrolling").hasClass("no-more")) {
						showNoMore();
					}	
				}
				$("#loading").hide();
			//},true);
			},false);
		}
		
		function addRow(news){
			var $list = $("#listviewScrolling");
			//var firstLi = $list.find('li:last');
			var row = $("<li></li>");
			//row.append("<a href='/servlet/wap/WapEnter?handle=news_detail&newsId="+news.newsId+"' data-ajax='true'><h1 class='longContent'>"+news.subject+"</h1><h2 class='longContent'>"+news.creatDate+"</h2></a>");
			row.append("<a href='/servlet/wap/WapEnter?handle=news_detail&newsId="+news.newsId+"' data-ajax='true'><h1 class='longContent'>"+news.subject+"</h1><h2 class='longContent'>"+dateReg(news.creatDate)+"</h2></a>");
			
			$list.append(row);
			$list.listview("refresh");
		}
		
		//提示没有新增内容
		function showNoMore(){
			var $list = $("#listviewScrolling");
			$list.append("<li>已经没有更多内容了</li>");
			$list.addClass("no-more");
			$list.listview("refresh");
		}
		
		//正则表达式过滤日期
		function dateReg(str) {
			//var strRegExp = /^(\d{4})\-(\d{2})\-(\d{2})$/;  
			var strRegExp = new RegExp(/^\d{4}\-\d{2}\-\d{2}/);
			return strRegExp.exec(str);
		}
		</script>	
	</section>
</body>
</html>