package cineforum.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RichiestaFilmDAO implements DAOInterfaceSinglePK<RichiestaFilm, Integer>{

	/**
	 * costante public da usare per il metodo retrieveAll(int option, String value)
	 * come parametro "option"
	 */
	public static final int BY_UTENTE = 0;
	
	@Override
	public ArrayList<RichiestaFilm> retrieveAll() {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		ArrayList<RichiestaFilm> list = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("SELECT * FROM richiesta_film");
			rs = sql.executeQuery();
			
			list = new ArrayList<RichiestaFilm>();
			
			while(rs.next()) {
				RichiestaFilm bean = new RichiestaFilm();
				bean.setIdRichiesta(rs.getInt("Id_richiesta"));
				bean.setCommento(rs.getString("Commento"));
				bean.setEsito(rs.getString("Esito"));
				bean.setUsername(rs.getString("Username"));
				bean.setTitolo(rs.getString("Titolo"));
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
	 *  		- BY_UTENTE: recupera le richieste effettuate da un utente con Username specificato dal parametro value
	 *  
	 * @param value rappresenta il valore di un attributo, definito da 'option', da inserire nella query
	 */
	@Override
	public ArrayList<RichiestaFilm> retrieveAll(int option, String value) {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		ArrayList<RichiestaFilm> list = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			if(option == BY_UTENTE) {
				sql = conn.prepareStatement("SELECT * FROM richiesta_film WHERE Username = ?");
				sql.setString(1, value);
			} else {
				return retrieveAll();
			}
			
			rs = sql.executeQuery();
			
			list = new ArrayList<RichiestaFilm>();
			
			while(rs.next()) {
				RichiestaFilm bean = new RichiestaFilm();
				bean.setIdRichiesta(rs.getInt("Id_richiesta"));
				bean.setCommento(rs.getString("Commento"));
				bean.setEsito(rs.getString("Esito"));
				bean.setUsername(rs.getString("Username"));
				bean.setTitolo(rs.getString("Titolo"));
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
	public RichiestaFilm retrieveByKey(Integer key) {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		RichiestaFilm bean = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("SELECT * FROM richiesta_film WHERE Id_richiesta = ?");
			sql.setInt(1, key);
			rs = sql.executeQuery();
		
			
			if(rs.next()) {
				bean = new RichiestaFilm();
				bean.setIdRichiesta(rs.getInt("Id_richiesta"));
				bean.setCommento(rs.getString("Commento"));
				bean.setEsito(rs.getString("Esito"));
				bean.setUsername(rs.getString("Username"));
				bean.setTitolo(rs.getString("Titolo"));
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
	public void save(RichiestaFilm bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("INSERT INTO richiesta_film(Commento, Esito, Username, Titolo) VALUES (?, ?, ?, ?)");
			sql.setString(1, bean.getCommento());
			sql.setString(2, bean.getEsito());
			sql.setString(3, bean.getUsername());
			sql.setString(4, bean.getTitolo());
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
	public void update(RichiestaFilm bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("UPDATE richiesta_film SET Commento = ?, Esito = ?, Username = ?, Titolo = ? WHERE Id_richiesta = ?");
			sql.setString(1, bean.getCommento());
			sql.setString(2, bean.getEsito());
			sql.setString(3, bean.getUsername());
			sql.setString(4, bean.getTitolo());
			sql.setInt(5, bean.getIdRichiesta());
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
	public void delete(RichiestaFilm bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("DELETE FROM richiesta_film WHERE Id_richiesta = ?");
			sql.setInt(1, bean.getIdRichiesta());
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
