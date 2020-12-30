<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cineforum.model.Film, cineforum.model.CommentoFilm, java.util.ArrayList"%>
<%! @SuppressWarnings("unchecked") %>
<%
	Film film = (Film) request.getAttribute("film");
	ArrayList<CommentoFilm> commenti = (ArrayList<CommentoFilm>) request.getAttribute("commenti");

	String error = (String) session.getAttribute("error");
	if(error == null)
		error = "";
	else
		session.removeAttribute("error");
	
	Boolean checkFilmLista = (Boolean) request.getAttribute("checkFilmLista");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title><%= film.getTitolo() %></title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="css/navbar.css">
		<link rel="stylesheet" href="css/paginafilm.css">
	</head>
	<body>
	<%@ include file="navbar.jsp" %>
		<h1>Pagina Film</h1>
		<div class="container">
		<div class="row">
			<div class="col p-0 m-0">
				<img class="mx-auto d-block" src="<%= response.encodeURL("GetPicture?film=" + film.getCodiceFilm()) %>" alt="immagine film" onerror="this.src='img/noimage.png'"/>
			</div>
			<div class="col p-0 m-0">
				<p class="mb-1"><span class="font-weight-bold"> Titolo:</span> <span> <%= film.getTitolo() %></span> </p>
				<p class="mb-1"><span class="font-weight-bold"> Genere:</span> <span><%= film.getGenere() %></span> </p>
				<p class="mb-1"><span class="font-weight-bold"> Data di Uscita:</span> <span><%= film.getDataUscita() %></span> </p>
				<p class="mb-1"><span class="font-weight-bold"> Descrizione:</span> <span><%= film.getDescrizione() %></span> </p>
				<p class="mb-0"><span class="font-weight-bold"> Durata:</span> <span><%= film.getDurata() + "'" %></span> <br/></p>
				
				<p class="headerAddList mt-5 mb-0 pb-0">Aggiungi film alla lista personale</p><br/>
				<% if (user != null && !checkFilmLista) { %>
					<a href="" data-toggle="modal" data-target="#addModal" onClick="setCategoria('visti')">Aggiungi il film alla tua lista dei film visti</a><br/>
					<a href="" data-toggle="modal" data-target="#addModal" onClick="setCategoria('in programma')">Aggiungi il film alla tua lista dei film in programma</a><br/>
				<% } else if (user == null){ %>
					<p>Devi essere loggato per poter aggiungere il film alla lista personale</p>
				<% } else {%>
					<p>Film già presente nella lista</p>
				<% } %>
			</div>
		</div><br/>

		<br/>
		<div id=commenti>
			<h5>Commenti</h5>
			<% for (CommentoFilm commento : commenti) { %>
				<p>
					<span class="font-weight-bold">Orario:</span> <span> <%= commento.getOrario() %></span> <br/>
					<span class="font-weight-bold ">Utente:</span> <span> <%= commento.getUsername() %></span> <br/>
					<span class="font-weight-bold">Commento</span> <br/>
					<span id="commento"><%= commento.getCommento() %></span>
				</p>
			<% } %>
			
			<br/>
			<span>Inserisci un commento</span><br/>
			<% if (user != null && checkFilmLista) { %>
				<form name="inserimentoCommento" action="<%= response.encodeURL("CommentoControl") %>" method="get" onsubmit="return checkCommento()">
					<textarea maxlength="255" name="commento" rows="5" cols="51" required></textarea><br/>
					<p class="error" id="errorCommento"></p>
					<input type="hidden" name="film" value="<%= film.getCodiceFilm() %>">
					<input type="submit"> <input type="reset">
				</form>
				<p><%= error %></p>
			<% } else if (user == null){ %>
				<p>Devi essere loggato per inserire un commento</p>
			<% } else { %>
				<p>Devi prima aver aggiunto il film nella lista personale per poter inserire un commento</p>
			<% } %>	
			</div>
			
			<% if (user != null) { %>
			<!-- Modal per form aggiunta film alla lista -->
			<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="addModalLabel">Aggiungi il film alla lista</h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">        
			        <form id="formInsert" name="insertFilm" action="ListaOperation" method="post">
			        	<input type="hidden" name="username" value="<%= user.getUsername() %>"/>
			        	<input type="hidden" name="codiceFilm" value="<%= film.getCodiceFilm() %>"/>
			        	<input type="hidden" name="categoria" value=""/>
			      		<input type="hidden" name="action" value="insert"/>
			        	<label>Voto: 
				        	<select name="voto" required>
				        		<option value="0" value="0" selected>-</option>
				        		<option value="10">10</option>
				        		<option value="9">9</option>
				        		<option value="8">8</option>
				        		<option value="7">7</option>
				        		<option value="6">6</option>
				        		<option value="5">5</option>
				        		<option value="4">4</option>
				        		<option value="3">3</option>
				        		<option value="2">2</option>
				        		<option value="1">1</option>
				        	</select>
			        	</label> <br/>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				        <input type="submit" class="btn btn-primary"/>
				      </div>
					</form>
			    </div>
			  </div>
			</div>
			<!-- fine modal inserimento -->
			<% } %>
			
		</div>
		<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		<script src="./js/paginafilm.js"></script>
	</body>
</html>