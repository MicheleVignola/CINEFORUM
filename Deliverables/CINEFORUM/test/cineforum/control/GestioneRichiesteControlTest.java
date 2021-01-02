package cineforum.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import cineforum.model.RichiestaFilm;
import cineforum.model.RichiestaFilmDAO;

@ExtendWith(MockitoExtension.class)
class GestioneRichiesteControlTest {

	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	ServletContext sc;
	
	@Mock
	ServletConfig sg;
	
	@Mock
	RequestDispatcher dispatcher;
	
	int codiceRichiesta = -1;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		if (codiceRichiesta != -1) {
			RichiestaFilmDAO richiestaOp = new RichiestaFilmDAO();
			RichiestaFilm richiesta = richiestaOp.retrieveByKey(codiceRichiesta);
			richiestaOp.delete(richiesta);
		}
	}

	@Test
	void testToGestioneRichieste() throws ServletException, IOException {
		GestioneRichiesteControl servlet = new GestioneRichiesteControl();
		servlet.init(sg);

		Mockito.when(request.getParameter("action")).thenReturn(null);
		Mockito.when(sg.getServletContext()).thenReturn(sc);
		Mockito.when(sc.getRequestDispatcher("/admin/gestionerichieste.jsp")).thenReturn(dispatcher);	
		
		servlet.doPost(request, response);
		
		Mockito.verify(request).setAttribute(Mockito.eq("richieste"), Mockito.any(ArrayList.class));
		Mockito.verify(dispatcher).forward(request, response);
	}
	
	@Test
	void testValutaRichiesta() throws ServletException, IOException {
		GestioneRichiesteControl servlet = new GestioneRichiesteControl();
		RichiestaFilmDAO richiestaOp = new RichiestaFilmDAO();
		
		//Aggiungo richiesta per testare la valutazione
		RichiestaFilm richiesta = new RichiestaFilm();
		richiesta.setCommento("");
		richiesta.setEsito("in attesa");
		richiesta.setTitolo("Dracula");
		richiesta.setUsername("giuseppeandreozzi");
		richiestaOp.save(richiesta);
		
		//mi prendo la richiesta salvata nel db perchè serve la chiave primaria
		Iterator<RichiestaFilm> iterator = richiestaOp.retrieveAll(RichiestaFilmDAO.BY_UTENTE, "giuseppeandreozzi").iterator();		
		while(iterator.hasNext()) {
			richiesta = iterator.next();
			if(richiesta.getTitolo().equals("Dracula")) {
				codiceRichiesta = richiesta.getIdRichiesta();
				break;
			}
		}
				
		Mockito.when(request.getParameter("action")).thenReturn("valuta");
		Mockito.when(request.getParameter("richiesta")).thenReturn(Integer.toString(codiceRichiesta));
		Mockito.when(request.getParameter("esito")).thenReturn("accettata"); //attributo modificato	

		servlet.doPost(request, response);
		
		Mockito.verify(response).sendRedirect(response.encodeURL("GestioneRichieste"));
		//verifico che sia stata invocata la update, controllando che l'attributo sia modificato
		assertEquals(richiestaOp.retrieveByKey(codiceRichiesta).getEsito(), "accettata");
	}

}
