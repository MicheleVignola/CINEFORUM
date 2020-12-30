package cineforum.control;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import cineforum.model.DBConnectionPool;
/**
 * 
 * Inizializza la connessione al DB quando si avvia il server 
 *
 */
@WebListener
public class InitDBListener implements ServletContextListener {

    public InitDBListener() {
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    	DBConnectionPool.closeConnection();
    }


    public void contextInitialized(ServletContextEvent sce)  { 
    	DBConnectionPool.initDB();
    }
	
}
