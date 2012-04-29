package net.thesocialos.server;

import java.util.Date;

import javax.servlet.http.HttpSession;

import net.thesocialos.server.utils.ChannelServer;
import net.thesocialos.shared.LineChat;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class UserHelper extends RemoteServiceServlet {
	final static Class<User> USER = net.thesocialos.shared.model.User.class;
	final static Class<Session> SESSION = net.thesocialos.shared.model.Session.class;
	
	// final names;
	final static String SESSIONN = "session";
	final static String USERN = "user";
	final static String OBJECITIFY = "objetify";
	
	/**
	 * Create and add a new Session on one User
	 * 
	 * @param user
	 * @param httpSession
	 * @param duration
	 * @param ofy
	 * @return
	 */
	public static synchronized boolean addSessiontoUser(User user, Session session, long duration, Objectify ofy) {
		
		user.getSessions().add(ofy.put(session));
		return true;
	}
	
	/**
	 * Return a user
	 * 
	 * @param email
	 * @param ofy
	 * @return A user Class
	 * @throws NotFoundException
	 *             user has not found
	 */
	public static synchronized User authenticateUser(String email, Objectify ofy) throws NotFoundException {
		return ofy.get(User.class, email);
	}
	
	/**
	 * 
	 * @param sid
	 * @param ofy
	 * @return
	 * @throws NotFoundException
	 */
	public static synchronized Session getSessionWithCookies(String sid, Objectify ofy) throws NotFoundException {
		return ofy.get(SESSION, sid);
	}
	
	/**
	 * Get Session of HttpSession
	 * 
	 * @param httpSession
	 * @return Session Object
	 */
	public static synchronized Session getSesssionHttpSession(HttpSession httpSession) {
		return (Session) httpSession.getAttribute(SESSIONN);
	}
	
	/**
	 * Get User of Httpsession
	 * 
	 * @param httpSession
	 * @return User Object
	 */
	public static synchronized String getUserHttpSession(HttpSession httpSession) {
		return (String) httpSession.getAttribute(USERN);
	}
	
	/**
	 * Get the loged User
	 * 
	 * @param httpSession
	 * @param ofy
	 * @return
	 * @throws NotFoundException
	 */
	public static synchronized User getUserSession(HttpSession httpSession, Objectify ofy) throws NotFoundException {
		String email = (String) httpSession.getAttribute(USERN);
		return ofy.get(USER, email);
	}
	
	/**
	 * Return the object user
	 * 
	 * @param email
	 * @param ofy
	 *            Objectify instance
	 * @return User object
	 * @throws NotFoundException
	 */
	public static synchronized User getUserWithEmail(String email, Objectify ofy) throws NotFoundException {
		return ofy.get(USER, email);
	}
	
	/**
	 * 
	 * @param uid
	 * @param ofy
	 * @return User model
	 * @throws NotFoundException
	 */
	public static synchronized User getUserWithSession(Session session, Objectify ofy) throws NotFoundException {
		
		return ofy.get(User.class, session.getUser().getName());
		
	}
	
	/**
	 * @param session
	 *            of one user
	 * @param user
	 *            to get keyID
	 */
	private static String loginStarts(HttpSession session, User user) {
		Objectify ofy = ObjectifyService.begin();
		LineChat lineChat;
		String token = ChannelServer.createChannel(user.getEmail());
		try {
			lineChat = ofy.get(LineChat.class, user.getEmail());
			lineChat.setChannel(token);
			lineChat.setDate(new Date().getTime());
		} catch (NotFoundException e) {
			lineChat = new LineChat(user.getEmail(), token, new Date().getTime());
		}
		ofy.put(lineChat);
		session.setAttribute("channelID", user.getEmail());
		return token;
	}
	
	/**
	 * Guarda cambios hechos en el usuario tanto en la BBDD como en la session
	 * 
	 * @param user
	 * @param httpSession
	 * @param ofy
	 * @return
	 */
	public static synchronized boolean saveUser(User user, HttpSession httpSession, Objectify ofy) {
		if (!((User) httpSession.getAttribute(USERN)).getEmail().equalsIgnoreCase(user.getEmail())) return false;
		httpSession.setAttribute(USERN, user);
		ofy.put(user);
		return true;
	}
	
	/**
	 * Set a User String of the session
	 * 
	 * @param session
	 *            a user session
	 * @param httpSession
	 *            HttpSession
	 * @return
	 */
	public static synchronized boolean saveUsertohttpSession(Session session, String userEmail, HttpSession httpSession) {
		httpSession.setAttribute(USERN, userEmail);
		httpSession.setAttribute(SESSIONN, session);
		return true;
	}
	
}
