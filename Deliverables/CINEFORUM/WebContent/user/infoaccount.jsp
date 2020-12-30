<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String msg = (String) session.getAttribute("msg"); //contiene messaggio di errore o di successo
	if (msg == null)
		msg = "";
	else
		session.removeAttribute("msg");
	
	String msgList = (String) session.getAttribute("msgList"); 
	if (msgList == null)
		msgList = "";
	else
		session.removeAttribute("msgList");
%>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Informazioni Account</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="../css/navbar.css">
	</head>
	<body>
		<%@include file="../navbar.jsp" %>
		<br/>
		
		<div class="container">
			<form class="my-5">
				<label>
					Username: <input type="text" value="<%= user.getUsername() %>" disabled/>
				</label>
				<br/>
				<label>
					E-mail: <input type="text" value="<%= user.getEmail() %>" disabled/>
				</label>
			</form>
			
			<h4>Modificare password</h4>
			<form name="editPassword" action="../InfoAccount" method="post" onSubmit="return checkForm()">
				<input type="hidden" name="action" value="editPassword"/>
				<label>
					Password attuale: <input type="password" name="passwordAttuale" required/>
				</label>
				<br/>
				<label>
					Nuova password: <input type="password" name="nuovaPassword" required/>
				</label>
				<p class="error" id="errorPassword"></p>
				<br/>
				<input type="submit"/> <input type="reset"/>
			</form>
			<p class="error mb-5"><%= msg %></p>
			
			<h4>Cancella Lista</h4>
			<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#deleteListModal">
		  		Cancella
			</button>
			<p><%= msgList %></p>
		</div>
		
		<!-- Modal per cancella lista -->
		<div class="modal fade" id="deleteListModal" tabindex="-1" role="dialog" aria-labelledby="deleteListLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="deleteListLabel">Conferma operazione</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <h6>Sei sicuro di cancellare il contenuto della tua lista?</h6>
		      </div>
		      <div class="modal-footer">
		      	<form action="../InfoAccount" method="post">
			      	<input type="hidden" name="action" value="deleteList"/>
			      	<input type="hidden" name="username" value="<%= user.getUsername() %>"/>
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
			        <input type="submit" class="btn btn-primary" value="Conferma"/>
		        </form>
		      </div>
		    </div>
		  </div>
		</div>
	
		<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		<script src="../js/infoaccount.js"></script>
	</body>
</html>