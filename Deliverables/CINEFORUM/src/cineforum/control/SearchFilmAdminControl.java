package cineforum.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import cineforum.model.Film;
import cineforum.model.FilmDAO;

/**
 * Recupera i dati del film scelto per la modifica nella pagina della gestione film
 *
 */
@WebServlet("/SearchFilmAdmin")
public class SearchFilmAdminControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchFilmAdminControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		out.write("Error: GET method is used but POST method is required");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonObj = new JSONObject(request.getParameter("json"));
		JSONObject jsonResponse = new JSONObject(); //json da inviare nella response
		FilmDAO filmOp = new FilmDAO();
		
		Film film = filmOp.retrieveAll(FilmDAO.BY_TITLE, jsonObj.getString("titolo")).get(0); // recupero il film specificato da "titolo"
		
		jsonResponse.put("titolo", film.getTitolo());
		jsonResponse.put("codiceFilm", film.getCodiceFilm());
		jsonResponse.put("descrizione", film.getDescrizione());
		jsonResponse.put("genere", film.getGenere());
		jsonResponse.put("dataUscita", film.getDataUscita());
		jsonResponse.put("durata", film.getDurata());
		
		response.setContentType("application/json");
		response.getWriter().write(jsonResponse.toString());
	}

}
