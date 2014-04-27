<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/wind-reader.css" />
<title>阅读类别</title>
</head>
<body>
	<c:forEach items="${categoryMap}" var="entry">
	    <div class="chartTitle">
	    	<h3 class="font">${entry.key.title }</h3>
	    </div>
	    <div class="chartContent">
		<table class="data-load" width="100%">
			<thead>
				<tr>
					<th>子类别</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${entry.value }" var="current" varStatus="i">
				<tr>
					<td>${current.title }</td>
					<td><a href="category/${current.id}/" target="blank">查看</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
	</c:forEach>
</body>
</html>