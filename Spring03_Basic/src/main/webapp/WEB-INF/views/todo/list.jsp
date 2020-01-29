<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %> 	<!-- jstl을 바로 사용할 수 있는 이유: pom.xml에 jstl이 추가되어 있기 때문에 선언만 해주면 사용할 수 있다. 따로 jstl.jar 파일이 필요없다! -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/WEB-INF/views/todo/list.jsp</title>
</head>
<body>
<h1>할 일 목록입니다.</h1>
<ul>
	<c:forEach var="tmp" items="${todoList }">
		<li>${tmp }</li>
	</c:forEach>
</ul>
</body>
</html>