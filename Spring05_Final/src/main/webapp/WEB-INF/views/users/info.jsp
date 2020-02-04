<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/info.jsp</title>	<!-- private 하위의 요청은 LoginFilter로 거르기. 로그인 안한 상태에서 url로 info.jsp 경로를 입력했을 때 로그인을 우선할 수 있도록-->
<jsp:include page="../include/resource.jsp"></jsp:include>
<style>
	/* 프로필 이미지가 가로 세로 50px 인 원형으로 표시될 수 있도록*/
	#profileLink img{
		width: 50px;
		height: 50px;
		border-radius: 50%;	
	}
	#profileForm{
		display: none;
	}
</style>
</head>
<body>
<jsp:include page="../include/navbar.jsp"></jsp:include>
<div class="container">
	<h1>개인정보 페이지</h1>
	<table class="table table-bordered">
		<tr>
			<th>아이디</th>
			<td>${dto.id }</td>
		</tr>
		<tr>
			<th>프로필 이미지</th>
			<td>
				<a href="javascript:" id="profileLink">
					<c:choose>
						<c:when test="${empty dto.profile }">	<!-- 프로필 이미지가 등록이 되어있지 않은경우 -->
							<img src="${pageContext.request.contextPath }/resources/images/default_user.jpeg" />
						</c:when>
						<c:otherwise>	<!-- 저장된게 있으면 -->
							<img src="${pageContext.request.contextPath }${dto.profile}" />	<!-- 경로를 출력. upload폴더에 저장을 해두고. /upload/14245152xx.jpg -->						
						</c:otherwise>
					</c:choose>
				</a>
			</td>
		</tr>
		<tr>
			<th>프로필경로보기</th>
			<td><strong>${profile }</strong></a></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><a href="pwd_updateform.do">수정하기</a></td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>${dto.email }</td>
		</tr>
		<tr>
			<th>가입일</th>
			<td>${dto.regdate }</td>
		</tr>
	</table>
	<a class="btn btn-primary btn-sm" href="updateform.do">개인 정보 수정하기</a>
	<a class="btn btn-danger btn-sm" href="javascript:deleteConfirm();">회원 탈퇴</a>
</div>
<form action="profile_upload.do" method="post" enctype="multipart/form-data" id="profileForm">
	<label for="profile">프로필 이미지 선택</label>
	<input type="file" name="profileImage" id="profileImage" accept=".jpg, .jpeg, .png, .JPG, .JPEG" />	<%-- 확장자를 정해준 것만 보이도록 한다. --%>
</form>
<%-- jquery form 플러그인 javascript 로딩 --%>
<script src="${pageContext.request.contextPath }/resources/js/jquery.form.min.js"></script>
<script>
	//프로파일 이미지를 클릭하면
	$("#profileLink").click(function(){
		//강제로 <input type="file" /> 을 클릭해서 파일 선택창을 띄우고
		$("#profileImage").click();		//나머지 폼은 css로 숨김.
	});
	//input type="file" 에 파일이 선택되면 
	$("#profileImage").on("change", function(){
		//폼을 강제 제출하고(submit 버튼이 없어도 제출이 되고, 페이지 전환이 없다.)
		$("#profileForm").submit();								//submit 이벤트를 발생시키는 것!
	})
	// jquery form 플러그인의 동작을 이용해서 폼이 ajax로 제출되로록 한다.(페이지 전환없이 비동기로 제출하겠다는 뜻)... (ajax는 원래 없기 때문에 js를 따로 플러그인 해주고 사용하는 함수)
	$("#profileForm").ajaxForm(function(responseData){			//ajaxForm플러그인이 개입해서 제출 이벤트를 막고 페이지 전환을 막는다. 
		//responseData 는 plain object 이다.(json형태)
		//{savePath:"/upload/저장된이미지파일명"}
		//savedPath 라는 방에 저장된 이미지의 경로가 들어있다.
		console.log(responseData);	//ajax요청에 대한 응답이 들어온다.
		var src="${pageContext.request.contextPath }"+responseData.savedPath;
		//img 의 src 속성에 반영함으로써 이미지가 업데이트 되도록 한다.
		$("#profileLink img").attr("src", src);		//이미지는 서버에 다시 요청해서 실시간으로 바뀌는 모습을 볼 수 있다.
	});
	
	function deleteConfirm(){
		var isDelete=confirm("${id} 님 탈퇴 하시겠습니까?");
		if(isDelete){
			location.href="delete.do";
		}
	}
</script>
</body>
</html>