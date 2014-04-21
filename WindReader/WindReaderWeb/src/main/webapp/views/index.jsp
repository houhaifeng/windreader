<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/wind-reader.css" />
<title>阅读</title>
</head>
<body>
 		<a href="content/" target="_blank">所有RSS分类</a>
 		<a href="provider/" target="_blank">所有RSS提供者</a>
	    <div class="chartTitle">
	    	<h3 class="font">最新内容</h3>
	    </div>
	   
	    <div class="chartContent">
		<table class="data-load" width="100%">
			<thead>
				<tr>
					<th>编号</th>
					<th>名称</th>
					<th>日期</th>
					<th>来源</th>
					<th>链接</th>
					<th>hash</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${contentList }" var="current" varStatus="i">
				<tr>
					<td>${i.index }</td>
					<td>${current.title }</td>
					<td><date:date value="${current.pubDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${current.source }</td>
					<td>
					<c:choose>
						<c:when test="${fn:length(current.link) > 50}">
							 <a href="${current.link }" target="blank"><c:out value="${fn:substring(current.link, 0, 50)}......" /></a> 
						</c:when>
						<c:otherwise>
						<a href="${current.link }" target="blank">${current.link}</a>
						</c:otherwise>
					</c:choose>
					</td>
					<td>${current.hash }</td>
					<td><a href="${base}content/edit/${current.id}/">编辑</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
</body>
</html>