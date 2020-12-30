<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList"%>
<%! @SuppressWarnings("unchecked") %>
<%
	ArrayList<Utente> utenti = (ArrayList<Utente>) request.getAttribute("utenti");

%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Gestione Utenti</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="../css/navbar.css">
	</head>
	<body>
		<%@include file="../navbar.jsp" %>
	
		<div>
			<label>
				Cerca utente <br/>
				<input type="text"/><button>Cerca</button>
			</label>
		</div>
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th>Nome Utente</th>
						<th>E-mail</th>
						<th>Rank</th>
						<th>Azioni</th>
					</tr>
				</thead>
				
				<% for (Utente utente : utenti) { %>
					<tr>
						<td><%= utente.getUsername() %></td>
						<td><%= utente.getEmail() %></td>
						<td><%= utente.getRuolo() %></td>
						<td>
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#deleteModal" onClick="setUserDelete('<%= utente.getUsername() %>')">
								Cancella
							</button>
	
							<div class="dropdown">
							  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							    Rank
							  </button>
							  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
							    <a class="dropdown-item" href="#" data-toggle="modal" data-target="#rankUpModal" onClick="setUserRank('<%= utente.getUsername() %>', 'registered')">registered</a>
							    <a class="dropdown-item" href="#" data-toggle="modal" data-target="#rankUpModal" onClick="setUserRank('<%= utente.getUsername() %>', 'admin')">admin</a>
							  </div>
							</div>
						</td>
					</tr>
				<% } %>
			</table>
		</div>
		
		<!-- Modal per verifica password in caso di cancellazione utente -->
		<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="deleteModalLabel">Verifica password</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <form id="deleteForm" action="./GestioneUtenti" method="post">
			      <div class="modal-body">
			        	<input type="hidden" name="usernameDelete" value="" />
			        	<input type="hidden" name="action" value="delete" />
			        	<label>
			        		Password: <input type="password" name="passwordAdmin" required/>
			        	</label>
			        	<span id="error"></span>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
			        <button type="button" class="btn btn-primary" onClick="checkPasswordAdmin()">Conferma</button>
			      </div>
		      </form>
		    </div>
		  </div>
		</div>
	
	
		<!-- Modal per rank up utente -->
		<div class="modal fade" id="rankUpModal" tabindex="-1" role="dialog" aria-labelledby="rankUpLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="RankUpLabel">Conferma operazione</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <h6>Sei sicuro di voler promuovere l'utente?</h6>
		      </div>
		      <div class="modal-footer">
		      	<form action="./GestioneUtenti" method="post">
			      	<input type="hidden" name="action" value="rankUp"/>
			      	<input type="hidden" name="usernameRankUp" value=""/>
			      	<input type="hidden" name="rank" value=""/>
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
		<script src="./js/gestioneutenti.js"></script>
	</body>
</html>