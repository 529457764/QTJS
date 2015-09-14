<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://qtone.cn/jsp/tag/functions" prefix="qt_fn"%>
<%@ taglib uri="http://qtone.cn/jsp/tag" prefix="qt"%>
<%@ page import="cn.qtone.modules.trouble.Const"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>报障详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.css">
	<script src="/jsp/wap/css/jquery-mobile/jquery-1.11.2.min.js"></script>
	<script src="/jsp/wap/css/jquery-mobile/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
	<section data-role="page" data-title="报障详情" id="report-detail">
		<link rel="stylesheet" href="/jsp/wap/css/reset.css">
		<link rel="stylesheet" href="/jsp/wap/css/font.css">
		<link rel="stylesheet" href="/jsp/wap/css/basic.css">
		<link rel="stylesheet" href="/jsp/wap/css/report-detail.css">
		<header data-role="header" data-position="fixed" data-theme="a" role="banner" class="ui-header ui-bar-a ui-header-fixed slidedown headerBar">
			<a href="#nav-panel-report-detail" data-icon="bars" data-iconpos="notext">目录</a>
			<a href="/servlet/wap/WapEnter" class="ui-btn ui-icon-home ui-btn-icon-notext ui-corner-all">主页</a>
			<h1 class="ui-title" role="heading" aria-level="1">报障详情</h1>
		</header>
		<div data-role="content">
			<ul data-role="listview" data-icon="false">
				<li class="report-row">
					<div class="row-left row-item">
						<h1 class="longContent">联系人</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent"><c:out value="${troubleWx.linkman}"/></h1>
					</div>
				</li>
				<li class="report-row">
					<div class="row-left row-item">
						<h1 class="longContent">联系电话</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent"><c:out value="${troubleWx.tel}"/></h1>
					</div>
				</li>
				<li class="report-row">
					<div class="row-left row-item">
						<h1 class="longContent">上门时间</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent"><c:out value="${troubleWx.visitDate}"/></h1>
					</div>
				</li>
				<li class="report-row">
					<div class="row-left row-item">
						<h1 class="longContent">故障描述</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent"><c:out value="${troubleWx.content}"/></h1>
					</div>
				</li>
				<li class="report-row">
					<div class="row-left row-item">
						<h1 class="longContent">状态</h1>
					</div>
					<div class="row-right row-item">
						<h1 class="longContent"><qt:hashtableValue table="<%=Const.getTState()%>">${troubleWx.tState}</qt:hashtableValue></h1>
					</div>
				</li>
			<c:if test="${empty troubleWx}">
            	<li class="report-row">
                	<h1 class="longContent">当前没有报障记录！</h1>
                </li>
			</c:if>
			</ul>
		</div>
		<div data-role="panel" data-position-fixed="true" data-display="push" data-theme="b" id="nav-panel-report-detail">
			<%@include file="/jsp/wap/panel.jsp"%>
		</div>
	</section>
	
</body>
</html>