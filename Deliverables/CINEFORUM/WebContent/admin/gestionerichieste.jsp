<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, cineforum.model.RichiestaFilm"%>
<%! @SuppressWarnings("unchecked") %>
<%
	ArrayList<RichiestaFilm> richieste = (ArrayList<RichiestaFilm>) request.getAttribute("richieste");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Gestione Richieste</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="../css/navbar.css">
	</head>
	<body>
		<%@ include file="../navbar.jsp" %>
		<h2>Gestione Richieste</h2>
		
		<div class="container">
			<% for (RichiestaFilm richiesta : richieste) { %>
				<div id="richiesta" class="row">
					<div id="accordion" class="col-md">
				  		<div class="card">
						    <div class="card-header w-100 p-0 m-0" id="headingOne">
						      <h5 class="mb-0">
						      	<button class="btn btn-link w-100 m-0 p-0" data-toggle="collapse" data-target="<%= "#" + richiesta.getIdRichiesta() %>" aria-expanded="true" aria-controls="collapseOne">
							      <ul class="list-group w-100">
					  				<li class="list-group-item">Autore: <%= richiesta.getUsername() %></li>
					  				
						  			<li class="list-group-item">
							        	Titolo: <%= richiesta.getTitolo() %>
							        </li>
					  			  </ul>
					  			  </button>
						      </h5>
						    </div>
						
						    <div id="<%= richiesta.getIdRichiesta() %>" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
						    	<div class="card-body">
						        	 <h5>Commento</h5> 
						        	 <p><%= richiesta.getCommento() %></p>
						        	 
						        	 <p>Stato: <%= richiesta.getEsito() %> </p>
								</div>
				    		</div>
				  		</div>
				  	</div>
				  	
				  	<div class="col-md">
						<span>Valuta la richiesta</span><br/>
					  	<form action="./GestioneRichieste" method="get">
					  		<input type="hidden" name="action" value="valuta"/>
					  		<input type="hidden" name="richiesta" value="<%= richiesta.getIdRichiesta() %>"/>
					  		<input type="radio" name="esito" value="rifiutata" required/>Rifiutata <br/>
					  		<input type="radio" name="esito" value="accettata"/>Accettata <br/>
					  		<input type="radio" name="esito" value="in attesa"/>In attesa <br/>
					  		<input type="submit" value="Invia"/> <input type="reset" value="Cancel"/>
					  	</form>
				  	</div>
			  	</div>
		  	</div>
	  	<% } %>
		<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	</body>
</html>