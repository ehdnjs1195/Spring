<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/cafe/list.do</title>
<jsp:include page="../include/resource.jsp"></jsp:include>
<style>
	#ul{
		position:relative;
		left: 50%;
		transform: translateX(-50%);
	}
	#popList {
		background-color: #fab1a0;
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
	</ol>
	<h1>글 목록 입니다.</h1>
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
	<table class="table table-hover table-condensed">
		<colgroup>
			<col class="col-xs-1"/>
			<col class="col-xs-2"/>
			<col class="col-xs-5"/>
			<col class="col-xs-1"/>
			<col class="col-xs-1"/>
			<col class="col-xs-2"/>
		</colgroup>
		<thead>
			<tr>
				<th>글번호</th>
				<th>작성자</th>
				<th>제목</th>
				<th>댓글수</th>
				<th>조회수</th>
				<th>등록일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tmp" items="${popList }">
				<tr id="popList">
					<td><span style="color:#d63031;"><strong>인기글</strong></span></td>
					<td>${tmp.writer }</td>
					<td><a href="detail.do?num=${tmp.num }&pageNum=${pageNum }">${tmp.title }</a></td>
					<td>${CommentDao.getInstance().getCommentCount(tmp.num)}</td>
					<td>${tmp.viewCount }</td>
					<td>${tmp.regdate }</td>
				</tr>
			</c:forEach>
			<c:forEach var="tmp" items="${list }">
				<tr>
					<td>${tmp.num }</td>
					<td>${tmp.writer }</td>
					<td>
						<c:if test="${tmp.viewCount ge 30 }"><i class="fas fa-heart" style="color:red;"></i><span style="color:orange;">조회수30통과!</span></c:if>
						<a href="detail.do?num=${tmp.num }&pageNum=${pageNum }">${tmp.title }</a>
					</td>
					<td>${CommentDao.getInstance().getCommentCount(tmp.num)}</td>
					<td>${tmp.viewCount }</td>
					<td>${tmp.regdate }</td>
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
			
			<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }" step="1">		<%-- for(int i=startPageNum; i<=endPageNum; i++) 과 같은것 --%>
				<c:choose>
					<c:when test="${i eq pageNum }">
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
				<c:when test="${endPageNum lt totalPageCount }">
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
	<a class="btn btn-primary pull-right" href="insertform.do" id="a">새글 작성</a>
	<form action="list.do" method="get">	<!-- get 방식이므로 /file/list.do?condition=xxx&keyword=xxx 와 같은 요청이 된다. -->
		<label for="condition">검색조건</label>
		<select name="condition" id="condition">
			<option value="titlewriter" <c:if test="${condition eq 'titlewriter' }">selected</c:if> >제목+작성자</option> 
			<option value="title" <c:if test="${condition eq 'title' }">selected</c:if>>제목</option>
			<option value="writer" <c:if test="${condition eq 'writer' }">selected</c:if>>작성자</option>
		</select>
		<input type="text" name="keyword" id="keyword" placeholder="검색어 ..." value="${keyword }"/>
		<button type="submit">검색</button>
	</form>
	</div>
</div>
</body>
</html>