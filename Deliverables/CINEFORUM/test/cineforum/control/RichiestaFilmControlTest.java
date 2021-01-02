package cineforum.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Iterator;

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

import cineforum.model.RichiestaFilm;
import cineforum.model.RichiestaFilmDAO;
import cineforum.model.UtenteDAO;

@ExtendWith(MockitoExtension.class)
class RichiestaFilmControlTest {

	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
		
	@Mock
	HttpSession session;

	int codiceRichiesta = -1;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		//cancello la richiesta fatta
		RichiestaFilmDAO richiestaOp = new RichiestaFilmDAO();
		RichiestaFilm richiesta = richiestaOp.retrieveByKey(codiceRichiesta);
		
		if (richiesta != null)
			richiestaOp.delete(richiesta);
	}

	@Test
	void testAggiungiRichiestaFilm() throws ServletException, IOException {
		RichiestaFilmControl servlet = new RichiestaFilmControl();
		RichiestaFilmDAO richiestaOp = new RichiestaFilmDAO();
		UtenteDAO userOp = new UtenteDAO();
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("user")).thenReturn(userOp.retrieveByKey("giuseppeandreozzi"));
		Mockito.when(request.getParameter("titolo")).thenReturn("Taken");
		Mockito.when(request.getParameter("commento")).thenReturn("Questo film non può non esserci");

		
		servlet.doPost(request, response);

		Mockito.verify(response).sendRedirect(response.encodeURL("user/richiestafilm.jsp"));
		
		//verifico che sia stata fatta la save, controllando che sia salvata nel db
		RichiestaFilm richiesta = new RichiestaFilm();
		Iterator<RichiestaFilm> iterator = richiestaOp.retrieveAll(RichiestaFilmDAO.BY_UTENTE, "giuseppeandreozzi").iterator();		
		while(iterator.hasNext()) {
			richiesta = iterator.next();
			if(richiesta.getTitolo().equals("Taken")) {
				codiceRichiesta = richiesta.getIdRichiesta();
				break;
			}
		}
		assertEquals(richiesta.getTitolo(), "Taken");
		assertEquals(richiesta.getCommento(), "Questo film non può non esserci");
		assertEquals(richiesta.getUsername(), "giuseppeandreozzi");
	}

}
