package cineforum.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchFilmAdminControlTest {

	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRecuperaFilm() throws IOException, ServletException {
		SearchFilmAdminControl servlet = new SearchFilmAdminControl();
		JSONObject json = new JSONObject();
		StringWriter stringWriter = new StringWriter();
	    PrintWriter writer = new PrintWriter(stringWriter);
		json.put("titolo", "Cloudy");
		
		Mockito.when(request.getParameter("json")).thenReturn(json.toString());
	    Mockito.when(response.getWriter()).thenReturn(writer);
	    
		servlet.doPost(request, response);
		
		Mockito.verify(response).setContentType("application/json");
		writer.flush();
		JSONObject jsonResponse = new JSONObject(stringWriter.toString());
		assertEquals(jsonResponse.get("titolo"), "Cloudy");
	}

}
