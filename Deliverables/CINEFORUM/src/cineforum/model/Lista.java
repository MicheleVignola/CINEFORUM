package cineforum.model;

public class Lista {
	private int codiceFilm;
	private String username;
	private short voto;
	private String categoria;
	
	public Lista() {

	}

	public int getCodiceFilm() {
		return codiceFilm;
	}

	public void setCodiceFilm(int codiceFilm) {
		this.codiceFilm = codiceFilm;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public short getVoto() {
		return voto;
	}

	public void setVoto(short voto) {
		this.voto = voto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
}
