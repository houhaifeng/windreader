<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${base }css/wind-reader.css" />
<title>Rss Provider</title>
</head>
<body>
    <form action="." method="POST">
    	id:<input type="text" name="id" value="${rssProvider.id }">
    	name:<input type="text" name="name" value="${rssProvider.name }">
    	url:<input type="text" name="url" value="${rssProvider.url }">
    	desc:<input type="text" name="description" value="${rssProvider.description }">
    	category:
    	<select id="category" name="category">
    		<c:forEach items="${categoryList }" var="item">
    			<option value="${item.id }" <c:if test="${item.id == rssProvider.category }">selected="selected"</c:if>>${item.description }</option>
    		</c:forEach>
    	</select>
    	parent:<input type="text" name="parentId" value="${rssProvider.parentId }">
    	<input type="submit" value="submit">
    </form>
</body>
</html>