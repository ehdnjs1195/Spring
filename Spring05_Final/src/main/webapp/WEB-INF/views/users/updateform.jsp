<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/update.jsp</title>
<jsp:include page="../include/resource.jsp"></jsp:include>
</head>
<body>
<div class="container">
	<h1>회원정보 수정폼</h1>
	<form action="update.do" method="post">
		<input type="hidden" name="id" value="${id }"/>	<!-- update.jsp에서 id를 알아야하므로 hidden으로 전달(disabled 처리되면 name으로 파라미터를 전달할 수 없기 때문에), id는 session 영역에 있다.(로그인 된 상태이므로) -->
		<div class="form-group">
			<label for="id">아이디</label>
			<input class="form-control" type="text" id="id" value="${id }" disabled/>
		</div>
		<div class="form-group">
			<label for="email">이메일</label>
			<input class="form-control" type="email" id="email" name="email" value="${dto.email }" />
		</div>
		<button class="btn btn-warning" type="submit">수정확인</button>
	</form>
</div>
</body>
</html>