package cineforum.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListaDAO implements DAOInterfaceDoublePK<Lista, Integer, String> {

	/**
	 * costante public da usare per il metodo retrieveAll(int option, String value)
	 * come parametro "option"
	 */
	public static final int BY_UTENTE = 0;
	public static final int BY_UTENTE_VISTI = 1;
	public static final int BY_UTENTE_INPROGRAMMA = 2;
	
	@Override
	public ArrayList<Lista> retrieveAll() {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		ArrayList<Lista> list = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("SELECT * FROM lista");
			rs = sql.executeQuery();
			
			list = new ArrayList<Lista>();
			
			while(rs.next()) {
				Lista bean = new Lista();
				bean.setCodiceFilm(rs.getInt("CodiceFilm"));
				bean.setUsername(rs.getString("Username"));
				bean.setVoto(rs.getShort("Voto"));
				bean.setCategoria(rs.getString("Categoria"));
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
	 * recuperare i film aggiungi nelle liste.
	 * 
	 * @param option definisce la scelta della query tramite le seguenti costanti:
	 *  		- BY_UTENTE: recupera la lista di un dato utente con username specificato dal parametro "value";
	 *  		- BY_UTENTE_VISTI: recupera la lista dei film visti di un dato utente con username specificato da "value";
	 *  		- BY_UTENTE_INPROGRAMMA: recupera la lista dei film 'in programma' di un dato utente con username specificato da "value"
	 *  
	 * @param value rappresenta il valore di un attributo, definito da 'option', da inserire nella query
	 */

	@Override
	public ArrayList<Lista> retrieveAll(int option, String value) {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		ArrayList<Lista> list = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			if (option == BY_UTENTE) {
				sql = conn.prepareStatement("SELECT * FROM lista WHERE Username = ?");
				sql.setString(1, value);
			} else if (option == BY_UTENTE_VISTI) {
				sql = conn.prepareStatement("SELECT * FROM lista WHERE Username = ? AND Categoria= ?");
				sql.setString(1, value);
				sql.setString(2, "visti");
			} else if (option == BY_UTENTE_INPROGRAMMA) {
				sql = conn.prepareStatement("SELECT * FROM lista WHERE Username = ? AND Categoria = ?");
				sql.setString(1, value);
				sql.setString(2, "in programma");
			} else {
				return retrieveAll();
			}
			
			rs = sql.executeQuery();
			
			list = new ArrayList<Lista>();
			
			while(rs.next()) {
				Lista bean = new Lista();
				bean.setCodiceFilm(rs.getInt("CodiceFilm"));
				bean.setUsername(rs.getString("Username"));
				bean.setVoto(rs.getShort("Voto"));
				bean.setCategoria(rs.getString("Categoria"));
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
	public Lista retrieveByKey(Integer codiceFilm, String username) {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		Lista bean = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("SELECT * FROM lista WHERE CodiceFilm = ? AND Username = ?");
			sql.setInt(1, codiceFilm);
			sql.setString(2, username);
			rs = sql.executeQuery();
			

			
			if(rs.next()) {
				bean = new Lista();
				bean.setCodiceFilm(rs.getInt("CodiceFilm"));
				bean.setUsername(rs.getString("Username"));
				bean.setVoto(rs.getShort("Voto"));
				bean.setCategoria(rs.getString("Categoria"));
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
		
		return bean;
	}
	
	@Override
	public void save(Lista bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("INSERT INTO lista VALUES (?, ?, ?, ?)");
			sql.setInt(1, bean.getCodiceFilm());
			sql.setString(2, bean.getUsername());
			sql.setShort(3, bean.getVoto());
			sql.setString(4, bean.getCategoria());
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
	public void update(Lista bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("UPDATE lista SET Voto = ?, Categoria = ? WHERE CodiceFilm = ? AND Username = ?");
			sql.setShort(1, bean.getVoto());
			sql.setString(2, bean.getCategoria());
			sql.setInt(3, bean.getCodiceFilm());
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
	public void delete(Lista bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("DELETE FROM lista WHERE CodiceFilm = ? AND Username = ?");
			sql.setInt(1, bean.getCodiceFilm());
			sql.setString(2, bean.getUsername());
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
