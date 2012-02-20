package net.thesocialos.server.jdo;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.thesocialos.server.PMF;

public final class DeleteJDO {
	public static <T> boolean deleteQuery(Class<T> object,String arg0, String attribute){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(object,arg0);
		q.deletePersistentAll(attribute);
		return false;
		
	}
}
