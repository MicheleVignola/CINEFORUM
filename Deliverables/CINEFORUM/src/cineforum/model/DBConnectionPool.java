package cineforum.model;
import java.sql.*;
import java.util.ArrayList;

/**
 * Connection Pool del db
 *
 */
public class DBConnectionPool {
	private static ArrayList<Connection> pool;
	
	static {
		pool = new ArrayList<Connection>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("DB Inizializzato");
		}
		catch(ClassNotFoundException ex) {
		   System.out.println("ClassNotFoundException: " + ex.getMessage());
		   System.exit(1);
		}
	}
	
	public static void initDB() {
		
	}
	
	private static Connection newConnection() throws SQLException {
		Connection tmp = null;
		try {
			tmp = DriverManager.getConnection("jdbc:mysql://localhost:3306/cineforum?serverTimezone=UTC","cineadmin", "Cine.123");
			tmp.setAutoCommit(false);
		
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.exit(1);
		}
		return tmp;
	}
	
	public static synchronized Connection getDBConnection() throws SQLException{
		Connection conn = null;
		
		if(pool.isEmpty()) {
			conn = newConnection();
		}
		else {
			conn = pool.get(0);
			pool.remove(0);
			
			try {
				if(conn.isClosed())
					conn = getDBConnection();
			} catch (SQLException e) {
				if (conn != null)
					conn.close();
				
				conn = getDBConnection();
			}
		}
		return conn;
	}


	public static synchronized void releaseConnection(Connection conn) {
		pool.add(conn);
	}
	
	public static synchronized void closeConnection() {
        if(!pool.isEmpty() && pool != null) {
          try {
            for (Connection conn : pool) {
              if (conn != null)
                conn.close();
            }
          }
          catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
          }
        }
      }
	
}