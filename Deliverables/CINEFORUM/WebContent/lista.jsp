<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, java.util.HashMap, cineforum.model.Film, cineforum.model.Lista"%>
<%! @SuppressWarnings("unchecked") %>
<%
	ArrayList<Lista> lista = (ArrayList<Lista>) request.getAttribute("lista");
	HashMap<Lista, Film> hashFilm = (HashMap<Lista, Film>) request.getAttribute("film");
	Film film = null;
	String userLista = (String) request.getParameter("user");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Lista</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="./css/lista.css"/>
		<link rel="stylesheet" href="css/navbar.css">
	</head>
	
	<body>
	<%@ include file="navbar.jsp" %>
	
		<h1>Lista <%= userLista %></h1>
		
		<div class="container">	
		
			<div class="btn-group mb-3" role="group" aria-label="Basic example">
				<a class="btn btn-secondary" href="<%= response.encodeURL("Lista?user=" + userLista + "&categoria=visti") %>" id="visti">Visti</a>
				<a class="btn btn-secondary" href="<%= response.encodeURL("Lista?user=" + userLista + "&categoria=in_programma") %>">In programma</a>
			</div>
			
			<% if (user != null && user.getUsername().equals(userLista)) { %>
				<a id="addFilm" class="mx-auto d-block mb-3" href="#" data-toggle="modal" data-target="#addModal" onClick="resetModal()">
		  			Aggiungi un film
				</a>
			<% } %>
			<br/>
			<div id="lista">
				<% for(Lista element : lista) {
					film = hashFilm.get(element);
				%>
					<div class="row mb-5">
						<div class="col-5 p-0 m-0">
						<img class="d-block float-right mx-5" src="<%= response.encodeURL("GetPicture?film=" + film.getCodiceFilm()) %>" alt="immagine film" onerror="this.src='img/noimage.png'"/>
						</div>
						<div id="infoFilmLista">
							<span><%= film.getTitolo() %></span><br/>
							<span><%= element.getVoto() %></span>
							<span id="linkLista">
								<% if (user != null && user.getUsername().equals(userLista)) { %>
									<a href="" data-toggle="modal" data-target="#editModal" onClick="setEditForm('<%=film.getCodiceFilm() %>', '<%=film.getTitolo() %>')">modifica</a>
									<a href="<%= "ListaOperation?action=remove&codiceFilm=" + film.getCodiceFilm() + "&username=" +  user.getUsername() %>">elimina</a>
								<% } %>
							</span>
						</div>
					</div>
				<% } %>
			</div>
		</div>
		
		<!-- Modal per form aggiunta film alla lista -->
		<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="addModalLabel">Aggiungi un film alla lista</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		      	<div id="searchFilm">
			      		<label>Titolo:
			      			<input type="text" id="titolo"/>
			      			<button onClick="getFilms()">Cerca</button>
			        	</label> <br/>

			        
			        <span id="films"></span>
		        </div>
		        
		        <form id="formInsert" name="insertFilm" action="ListaOperation" method="post">
		        	<input type="hidden" name="username" value="<%= userLista %>"/>
		        	<input type="hidden" name="codiceFilm" value=""/>
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
		        	
		        	<label>Categoria: 
			        	<select name="categoria" required>
			        		<option value="default" disabled selected>Scegli una categoria</option>
			        		<option value="visti">visti</option>
			        		<option value="in programma">in programma</option>
			        	</select>
		        	</label>
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

		<!-- Modal per modifica film nella lista -->
		<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="editModalLabel"></h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>        
		      <form id="formEdit" name="editFilm" action="ListaOperation" method="post">
		      	<div class="modal-body">
		        	<input type="hidden" name="username" value="<%= userLista %>"/>
		        	<input type="hidden" name="codiceFilm" value=""/>
		      		<input type="hidden" name="action" value="edit"/>
		        	<label>Voto: 
			        	<select name="voto" required>
			        		<option value="0" selected>-</option>
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
		        	
		        	<label>Categoria: 
			        	<select name="categoria" required>
			        		<option value="default" disabled selected>Scegli una categoria</option>
			        		<option value="visti">visti</option>
			        		<option value="in programma">in programma</option>
			        	</select>
		        	</label>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			        <input type="submit" class="btn btn-primary"/>
			      </div>
				</form>
		    </div>
		  </div>
		</div>
		<!-- fine modal modifica -->
		<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		<script src="js/lista.js"></script>
	</body>
</html>