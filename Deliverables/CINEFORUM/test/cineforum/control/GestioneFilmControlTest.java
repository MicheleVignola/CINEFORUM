package cineforum.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import cineforum.model.Film;
import cineforum.model.FilmDAO;

@ExtendWith(MockitoExtension.class)
class GestioneFilmControlTest {

	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterAll
	static void tearDown() throws Exception {
		FilmDAO filmOp = new FilmDAO();
		Film film = filmOp.retrieveAll(FilmDAO.BY_EQUALTITLE, "Demon Slayer").get(0);
		
		if (film != null)
			filmOp.delete(film);
		
		film = filmOp.retrieveAll(FilmDAO.BY_EQUALTITLE, "Holidate").get(0);
		
		if (film != null)
			filmOp.delete(film);
	}

	@Test
	void testInserimentoFilm() throws ServletException, IOException {
		GestioneFilmControl servlet = new GestioneFilmControl();
		FilmDAO filmOp = new FilmDAO();
		
		Mockito.when(request.getParameter("action")).thenReturn("insert");
		Mockito.when(request.getParameter("titolo")).thenReturn("Demon Slayer");
		Mockito.when(request.getParameter("descrizione")).thenReturn("Lorem ipsum dolor sit egestas.");
		Mockito.when(request.getParameter("genere")).thenReturn("Horror");
		Mockito.when(request.getParameter("durata")).thenReturn("122");
		Mockito.when(request.getParameter("dataUscita")).thenReturn("2020/10/10");
		Mockito.when(request.getPart("foto")).thenReturn(null);
		
		servlet.doPost(request, response);
		
		Mockito.verify(response).sendRedirect(response.encodeURL("admin/gestionefilm.jsp"));
		assertNotNull(filmOp.retrieveAll(FilmDAO.BY_EQUALTITLE, "Demon Slayer").get(0)); //verifico che sia stata invocata la save, controllando che il film si sia salvato nel db
	}
	
	@Test
	void testModificaFilm() throws ServletException, IOException {
		GestioneFilmControl servlet = new GestioneFilmControl();
		FilmDAO filmOp = new FilmDAO();
		
		//Inserisco un film per poterlo modificare
		Film filmTemp = new Film();
		filmTemp.setDataUscita("2020/11/11");
		filmTemp.setDescrizione("Lorem ipsum dolor sit egestas.");
		filmTemp.setDurata((short) 135);
		filmTemp.setGenere("Azione");
		filmTemp.setTitolo("Holidate");
		filmOp.save(filmTemp);
		filmTemp = filmOp.retrieveAll(FilmDAO.BY_EQUALTITLE, "Holidate").get(0); //lo recupero perchè serve il CodiceFilm che viene associato automaticamente poichè è AUTO_INCREMENT
		
		Mockito.when(request.getParameter("action")).thenReturn("edit");
		Mockito.when(request.getParameter("codiceFilm")).thenReturn(Integer.toString(filmTemp.getCodiceFilm()));
		Mockito.when(request.getParameter("titolo")).thenReturn("Holidate");
		Mockito.when(request.getParameter("descrizione")).thenReturn("Lorem ipsum dolor sit egestas.");
		Mockito.when(request.getParameter("genere")).thenReturn("Romantico"); //attributo modificato
		Mockito.when(request.getParameter("durata")).thenReturn("122");
		Mockito.when(request.getParameter("dataUscita")).thenReturn("2020/10/10");
		Mockito.when(request.getPart("foto")).thenReturn(null);
		
		servlet.doPost(request, response);
		
		Mockito.verify(response).sendRedirect(response.encodeURL("admin/gestionefilm.jsp"));
		Film film = filmOp.retrieveAll(FilmDAO.BY_EQUALTITLE, "Holidate").get(0);
		assertEquals(film.getGenere(), "Romantico"); //verifico che sia stata invocata la update
	}
	
	@Test
	void testRimozioneFilm() throws ServletException, IOException {
		GestioneFilmControl servlet = new GestioneFilmControl();
		
		//Inserisco un film per poterlo eliminare
		FilmDAO filmOp = new FilmDAO();
		Film filmTemp = new Film();
		filmTemp.setDataUscita("2018/11/11");
		filmTemp.setDescrizione("Lorem ipsum dolor sit egestas.");
		filmTemp.setDurata((short) 125);
		filmTemp.setGenere("Azione");
		filmTemp.setTitolo("Never Back Down");
		filmOp.save(filmTemp);
		
		Mockito.when(request.getParameter("action")).thenReturn("delete");
		Mockito.when(request.getParameter("titolo")).thenReturn("Never Back Down");
		
		servlet.doPost(request, response);
		
		Mockito.verify(response).sendRedirect(response.encodeURL("admin/gestionefilm.jsp"));
		
		//verifico che sia stata invocata la delete, controllando che non trova nessun film col titolo "Never Back Down"
		assertTrue(filmOp.retrieveAll(FilmDAO.BY_EQUALTITLE, "Never Back Down").isEmpty()); 
	}

}
