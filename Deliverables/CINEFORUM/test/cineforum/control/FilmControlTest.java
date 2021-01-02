package cineforum.control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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

import cineforum.model.Film;
import cineforum.model.Utente;


@ExtendWith(MockitoExtension.class)
class FilmControlTest {

	@Mock 
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestDispatcher dispatcher;
	
	@Mock
	ServletContext sc;
	
	@Mock
	ServletConfig sg;
	
	@Mock
	HttpSession session;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testToRaccoltaFilm() throws ServletException, IOException {
		FilmControl servlet = new FilmControl();
		servlet.init(sg);

		Mockito.when(sg.getServletContext()).thenReturn(sc);
		Mockito.when(request.getParameter("film")).thenReturn(null);
		Mockito.when(sc.getRequestDispatcher("/raccoltafilm.jsp")).thenReturn(dispatcher);		
		
		servlet.doGet(request, response);
		
		Mockito.verify(request).setAttribute(Mockito.eq("film"), Mockito.any(ArrayList.class));
		Mockito.verify(dispatcher).forward(request, response);

	}

	@Test
	void testToPaginaFilm() throws ServletException, IOException {
		FilmControl servlet = new FilmControl();
		servlet.init(sg);

		Mockito.when(sg.getServletContext()).thenReturn(sc);
		Mockito.when(request.getParameter("film")).thenReturn("1");
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("user")).thenReturn(new Utente());
		Mockito.when(sc.getRequestDispatcher("/paginafilm.jsp")).thenReturn(dispatcher);		
		
		servlet.doGet(request, response);
		
		Mockito.verify(request).setAttribute(Mockito.eq("checkFilmLista"), Mockito.anyBoolean());
		Mockito.verify(request).setAttribute(Mockito.eq("film"), Mockito.any(Film.class));
		Mockito.verify(request).setAttribute(Mockito.eq("commenti"), Mockito.any(ArrayList.class));
		Mockito.verify(dispatcher).forward(request, response);
	}

}
