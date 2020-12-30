package cineforum.model;

public class Film {
	private int codiceFilm;
	private String titolo;
	private String genere;
	private String dataUscita;
	private String descrizione;
	private short durata;
	private byte[] immagine;
	
	public Film() {

	}

	public int getCodiceFilm() {
		return codiceFilm;
	}

	public void setCodiceFilm(int codiceFilm) {
		this.codiceFilm = codiceFilm;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}
	
	public String getDataUscita() {
		return dataUscita;
	}

	public void setDataUscita(String dataUscita) {
		this.dataUscita = dataUscita;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public short getDurata() {
		return durata;
	}

	public void setDurata(short durata) {
		this.durata = durata;
	}

	public byte[] getImmagine() {
		return immagine;
	}

	public void setImmagine(byte[] immagine) {
		this.immagine = immagine;
	}
	
	
}
