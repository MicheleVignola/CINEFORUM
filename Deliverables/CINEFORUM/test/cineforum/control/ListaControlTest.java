package cineforum.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ListaControlTest {

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
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testToListaUtente() throws ServletException, IOException {
		ListaControl servlet = new ListaControl();
		servlet.init(sg);

		Mockito.when(sg.getServletContext()).thenReturn(sc);
		Mockito.when(request.getParameter("categoria")).thenReturn(null);
		Mockito.when(request.getParameter("user")).thenReturn("giuseppeandreozzi");
		Mockito.when(sc.getRequestDispatcher("/lista.jsp")).thenReturn(dispatcher);		
		
		servlet.doGet(request, response);

		Mockito.verify(request).setAttribute(Mockito.eq("lista"), Mockito.any(ArrayList.class));
		Mockito.verify(request).setAttribute(Mockito.eq("film"), Mockito.any(HashMap.class));
		Mockito.verify(request).setAttribute("user", "giuseppeandreozzi");
		Mockito.verify(dispatcher).forward(request, response);
	}

}
