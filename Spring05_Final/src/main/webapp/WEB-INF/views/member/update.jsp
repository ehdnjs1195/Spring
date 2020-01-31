<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/member/update.jsp</title>
</head>
<body>
<div class="container">
	<h1>Alert</h1>
	<p><strong>${dto.num }</strong>번 회원 정보가 수정되었습니다.</p>
	<a href="${pageContext.request.contextPath }/member/list.do">확인</a>
</div>
</body>
</html>