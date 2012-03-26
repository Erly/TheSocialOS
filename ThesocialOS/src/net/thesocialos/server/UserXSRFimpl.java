package net.thesocialos.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;


import net.thesocialos.client.service.UserServiceXSRF;

import net.thesocialos.server.utils.BCrypt;
import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.exceptions.FriendNotFoundException;
import net.thesocialos.shared.exceptions.UserExistsException;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

@SuppressWarnings("serial")
public class UserXSRFimpl extends XsrfProtectedServiceServlet implements UserServiceXSRF {
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet#init()
	 * Cosas a tener en cuenta
	 * 1º Una vez hecho login el usuario tanto como la sesión se guardan en la sesión del servlet
	 * 2º Al actualizar los datos del usuario actualizar también los datos del usuario en la sesión del servlet
	 * 3º Extraer key  Ejemplo : ObjectifyService.factory().getKey(user);
	 */
	
	

	


	
	@Override
	public User getLoggedUser(String[] ids) {
		ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
		Objectify ofy = ObjectifyService.begin(opts);
		net.thesocialos.shared.model.User user;
		net.thesocialos.shared.model.Session session;
		HttpSession httpSession = perThreadRequest.get().getSession();
			if ((session = UserHelper.getSesssionHttpSession(httpSession)) != null
					&& (user = UserHelper.getUserHttpSession(httpSession)) != null){
				if (session.getSessionID().equalsIgnoreCase(ids[0])
						&& session.getUser().getName().equalsIgnoreCase(user.getEmail())){
					return User.toDTO(user.getEmail(),user.getAvatar(),user.getBackground(),user.getName(),
							user.getLastName(),user.getRole(),user.getBio());
				}
				
			}
		try{
			session = UserHelper.getSessionWithCookies(ids[0],ofy);
			user = UserHelper.getUserWithSession(session, ofy);
			
			UserHelper.saveUsertohttpSession(session, user,ofy,perThreadRequest.get().getSession());
			
			
			return User.toDTO(user.getEmail(),user.getAvatar(),user.getBackground(),user.getName(),
					user.getLastName(),user.getRole(),user.getBio());
		}catch (NotFoundException e) {
			return null;
		}catch (Exception e){
			return null;
		}
		
	}
	
	@Override
	public void register(User user) throws UserExistsException {
		Objectify ofy = UserHelper.getBBDD(perThreadRequest.get().getSession());
		
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
		long duration = 262045019291741L;//1000l * 60l * 60l * 24l * 30l; // Duration remembering login. 30 days in this case.
		ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
		Objectify ofy = ObjectifyService.begin(opts);
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
				System.currentTimeMillis() + duration,userKey);
		/*
		 * El usuario quiere seguir estando conectado
		 */
		if (keptloged) {
			UserHelper.addSessiontoUser(user, session, duration, ofy); //Add new session to user
		}else{
			duration = -1;
		}
		user.setLastTimeActive(System.currentTimeMillis()); //Set last time to user is login
		//Prueba
		User palomo = ofy.get(User.class,"juan@palomo.es");
		User pepito = ofy.get(User.class,"pepito@gmail.com");
		userKey = ObjectifyService.factory().getKey(palomo);
		user.addContacts(userKey);
		userKey = ObjectifyService.factory().getKey(pepito);
		user.addContacts(userKey);
		//Fin prueba
		UserHelper.saveUsertohttpSession(session, user,ofy, httpSession); //Store user and session
		ofy.put(user); //Save user
		return new LoginResult(User.toDTO(user.getEmail(),user.getAvatar(),user.getBackground(),
				user.getName(),user.getLastName(),user.getRole(),user.getBio()), httpSession.getId(),duration);
		 //UserHelper.login(email, password, keptloged, getThreadLocalRequest());
		
	}
	@Override
	public void createServerSession() {
		// TODO Auto-generated method stub
		
	}

	
	

}
