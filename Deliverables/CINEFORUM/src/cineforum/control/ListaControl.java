package cineforum.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cineforum.model.Lista;
import cineforum.model.ListaDAO;
import cineforum.model.Film;
import cineforum.model.FilmDAO;

/**
 * Gestisce l'operazione di recuperare i film presenti nella lista di un utente, specificato dal parametro "user"
 *
 */
@WebServlet("/Lista")
public class ListaControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListaControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = (String) request.getParameter("user");
		ListaDAO listaOp = new ListaDAO();
		ArrayList<Lista> lista = null;
		String categoria = (String) request.getParameter("categoria");
		
		if(categoria != null && categoria.equals("in_programma"))
			lista = listaOp.retrieveAll(ListaDAO.BY_UTENTE_INPROGRAMMA, user);
		else
			lista = listaOp.retrieveAll(ListaDAO.BY_UTENTE_VISTI, user);
		
		request.setAttribute("lista", lista);
		request.setAttribute("film", getFilmLista(lista));
		request.setAttribute("user", user);
		
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/lista.jsp");
		dispatcher.forward(request, response);
		return;
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	/**
	 * Produce un hashmap con chiave la valutazione del film e valore il film associato alla valutazione.
	 * Per poter mostrare correttamente titolo e voto all'utente.
	 * 
	 * @param lista la lista contente le valutazioni dell'utente 
	 * @return hashmap con chiave la valutazione del film e valore il film associato alla valutazione
	 */
	private HashMap<Lista, Film> getFilmLista(ArrayList<Lista> lista){
		HashMap<Lista, Film> hash = new HashMap<Lista, Film>();
		FilmDAO filmOp = new FilmDAO();
		Iterator<Lista> listaIterator = lista.iterator();
		Lista element = null;
		
		while(listaIterator.hasNext()) {
			element = listaIterator.next();
			hash.put(element, filmOp.retrieveByKey(element.getCodiceFilm()));
		}

		return hash;
	}
}
