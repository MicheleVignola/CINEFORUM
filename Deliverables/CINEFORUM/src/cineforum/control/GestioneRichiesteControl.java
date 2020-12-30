package cineforum.control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cineforum.model.RichiestaFilm;
import cineforum.model.RichiestaFilmDAO;

/**
 * Si occupa della gestione delle richieste fatte da parte dell'amministratore, salvando l'esito e/o recuperando le richieste dal db
 *
 */
@WebServlet("/GestioneRichieste")
public class GestioneRichiesteControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GestioneRichiesteControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		RichiestaFilmDAO richiestaOp = new RichiestaFilmDAO();
		ArrayList<RichiestaFilm> list = null;
		
		if(action != null && action.equals("valuta")) {
			RichiestaFilm richiesta = richiestaOp.retrieveByKey(Integer.parseInt(request.getParameter("richiesta")));
			richiesta.setEsito(request.getParameter("esito"));
			richiestaOp.update(richiesta);
			response.sendRedirect(response.encodeURL("GestioneRichieste")); //per eliminare i parametri get dall'URL
			return;
		}
		
		list = richiestaOp.retrieveAll();
		request.setAttribute("richieste", list);
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/gestionerichieste.jsp");
		dispatcher.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
