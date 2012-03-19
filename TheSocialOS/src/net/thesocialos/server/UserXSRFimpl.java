package net.thesocialos.server;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;


import net.thesocialos.client.service.UserServiceXSRF;

import net.thesocialos.server.utils.BCrypt;
import net.thesocialos.shared.Chat;
import net.thesocialos.shared.LineChat;
import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserSummaryDTO;
import net.thesocialos.shared.exceptions.UserExistsException;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class UserXSRFimpl extends XsrfProtectedServiceServlet implements UserServiceXSRF {

	@Override
	public void destroy(){
		
	}
	
	@Override
	public void init(){
		try {
			ObjectifyService.register(LineChat.class);
			ObjectifyService.register(Chat.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			super.init();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<UserSummaryDTO> getFriendSummaries(String sid) {
		ArrayList<UserSummaryDTO> userSummaries = new ArrayList<UserSummaryDTO>();
		
		// Convert friends to UserSummaryDTO and introduce them in an ArrayList
		
		// Return the ArrayList
		return userSummaries;
	}

	@Override
	public User getFriend(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User friend = pm.getObjectById(User.class, id);
		
		return User.toDTO(friend);
	}

	
	@Override
	public net.thesocialos.shared.model.User getLoggedUser(String[] ids) {
		Objectify ofy = ObjectifyService.begin();
		net.thesocialos.shared.model.User user;
		net.thesocialos.shared.model.Session session;
		HttpSession httpSession = perThreadRequest.get().getSession();
			if ((session = UserHelper.getSesssionHttpSession(httpSession)) != null
					&& (user = UserHelper.getUserHttpSession(httpSession)) != null){
				if (session.getSessionID().equalsIgnoreCase(ids[0])
						&& session.getUser().getName().equalsIgnoreCase(user.getEmail())){
					return User.toDTO(user);
				}
				
			}
		try{
			session = UserHelper.getSessionWithCookies(ids[0],ofy);
			user = UserHelper.getUserWithSession(session, ofy);
			
			UserHelper.saveUsertohttpSession(session, user,perThreadRequest.get().getSession());
			return User.toDTO(user);
		}catch (NotFoundException e) {
			return null;
		}catch (Exception e){
			return null;
		}
		
	}
	
	@Override
	public void register(User user) throws UserExistsException {
		Objectify ofy = ObjectifyService.begin();
		
		try{
			ofy.get(User.class,user.getEmail());
			throw new UserExistsException("Email '" + user.getEmail() + "' already registered");
		}catch (NotFoundException e) {
			user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));
			ofy.put(user);	// Save
		}
		//user = new User(email, BCrypt.hashpw(password, BCrypt.gensalt()), name, lastName); // Encrypt the password
		
		
	}

	@Override
	public void logout() {
		getThreadLocalRequest().getSession().invalidate();
		return;
	}
	
	
	@Override
	public LoginResult login(String email, String password, boolean keptloged) {
		long duration = 2592000000L;//1000l * 60l * 60l * 24l * 30l; // Duration remembering login. 30 days in this case.
		Objectify ofy = ObjectifyService.begin();
		User user;
		HttpSession httpSession;
		try{
			user = UserHelper.getUserWithEmail(email, ofy);
			
			httpSession = perThreadRequest.get().getSession();
			
		}catch (NotFoundException e){
			return null;
		}
		if (BCrypt.checkpw(password, user.getPassword())== false) { // Encrypt the entered password and compare it with the stored one
			return null;
		}
		Key<User> userKey = ObjectifyService.factory().getKey(user);
		Session session = new Session(httpSession.getId(), 
				new Date(System.currentTimeMillis() + duration),userKey);
		/*
		 * El usuario quiere seguir estando conectado
		 */
		if (keptloged) {
			UserHelper.addSessiontoUser(user, session, duration, ofy); //Add new session to user
		}else{
			duration = -1;
		}
		user.setLastTimeActive(new Date()); //Set last time to user is login
		UserHelper.saveUsertohttpSession(session, user, httpSession); //Store user and session
		ofy.put(user); //Save user
		return new LoginResult(User.toDTO(user), httpSession.getId(), duration);
		 //UserHelper.login(email, password, keptloged, getThreadLocalRequest());
		
	}
	@Override
	public void createServerSession() {
		// TODO Auto-generated method stub
		
	}

}
