package cineforum.control;

import java.io.IOException;
import java.util.ArrayList;

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
class ConsultaListeControlTest {

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
	void testToConsultaListe() throws ServletException, IOException {
		ConsultaListeControl servlet = new ConsultaListeControl();
		servlet.init(sg);
		
		Mockito.when(sg.getServletContext()).thenReturn(sc);
		Mockito.when(sc.getRequestDispatcher("/consultaliste.jsp")).thenReturn(dispatcher);		
		
		servlet.doGet(request, response);
		
		Mockito.verify(request).setAttribute(Mockito.eq("utenti"), Mockito.any(ArrayList.class));
		Mockito.verify(dispatcher).forward(request, response);
	}

}
