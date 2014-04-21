<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/wind-reader.css" />
<title>Rss Provider Category</title>
</head>
<body>
    <form action="." method="POST">
        id:<span>${category.id }</span>
    	title:<input type="text" name="title" value="${category.title }">
       	desc:<input type="text" name="description" value="${category.description }">
    	parent:<input type="text" name="parentId" value="${category.parentId }">
    	<input type="submit" value="submit">
    </form>
</body>
</html>