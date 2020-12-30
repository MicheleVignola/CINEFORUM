package cineforum.model;

/**
 * Interfaccia che definisce i metodi in comune a tutte le implementazioni DAO per i JavaBeans
 *
 * @param <T> tipo del bean su cui implementare l'interfaccia
 */
interface DAOInterface<T> {
	public java.util.ArrayList<T> retrieveAll();
	public java.util.ArrayList<T> retrieveAll(int option, String value);
	public void save(T bean);
	public void update(T bean);
	public void delete(T bean);
}