<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/login.jsp</title>
<jsp:include page="../include/resource.jsp"></jsp:include>
</head>
<body>
<div class="container">
	<h1>Alert</h1>
	<c:choose>
		<c:when test="${not empty id }">	<%-- sessionScope.id 에서 가져옴. --%>
			<script>
				alert("${id} 님 반갑습니다!");
				location.href="${url}";
			</script>
		</c:when>
		<c:otherwise>
			<p class="alert alert-danger">
				아이디  혹은 비밀번호가 틀려요!
				<a class="alert alert-link" href="loginform.do?url=${encodedUrl }">로그인 재시도</a>	<!-- 틀려도 다시 로그인하러 갔을 때에도  url을 들고 가야한다. 로그인 했을 때 다시 원래 있던 페이지로 돌아가도록. -->
			</p>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>