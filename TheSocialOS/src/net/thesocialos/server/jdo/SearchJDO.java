package net.thesocialos.server.jdo;


import net.thesocialos.server.model.Session;
import net.thesocialos.server.model.User;

public class SearchJDO {
/**
 * Search a one session in one user
 * @param user to search the session
 * @param attribute is the session identification
 * @return
 */
	public final Session searchSession(User user, String attribute){
		java.util.Iterator<Session> iterator = user.getSessions().iterator();
		Session session;
		while (iterator.hasNext()){
			session =  iterator.next();
			if (session.getSessionID().equals(attribute)){
				return session;
			}
		}
		return null;
		
	}
}
