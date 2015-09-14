<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://qtone.cn/jsp/tag" prefix="qt"%>
<%@ taglib uri="http://qtone.cn/jsp/tag/functions" prefix="qt_fn"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>历史咨询</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
	<!-- faqList -->
	<section data-role="page" data-title="历史咨询" id="faqList">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/news.css">
		<link rel="stylesheet" href="/jsp/wap/css/input-error.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-faqList" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">历史咨询</h1>
		</header>
		<div data-role="content">
			<c:if test="${param.msg=='faqOk'}">
				<div class="msg-box"></div>
				<script src="/jsp/wap/js/msg.js"></script> 
				<script>
					var type = "faq-list";
					addMsg("${param.msg}", type);
					setTimeout(function(){
		            	$("#success-msg-"+type+"").popup("open");
		            }, 1000);
					setTimeout(function(){	
		            	$("#success-msg-"+type+"").popup("close");
		            }, 4000);
				</script>
			</c:if>
			<div class="search-box">
				<form action="/servlet/wap/WapEnter?handle=faqList" method="POST">
					<div class="search-text-box search">
						<input type="search" placeholder="可以搜索关键字哦" name="keyword" class="search-text" value="${param.keyword}">
					</div>
					<div class="search-btn-box search">
						<input type="submit" value="搜索" name="search_btn" class="search-btn"> 
					</div>
				</form> 
			</div>
			<ul data-role="listview" id="listviewScrolling" data-icon="false">
			<c:forEach items="${faqList}" var="faq">	
				<li>
					<a href="/servlet/wap/WapEnter?handle=faqDetail&faqId=${faq.faqId}" data-ajax="true">
						<h1 class="longContent">
							<c:out value="${faq.questionTitle}"/>
						</h1>
						<h2 class="longContent">
							${fn:substringBefore(faq.askDate," ")}
						</h2>
					</a>
				</li>
			</c:forEach>	
			</ul>
		</div>
		<div id="loading" style="display:none;">
			<p>加载内容中...</p>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-faqList">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
		<footer data-role="footer" data-position="fixed" data-theme="b" role="contentinfo" class="ui-footer ui-bar-a ui-footer-fixed slideup footerBar">
			<div data-role="navbar" class="ui-navbar" role="navigation">
				<ul>
					<li class="ui-block-a longContent">
						<a href="/servlet/wap/WapEnter?handle=addFaq" class="ui-btn ui-btn-icon-top ui-icon-user longContent">我要咨询</a>
					</li>
					<li class="ui-block-b longContent">
						<a href="/servlet/wap/WapEnter?handle=faqList" class="ui-btn ui-btn-active ui-btn-icon-top ui-icon-clock longContent">历史咨询</a>
					</li>
				</ul>
			</div>
		</footer>
		<script src="/jsp/wap/js/basic.js"></script>
		<script src="/jsp/wap/js/ajax_wap.js"></script>
		<script>
		var nextPage=2;	
		$(document).ready(function(){
			$(window).scroll(function(){
				
				if($("#faqList").css('display')=='none' || $("#faqList").css('display')==null){
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
			var keyword = $(".search-text").val();
			myajax.postJSON("/servlet/wap/WapEnter?handle=faqJSON&keyword="+keyword+"&page="+nextPage,"",function(faqList){
			//myajax.postJSON("/servlet/wap/WapEnter?handle=faqJSON&keyword=&page="+nextPage,"",function(faqList){
				if(faqList!=null&&faqList.length>0) {
					nextPage++;
					for(var i=0;i<faqList.length;i++){
						var faq=faqList[i];
						addRow(faq);
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
		
		function addRow(faq){
			var $list = $("#listviewScrolling");
			//var firstLi = $list.find('li:last');
			var row = $("<li></li>");
			//row.append("<a href='/servlet/wap/WapEnter?handle=news_detail&newsId="+news.newsId+"' data-ajax='true'><h1 class='longContent'>"+news.subject+"</h1><h2 class='longContent'>"+news.creatDate+"</h2></a>");
			//row.append("<a href='/servlet/wap/WapEnter?handle=faqDetail&faqId=${faq.faqId}'><h1 class='longContent'><c:out value='${faq.questionTitle}'/></h1><h2 class='longContent'>${fn:substringBefore(faq.askDate,' ')}</h2></a>");
			row.append("<a href='/servlet/wap/WapEnter?handle=faqDetail&faqId="+faq.faqId+"' data-ajax='true'><h1 class='longContent'>"+faq.questionTitle+"</h1><h2 class='longContent'>"+dateReg(faq.askDate)+"</h2></a>");
			//row.append("<a href='#'>"+faq.faqId+"</a>");
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