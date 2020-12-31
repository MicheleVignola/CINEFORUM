package cineforum.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cineforum.model.Film;
import cineforum.model.FilmDAO;

class FilmDAOTest {
	
	@SuppressWarnings("unused")
	private int id;
	private FilmDAO filmOp;
	private ArrayList<Film> output;
	private ArrayList<Film> output2;
	private ArrayList<Film> output3;
	private Film bean;
	private Film out;

	@BeforeEach
	void setUp() throws Exception {
	
		filmOp = new FilmDAO();
		
		bean = new Film();
		bean.setTitolo("Titanic");
		bean.setDescrizione("Questo film narra le vicende del naufragio del Titanic");
		bean.setGenere("Drammatico");
		bean.setDataUscita("2001-01-15");
		bean.setDurata((short) 145);
	}

	@Test
	void testRetrieveAll() {
		output = filmOp.retrieveAll();
		assert(output.size() > 0);	
	}

	@Test
	void testRetrieveAllIntString() {
		output2 = filmOp.retrieveAll(FilmDAO.BY_TITLE, "Circle");
		output3 = filmOp.retrieveAll(FilmDAO.BY_EQUALTITLE, "Circle");
		
		assert(output2.size() > 0);
		assert(output3.size() > 0);
	}

	@Test
	void testRetrieveByKey() {
		out = filmOp.retrieveByKey(1);
		assert(out != null);
	}

	@Test
	void testSave() {
		filmOp.save(bean);
		ArrayList<Film> comm = filmOp.retrieveAll();
		
		Film cf = null;
		
		Iterator<Film> listIterator = comm.iterator();
		while (listIterator.hasNext()) {
			cf = listIterator.next();
			//if(!listIterator.hasNext()) 
			//	id = cf.getCodiceFilm();
			
		}
		
		assertEquals(bean.getTitolo(), cf.getTitolo());
		assertEquals(bean.getGenere(), cf.getGenere());
		assertEquals(bean.getDataUscita(), cf.getDataUscita());
		assertEquals(bean.getDescrizione(), cf.getDescrizione());
		assertEquals(bean.getDurata(), cf.getDurata());
	}

	@Test
	void testUpdate() {
		Film beanUpdated = filmOp.retrieveByKey(1);
		beanUpdated.setDurata((short) 120);
		filmOp.update(beanUpdated);
		
		beanUpdated = filmOp.retrieveByKey(1);
		assertEquals(beanUpdated.getDurata(), 120);
	}

	@Test
	void testDelete() {
		Film cf = filmOp.retrieveByKey(2);
		filmOp.delete(cf);
		
		assertNull(filmOp.retrieveByKey(2));
		
	}

}
