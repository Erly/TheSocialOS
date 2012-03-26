package net.thesocialos.server;


import java.util.Date;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import net.thesocialos.server.utils.BCrypt;
import net.thesocialos.server.utils.ChannelServer;
import net.thesocialos.shared.Chat;
import net.thesocialos.shared.LineChat;
import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.exceptions.UserExistsException;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.User;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;


@SuppressWarnings("serial")
public class UserHelper extends RemoteServiceServlet{
final static Class<net.thesocialos.shared.model.User> USER = net.thesocialos.shared.model.User.class;
final static Class<net.thesocialos.shared.model.Session> SESSION = net.thesocialos.shared.model.Session.class;


//final names;
final static String sessionN = "session";
final static String userN = "user";



	
	
	
	/**
	 * Return the object user
	 * @param email
	 * @param ofy Objectify instance
	 * @return User object
	 * @throws NotFoundException
	 */
	public static synchronized User getUserWithEmail(String email,Objectify ofy) throws NotFoundException{
		return ofy.get(USER, email);
		 
	}
	
	/**
	 * Set a User object in session
	 * @param session a user session
	 * @param httpSession HttpSession
	 * @return
	 */
	public static synchronized boolean saveUsertohttpSession(net.thesocialos.shared.model.Session session,net.thesocialos.shared.model.User user, HttpSession httpSession){
		httpSession.setAttribute(userN, user);
		httpSession.setAttribute(sessionN, session);
		return true;
	}
	/**
	 * 
	 * @param httpSession
	 * @return
	 */
	public static synchronized User getUserfromSession(HttpSession httpSession){
		return (User) httpSession.getAttribute(userN);
	}
	/**
	 * 
	 * @param httpSession
	 * @return
	 */
	public static synchronized Session getSessionfromSession(HttpSession httpSession){
		return (Session) httpSession.getAttribute(sessionN);
	}
	/**
	 * 
	 * @param sid
	 * @param ofy
	 * @return
	 * @throws NotFoundException
	 */
	public static synchronized Session getSessionWithCookies(String sid, Objectify ofy) throws NotFoundException{
		return ofy.get(SESSION,sid);	
	}
	/**
	 * 
	 * @param uid
	 * @param ofy
	 * @return User model
	 * @throws NotFoundException
	 */
	public static synchronized User getUserWithSession(Session session, Objectify ofy) throws NotFoundException{
		System.out.println(session.getUser().getName());
		return ofy.get(User.class,session.getUser().getName());
	}
	/**
	 * Get User of Httpsession
	 * @param httpSession
	 * @return User Object
	 */
	public static synchronized User getUserHttpSession(HttpSession httpSession){
		return (User) httpSession.getAttribute(userN);
	}
	/**
	 * Get Session of HttpSession
	 * @param httpSession
	 * @return Session Object
	 */
	public static synchronized Session getSesssionHttpSession(HttpSession httpSession){
		return (Session) httpSession.getAttribute(sessionN);
	}
	/**
	 * Return a user
	 * @param email
	 * @param ofy
	 * @return A user Class
	 * @throws NotFoundException user has not found
	 */
	public static synchronized User authenticateUser (String email, Objectify ofy) throws NotFoundException{
		return ofy.get(User.class,email);
	}
	/**
	 * Create and add a new Session on one User
	 * @param user
	 * @param httpSession
	 * @param duration
	 * @param ofy
	 * @return
	 */
	public static synchronized boolean addSessiontoUser (User user, Session session,
			long duration,Objectify ofy){
		
		user.getSessions().add(ofy.put(session));
		return true;
	}
	
	
	
	/**
	 * @param session of one user
	 * @param user to get keyID
	 */
	private static String loginStarts(HttpSession session,User user) {
		Objectify ofy = ObjectifyService.begin();
		LineChat lineChat;
		String token = 	ChannelServer.createChannel(user.getEmail());
			try {
				lineChat = ofy.get(LineChat.class,user.getEmail());
				lineChat.setChannel(token);
				lineChat.setDate(new Date().getTime());
			} catch (NotFoundException e) {
				lineChat = new LineChat(user.getEmail(), token, new Date().getTime());
			
			}
			ofy.put(lineChat);		
	       session.setAttribute("channelID", user.getEmail());
	       return token;
	        
	}
	

}
