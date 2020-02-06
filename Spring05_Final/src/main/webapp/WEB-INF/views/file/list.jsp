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
	<c:choose>
		<c:when test="${not empty keyword }">
			<p class="breadcrumb">
				<strong>${keyword }</strong> 키워드로 검색된
				<strong>${totalRow }</strong> 개의 파일이 있습니다.
			</p>
		</c:when>
		<c:otherwise>
			<p class="breadcrumb"><strong>${totalRow }</strong> 개의 파일이 있습니다.</p>
		</c:otherwise>
	</c:choose>
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
				<a href="list.do?pageNum=1&condition=${condition}&keyword=${encodedKeyword}">처음으로</a>
			</li>
			<c:choose>
				<c:when test="${startPageNum ne 1 }">
					<li>
						<a href="list.do?pageNum=${startPageNum -1 }&condition=${condition}&keyword=${encodedKeyword}"><i class="fas fa-arrow-left"></i></a>
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
							<a href="list.do?pageNum=${i }&condition=${condition}&keyword=${encodedKeyword}">${i }</a>
						</li>	
					</c:when>
					<c:otherwise>
						<li>
							<a href="list.do?pageNum=${i }&condition=${condition}&keyword=${encodedKeyword}">${i }</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>	
			<c:choose>
				<c:when test="${endPageNum < totalPageCount }">
					<li>
						<a href="list.do?pageNum=${endPageNum + 1 }&condition=${condition}&keyword=${encodedKeyword}"><i class="fas fa-arrow-right"></i></a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="disabled">
						<a href="javascript:">&raquo;</a>
					</li>
				</c:otherwise>
			</c:choose>
			<li>
				<a href="list.do?pageNum=${totalPageCount }&condition=${condition}&keyword=${encodedKeyword}">끝으로</a>
			</li>
		</ul>	
	<a class="btn btn-primary pull-right" href="${pageContext.request.contextPath }/file/upload_form.do" id="a">파일 업로드</a>
	</div>
	<form action="list.do" method="get">	<!-- get 방식이므로 /file/list.do?condition=xxx&keyword=xxx 와 같은 요청이 된다. -->
		<label for="condition">검색조건</label>
		<select name="condition" id="condition">
			<option value="titlename" <c:if test="${condition eq 'titlename' }">selected</c:if> >제목+파일명</option> 
			<option value="title" <c:if test="${condition eq 'title' }">selected</c:if>>제목</option>
			<option value="writer" <c:if test="${condition eq 'writer' }">selected</c:if>>작성자</option>
		</select>
		<input type="text" name="keyword" id="keyword" placeholder="검색어 ..." value="${keyword }"/>
		<button type="submit">검색</button>
	</form>
</div><!-- /.container -->
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