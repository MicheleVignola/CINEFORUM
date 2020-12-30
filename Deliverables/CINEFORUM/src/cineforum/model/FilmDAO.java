package cineforum.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilmDAO implements DAOInterfaceSinglePK<Film, Integer>{

	/**
	 * costante public da usare per il metodo retrieveAll(int option, String value)
	 * come parametro "option"
	 */
	public static final int BY_TITLE = 0;
	public static final int BY_EQUALTITLE = 1;
	
	@Override
	public ArrayList<Film> retrieveAll() {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		ArrayList<Film> list = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("SELECT * FROM film");
			rs = sql.executeQuery();
			
			list = new ArrayList<Film>();
			
			while(rs.next()) {
				Film bean = new Film();
				bean.setCodiceFilm(rs.getInt("CodiceFilm"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setGenere(rs.getString("Genere"));
				bean.setDataUscita(rs.getString("DataUscita"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setDurata(rs.getShort("Durata"));
				bean.setImmagine(rs.getBytes("Immagine"));
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
	 *  		- BY_TITLE: recupera i film con titolo che risulta uguale, o contentente come sottostringa, 
	 *  					il titolo specificato dal parametro "value"
	 *  		- BY_EQUALTITLE: recupera i film con titolo uguale a quello specificato dal parametro "value"
	 *  
	 * @param value rappresenta il valore di un attributo, definito da 'option', da inserire nella query
	 */
	@Override
	public ArrayList<Film> retrieveAll(int option, String value) {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		ArrayList<Film> list = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			if(option == BY_TITLE) {
				sql = conn.prepareStatement("SELECT * FROM film WHERE Titolo LIKE ?");
				String title = value + "%";
				sql.setString(1, title);
			} else if(option == BY_EQUALTITLE) {
				sql = conn.prepareStatement("SELECT * FROM film WHERE Titolo = ?");
				sql.setString(1, value);
			} else {
				return retrieveAll();
			}
			
			rs = sql.executeQuery();
			
			list = new ArrayList<Film>();
			
			while(rs.next()) {
				Film bean = new Film();
				bean.setCodiceFilm(rs.getInt("CodiceFilm"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setGenere(rs.getString("Genere"));
				bean.setDataUscita(rs.getString("DataUscita"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setDurata(rs.getShort("Durata"));
				bean.setImmagine(rs.getBytes("Immagine"));
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
	public Film retrieveByKey(Integer key) {
		Connection conn = null;
		PreparedStatement sql = null;
		ResultSet rs = null;
		Film bean = null;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("SELECT * FROM film WHERE CodiceFilm = ?");
			sql.setInt(1, key);
			rs = sql.executeQuery();
			
			
			if(rs.next()) {
				bean = new Film();
				bean.setCodiceFilm(rs.getInt("CodiceFilm"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setGenere(rs.getString("Genere"));
				bean.setDataUscita(rs.getString("DataUscita"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setDurata(rs.getShort("Durata"));
				bean.setImmagine(rs.getBytes("Immagine"));
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
	public void save(Film bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("INSERT INTO film(Titolo, Genere, DataUscita, Descrizione, Durata, Immagine) VALUES (?, ?, ?, ?, ?, ?)");
			sql.setString(1, bean.getTitolo());
			sql.setString(2,  bean.getGenere());
			sql.setString(3, bean.getDataUscita());
			sql.setString(4, bean.getDescrizione());
			sql.setShort(5, bean.getDurata());
			sql.setBytes(6, bean.getImmagine());
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
	public void update(Film bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("UPDATE film SET Titolo = ?, Genere = ?, DataUscita = ?, Descrizione = ?, Durata = ?, Immagine = ? WHERE CodiceFilm = ?");
			sql.setString(1, bean.getTitolo());
			sql.setString(2,  bean.getGenere());
			sql.setString(3, bean.getDataUscita());
			sql.setString(4, bean.getDescrizione());
			sql.setShort(5, bean.getDurata());
			sql.setBytes(6, bean.getImmagine());
			sql.setInt(7, bean.getCodiceFilm());
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
	public void delete(Film bean) {
		Connection conn = null;
		PreparedStatement sql = null;
		int result = 0;
		
		try {
			conn = DBConnectionPool.getDBConnection();
			sql = conn.prepareStatement("DELETE FROM film WHERE CodiceFilm = ?");
			sql.setInt(1, bean.getCodiceFilm());
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
