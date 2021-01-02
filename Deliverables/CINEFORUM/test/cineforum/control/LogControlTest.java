package cineforum.control;

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

@ExtendWith(MockitoExtension.class)
class LogControlTest {

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
	}

	@Test
	void testLogin() throws ServletException, IOException {
		LogControl servlet = new LogControl();
				
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getParameter("user")).thenReturn("giuseppeandreozzi");
		Mockito.when(request.getParameter("password")).thenReturn("marcantuono");
			
		servlet.doPost(request, response);
		
		Mockito.verify(session).setAttribute(Mockito.eq("user"), Mockito.any(Utente.class));
		Mockito.verify(response).sendRedirect(response.encodeURL("homepage.jsp"));	
	}

}
