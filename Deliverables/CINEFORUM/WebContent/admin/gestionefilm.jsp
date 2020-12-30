<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String errorInsert = (String) session.getAttribute("errorInsert"); //contiene messaggio di errore o di successo
	if (errorInsert == null)
		errorInsert = "";
	else
		session.removeAttribute("errorInsert");
	
	String errorEdit = (String) session.getAttribute("errorEdit"); 
	if (errorEdit == null)
		errorEdit = "";
	else
		session.removeAttribute("errorEdit");
	
	String errorDelete = (String) session.getAttribute("errorDelete"); 
	if (errorDelete == null)
		errorDelete = "";
	else
		session.removeAttribute("errorDelete");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Gestione Film</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="../css/navbar.css">
	</head>
	<body>
		<%@ include file="../navbar.jsp" %>
		<h2>Gestione Film</h2>
		
		<div class="container">
			<div id="" class="row mb-5">
				<div id="" class="col-md">
					<h5>Aggiungi un film</h5>
					<form id="formInsert" action="../GestioneFilm" enctype="multipart/form-data" method="post" onSubmit="return checkForm('formInsert')">
						<input type="hidden" name="action" value="insert"/>
						<label>
							Titolo <input type="text" name="titolo" required/>
						</label>
						<p class="error" id="errorTitolo"></p>
						<label>
							Descrizione<br/>
							<textarea name="descrizione" maxlength="255" rows="5" cols="51" required></textarea>
						</label>
						<p class="error" id="errorDescrizione"></p>
						<label>
							Genere <input type="text" name="genere" required/>
						</label>
						<p class="error" id="errorGenere"></p>
						<label>
							Data di uscita <input type="text" name="dataUscita" required/>
						</label>
						<p class="error" id="errorDataUscita"></p>
						<label>
							Durata <input type="text" name="durata" required/>
						</label>
						<p class="error" id="errorDurata"></p>
						<label>
							Foto <input type="file" name="foto" required/>
						</label><br/>
						<input type="submit" value="Invia"/>
					</form>
					<p class="error"><%= errorInsert %></p>
				</div>
						  	
				<div class="col-md">
					<h5>Modifica un film</h5>
					<form id="edit" action="../GestioneFilm" enctype="multipart/form-data" method="post">
						<input type="hidden" name="action" value="edit"/>
						<input type="hidden" name="codiceFilm" value=""/>
						<div class="form-group">
							<label>
								Cerca film <input list="filmEdit" id="titoloEdit" onInput="searchFilmEdit()" onChange="setValueInput()"/>
							</label><br/>
							<datalist id="filmEdit"></datalist>
						</div>
						<div class="form-group">
							<label>
								Titolo <input type="text" name="titolo" required/>
							</label>
							<p class="error" id="errorTitolo"></p>
							<label>
								Descrizione<br/>
								<textarea name="descrizione" maxlength="255" rows="5" cols="51" required></textarea>
							</label>
							<p class="error" id="errorDescrizione"></p>
							<label>
								Genere <input type="text" name="genere" required/>
							</label>
							<p class="error" id="errorGenere"></p>
							<label>
								Data di uscita <input type="text" name="dataUscita" required/>
							</label>
							<p class="error" id="errorDataUscita"></p>
							<label>
								Durata <input type="text" name="durata" required/>
							</label>
							<p class="error" id="errorDurata"></p>
							<label>
								Foto <input type="file" name="foto"/>
							</label><br/>
							<label>
								Password admin <input type="password" name="password" required/>
							</label>
							<p class="error" id="error"></p>
							<button type="button" class="btn btn-primary" onClick="checkPasswordAdmin('edit')">Conferma</button>
						</div>
					</form>
					<p class="error"><%= errorEdit %></p>
				</div>
			</div>
			<div class="row">
				<div class="col-md">
					<h5>Cancella un film</h5>
					<form id="delete" action="../GestioneFilm" enctype="multipart/form-data" method="post">
						<input type="hidden" name="action" value="delete"/>
						<label>
							Titolo <input list="filmDelete" id="titoloDelete" name="titolo" onInput="searchFilmDelete()" required/>
						</label><br/>
						<datalist id="filmDelete"></datalist>
						<label>
							Password admin <input type="password" name="password" required/>
						</label><br/>
						<p id="error"></p>
						 <button type="button" class="btn btn-primary" onClick="checkPasswordAdmin('delete')">Conferma</button>
					</form>
					<p class="error"><%= errorDelete %></p>
				</div>
			</div>
		</div>
		  	
		<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		<script src="../js/gestionefilm.js"></script>
	</body>
</html>