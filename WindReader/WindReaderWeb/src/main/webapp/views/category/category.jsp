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
    <form action="./category" method="POST">
    	title:<input type="text" name="title" value="">
       	desc:<input type="text" name="description" value="">
    	parent:<input type="text" name="parentId" value="15">
    	<input type="submit" value="submit">
    </form>
    <div class="chartTitle">
    <h3 class="font">rss源类别</h3>
    </div>
    <div class="chartContent">
    <table class="data-load" width="100%">
        <thead>
			<tr>
				<th>编号</th>
				<th>名称</th>
				<th>父类别</th>
				<th>操作</th>
			</tr>
		</thead>
		<c:forEach items="${categoryList}" var="current" varStatus="i">
			<tr>
				<td>${current.id }</td>
				<td>${current.title}</td>
				<td>${current.parentId}</td>
				<td><a href="./category/edit/${current.id }/">编辑</a></td>
			</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>