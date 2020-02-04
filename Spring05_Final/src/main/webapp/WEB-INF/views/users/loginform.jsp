<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Loginform.jsp</title>
<style>
body{
	margin: 0;
	padding: 0;
	font-family: sans-serif;
	background: #33495e;
}
.box{
	width: 300px;
	padding: 40px;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	background: #191919;
	text-align: center;
}
.box h1{
	color: white;
	text-transform: uppercase;
	font-weight: 500;
}
.box input[type = "text"],.box input[type = "password"]{
	border:0;
	background: none;
	display: block;
	margin: 20px auto;
	text-align: center;
	border: 2px solid #3498db;
	padding: 14px 10px;
	width: 200px;
	outline: none;
	color: white;
	border-radius: 24px;
	transition: 0.25s;
}
.box input[type = "text"]:focus,.box input[type = "password"]:focus{
	width: 280px;
	border-color: #2ecc71;
}
.box input[type="submit"]{
	border:0;
	background: none;
	display: block;
	margin: 20px auto;
	text-align: center;
	border: 2px solid #2ecc71;
	padding: 14px 40px;
	width: 200px;
	outline: none;
	color: white;
	border-radius: 24px;
	transition: 0.25s;
	cursor: pointer;
}
.box input[type="submit"]:hover{
	background: #2ecc71;
}
a{
	border:0;
	background: none;
	display: block;
	margin: 20px auto;
	text-align: center;
	border: 2px solid #2ecc71;
	padding: 14px 40px;
	width: 200px;
	outline: none;
	color: white;
	border-radius: 24px;
	transition: 0.25s;
	cursor: pointer;
	
}
a:hover{
	background: #2ecc71;
}
label{
	color:gray;
	font-size: 13px;
	margin: 5px, 0;
}
</style>
</head>
<body>
<form class="box" action="login.do" method="post">
	<h1>Login</h1>
		<input type="hidden" name="url" value="${url }" />
		<input type="text" name="id" placeholder="Username" value="${savedId }"/>
		<input type="password" name="pwd" placeholder="Password" value="${savedPwd }"/>
		<label><input type="checkbox" name="isSave" id="yes" /> Save ID, Password</label> 
		<input type="submit" name="" value="Login"/>
</form>
</body>
</html>