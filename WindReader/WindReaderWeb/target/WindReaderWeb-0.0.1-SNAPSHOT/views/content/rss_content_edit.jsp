<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/tags" prefix="date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${base }ss/wind-reader.css" />
<title>阅读类别</title>
</head>
<body>
    <form action="." method="POST">
        id:<input type="text" name="id" value="${rssContent.id }"><br>
    	title:<input type="text" name="title" value="${rssContent.title }"><br>
    	link:<input type="text" name="link" value="${rssContent.link }"><br>
    	description:<textarea cols=40 rows=10 name="description" style="background-color:BFCEDC">${rssContent.description }</textarea><br>
    	pub_date:<input type="text" name="pub_date" value="${rssContent.pubDate }"><br>
    	guid:<input type="text" name="guid" value="${rssContent.guid }"><br>
    	categoryId:<input type="text" name="category_id" value="${rssContent.categoryId }"><br>
    	category:<input type="text" name="category" value="${rssContent.category }"><br>
    	comments:<input type="text" name="comments" value="${rssContent.comments }"><br>
    	providerId:<input type="text" name="parent" value="${rssContent.providerId }"><br>
    	<input type="submit" value="submit">
    </form>
</body>
</html>