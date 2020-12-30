package cineforum.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cineforum.model.CommentoFilmDAO;
import cineforum.model.FilmDAO;
import cineforum.model.ListaDAO;
import cineforum.model.Utente;

/**
 * Gestisce le request per accedere alla raccolta film, recuperando tutti i film dal db
 * oppure
 * Gestisce le request per accedere alla pagina dedicata al film (parametro della request "film" non null)
 */
@WebServlet("/Film")
public class FilmControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FilmControl() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmDAO filmOp = new FilmDAO();
		String codiceFilm = (String) request.getParameter("film");
		
		if(codiceFilm != null) { //significa che l'utente deve accedere a una pagina dedicata ad un film
			CommentoFilmDAO commentoOp = new CommentoFilmDAO();
			HttpSession session = request.getSession();
			Utente user = (Utente) session.getAttribute("user");
			
			if(user != null)
				request.setAttribute("checkFilmLista", checkFilmLista(Integer.parseInt(codiceFilm), user.getUsername()));
			
			request.setAttribute("film", filmOp.retrieveByKey(Integer.parseInt(codiceFilm)));
			request.setAttribute("commenti", commentoOp.retrieveAll(CommentoFilmDAO.BY_FILM, codiceFilm));
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/paginafilm.jsp");
			dispatcher.forward(request, response);
			return;
		} else { // significa che l'utente deve accedere alla raccolta dei film
			request.setAttribute("film", filmOp.retrieveAll());			
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/raccoltafilm.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean checkFilmLista(int codiceFilm, String username) {
		ListaDAO listaOp = new ListaDAO();
		
		if(listaOp.retrieveByKey(codiceFilm, username) != null)
			return true;
		else
			return false;
	}
}
