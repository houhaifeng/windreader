<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../../css/wind-reader.css"/>
<title>阅读类别详情</title>
</head>
<body>
	    <div class="chartTitle">
	    	<h3 class="font">内容详情</h3>
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
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${contentList }" var="current" varStatus="i">
				<tr>
					<td>${current.id }</td>
					<td>${current.title }</td>
					<td><date:date value="${current.pubDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${current.source }</td>
					<td>
					<c:choose>
						<c:when test="${fn:length(current.link) > 50}">
							 <a href="${current.link }" target="_blank"><c:out value="${fn:substring(current.link, 0, 50)}......" /></a> 
						</c:when>
						<c:otherwise>
						<a href="${current.link }" target="_blank">${current.link}</a>
						</c:otherwise>
					</c:choose>
					</td>
					<td>${current.hash }</td>
					<td><a href="${base}content/edit/${current.id}/">编辑</a></td>
					<td><a href="../../../extractor/?link=${current.link}" target="_blank">抽取</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
</body>
</html>