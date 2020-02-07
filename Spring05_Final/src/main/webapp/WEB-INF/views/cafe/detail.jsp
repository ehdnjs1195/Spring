<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/cafe/detail.jsp</title>
<jsp:include page="../include/resource.jsp"></jsp:include>
<style>
	/* div */
	.contents, table{
		width: 100%;
		border: 1px dotted #cecece;
		box-shadow: 1px 3px 3px 1px #ccc; /* 그림자 추가 */
	}
	#comment_table{
		margin-top:60px;
	}



	}
	/* 댓글에 관련된 css */
	.comments ul{
		padding: 0;
		margin: 0;
		list-style-type: none;
	}
	.comments ul li{
		border-top: 1px solid #888; /* li 의 윗쪽 경계선 */
	}
	.comments dt{
		margin-top: 5px;
	}
	.comments dd{
		margin-left: 26px;
	}
	.comments form textarea, .comments form button{
		float: left;
	}
	.comments li{
		clear: left;
	}
	.comments form textarea{
		width: 85%;
		height: 100px;
	}
	.comments form button{
		width: 15%;
		height: 100px;
	}
	/* 댓글에 댓글을 다는 폼과 수정폼을 일단 숨긴다. */
	.comment form{
		display: none;
	}
	.comment{
		position: relative;
	}
	.comment .reply_icon{
		width: 8px;
		height: 8px;
		position: absolute;
		top: 10px;
		left: 30px;
	}
	.comments .user-img{
		width: 20px;
		height: 20px;
		border-radius: 50%;
	}
</style>
</head>
<body>
<jsp:include page="../include/navbar.jsp">
	<jsp:param value="cafe" name="category"/>
</jsp:include>
<div class="container">
	<ol class="breadcrumb">
		<li><a href="${pageContext.request.contextPath }/cafe/list.do">목록</a></li>
		<li>글 상세 보기</li>
	</ol>
	<table class="table table-bordered table-condensed">
		<colgroup>
			<col class="col-xs-4"/>
			<col class="col-xs-8"/>
		</colgroup>
		<tr>
			<th>글번호</th>
			<td>${dto.num }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${dto.writer }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${dto.title }</td>
		</tr>
		<tr>
			<th>등록일</th>
			<td>${dto.regdate }</td>
		</tr>
	</table>
	<div class="contents">${dto.content }</div>	<!-- div로 뿌려주면 에디터로 작성한 것이 그대로 출력된다. (textarea말고) -->
	<div style="margin-top:10px;">
		<a class="btn btn-primary pull-right" href="list.do?pageNum=${pageNum }">목록 보기</a>
		<%-- 
			글 작성자와 로그인 된 아이디가 같을때만 기능을 제공해준다.
			즉, 본인이 작성한 글만 수정할 수 있도록 하기위해(조건부로 출력)
		 --%>
		<c:if test="${dto.writer eq id }">
			<a class="btn btn-primary" style="margin-right:5px;" href="updateform.do?num=${dto.num }&pageNum=${pageNum}">수정</a>	<!-- 경로를 private으로 보내는 이유는 로그인 되있는 상태이어야 수정이 가능하도록 하기 위해서. -->	
			<a class="btn btn-warning" href="javascript:deleteConfirm();">삭제</a>
		</c:if>
	</div>
	
	<div class="comments">
		<!-- 원글에 댓글을 작성할 수 있는 폼 -->
		<div class="comment_form">
			<form action="comment_insert.do" method="post">
				<!-- 댓글의 그룹번호는 원글의 글번호가 된다. -->
				<input type="hidden" name="pageNum" value="${pageNum }" />
				<input type="hidden" name="ref_group" value="${dto.num }" />
				<!-- 댓글의 대상자는 원들의 작성자가 된다. -->
				<input type="hidden" name="target_id" value="${dto.writer }" />
				<textarea name="content"><c:if test="${empty id }">로그인이 필요합니다.</c:if></textarea> 
				<button type="submit">등록</button>
			</form>
		</div>
	</div>
	</div>
</div>
<script>
	function deleteConfirm(){
		var isDelete=confirm("글을 삭제 하시겠습니까?");
		if(isDelete){
			location.href="delete.do?num=${dto.num}";
		}
	}
</script>
</body>
</html>