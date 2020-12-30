package cineforum.model;

/**
 * Interfaccia che definisce il metodo per recuperare una singola riga dal DB in base 
 * all'unica primary key della tabella
 *
 * @param <T> tipo del bean su cui implementare l'interfaccia
 * @param <PK> tipo della primary key
 */
interface DAOInterfaceSinglePK<T, PK> extends DAOInterface<T>{
	T retrieveByKey(PK key);
}
