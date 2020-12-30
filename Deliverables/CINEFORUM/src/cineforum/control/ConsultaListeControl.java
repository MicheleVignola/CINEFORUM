package cineforum.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cineforum.model.Utente;
import cineforum.model.UtenteDAO;
/**
 * Genera la lista di utenti, scelti casualmente, da mostrare nella pagina di “Consulta altre liste”
 *
 */
@WebServlet("/ConsultaListe")
public class ConsultaListeControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ConsultaListeControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UtenteDAO utenteOp = new UtenteDAO();
		
		request.setAttribute("utenti", getRandomUser(utenteOp.retrieveAll()));
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/consultaliste.jsp");
		dispatcher.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Recupera randomicamente un numero di massimo 3 utenti da mostrare sulla pagina "Consulta altre liste"
	 * @param list lista contente gli utenti del sistema
	 * @return ArrayList<Utente> contente gli utenti scelti randomicamente
	 */
	private ArrayList<Utente> getRandomUser(ArrayList<Utente> list){
		ArrayList<Utente> listRandom = new ArrayList<Utente>();
		Random generator = new Random();
		int index = 0;
		
		for(int numberUser = 0; numberUser < 3 && numberUser <= list.size(); numberUser++) {
			index = generator.nextInt(list.size());
			listRandom.add(list.get(index));
			list.remove(index);
		}
		
		return listRandom;
	}
}
