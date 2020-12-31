package cineforum.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cineforum.model.Lista;
import cineforum.model.ListaDAO;


class ListaDAOTest {

	@SuppressWarnings("unused")
	private int id;
	private ListaDAO listaOp;
	private ArrayList<Lista> output;
	private ArrayList<Lista> output2;
	private ArrayList<Lista> output3;
	private ArrayList<Lista> output4;
	private Lista bean;
	private Lista out;
	
	@BeforeEach
	void setUp() throws Exception {
		
		listaOp = new ListaDAO();
		
		bean = new Lista();
		bean.setCodiceFilm(5);
		bean.setCategoria("in programma");
		bean.setVoto((short) 8);
		bean.setUsername("vyncy98");
	}

	@Test
	void testRetrieveAll() {
		output = listaOp.retrieveAll();
		assert(output.size() > 0);	
	}

	@Test
	void testRetrieveAllIntString() {
		output2 = listaOp.retrieveAll(ListaDAO.BY_UTENTE, "vyncy98");
		output3 = listaOp.retrieveAll(ListaDAO.BY_UTENTE_VISTI, "vyncy98");
		output4 = listaOp.retrieveAll(ListaDAO.BY_UTENTE_INPROGRAMMA, "vyncy98");
		
		assert(output2.size() > 0);
		assert(output3.size() > 0);
		assert(output4.size() > 0);
	}

	@Test
	void testRetrieveByKey() {
		out = listaOp.retrieveByKey(1, "vyncy98");
		assert(out != null);
	}

	@Test
	void testSave() {
		listaOp.save(bean);
		ArrayList<Lista> comm = listaOp.retrieveAll();
		
		Lista cf = null;
		
		Iterator<Lista> listIterator = comm.iterator();
		while (listIterator.hasNext()) {
			cf = listIterator.next();
			//if(!listIterator.hasNext()) 
			//	id = cf.getCodiceFilm();
			
		}
		
		assertEquals(bean.getUsername(), cf.getUsername());
		assertEquals(bean.getCodiceFilm(), cf.getCodiceFilm());
		assertEquals(bean.getVoto(), cf.getVoto());
		assertEquals(bean.getCategoria(), cf.getCategoria());
	}

	@Test
	void testUpdate() {
		Lista beanUpdated = listaOp.retrieveByKey(1, "vyncy98");
		beanUpdated.setVoto((short) 6);
		listaOp.update(beanUpdated);
		
		beanUpdated = listaOp.retrieveByKey(1, "vyncy98");
		assertEquals(beanUpdated.getVoto(), 6);
	}

	@Test
	void testDelete() {
		Lista cf = listaOp.retrieveByKey(2, "vyncy98");
		listaOp.delete(cf);
		
		assertNull(listaOp.retrieveByKey(2, "vyncy98"));
	}

}
