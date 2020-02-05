<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	[ 파일 업로드 처리 하는 방법 ]
	
	1. form 속성에 method="post" enctype="multipart/form-data"
	2. input type="file"
	3. commons-io.jar, commons-fileupload.jar 라이브러리를 WEB-INF/lib 폴더에 붙여넣기 해서 사용할 준비하기
	4. WebContent/upload 폴더 만들기
	5. upload.jsp 에서 파일 업로드에 관련된 비즈니스 로직 처리하기
 --%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/file/upload_form.jsp</title>
<jsp:include page="../include/resource.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../include/navbar.jsp">
	<jsp:param value="file" name="category"/>
</jsp:include>
<div class="container">
	<h1>파일 업로드 양식</h1>
	<form action="upload.do" method="post" enctype="multipart/form-data">	<%-- enctype 속성을 추가한다. 없으면 경로만 전송되고 파일은 전송되지 못한다. --%>
		<div class="form-group">
			<label for="title">제목</label>
			<input class="form-control" type="text" name="title" id="title"/>
		</div>
		<div class="form-group">
			<label for="myFile">첨부파일</label>
			<input class="form-control" type="file" name="myFile" id="myFile" />
		</div>
		<button class="btn btn-primary" type="submit">업로드</button>
		<button class="btn btn-warning" type="reset">취소</button>
	</form>
</div>
</body>
</html>
<%-- 
	lib에 있는 commons-fileupload.jar, commons-io.jar 라이브러리를 이용해서
	파일 업로드하는 코딩을 간략히 한다.(원래는 코딩양이 굉장히 많지만,,) 네이버 스마트에디터도 이 라이브러리를 이용함 
 --%>