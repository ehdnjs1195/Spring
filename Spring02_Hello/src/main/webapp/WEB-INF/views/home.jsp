<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %> 	<!-- .com/jsp/jstl/core 를 lib에 넣지 않았는데도 사용할 수 잇는 이유는 pom.xml에 이미 사용할 준비가 되어 있기 때문에(jstl을 찾으면 있음) -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/home.jsp</title>
</head>
<body>
<h1>인덱스 페이지 입니다.</h1>
<ul>
	<li><a href="fortune.do">오늘의 운세 보기</a></li>
	<li><a href="person.do">오늘의 인물 보기</a></li>
</ul>

<h2>공지사항</h2>
<ul>
	<c:forEach var="tmp" items="${notice }">
		<li>${tmp }</li>
	</c:forEach>
</ul>
</body>
</html>