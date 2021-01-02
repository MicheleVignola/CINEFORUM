package cineforum.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

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

import cineforum.model.Utente;
import cineforum.model.UtenteDAO;

@ExtendWith(MockitoExtension.class)
class GestioneUtentiControlTest {

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
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		UtenteDAO userOp = new UtenteDAO();
		Utente user = userOp.retrieveByKey("giuseppeTest");
		
		if(user != null)
			userOp.delete(user);
		
		user = userOp.retrieveByKey("giuseppeTesting");
		
		if(user != null)
			userOp.delete(user);
	}

	@Test
	void testToGestioneUtenti() throws ServletException, IOException {
		GestioneUtentiControl servlet = new GestioneUtentiControl();
		servlet.init(sg);

		Mockito.when(sg.getServletContext()).thenReturn(sc);
		Mockito.when(sc.getRequestDispatcher("/admin/gestioneutenti.jsp")).thenReturn(dispatcher);		
		
		servlet.doGet(request, response);
		
		Mockito.verify(request).setAttribute(Mockito.eq("utenti"), Mockito.any(ArrayList.class));
		Mockito.verify(dispatcher).forward(request, response);
	}
	
	@Test
	void testCancellaAccountUtente() throws ServletException, IOException {
		GestioneUtentiControl servlet = new GestioneUtentiControl();
		UtenteDAO userOp = new UtenteDAO();
		servlet.init(sg);

		//Mi creo un Utente per poterlo cancellare
		Utente user = new Utente();
		user.setEmail("test@testing.it");
		user.setUsername("giuseppeTesting");
		user.setRuolo("registered");
		user.setPassword("*************");
		userOp.save(user);
		
		Mockito.when(request.getParameter("action")).thenReturn("delete");
		Mockito.when(request.getParameter("usernameDelete")).thenReturn("giuseppeTesting");
			
		servlet.doPost(request, response);
		
		Mockito.verify(response).sendRedirect(response.encodeURL("GestioneUtenti"));
		assertNull(userOp.retrieveByKey("giuseppeTesting")); //verifico che è stata invocata la delete, controllando che sia stato cancellato
	}
	
	@Test
	void testRankUp() throws ServletException, IOException {
		GestioneUtentiControl servlet = new GestioneUtentiControl();
		UtenteDAO userOp = new UtenteDAO();
		servlet.init(sg);

		//Mi creo un Utente per potergli modificare il ruolo
		Utente user = new Utente();
		user.setEmail("testing@test.it");
		user.setUsername("giuseppeTest");
		user.setRuolo("registered");
		user.setPassword("*************");
		userOp.save(user);
		
		Mockito.when(request.getParameter("action")).thenReturn("rankUp");
		Mockito.when(request.getParameter("usernameRankUp")).thenReturn("giuseppeTest");
		Mockito.when(request.getParameter("rank")).thenReturn("admin");

		servlet.doPost(request, response);

		Mockito.verify(response).sendRedirect(response.encodeURL("GestioneUtenti"));
		
		assertEquals(userOp.retrieveByKey("giuseppeTest").getRuolo(), "admin"); //verifico che è stata invocata la update
	}

}
