package cineforum.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cineforum.model.Utente;
import cineforum.model.UtenteDAO;

/**
 * Gestisce le operazioni di cancellazione utente o rank-up effettuate da un amministratore su un account di un utente.
 *
 */
@WebServlet("/GestioneUtenti")
public class GestioneUtentiControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GestioneUtentiControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteDAO userOp = new UtenteDAO();
		
		request.setAttribute("utenti", userOp.retrieveAll());

		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/admin/gestioneutenti.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteDAO userOp = new UtenteDAO();
		String action = (String) request.getParameter("action");
		Utente user = new Utente();
	
		if(action.equals("delete")) {
			user.setUsername(request.getParameter("usernameDelete"));
			userOp.delete(user);
		} else if (action.equals("rankUp")) {
			user = userOp.retrieveByKey(request.getParameter("usernameRankUp"));
			user.setRuolo(request.getParameter("rank"));
			userOp.update(user);
		}
		
		response.sendRedirect(response.encodeURL("GestioneUtenti"));
		return;
	}
	
}
