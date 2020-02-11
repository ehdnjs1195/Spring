<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %> 	<!-- .com/jsp/jstl/core 를 lib에 넣지 않았는데도 사용할 수 잇는 이유는 pom.xml에 이미 사용할 준비가 되어 있기 때문에(jstl을 찾으면 있음) -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/home.jsp</title>
<jsp:include page="include/resource.jsp"/>	<!-- 파라미터 전달할 게 없으면 바로 닫아주면 된다. -->
</head>
<body>
<jsp:include page="include/navbar.jsp"/>
<div class="container">
	<h1>인덱스 페이지 입니다.</h1>
	<ul>
		<li><a href="member/list.do">회원 목록 보기(member 테이블)</a></li>
		<li><a href="angular/test01.html">angularjs Test</a></li>
		<li><a href="angular/test02.html">angularjs Test2</a></li>
	</ul>
	<h2>공지사항</h2>
	<ul>
		<c:forEach var="tmp" items="${notice }">
			<li>${tmp }</li>
		</c:forEach>
	</ul>
</div>
</body>
</html>