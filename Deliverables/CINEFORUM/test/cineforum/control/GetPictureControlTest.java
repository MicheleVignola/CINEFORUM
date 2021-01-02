package cineforum.control;

import static org.apache.commons.io.IOUtils.toByteArray;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
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

import cineforum.model.Film;
import cineforum.model.FilmDAO;
import cineforum.model.UtenteDAO;

@ExtendWith(MockitoExtension.class)
class GetPictureControlTest {

	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	ServletOutputStream out;
	
	byte[] oldImage;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		FilmDAO filmOp = new FilmDAO();
		Film film = filmOp.retrieveByKey(2);
		film.setImmagine(oldImage);
		filmOp.update(film);

	}

	@Test
	void testRetrieveImage() throws IOException, ServletException {
		GetPictureControl servlet = new GetPictureControl();
		
		Mockito.when(request.getParameter("film")).thenReturn("2");
	    Mockito.when(response.getOutputStream()).thenReturn(out);
	    
	    //inserisco la foto
	    FilmDAO filmOp = new FilmDAO();
	    Film film = filmOp.retrieveByKey(2);
	    oldImage = film.getImmagine();
	    InputStream inputStream = new FileInputStream("WebContent\\img\\noimage.png");
	    film.setImmagine(toByteArray(inputStream));
	    filmOp.update(film);
	    
		servlet.doGet(request, response);
	
		Mockito.verify(response).setContentType("image/png");
		Mockito.verify(out).write(Mockito.any(byte[].class));
		Mockito.verify(out).close();
	}

}
