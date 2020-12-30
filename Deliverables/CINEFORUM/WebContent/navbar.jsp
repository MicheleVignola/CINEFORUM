<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cineforum.model.Utente"%>
    
<%
	Utente user = (Utente) session.getAttribute("user");
%>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="navbar-brand">
			<span id="eng">Cineforum</span>
		</div>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	
	  <div class="collapse navbar-collapse" id="navbarSupportedContent">
	    <ul class="navbar-nav mr-auto">
	      <li class="nav-item mx-auto">
	        <a class="nav-link" href="<%= response.encodeURL(getServletContext().getContextPath() + "/homepage.jsp") %>">Home</a>      
	      </li>
	      <li class="nav-item mx-auto">
	        <a class="nav-link" href="<%= response.encodeURL(getServletContext().getContextPath() + "/Film") %>">Raccolta Film</a>
	      </li>
	      <% if (user != null) { %>
		      <li class="nav-item mx-auto">
		        <a class="nav-link" href="<%= response.encodeURL(getServletContext().getContextPath() + "") %>">Suggerimenti personalizzati</a>
		      </li>
	      <% } %>
	      <li class="nav-item mx-auto">
	        <a class="nav-link" href="<%= response.encodeURL(getServletContext().getContextPath() + "/ConsultaListe") %>">Consula altre liste</a>
	      </li>
	    </ul>
	    
	    <ul class="navbar-nav ml-auto text-center">	    
	    <% if (user == null) { %>
	      	<li class="nav-item align-middle">
		        <a class="nav-link" href="<%= response.encodeURL(getServletContext().getContextPath() + "/signup.jsp") %>"> SignUp </a>
		    </li>
	      <li class="nav-item">
	        <a class="nav-link" href="<%= response.encodeURL(getServletContext().getContextPath() + "/login.jsp") %>">LogIn</a>
	      </li>
	      <% } else { %>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          Sezione Utente
		        </a>
		        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
		          <a class="dropdown-item" href="<%= response.encodeURL(getServletContext().getContextPath() + "/Lista?user=" + user.getUsername()) %>">Lista personale</a>
		          <a class="dropdown-item" href="<%= response.encodeURL(getServletContext().getContextPath() + "/user/infoaccount.jsp") %>">Info account</a>
		          <!--  <div class="dropdown-divider"></div>-->
		          <% if (user.getRuolo().equals("registered")) { %>
		          	<a class="dropdown-item" href="<%= response.encodeURL(getServletContext().getContextPath() + "/user/richiestafilm.jsp") %>">Richiesta aggiunta film</a>
		          <% } %>
		          <div class="dropdown-divider"></div>
		          <a class="dropdown-item" href="<%= response.encodeURL(getServletContext().getContextPath() + "/LoutCheck") %>">Log out</a>
		        </div>
		      </li>
		      
		      <% if (user.getRuolo().equals("admin")) { %>
		      	<li class="nav-item dropdown">
			        <a class="nav-link dropdown-toggle" href="#" id="adminDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			          Sezione Admin
			        </a>
			        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="adminDropdown">
			          <a class="dropdown-item" href="<%= response.encodeURL(getServletContext().getContextPath() + "/GestioneUtenti") %>">Gestione Utenti</a>
			          <a class="dropdown-item" href="<%= response.encodeURL(getServletContext().getContextPath() + "/admin/gestionefilm.jsp") %>">Gestione Film</a>
			          <!--  <div class="dropdown-divider"></div>-->
			          <a class="dropdown-item" href="<%= response.encodeURL(getServletContext().getContextPath() + "/GestioneRichieste") %>">Gestione Richieste</a>
			        </div>
		      	</li>
		      <% } %>
		      
		      <li>
		      	<a class="nav-link" href="<%= response.encodeURL(getServletContext().getContextPath() + "/LoutCheck") %>">Log out</a>
		      </li>
	      <% } %>
	    </ul>
	
	  </div>
	</nav>
	