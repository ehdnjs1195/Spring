<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %> 	<!-- .com/jsp/jstl/core 를 lib에 넣지 않았는데도 사용할 수 잇는 이유는 pom.xml에 이미 사용할 준비가 되어 있기 때문에(jstl을 찾으면 있음) -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/views/home.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />	
</head>					<!-- ${pageContext.request.contextPath }/ 까지의 경로가 webapp 이다. 이 경로는 web.xml에 있는 dispatcher를 거치지 않는다. (.do가 아니다) 이 경로는 따로 정적인 자원을 로딩하는 것이다.-->
<body>					<!-- 이 경로는 또한 클라이언트들이 사용해야 하므로 .do를 거치면 안된다.(디스패쳐를 거치면 안된다) webapp는 공개폴더이지만 WEB-INF는 비공개폴더이기 때문이다. => WEB-INF경로와는 아무 상관이 없다!!! -->
<h1>인덱스 페이지 입니다.</h1>
<c:if test="${not empty id }">
	<p>
		<strong>${id }</strong> 님 로그인 중...
		<a href="users/logout.do">로그아웃</a>
	</p>
</c:if>
<ul>
	<li><a href="${pageContext.request.contextPath }/todo/list.do">할 일 목록 보기</a></li>
	<li><a href="todo/list2.do">할 일 목록 보기2</a></li>
	<li><a href="todo/list3.do">할 일 목록 보기3</a></li>
	<li><a href="users/loginform.do">로그인 하러가기</a></li>
	<li><a href="play.do">회원 전용 공간으로 가기</a></li>
</ul>
<h2>공지사항</h2>
<ul>
	<c:forEach var="tmp" items="${notice }">
		<li>${tmp }</li>
	</c:forEach>
</ul>
</body>
</html>