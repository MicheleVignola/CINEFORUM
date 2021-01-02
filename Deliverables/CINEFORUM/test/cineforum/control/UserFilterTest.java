package cineforum.control;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserFilterTest {

	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	FilterChain chain;
	
	@Mock
	HttpSession session;
	
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDoFilter() throws IOException, ServletException {
		UserFilter servlet = new UserFilter();
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("user")).thenReturn(null);
		
		servlet.doFilter(request, response, chain);
		
		Mockito.verify(session).setAttribute("error", "Devi essere loggato per accedere a questa pagina");
		Mockito.verify(response).sendRedirect(response.encodeURL("login.jsp"));
	}

}
