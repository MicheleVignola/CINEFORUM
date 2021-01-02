package cineforum.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import cineforum.model.CommentoFilm;
import cineforum.model.CommentoFilmDAO;
import cineforum.model.Utente;
import cineforum.model.UtenteDAO;

@ExtendWith(MockitoExtension.class)
class CommentoFilmControlTest {

	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestDispatcher dispatcher;
	
	@Mock
	HttpSession session;
	
	CommentoFilm commentoTrovato = null;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		CommentoFilmDAO commentoOp = new CommentoFilmDAO();
		if(commentoTrovato != null)
			commentoOp.delete(commentoTrovato);
			
	}

	@Test
	void testInserimentoCommento() throws ServletException, IOException {
		CommentoFilmControl servlet = new CommentoFilmControl();
		CommentoFilmDAO commentoOp = new CommentoFilmDAO();
		
		UtenteDAO userOp = new UtenteDAO();
		Utente user = userOp.retrieveByKey("giuseppeandreozzi");
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("user")).thenReturn(user);
		Mockito.when(request.getParameter("film")).thenReturn("1");
		Mockito.when(request.getParameter("commento")).thenReturn("Lorem ipsum dolor sit blandit.");
			
		servlet.doGet(request, response);
		
		//verifico che la save sia stata eseguita cercando il commento inserito
		Iterator<CommentoFilm> iterator = commentoOp.retrieveAll(CommentoFilmDAO.BY_FILM, "1").iterator();
		CommentoFilm commento = null;
		while(iterator.hasNext()) {
			commento = iterator.next();
			if(commento.getUsername().equals("giuseppeandreozzi") && commento.getCommento().equals("Lorem ipsum dolor sit blandit.")) {
				commentoTrovato = commento;
				break;
			}
		}
		
		Mockito.verify(response).sendRedirect(response.encodeURL("Film?film=1"));
		assertNotNull(commentoTrovato); //se non è null significa che è stata eseguita la save, quindi correttamente salvato
	}

}
