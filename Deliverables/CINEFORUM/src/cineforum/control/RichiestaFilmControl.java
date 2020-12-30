package cineforum.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cineforum.model.FilmDAO;
import cineforum.model.RichiestaFilm;
import cineforum.model.RichiestaFilmDAO;
import cineforum.model.Utente;

/**
 * Gestisce l'inserimento delle richieste di aggiungere un film effettuate da un utente
 *
 */
@WebServlet("/RichiestaFilm")
public class RichiestaFilmControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RichiestaFilmControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		out.write("Error: GET method is used but POST method is required");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente user = (Utente) session.getAttribute("user");
		RichiestaFilm richiesta = new RichiestaFilm();
		RichiestaFilmDAO richiestaOp = new RichiestaFilmDAO();
		FilmDAO filmOp = new FilmDAO();
		
		String titolo = request.getParameter("titolo");
		
		//verifico che il film richiesto non sia presente nel database
		if(!(filmOp.retrieveAll(FilmDAO.BY_EQUALTITLE, titolo)).isEmpty()) {
			session.setAttribute("error", "Film già presente");
			response.sendRedirect(response.encodeURL("user/richiestafilm.jsp"));
			return;			
		}
		
		richiesta.setUsername(user.getUsername());
		richiesta.setTitolo(titolo);
		richiesta.setCommento(request.getParameter("commento"));
		richiesta.setEsito("in attesa");
		richiestaOp.save(richiesta);
		
		response.sendRedirect(response.encodeURL("user/richiestafilm.jsp"));
		
	}

}
