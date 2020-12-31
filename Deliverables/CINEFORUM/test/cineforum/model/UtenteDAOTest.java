package cineforum.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cineforum.model.Utente;
import cineforum.model.UtenteDAO;

class UtenteDAOTest {

	@SuppressWarnings("unused")
	private int id;
	private UtenteDAO utenteOp;
	private ArrayList<Utente> output;
	private ArrayList<Utente> output2;
	private Utente bean;
	private Utente out;
	
	@BeforeEach
	void setUp() throws Exception {
		
		utenteOp = new UtenteDAO();
		
		bean = new Utente();
		bean.setUsername("zac99");
		bean.setEmail("zac99@gmail.com");
		bean.setPassword("marcantuono");
		bean.setRuolo("registered");
	}

	@Test
	void testRetrieveAll() {
		output = utenteOp.retrieveAll();
		assert(output.size() > 0);	
	}

	@Test
	void testRetrieveAllIntString() {
		output2 = utenteOp.retrieveAll(UtenteDAO.BY_EMAIL, "vyncy98@gmail.com");
		
		assert(output2.size() > 0);
	}

	@Test
	void testRetrieveByKey() {
		out = utenteOp.retrieveByKey("vyncy98");
		assert(out != null);
	}

	@Test
	void testSave() {
		utenteOp.save(bean);
		ArrayList<Utente> comm = utenteOp.retrieveAll();
		
		Utente cf = null;
		
		Iterator<Utente> listIterator = comm.iterator();
		while (listIterator.hasNext()) {
			cf = listIterator.next();
			//if(!listIterator.hasNext()) 
			//	id = cf.getCodiceFilm();
			
		}
		
		assertEquals(bean.getUsername(), cf.getUsername());
		assertEquals(bean.getPassword(), cf.getPassword());
		assertEquals(bean.getEmail(), cf.getEmail());
		assertEquals(bean.getRuolo(), cf.getRuolo());
	}

	@Test
	void testUpdate() {
		Utente beanUpdated = utenteOp.retrieveByKey("giuseppeandreozzi");
		beanUpdated.setRuolo("registered");
		utenteOp.update(beanUpdated);
		
		beanUpdated = utenteOp.retrieveByKey("giuseppeandreozzi");
		assertEquals(beanUpdated.getRuolo(), "registered");
	}

	@Test
	void testDelete() {
		Utente cf = utenteOp.retrieveByKey("michele97");
		utenteOp.delete(cf);
		
		assertNull(utenteOp.retrieveByKey("michele97"));
	}

}
