package cineforum.control;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cineforum.model.CommentoFilm;
import cineforum.model.CommentoFilmDAO;
import cineforum.model.Utente;

/**
 * Gestisce l'inserimento di un commento su un film
 */
@WebServlet("/CommentoControl")
public class CommentoFilmControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CommentoFilmControl() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = null;
		HttpSession session = request.getSession();
		
		if ((error = checkForm(request)) != null) {
			session.setAttribute("error", error);
			response.sendRedirect(response.encodeURL("Film?film=" + request.getParameter("commento")));
		}
		
		CommentoFilmDAO commentoOp = new CommentoFilmDAO();
		CommentoFilm commento = new CommentoFilm();
		Utente user = (Utente) session.getAttribute("user");
		
		commento.setCodiceFilm(Integer.parseInt(request.getParameter("film")));
		commento.setCommento(request.getParameter("commento"));
		commento.setUsername(user.getUsername());
		commento.setOrario(formattaOrario(LocalDateTime.now()));
		commentoOp.save(commento);
		
		response.sendRedirect(response.encodeURL("Film?film=" + commento.getCodiceFilm()));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Formatta l'oggetto LocalDateTime nel pattern "yyyy-MM-dd HH:mm" per poterlo inserire nel database
	 * @param orario oggetto da formattare
	 * @return orario formattato
	 */
	private String formattaOrario(LocalDateTime orario) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return orario.format(formatter); 
	}
	
	/**
	 * Verifica se il commento inserito dall'utente rispetta certi criteri
	 * @param request la request contenente i parametri del form da controllare
	 * @return messaggio di errore, altrimenti null
	 */
	private String checkForm(HttpServletRequest request) {
		String commento = request.getParameter("commento");

		if (commento == null || commento.length() < 25 || commento.length() > 255 )
			return "Il commento deve essere composto da un minimo di 25 caratteri e un massimo di 255";
		
		return null;
		
	}
}
