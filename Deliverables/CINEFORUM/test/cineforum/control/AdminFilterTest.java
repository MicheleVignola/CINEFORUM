package cineforum.control;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
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
class AdminFilterTest {

	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	FilterChain chain;
	
	@Mock
	HttpSession session;
	
	@Mock
	ServletContext sc;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDoFilter() throws IOException, ServletException {
		AdminFilter servlet = new AdminFilter();
		
		//Utente fittizio con ruolo "registered" per verificare che il filtro funzioni correttamente
		//ossia, vietando l'accesso e reinderizzando alla homepage
		Utente user = new Utente();
		user.setRuolo("registered");

		Mockito.when(request.getServletContext()).thenReturn(sc);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("user")).thenReturn(user);
		Mockito.when(sc.getContextPath()).thenReturn("CINEFORUM");
		
		servlet.doFilter(request, response, chain);
		
		Mockito.verify(response).sendRedirect(response.encodeURL(request.getServletContext().getContextPath() +"/homepage.jsp"));
	}

}
