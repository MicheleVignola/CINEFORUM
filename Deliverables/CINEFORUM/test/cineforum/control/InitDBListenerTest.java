package cineforum.control;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContextEvent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cineforum.model.DBConnectionPool;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
class InitDBListenerTest {

	@Mock
	ServletContextEvent sce;
	
	@Mock
	DBConnectionPool pool;
	
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testContextDestroyed() throws SQLException {
		InitDBListener servlet = new InitDBListener();
		
		//creo una connessione per poterla chiudere
		Connection conn = DBConnectionPool.getDBConnection();
		DBConnectionPool.releaseConnection(conn);
		
		servlet.contextDestroyed(sce);
		
	    ArrayList<Connection> pool = null;
	    Field field;
		try {
			field = DBConnectionPool.class.getDeclaredField("pool");
			field.setAccessible(true);
			pool = (ArrayList<Connection>) field.get(DBConnectionPool.class);
		} catch (Exception e) {
			fail("Exception: ");
			e.printStackTrace();
		}
		assertNotNull(pool);
		
		for(Connection connection : pool)
			assertTrue(connection.isClosed());
	}

	@Test
	void testContextInitialized() throws SQLException {
		InitDBListener servlet = new InitDBListener();
		
		
		servlet.contextInitialized(sce);
		
	    ArrayList<Connection> pool = null;
	    Field field;
		try {
			field = DBConnectionPool.class.getDeclaredField("pool");
			field.setAccessible(true);
			pool = (ArrayList<Connection>) field.get(DBConnectionPool.class);
		} catch (Exception e) {
			fail("Exception: ");
			e.printStackTrace();
		}
		assertNotNull(pool);
	}

}
