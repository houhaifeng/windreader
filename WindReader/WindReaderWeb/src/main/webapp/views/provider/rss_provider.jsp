<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../css/wind-reader.css" />
<title>Rss Provider</title>
</head>
<body>
    <form action="./provider" method="POST">
    	name:<input type="text" name="name" value="">
    	url:<input type="text" name="url" value="">
    	desc:<input type="text" name="description" value="">
    	category:
		<select id="category" name="category">
   		<c:forEach items="${categoryList }" var="item">
   			<option value="${item.id }" <c:if test="${item.id == current.category }">selected="selected"</c:if>>${item.description }</option>
   		</c:forEach>
		</select>
    	parent:<input type="text" name="parentId" value="1209">
    	<input type="submit" value="submit">
    </form>
    <div class="chartTitle">
    <h3 class="font">rss源</h3>
    </div>
    <div class="chartContent">
    <table class="data-load" width="100%">
        <thead>
			<tr>
				<th>编号</th>
				<th>名称</th>
				<th>链接</th>
				<th>类别</th>
				<th>操作</th>
			</tr>
		</thead>
		
		<c:forEach items="${providerList}" var="current" varStatus="i">		
			<tr>
			    <form  id="form${current.id }" action="./provider/update/${current.id }/" method="POST">
					<td>${current.id}</td>
					<td>${current.name }</td>
					<td>${current.url }</td>
					<td>
						<select id="category" name="category">
				    		<c:forEach items="${categoryList }" var="item">
				    			<option value="${item.id }" <c:if test="${item.id == current.category }">selected="selected"</c:if>>${item.description }</option>
				    		</c:forEach>
	    				</select>
					</td>
					<td>
					<a href="./provider/edit/${current.id }/">编辑</a>
					<a href="javascript:update(${current.id});return false;">更新</a>
					</td>
				</form>
			</tr>
			
		</c:forEach>
	</table>
	</div>
</body>
<script type="text/javascript">
    function update(id){
	    var prefix="form";
	 	document.getElementById(prefix.concat(id)).submit();
	 	return false;
	}
</script>
</html>