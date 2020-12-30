package cineforum.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentoFilmDAO implements DAOInterfaceSinglePK<CommentoFilm, Integer>{

	/**
	 * costante public da usare per il metodo retrieveAll(int option, String value)
	 * come parametro "option"
	 */
	public static final int BY_FILM = 0;
	
	@Override
	public ArrayList<CommentoFilm> retrieveAll() {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		ArrayList<CommentoFilm> list = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("SELECT * FROM commento_film");
			rs = sql.executeQuery();
			
			list = new ArrayList<CommentoFilm>();
			
			while(rs.next()) {
				CommentoFilm bean = new CommentoFilm();
				bean.setIdCommento(rs.getInt("Id_commento"));
				bean.setCommento(rs.getString("Commento"));
				bean.setOrario(rs.getString("Orario"));
				bean.setUsername(rs.getString("Username"));
				bean.setCodiceFilm(rs.getInt("CodiceFilm"));
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
	 * recuperare commenti sui film.
	 * 
	 * @param option definisce la scelta della query tramite le seguenti costanti:
	 *  		- BY_FILM: recupera i commenti sul film con CodiceFilm specificato dal parametro value
	 *  
	 * @param value rappresenta il valore di un attributo, definito da 'option', da inserire nella query
	 */
	@Override
	public ArrayList<CommentoFilm> retrieveAll(int option, String value) {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		ArrayList<CommentoFilm> list = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			
			if(option == BY_FILM) {
				sql = conn.prepareStatement("SELECT * FROM commento_film WHERE CodiceFilm = ? ORDER BY Orario DESC");
				sql.setString(1, value);
			} else {
				return retrieveAll();
			}
			
			rs = sql.executeQuery();
			
			list = new ArrayList<CommentoFilm>();
			
			while(rs.next()) {
				CommentoFilm bean = new CommentoFilm();
				bean.setIdCommento(rs.getInt("Id_commento"));
				bean.setCommento(rs.getString("Commento"));
				bean.setOrario(rs.getString("Orario"));
				bean.setUsername(rs.getString("Username"));
				bean.setCodiceFilm(rs.getInt("CodiceFilm"));
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
	public CommentoFilm retrieveByKey(Integer key) {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		CommentoFilm bean = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("SELECT * FROM commento_film WHERE Id_commento = ?");
			sql.setInt(1, key);
			rs = sql.executeQuery();
			
			
			if(rs.next()) {
				bean = new CommentoFilm();
				bean.setIdCommento(rs.getInt("Id_commento"));
				bean.setCommento(rs.getString("Commento"));
				bean.setOrario(rs.getString("Orario"));
				bean.setUsername(rs.getString("Username"));
				bean.setCodiceFilm(rs.getInt("CodiceFilm"));
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
	public void save(CommentoFilm bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("INSERT INTO commento_film(Commento, Orario, Username, CodiceFilm) VALUES (?, ?, ?, ?)");
			sql.setString(1, bean.getCommento());
			sql.setString(2, bean.getOrario());
			sql.setString(3, bean.getUsername());
			sql.setInt(4, bean.getCodiceFilm());
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
	public void update(CommentoFilm bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("UPDATE commento_film SET Commento = ?, Orario = ?, Username = ?, CodiceFilm = ? WHERE Id_commento = ?");
			sql.setString(1, bean.getCommento());
			sql.setString(2, bean.getOrario());
			sql.setString(3, bean.getUsername());
			sql.setInt(4, bean.getCodiceFilm());
			sql.setInt(5, bean.getIdCommento());
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
	public void delete(CommentoFilm bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("DELETE FROM commento_film WHERE Id_commento = ?");
			sql.setInt(1, bean.getIdCommento());
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
