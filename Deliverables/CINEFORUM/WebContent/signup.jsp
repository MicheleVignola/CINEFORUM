<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String error = (String) session.getAttribute("error");
	if(error == null)
		error = "";
	else
		session.removeAttribute("error");
	
	
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Registrazione</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="css/navbar.css">
	</head>
	<body>
		<%@ include file="navbar.jsp" %>
		
		<div class="container mt-5">
			<h2>Registrati</h2>
			
			<form name="signForm" action="<%= response.encodeURL("SignControl") %>" method="post" onsubmit="return checkSignForm()">
				<label>Username: <input type="text" name="user" required/> </label> <br/>
				<p class="error" id="errorUsername"></p>
				<label>E-mail: <input type="email" name="email" required/> </label> <br/>
				<p class="error" id="errorEmail"></p>
				<label>Password: <input type="password" name="password" required/> </label> <br/>
				<p class="error" id="errorPassword"></p>
				<label>Ripeti password: <input type="password" name="ripetiPassword" required/> </label> <br/>
				<p class="error" id="errorRipetiPassword"></p>
				<input type="submit"/> <input type="reset"/>
			</form>
			
			<p class="error" id="errorSign"> <%= error %> </p>
		</div>
		
		<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		<script src="./js/signup.js"></script>
	</body>
</html>