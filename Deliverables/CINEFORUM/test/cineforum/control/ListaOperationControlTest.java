package cineforum.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import cineforum.model.Lista;
import cineforum.model.ListaDAO;

@ExtendWith(MockitoExtension.class)
class ListaOperationControlTest {

	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterAll
	static void tearDown() throws Exception {
		ListaDAO listaDAO = new ListaDAO();
		Lista lista = listaDAO.retrieveByKey(11, "giuseppeandreozzi");
		
		if(lista != null)
			listaDAO.delete(lista);
		
		lista = listaDAO.retrieveByKey(10, "giuseppeandreozzi");
		
		if(lista != null)
			listaDAO.delete(lista);
		
	}

	@Test
	void testInserimentoFilmLista() throws ServletException, IOException {
		ListaOperationControl servlet = new ListaOperationControl();
		ListaDAO listaOp = new ListaDAO();
		
		Mockito.when(request.getHeader("referer")).thenReturn("/Lista");
		Mockito.when(request.getParameter("action")).thenReturn("insert");
		Mockito.when(request.getParameter("codiceFilm")).thenReturn("11");
		Mockito.when(request.getParameter("username")).thenReturn("giuseppeandreozzi");
		Mockito.when(request.getParameter("categoria")).thenReturn("visti");
		Mockito.when(request.getParameter("voto")).thenReturn("7");

		servlet.doPost(request, response);
		
		Mockito.verify(response).sendRedirect(response.encodeURL("/Lista"));
		
		//verifico che è stata eseguita la save, controllando che sia stato inserito nel db
		assertNotNull(listaOp.retrieveByKey(11, "giuseppeandreozzi"));
	}
	
	@Test
	void testModificaFilmLista() throws ServletException, IOException {
		ListaOperationControl servlet = new ListaOperationControl();
		ListaDAO listaOp = new ListaDAO();
		
		//mi inserisco un film per testare la modifica nella servlet
		Lista lista = new Lista();
		lista.setCategoria("in programma");
		lista.setUsername("giuseppeandreozzi");
		lista.setCodiceFilm(10);
		lista.setVoto((short) 5);
		listaOp.save(lista);
		
		Mockito.when(request.getHeader("referer")).thenReturn("/Lista");
		Mockito.when(request.getParameter("action")).thenReturn("edit");
		Mockito.when(request.getParameter("codiceFilm")).thenReturn("10");
		Mockito.when(request.getParameter("username")).thenReturn("giuseppeandreozzi");
		Mockito.when(request.getParameter("categoria")).thenReturn("visti"); //attributo modificato
		Mockito.when(request.getParameter("voto")).thenReturn("8"); //attributo modificato
		
		servlet.doPost(request, response);
		
		Mockito.verify(response).sendRedirect(response.encodeURL("/Lista"));
		
		//verifico che è stata eseguita la update, controllando che le modifiche siano state salvate nel db
		Lista listaUpdated = listaOp.retrieveByKey(10, "giuseppeandreozzi");
		assertEquals(listaUpdated.getCategoria(), "visti");
		assertEquals(listaUpdated.getVoto(), 8);
	}
	
	@Test
	void testRimozioneFilmLista() throws ServletException, IOException {
		ListaOperationControl servlet = new ListaOperationControl();
		ListaDAO listaOp = new ListaDAO();
		
		//inserisco un film per testare la cancellazione nella servlet
		Lista lista = new Lista();
		lista.setCategoria("visti");
		lista.setUsername("giuseppeandreozzi");
		lista.setCodiceFilm(15);
		lista.setVoto((short) 6);
		listaOp.save(lista);
		
		Mockito.when(request.getHeader("referer")).thenReturn("/Lista");
		Mockito.when(request.getParameter("action")).thenReturn("remove");
		Mockito.when(request.getParameter("codiceFilm")).thenReturn("15");
		Mockito.when(request.getParameter("username")).thenReturn("giuseppeandreozzi");
		Mockito.when(request.getParameter("categoria")).thenReturn("visti");
		
		servlet.doPost(request, response);
		
		Mockito.verify(response).sendRedirect(response.encodeURL("/Lista"));
		
		//verifico che è stata eseguita la delete, controllando se ci sia nel database
		assertNull(listaOp.retrieveByKey(15, "giuseppeandreozzi"));
	}
}
