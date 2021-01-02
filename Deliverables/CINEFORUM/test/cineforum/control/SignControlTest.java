package cineforum.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

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

import cineforum.model.Utente;
import cineforum.model.UtenteDAO;

@ExtendWith(MockitoExtension.class)
class SignControlTest {
	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	HttpSession session;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		UtenteDAO userOp = new UtenteDAO();
		Utente user = new Utente();
		user.setUsername("giuseppe");
		
		userOp.delete(user);
	}

	@Test
	void testSignup() throws ServletException, IOException {
		SignControl servlet = new SignControl();
		UtenteDAO userOp = new UtenteDAO();
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getParameter("user")).thenReturn("giuseppe");
		Mockito.when(request.getParameter("email")).thenReturn("giuseppe@libero.it");
		Mockito.when(request.getParameter("password")).thenReturn("Joseph.123");
		Mockito.when(request.getParameter("ripetiPassword")).thenReturn("Joseph.123");
	     
		servlet.doPost(request, response);
		
		Mockito.verify(response).sendRedirect("homepage.jsp");
		
		//verifico che sia stata effettuata la save, controllando che l'user sia nel db
		Utente user = userOp.retrieveByKey("giuseppe");
		assertNotNull(user);
	}

}
