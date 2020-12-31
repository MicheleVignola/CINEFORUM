package cineforum.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cineforum.model.CommentoFilm;
import cineforum.model.CommentoFilmDAO;

class CommentoFilmDAOTest {

	@SuppressWarnings("unused")
	private int id;
	private CommentoFilm bean;
	private CommentoFilm out;
	private ArrayList<CommentoFilm> output;
	private ArrayList<CommentoFilm> output2;
	private CommentoFilmDAO commentoOp;
	
	@BeforeEach
	void setUp() throws Exception {
		commentoOp = new CommentoFilmDAO();
		
		bean = new CommentoFilm();
		bean.setCommento("Film horror adatto ai soli adulti");	
		bean.setOrario("2020-01-01 00:00:00");
		bean.setCodiceFilm(1);
		bean.setUsername("vyncy98");
		
	}

	@Test
	void testRetrieveAll() {
		output = commentoOp.retrieveAll();
		assert(output.size() > 0);	
	}

	@Test
	void testRetrieveAllIntString() {
		output2 = commentoOp.retrieveAll(CommentoFilmDAO.BY_FILM, "1");
		assert(output2.size() > 0);
	}

	@Test
	void testRetrieveByKey() {
		out = commentoOp.retrieveByKey(1);
		assert(out != null);
	}

	@Test
	void testSave() {
		commentoOp.save(bean);
		ArrayList<CommentoFilm> comm = commentoOp.retrieveAll();
		
		CommentoFilm cf = null;
		
		Iterator<CommentoFilm> listIterator = comm.iterator();
		while (listIterator.hasNext()) {
			cf = listIterator.next();
			//if(!listIterator.hasNext()) 
			//	id = cf.getIdCommento();
			
		}
		
		assertEquals(bean.getCommento(), cf.getCommento());
		assertEquals(bean.getOrario(), cf.getOrario());
		assertEquals(bean.getCodiceFilm(), cf.getCodiceFilm());
		assertEquals(bean.getUsername(), cf.getUsername());
		
		
		
	}

	@Test
	void testUpdate() {
		CommentoFilm beanUpdated = commentoOp.retrieveByKey(1);
		beanUpdated.setCommento("Film non appropriato per i bambini al di sotto dei 15 anni");
		commentoOp.update(beanUpdated);
		
		beanUpdated = commentoOp.retrieveByKey(1);
		assertEquals(beanUpdated.getCommento(), "Film non appropriato per i bambini al di sotto dei 15 anni");
		
	}

	@Test
	void testDelete() {
		CommentoFilm cf = commentoOp.retrieveByKey(2);
		commentoOp.delete(cf);
		
		assertNull(commentoOp.retrieveByKey(2));
		
	}
	

}
