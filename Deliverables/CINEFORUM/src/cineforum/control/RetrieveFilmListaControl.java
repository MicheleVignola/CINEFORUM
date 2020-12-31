package cineforum.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import cineforum.model.Film;
import cineforum.model.FilmDAO;
import cineforum.model.ListaDAO;
import cineforum.model.Utente;

/**
 * Gestisce l'operazione di recupero dei film ricercati dall'utente per poterli inserire nella propria lista.
 * Ricerca effettuta tramite AJAX sulla pagina della lista (lista.jsp)
 *
 */
@WebServlet("/RetrieveFilm")
public class RetrieveFilmListaControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public RetrieveFilmListaControl() {
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
		JSONObject jsonTemp = null;
		JSONArray jsonArr = new JSONArray(); //array contenente tutti i film trovati dalla ricerca
		ArrayList<Film> lista = null;
		FilmDAO filmOp = new FilmDAO();
		HttpSession session = request.getSession();
		
		lista = filmOp.retrieveAll(FilmDAO.BY_TITLE, jsonObj.getString("titolo"));
		
		if (lista.isEmpty()) {
			jsonResponse.put("esito", false);
		} else {
			Utente user = (Utente) session.getAttribute("user");
			removeFilmSeen(lista, user.getUsername());
			
			if (lista.isEmpty())
				jsonResponse.put("esito", false);
				
			for (Film element : lista) {
				jsonTemp = new JSONObject();
				jsonTemp.put("titolo", element.getTitolo());
				jsonTemp.put("codiceFilm", element.getCodiceFilm());
				jsonArr.put(jsonTemp);
			}
		}
		jsonResponse.put("lista", jsonArr);
			
		response.setContentType("application/json");
		response.getWriter().write(jsonResponse.toString());
	}

	private void removeFilmSeen(ArrayList<Film> lista, String username) {
		Iterator<Film> iterator = lista.iterator();
		ListaDAO listaOp = new ListaDAO();
		Film film = null;
		
		while(iterator.hasNext()) {
			film = iterator.next();
			if (listaOp.retrieveByKey(film.getCodiceFilm(), username) != null)
				iterator.remove();
		}
	}
}
