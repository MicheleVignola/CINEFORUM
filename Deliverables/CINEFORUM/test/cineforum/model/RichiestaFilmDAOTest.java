package cineforum.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cineforum.model.RichiestaFilm;
import cineforum.model.RichiestaFilmDAO;

class RichiestaFilmDAOTest {
	
	@SuppressWarnings("unused")
	private int id;
	private RichiestaFilmDAO richiestafilmOp;
	private ArrayList<RichiestaFilm> output;
	private ArrayList<RichiestaFilm> output2;
	private RichiestaFilm bean;
	private RichiestaFilm out;

	@BeforeEach
	void setUp() throws Exception {
		
		richiestafilmOp = new RichiestaFilmDAO();
		
		bean = new RichiestaFilm();
		bean.setCommento("Film urgente");
		bean.setEsito("in attesa");
		bean.setUsername("vyncy98");
		bean.setTitolo("Tre uomini e una gamba");
	}

	@Test
	void testRetrieveAll() {
		output = richiestafilmOp.retrieveAll();
		assert(output.size() > 0);	
	}

	@Test
	void testRetrieveAllIntString() {
		output2 = richiestafilmOp.retrieveAll(RichiestaFilmDAO.BY_UTENTE, "vyncy98");
				
		assert(output2.size() > 0);
	}

	@Test
	void testRetrieveByKey() {
		out = richiestafilmOp.retrieveByKey(1);
		assert(out != null);
	}

	@Test
	void testSave() {
		richiestafilmOp.save(bean);
		ArrayList<RichiestaFilm> comm = richiestafilmOp.retrieveAll();
		
		RichiestaFilm cf = null;
		
		Iterator<RichiestaFilm> listIterator = comm.iterator();
		while (listIterator.hasNext()) {
			cf = listIterator.next();
			//if(!listIterator.hasNext()) 
			//	id = cf.getCodiceFilm();
			
		}
		
		assertEquals(bean.getUsername(), cf.getUsername());
		assertEquals(bean.getCommento(), cf.getCommento());
		assertEquals(bean.getEsito(), cf.getEsito());
		assertEquals(bean.getTitolo(), cf.getTitolo());
	}

	@Test
	void testUpdate() {
		RichiestaFilm beanUpdated = richiestafilmOp.retrieveByKey(1);
		beanUpdated.setCommento("DAIII, DEVE ESSERCI");
		richiestafilmOp.update(beanUpdated);
		
		beanUpdated = richiestafilmOp.retrieveByKey(1);
		assertEquals(beanUpdated.getCommento(), "DAIII, DEVE ESSERCI");
	}

	@Test
	void testDelete() {
		RichiestaFilm cf = richiestafilmOp.retrieveByKey(2);
		richiestafilmOp.delete(cf);
		
		assertNull(richiestafilmOp.retrieveByKey(2));
	}

}
