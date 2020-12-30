package cineforum.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDAO implements DAOInterfaceSinglePK<Utente, String>{

	/**
	 * costante public da usare per il metodo retrieveAll(int option, String value)
	 * come parametro "option"
	 */
	public static final int BY_EMAIL = 0;
	
	@Override
	public ArrayList<Utente> retrieveAll() {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		ArrayList<Utente> list = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("SELECT * FROM utente");
			rs = sql.executeQuery();
			
			list = new ArrayList<Utente>();
			
			while(rs.next()) {
				Utente bean = new Utente();
				bean.setUsername(rs.getString("Username"));
				bean.setPassword(rs.getString("Password"));
				bean.setEmail(rs.getString("Email"));
				bean.setRuolo(rs.getString("Ruolo"));
				list.add(bean);
			}
		
		}catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} finally {
			DBConnectionPool.releaseConnection(conn);
			try {
				sql.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			}	
		}
		
		return list;
	}

	/**
	 * Metodo che fornisce alternative, in base al parametro option, di query per 
	 * recuperare richieste di aggiungere un film.
	 * 
	 * @param option definisce la scelta della query tramite le seguenti costanti:
	 *  		- BY_EMAIL: recupera gli utenti con email specificato dal parametro "value"
	 *  
	 * @param value rappresenta il valore di un attributo, definito da 'option', da inserire nella query
	 */
	@Override
	public ArrayList<Utente> retrieveAll(int option, String value) {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		ArrayList<Utente> list = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			
			if(option == BY_EMAIL) {
				sql = conn.prepareStatement("SELECT * FROM utente WHERE Email = ?");
				sql.setString(1, value);
			} else {
				return retrieveAll();
			}
			
			rs = sql.executeQuery();
			
			list = new ArrayList<Utente>();
			
			while(rs.next()) {
				Utente bean = new Utente();
				bean.setUsername(rs.getString("Username"));
				bean.setPassword(rs.getString("Password"));
				bean.setEmail(rs.getString("Email"));
				bean.setRuolo(rs.getString("Ruolo"));
				list.add(bean);
			}
		
		}catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} finally {
			DBConnectionPool.releaseConnection(conn);
			try {
				sql.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			}	
		}
		
		return list;
	}

	@Override
	public Utente retrieveByKey(String key) {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		Utente user = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("SELECT * FROM utente WHERE Username = ?");
			sql.setString(1, key);
			rs = sql.executeQuery();
			
			if(rs.next()) {
				user = new Utente();
				user.setUsername(rs.getString("Username"));
				user.setPassword(rs.getString("Password"));
				user.setEmail(rs.getString("Email"));
				user.setRuolo(rs.getString("Ruolo"));
			}
		
		}catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} finally {
			DBConnectionPool.releaseConnection(conn);
			try {
				sql.close();
				rs.close();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			}	
		}
		
		return user;
	}
	
	@Override
	public void save(Utente bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("INSERT INTO utente VALUES (?, ?, ?, ?)");
			sql.setString(1, bean.getUsername());
			sql.setString(2, bean.getEmail());
			sql.setString(3, bean.getPassword());
			sql.setString(4, bean.getRuolo());
			result = sql.executeUpdate();
			
			
			if(result != 0)
				conn.commit();
		
		}catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} finally {
			DBConnectionPool.releaseConnection(conn);
			try {
				sql.close();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			}	
		}		
	}

	@Override
	public void update(Utente bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("UPDATE utente SET Email = ?, Password = ?, Ruolo = ? WHERE Username = ?");
			sql.setString(1, bean.getEmail());
			sql.setString(2, bean.getPassword());
			sql.setString(3, bean.getRuolo());
			sql.setString(4, bean.getUsername());
			result = sql.executeUpdate();
			
			
			if(result != 0)
				conn.commit();
		
		}catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} finally {
			DBConnectionPool.releaseConnection(conn);
			try {
				sql.close();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			}	
		}	
		
	}

	@Override
	public void delete(Utente bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("DELETE FROM utente WHERE Username = ?");
			sql.setString(1, bean.getUsername());
			result = sql.executeUpdate();
			
			
			if(result != 0)
				conn.commit();
		
		}catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		} finally {
			DBConnectionPool.releaseConnection(conn);
			try {
				sql.close();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			}	
		}			
	}

}
