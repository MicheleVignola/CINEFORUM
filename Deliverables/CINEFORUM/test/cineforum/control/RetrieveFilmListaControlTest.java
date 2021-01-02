package cineforum.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import cineforum.model.UtenteDAO;

@ExtendWith(MockitoExtension.class)
class RetrieveFilmListaControlTest {

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
	void testRecuperaFilm() throws ServletException, IOException {
		RetrieveFilmListaControl servlet = new RetrieveFilmListaControl();
		UtenteDAO userOp = new UtenteDAO();
		JSONObject json = new JSONObject();
		StringWriter stringWriter = new StringWriter();
	    PrintWriter writer = new PrintWriter(stringWriter);
		json.put("titolo", "the"); //cerca film "the" come sottostringa nel titolo
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("user")).thenReturn(userOp.retrieveByKey("giuseppeandreozzi"));
		Mockito.when(request.getParameter("json")).thenReturn(json.toString());
	    Mockito.when(response.getWriter()).thenReturn(writer);
	    
		servlet.doPost(request, response);
		
		Mockito.verify(response).setContentType("application/json");
		writer.flush();
		JSONObject jsonResponse = new JSONObject(stringWriter.toString());
		JSONArray list = (JSONArray) jsonResponse.get("lista");
		assertFalse(list.isEmpty());
	}

}
