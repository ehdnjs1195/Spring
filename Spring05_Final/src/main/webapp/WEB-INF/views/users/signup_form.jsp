<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/signupform.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/step03_custom.css" />
<style>
	/*페이지 로딩 시점에 도움말과 피드백 아이콘은 일단 숨기기*/
	.help-block, .form-control-feedback{
		display: none;
	}
</style>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.3.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/bootstrap.js"></script>
</head>
<body>
<div class="container">
	<h1>회원가입 페이지</h1>
	<form action="signup.do" method="post" id="signupForm">
		<div class="form-group has-feedback">
			<label class="control-label" for="id">아이디</label>
			<input class="form-control" type="text" id="id" name="id" />
			<p class="help-block" id="id_notusable" >사용 불가능한 아이디 입니다.</p>
			<p class="help-block" id="id_required" >반드시 입력 하세요.</p>
			<span  class="glyphicon glyphicon-remove form-control-feedback"></span>
			<span  class="glyphicon glyphicon-ok form-control-feedback"></span>
		</div>
		
		<div class="form-group has-feedback">
			<label class="control-label" for="pwd">비밀번호</label>
			<input class="form-control" type="password" id="pwd" name="pwd"/>
			<p class="help-block" id="pwd_required">반드시 입력하세요</p>
			<p class="help-block" id="pwd_notequal">아래의 확인란과 동일하게 입력하세요</p>
			<span  class="glyphicon glyphicon-remove form-control-feedback"></span>
			<span  class="glyphicon glyphicon-ok form-control-feedback"></span>
		</div>
		<div class="form-group">
			<label class="control-label" for="pwd2">비밀번호 확인</label>
			<input class="form-control" type="password" id="pwd2" name="pwd2"/>
		</div>
		<div class="form-group has-feedback">
			<label class="control-label" for="email">이메일</label>
			<input class="form-control" type="email" id="email" name="email" />
			<p class="help-block" id="email_notmatch">이메일 형식에 맞게 입력하세요.</p>
			<span  class="glyphicon glyphicon-remove form-control-feedback"></span>
			<span  class="glyphicon glyphicon-ok form-control-feedback"></span>
		</div>
		<button disabled="disabled" class="btn btn-primary" type="submit">가입</button>
		<a class="btn btn-warning" href="../">취소</a>
	</form>
</div>
<script>
	//아이디를 사용할 수 있는지 여부
	var isIdUsable=false;
	//아이디를 입력했는지 여부
	var isIdInput=false;		//둘중에 하나라도 false면 아이디 칸은 빨간색으로 나와야 함!
	
	//비밀번호를 확인란과 같게 입력했는지 여부
	var isPwdEqual=false;
	//비밀번호를 입력했는지 여부
	var isPwdInput=false;
	
	//이메일을 형식에 맞게 입력했는지 여부
	var isEmailMatch=false;
	//이메일을 입력했는지 여부
	var isEmailInput=false;
	
	//아이디 입력란에 한 번이라도 입력한 적이 있는지 여부(id를 입력했을 때 비밀번호에서 경고메세지가 뜨는 버그가 있기 때문에)
	var isIdDirty=false;
	//비밀번호 입력란에 한 번이라도 입력한 적이 있는지 여부
	var isPwdDirty=false;
	
	//이메일을 입력할 때 실행할 함수 등록
	$("#email").on("input", function(){
		var email=$("#email").val();
		
		if(email.match("^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$")){	//이메일 형식에 맞게 입력 했다면(이메일 정규식)
			isEmailMatch=true;
		}else{	//형식에 맞지 않게 입력했다면
			isEmailMatch=false;
		}
		if(email.length == 0){	//이메일을 입력하지 않았다면 
			isEmailInput=false;
		}else{		//이메일을 입력 했다면
			isEmailInput=true;
		}
		//이메일 에러 여부
		var isError=isEmailInput && !isEmailMatch;
		//이메일 상태 바꾸기
		setState("#email", isError);
	});
	
	//비밀번호를 입력할 때 실행할 함수 등록
	$("#pwd, #pwd2").on("input", function(){
		//입력했는지 상태값을 바꿔준다.
		isPwdDirty=true;
		
		//입력한 비밀번호를 읽어온다.
		var pwd=$("#pwd").val();
		var pwd2=$("#pwd2").val();
		
		if(pwd!=pwd2){	//두 비밀번호가 같지 않을 때		3항 연산자로 (isPwdEqual = pwd != pwd2 ? false : true ;)
			isPwdEqual=false;
		}else{
			isPwdEqual=true;
		}
		if(pwd.length == 0){
			isPwdInput=false;
		}else{
			isPwdInput=true;
		}
		//비밀번호 에러 여부
		var isError=!isPwdEqual || !isPwdInput;
		//비밀번호 상태 바꾸기
		setState("#pwd", isError);
	});

	//아이디를 입력할 때 실행할 함수 등록(입력할 때 마다 함수 실행.)
	$("#id").on("input", function(){
		//입력했는지 상태값을 바꿔준다.
		isIdDirty=true;
		
		//1. 입력한 아이디를 읽어온다.
		var inputId=$("#id").val();
		//2. 서버에 보내서 사용가능 여부를 응답받는다. (페이지 이동없이 자바스크립트로 원하는 시점에 요청을 하는 작업. 요청과 함께 파라미터를 전달.  	지금 까지는 링크를 누르거나 submit 버튼을 눌러서 요청을 해왔었다.)
		$.ajax({	//object를 전달.  key:value, key:value ... 전달.
			url:"${pageContext.request.contextPath }/users/checkid.do",		//요청 url
			method:"GET",	//요청 메소드
			data:{inputId:inputId},		//data라는 방에 요청 할 때 요청파라미터를 전달(콤마로 여러개의 파라미터를 전달). inputId라는 파라미터 명으로 inputId 값을 전달. (checkid.jsp?inputId=inputId 가 된다.)
			success:function(responseData){		//ajax 요청에 대해 서버에서 응답을 하면 이 함수가 호출된다. 응답한 인자로 responseData가 전달된다. jsp 문자열 전체가 전달된다.
				if(responseData.isExist){//이미 존재하는 아이디라면 
					isIdUsable=false;
				}else{
					isIdUsable=true;
				}
				//아이디 에러 여부
				var isError= !isIdUsable || !isIdInput;
				//아이디 상태 바꾸기
				setState("#id", isError);
			}
		});		//이렇게 요청을 하고도 페이지 전환 없이 응답하는 것을 ajax 통신이라 한다. (비동기 통신. 요청하고 응답이 오면 success 함수를 호출하고 끝.) =>보통 ajax 요청에 대한 응답은 xml,json 형식으로 한다.
		//폼 전송은 막을 필요가 없다.(return false;)
		
		//아이디를 입력했는지 검증
		if(inputId.length == 0){//만일 입력하지 않았다면 
			isIdInput=false;
		}else{
			isIdInput=true;
		}
		//아이디 에러 여부
		var isError= !isIdUsable || !isIdInput;
		//아이디 상태 바꾸기
		setState("#id", isError);
	});
	
	//입력란의 상태를 바꾸는 함수(세개의 함수를 통합)
	function setState(sel, isError){	//sel: 선택인자, isError: 에러여부검사
		$(sel).parent().removeClass("has-success has-error").find(".help-block, .form-control-feedback").hide();
		
		if(isError){//입력란이 error인 상태
			$(sel).parent().addClass("has-error").find(".glyphicon-remove").show();
		}else{	//입력란이 success인 상태
			$(sel).parent().addClass("has-success").find(".glyphicon-ok").show();
		}
		//에러가 있다면 에러 메세지 띄우기(각각 따로 관리 되므로. 모두 표기)
		if(isEmailInput && !isEmailMatch){
			$("#email_notmatch").show();
		}
		if(!isPwdEqual && isPwdDirty){
			$("#pwd_notequal").show();
		}
		if(!isPwdInput && isPwdDirty){
			$("#pwd_required").show();
		}
		if(!isIdUsable && isIdDirty){
			$("#id_notusable").show();
		}
		if(!isIdInput && isIdDirty){
			$("#id_required").show();
		}
		if (isIdUsable && isIdInput && isPwdEqual && isPwdInput	&& (!isEmailInput || isEmailMatch)) {
			$("button[type=submit]").removeAttr("disabled");
		} else {
			$("button[type=submit]").attr("disabled", "disabled");
		}
	}
	
</script>
</body>
</html>