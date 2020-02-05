<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/file/list.jsp</title>
<jsp:include page="../include/resource.jsp"></jsp:include>
<style>
	#ul{
		position:relative;
		left: 50%;
		transform: translateX(-50%);
	}
</style>
</head>
<body>
<jsp:include page="../include/navbar.jsp">
	<jsp:param value="file" name="category"/>
</jsp:include>
<div class="container">
	<h1>파일 목록 입니다.</h1>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th>번호</th>
				<th>작성자</th>
				<th>제목</th>
				<th>파일명</th>
				<th>파일크기</th>
				<th>다운횟수</th>
				<th>등록일</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tmp" items="${list }">
				<tr>
					<td>${tmp.num }</td>
					<td>${tmp.writer }</td>
					<td>${tmp.title }</td>
					<td><a href="${pageContext.request.contextPath }/file/download.do?num=${tmp.num }">${tmp.orgFileName }</a></td>
					<td>${tmp.fileSize }</td>
					<td>${tmp.downCount }</td>
					<td>${tmp.regdate }</td>
					<td>
						<c:if test="${tmp.writer eq id }">
							<a href="javascript:deleteConfirm(${tmp.num })">삭제</a>			<%-- 글 작성자와 세션에 저장된 id가 같을때만 삭제 --%>			
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div class="page-display">
		<ul class="pagination pagination-sm" id="ul">
			<li>
				<a href="list.do?pageNum=1">처음으로</a>
			</li>
			<c:choose>
				<c:when test="${startPageNum ne 1 }">
					<li>
						<a href="list.do?pageNum=${startPageNum -1 }"><i class="fas fa-arrow-left"></i></a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="disabled">
						<a href="javascript:">&laquo;</a>	<!-- javascript: 에 아무것도 적지 않으면 동작하지 않는 링크가 된다. -->
					</li>
				</c:otherwise>
			</c:choose>
			<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }" step="1">
				<c:choose>
					<c:when test="${pageNum eq i }">
						<li class="active">
							<a href="list.do?pageNum=${i }">${i }</a>
						</li>	
					</c:when>
					<c:otherwise>
						<li>
							<a href="list.do?pageNum=${i }">${i }</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>	
			<c:choose>
				<c:when test="${endPageNum < totalPageCount }">
					<li>
						<a href="list.do?pageNum=${endPageNum + 1 }"><i class="fas fa-arrow-right"></i></a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="disabled">
						<a href="javascript:">&raquo;</a>
					</li>
				</c:otherwise>
			</c:choose>
			<li>
				<a href="list.do?pageNum=${totalPageCount }">끝으로</a>
			</li>
		</ul>	
	<a class="btn btn-primary pull-right" href="${pageContext.request.contextPath }/file/upload_form.do" id="a">파일 업로드</a>
	</div>
</div>
<script>
	//삭제 여부를 확인하고 삭제를 진행하는 javascript 함수
	function deleteConfirm(num){
		var isDelete=confirm(num + "번 파일을 삭제 하시겠습니까?");
		if(isDelete){
			location.href="delete.do?num="+num;
		}
 	}
</script>
</body>
</html>