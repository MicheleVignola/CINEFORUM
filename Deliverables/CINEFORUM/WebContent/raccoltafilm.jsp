<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, cineforum.model.Film"%>
<%! @SuppressWarnings("unchecked") %>
<%
	ArrayList<Film> films = (ArrayList<Film>) request.getAttribute("film");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Raccolta film</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="css/navbar.css">
		<link rel="stylesheet" href="css/raccoltafilm.css">
	</head>
	<body>
	<%@ include file="navbar.jsp" %>
		<div id="film" class="container h-auto mt-4">
				<div class="mx-auto text-center">
				<h1>Raccolta Film</h1>
				<input type="text" name="titolo"/> <button name="cerca" onClick="searchFilm()">Cerca</button>
			</div>
			<div class="row">
				<% for(Film film : films) {%>
					<div class="col-3 mx-auto px-0 mt-5 text-center">
						<a href="<%= response.encodeURL("Film?film=" + film.getCodiceFilm() ) %>">
							<img src="<%= response.encodeURL("GetPicture?film=" + film.getCodiceFilm()) %>" alt="immagine film" onerror="this.src='img/noimage.png'" /><br/>
							<span class="titolo"> <%= film.getTitolo() %></span>
						</a>
					</div>
				<% } %>
			</div>
		</div>
		<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		<script src="js/raccoltafilm.js"></script>
	</body>
</html>