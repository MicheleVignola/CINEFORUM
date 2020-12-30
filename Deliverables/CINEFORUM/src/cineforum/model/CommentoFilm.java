package cineforum.model;

public class CommentoFilm {
	private int idCommento;
	private String commento;
	private String orario;
	private String username;
	private int codiceFilm;
	
	public CommentoFilm() {

	}

	public int getIdCommento() {
		return idCommento;
	}

	public void setIdCommento(int idCommento) {
		this.idCommento = idCommento;
	}

	public String getCommento() {
		return commento;
	}

	public void setCommento(String commento) {
		this.commento = commento;
	}

	public String getOrario() {
		return orario;
	}

	public void setOrario(String orario) {
		this.orario = orario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCodiceFilm() {
		return codiceFilm;
	}

	public void setCodiceFilm(int codiceFilm) {
		this.codiceFilm = codiceFilm;
	}
	
	
}
