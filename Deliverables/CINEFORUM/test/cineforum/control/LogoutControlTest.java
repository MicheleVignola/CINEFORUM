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


@ExtendWith(MockitoExtension.class)
class LogoutControlTest {

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
	void testLogout() throws ServletException, IOException {
		LogoutControl servlet = new LogoutControl();
		
		Mockito.when(request.getSession()).thenReturn(session);
			
		servlet.doGet(request, response);
		
		Mockito.verify(session).removeAttribute("user");
		Mockito.verify(session).invalidate();
		Mockito.verify(response).sendRedirect(response.encodeURL("homepage.jsp"));	
	}

}
