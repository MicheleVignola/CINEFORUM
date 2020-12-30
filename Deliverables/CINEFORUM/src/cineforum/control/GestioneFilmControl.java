package cineforum.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.apache.commons.io.IOUtils.toByteArray;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import cineforum.model.Film;
import cineforum.model.FilmDAO;

/**
 * Gestisce le operazioni di inserimento, modifica e cancellazione film
 */
@WebServlet("/GestioneFilm")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class GestioneFilmControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GestioneFilmControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		out.write("Error: GET method is used but POST method is required");
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		FilmDAO filmOp = new FilmDAO();
		Film film = null;
		
		if(action.equals("insert")) {
			String titolo = request.getParameter("titolo");
			
			if(!filmOp.retrieveAll(FilmDAO.BY_EQUALTITLE, titolo).isEmpty()) { //controllo se è già presente un film col titolo inserito
				HttpSession session = request.getSession();
				session.setAttribute("errorInsert", "Un film col titolo specificato è già presente nel sistema.");
				response.sendRedirect(response.encodeURL("admin/gestionefilm.jsp"));
				return;
			}
			
			film = new Film();
			film.setTitolo(titolo);
			film.setDescrizione(request.getParameter("descrizione"));
			film.setGenere(request.getParameter("genere"));
			film.setDurata(Short.parseShort(request.getParameter("durata")));
			film.setDataUscita(request.getParameter("dataUscita"));
			
			Part part = request.getPart("foto");
			if(part != null)
				film.setImmagine(toByteArray(part.getInputStream()));
			
			filmOp.save(film);
		} else if(action.equals("edit")) {
			film = filmOp.retrieveByKey(Integer.parseInt(request.getParameter("codiceFilm")));
			ArrayList<Film> films = null;
			
			if(!(films= filmOp.retrieveAll(FilmDAO.BY_EQUALTITLE, request.getParameter("titolo"))).isEmpty()) { //controllo se è presente un film col titolo specificato
				if (films.get(0).getCodiceFilm() != film.getCodiceFilm()) { //significa che c'è un film, diverso da quello modificato, con titolo uguale a quello inserito
					HttpSession session = request.getSession();
					session.setAttribute("errorEdit", "Un film col titolo specificato è già presente nel sistema.");
					response.sendRedirect(response.encodeURL("admin/gestionefilm.jsp"));
					return;
				}
			}
			
			film.setTitolo(request.getParameter("titolo"));
			film.setDescrizione(request.getParameter("descrizione"));
			film.setGenere(request.getParameter("genere"));
			film.setDurata(Short.parseShort(request.getParameter("durata")));
			film.setDataUscita(request.getParameter("dataUscita"));
			
			Part part = request.getPart("foto");
			
			if(part != null)
				film.setImmagine(toByteArray(part.getInputStream()));
			
			filmOp.update(film);
		} else if(action.equals("delete")) {
			film = filmOp.retrieveAll(FilmDAO.BY_TITLE, request.getParameter("titolo")).get(0); //mi prendo il film col titolo specificato da "titolo"
			
			if(film == null) { //controllo se è già presente un film col titolo inserito
				HttpSession session = request.getSession();
				session.setAttribute("errorDelete", "Film non presente nel sistema");
				response.sendRedirect(response.encodeURL("admin/gestionefilm.jsp"));
				return;
			}
			
			filmOp.delete(film);
		}
		
		response.sendRedirect(response.encodeURL("admin/gestionefilm.jsp"));
	}

}
