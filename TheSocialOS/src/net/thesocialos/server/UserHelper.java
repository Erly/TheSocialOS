package net.thesocialos.server;


import java.util.Date;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.thesocialos.server.jdo.SearchJDO;
import net.thesocialos.server.model.Session;
import net.thesocialos.server.model.User;
import net.thesocialos.server.utils.BCrypt;
import net.thesocialos.server.utils.ChannelServer;
import net.thesocialos.shared.Chat;
import net.thesocialos.shared.LineChat;
import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.exceptions.UserExistsException;

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
	 * 
	 * @param jsid
	 * @param sid
	 * @param uid
	 * @param request
	 * @return
	 */
	public static Boolean checkIds(String jsid, String sid, String uid, HttpServletRequest request) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User user;
		
		try{
			String sid1 = (String) request.getSession().getId();
			if (sid1.equals(jsid)) {
				String uid1 =(String) request.getSession().getAttribute("uid");
				user = pm.getObjectById(User.class, uid1);
				if (user != null)
					return true;
			}
			if (sid == null || uid == null)
				return false;
			user = pm.getObjectById(User.class, uid);
			if (user == null || user.searchSession(sid) == null) 
				return false;
			else {
				request.getSession().setAttribute("uid", uid);
				return true;
			}
		} finally {
			pm.close();
		}
	}
	
	/**
	 * Verify if you have sessions id in server sessions
	 * @return
	 */
	public static User checkSession(String sessionID, HttpServletRequest request){
		
			String sid = (String) request.getSession().getId();
			if (sid.equals(sessionID)) {
				String uid = (String) request.getSession().getAttribute("uid");
				if (uid == null)
					return null;
				GWT.log(sid + " : " + uid);
				PersistenceManager pm = PMF.get().getPersistenceManager();
				try{
					User user = pm.getObjectById(User.class, uid);
					if (user != null)
						return user;
				} finally {
					pm.close();
				}
			}
			return null;
	}
	/**
	 * 
	 * @param sid ID of session
	 * @param uid Code of user
	 * @return The user
	 */
	public static User checkSession(String sid, String uid){
		
		if (sid == null || uid == null){
			return null;
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			User user = pm.getObjectById(User.class, uid);
			if (user == null)
				return null;
			Session session = new SearchJDO().searchSession(user, sid);
			if (session == null) 
				return null;
			return user;	
			} finally {
				pm.close();
		}
	}
	
	/**
	 * Check if the user exist in the database
	 * @param email
	 * @param password
	 * @param checkLoged
	 * @param request
	 * @return
	 */
	public static LoginResult login(String email, String password, boolean checkLoged,HttpServletRequest request) {
		 long Duration = 1000l * 60l * 60l * 24l * 30l; // Duration remembering login. 30 days in this case.
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE email == :userEmail");
		q.setUnique(true);
		
		try{
			User user = (User) q.execute(email);
			if (user != null)
				if (BCrypt.checkpw(password, user.getPassword())) { // Encrypt the entered password and compare it with the stored one
					HttpSession session = request.getSession();
					
					//user.setSessionID(session.getId());
					session.setAttribute("uid", user.getKey());
					if (checkLoged) {
						
						Date expires = new Date(System.currentTimeMillis() + Duration);
						user.getSessions().add(new Session(session.getId(), user, expires));
					}else{
						Duration = -1;
					}
					
					user.setLastActive(new Date());
					user.setChannelID(loginStarts(request.getSession(), user));
					User userDTO = User.toDTO(user);
					// Encapsulate the UserDTO and the encrypted key in a LoginResult object and send it to the client.
					
					
					LoginResult result = new LoginResult(userDTO, session.getId(), user.getKey(),Duration);
					
					
					return result;
				} else
					return null;
			else
				return null;
		} finally {
			pm.close();
		}
	}
	/**
	 * Return the object user
	 * @param email
	 * @param ofy Objectify instance
	 * @return User object
	 * @throws NotFoundException
	 */
	public static synchronized net.thesocialos.shared.model.User getUserWithEmail(String email,Objectify ofy) throws NotFoundException{
		return (net.thesocialos.shared.model.User) ofy.query(USER).filter("email", email);
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
	public static synchronized net.thesocialos.shared.model.User getUserfromSession(HttpSession httpSession){
		return (net.thesocialos.shared.model.User) httpSession.getAttribute(userN);
	}
	/**
	 * 
	 * @param httpSession
	 * @return
	 */
	public static synchronized net.thesocialos.shared.model.Session getSessionfromSession(HttpSession httpSession){
		return (net.thesocialos.shared.model.Session) httpSession.getAttribute(sessionN);
	}
	/**
	 * 
	 * @param sid
	 * @param ofy
	 * @return
	 * @throws NotFoundException
	 */
	public static synchronized net.thesocialos.shared.model.Session getSessionWithCookies(String sid, Objectify ofy) throws NotFoundException{
		return ofy.get(SESSION,sid);
		
	}
	/**
	 * 
	 * @param uid
	 * @param ofy
	 * @return User model
	 * @throws NotFoundException
	 */
	public static synchronized net.thesocialos.shared.model.User getUserWithCookies(String uid, Objectify ofy) throws NotFoundException{
		return ofy.get(USER,uid);
	}
	
	
	/**
	 * Check if the session is valid
	 * @param jid
	 * @param httpSession
	 * @return true if user is correct 
	 */
	public static synchronized boolean isSessionValid(String jid,HttpSession httpSession){
		return httpSession.getId().equals(jid);
	}
	
	public static synchronized User getLoggedUser(String[] ids, HttpServletRequest request) {
		User user;
			if ((user = UserHelper.checkSession(ids[0], ids[1])) != null){ // Verifying the cookies IDs
				request.getSession().setAttribute("uid", ids[1]);
				
			} else
				return null;
		user.setChannelID(loginStarts(request.getSession(), user));
		return user;
	}
	
	
	/**
	 * Register a user
	 * @param email
	 * @param password
	 * @param name
	 * @param lastName
	 * @param pm
	 * @throws UserExistsException
	 */
	public static void register(String email, String password, String name, String lastName,PersistenceManager pm) throws UserExistsException {
		Query q = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE email == :userEmail");
		q.setUnique(true);
		try{
			User user = (User) q.execute(email);
			if(user!=null){
				throw new UserExistsException("Email '" + email + "' already registered");
			} else {
				user = new User(email, BCrypt.hashpw(password, BCrypt.gensalt()), name, lastName); // Encrypt the password
				pm.makePersistent(user);	// Save the user in the datastore
			}
		} finally {
			pm.close();
		}
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
