package cineforum.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Method;

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

import cineforum.model.Lista;
import cineforum.model.ListaDAO;
import cineforum.model.Utente;
import cineforum.model.UtenteDAO;

@ExtendWith(MockitoExtension.class)
class InfoAccountControlTest {

	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	HttpSession session;
	
	UtenteDAO userOp = new UtenteDAO();
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		//setto la password di default dell'utente dopo aver eseguito i test
		Method m = InfoAccountControl.class.getDeclaredMethod("encryptPassword", String.class);  
		m.setAccessible(true);
	    String password = (String) m.invoke(null, "marcantuono");  		
	    Utente user = new Utente();
		user = userOp.retrieveByKey("vyncy98");
		user.setPassword(password);
		userOp.update(user);
	}

	@Test
	void testCambiaPassword() throws ServletException, IOException {
		InfoAccountControl servlet = new InfoAccountControl();
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("user")).thenReturn(userOp.retrieveByKey("vyncy98"));
		Mockito.when(request.getParameter("action")).thenReturn("editPassword");
		Mockito.when(request.getParameter("passwordAttuale")).thenReturn("marcantuono");
		Mockito.when(request.getParameter("nuovaPassword")).thenReturn("Joseph.123");
	     
		servlet.doPost(request, response);
		
		Mockito.verify(session).setAttribute(Mockito.eq("user"), Mockito.any(Utente.class));
		Mockito.verify(session).setAttribute(Mockito.eq("msg"), Mockito.eq("Password cambiata con successo"));
		Mockito.verify(response).sendRedirect(response.encodeURL("user/infoaccount.jsp"));
		
		//reflection per poter utilizzare il metodo encryptPassword
		Method m;
		String password = "";
		try {
			m = InfoAccountControl.class.getDeclaredMethod("encryptPassword", String.class);
			m.setAccessible(true);
		    password = (String) m.invoke(null, "Joseph.123");  	
		} catch (Exception e) {
			fail("Exception reflection: ");
			e.printStackTrace();
		}

		//verifico che sia stata invocata la update, controllando che la password sia cambiata
		assertEquals(userOp.retrieveByKey("vyncy98").getPassword(), password);
	}

	@Test
	void testCancellaLista() throws ServletException, IOException {
		InfoAccountControl servlet = new InfoAccountControl();
		ListaDAO listaOp = new ListaDAO();
		
		//aggiungo qualche film nella lista per poterla cancellare
		Lista bean = new Lista();
		bean.setUsername("giuseppeandreozzi");
		bean.setCodiceFilm(8);
		bean.setCategoria("visti");
		bean.setVoto((short) 8);
		listaOp.save(bean);
		bean = new Lista();
		bean.setUsername("giuseppeandreozzi");
		bean.setCodiceFilm(5);
		bean.setCategoria("in programma");
		bean.setVoto((short) 0);
		listaOp.save(bean);
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getParameter("action")).thenReturn("deleteList");
		Mockito.when(request.getParameter("username")).thenReturn("giuseppeandreozzi");
     
		servlet.doPost(request, response);

		Mockito.verify(session).setAttribute(Mockito.eq("msgList"), Mockito.eq("Lista cancellata con successo"));
		Mockito.verify(response).sendRedirect(response.encodeURL("user/infoaccount.jsp"));
		
		//verifico che le varie delete sono state eseguite, controllando che la lista dell'utente sia vuota
		assertTrue(listaOp.retrieveAll(ListaDAO.BY_UTENTE, "giuseppeandreozzi").isEmpty());
	}
}
