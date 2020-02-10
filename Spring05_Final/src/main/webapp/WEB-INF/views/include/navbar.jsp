<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<!-- 홈페이지 링크와 버튼을 넣어둘 div -->
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath }/home.do"><i class="fab fa-youtube"></i>Acorn</a>
			<button class="navbar-toggle" data-toggle="collapse" data-target="#one">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
		</div>
		<!-- xs 영역에서는 숨겨졌다가 버튼을 누르면 나오게 할 컨텐츠 -->
		<div class="collapse navbar-collapse" id="one">
			<ul class="nav navbar-nav">
				<li <c:if test="${param.category eq 'cafe' }">class="active"</c:if> ><a href="${pageContext.request.contextPath }/cafe/list.do">Cafe</a></li>
				<li <c:if test="${param.category eq 'file' }">class="active"</c:if> ><a href="${pageContext.request.contextPath }/file/list.do">File</a></li>
				<li <c:if test="${param.category eq 'shop' }">class="active"</c:if> ><a href="${pageContext.request.contextPath }/shop/list.do">Shop</a></li>
			</ul>
			<c:choose>
				<c:when test="${empty sessionScope.id }">	<!-- sessionScope. 는 생략 가능 -->
					<div class="pull-right">
						<a class="btn btn-primary navbar-btn btn-xs" href="${pageContext.request.contextPath }/users/loginform.do">로그인</a>	<%-- 어디에 포함될지 모르니 절대경로를 넣어준다. --%>
						<a class="btn btn-warning navbar-btn btn-xs" href="${pageContext.request.contextPath }/users/signup_form.do">회원가입</a>
					</div>
				</c:when>
				<c:otherwise>
					<div class="pull-right">
						<strong><a class="navbar-link" href="${pageContext.request.contextPath }/users/info.do">
						<img src="${pageContext.request.contextPath }${profile}" style="width:50px; height:50px; border-radius:50%;" />${id }</a></strong>
						<a class="btn btn-warning navbar-btn btn-xs" href="${pageContext.request.contextPath }/users/logout.do">로그아웃</a>
					</div>
				</c:otherwise>
			</c:choose>  
		</div>
	</div>
</div>

<!-- 
	[include하면서 파라미터로 정보를 전달] 
	
	파라미터에 index에 포함되었는지 list에 포함되었는지 등의 정보를 전달할 수 있다.
	=> 각각 페이지에 맞는 css를 적용하기 위해서

-->