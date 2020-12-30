package cineforum.model;

/**
 * Interfaccia che definisce il metodo per recuperare una singola riga dal DB in base 
 * alle due primary key della tabella
 *
 * @param <T> tipo del bean su cui implementare l'interfaccia
 * @param <PK1> tipo della prima primary key
 * @param <PK2> tipo della seconda primary key
 */
interface DAOInterfaceDoublePK<T, PK1, PK2> extends DAOInterface<T>{
	T retrieveByKey(PK1 key1, PK2 key2);
}
