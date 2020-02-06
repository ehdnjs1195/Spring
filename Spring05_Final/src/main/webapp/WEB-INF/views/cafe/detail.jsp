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
	
	<div id="comment_table">
		<table class="table table-bordered table-condensed">
			<colgroup>
				<col class="col-xs-1"/>
				<col class="col-xs-1"/>
				<col class="col-xs-8"/>
				<col class="col-xs-2"/>
			</colgroup>
			<thead>
				<tr>
					<th style="text-align:center;">번호</th>
					<th style="text-align:center;">작성자</th>
					<th style="text-align:center;">댓글</th>
					<th style="text-align:center;">작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tmp" items="${list }" varStatus="status">
					<c:if test="${tmp.writeNum eq num }">
						<tr>
							<td style="text-align:center;">${tmp.rnum }</td>
							<td>${tmp.writer }</td>
							<td>${tmp.content }</td>
							<td style="font-size: 12px; text-align:center;">${tmp.regdate }</td>
						</tr>				
					</c:if>
				</c:forEach>
			</tbody>
		</table>
		<form action="${pageContext.request.contextPath }/cafe/private/comment_insert.do">
			<input type="hidden" name="writeNum" value="${dto.num }" />
			<input type="hidden" name="writer" value="${id }" />
			<input type="hidden" name="ip" value="${ip }" />
			<input type="hidden" name="num" value="${num }" />
			<input type="hidden" name="pageNum" value="${pageNum }" />
			<textarea name="content" id="content" cols="90" rows="2" placeholder="댓글입력.."></textarea>
			<button type="submit">등록</button>
		</form>
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