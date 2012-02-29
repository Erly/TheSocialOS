package net.thesocialos.server;

import java.util.ArrayList;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;


import net.thesocialos.client.service.UserServiceXSRF;
import net.thesocialos.server.model.User;
import net.thesocialos.shared.Chat;
import net.thesocialos.shared.LineChat;
import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;
import net.thesocialos.shared.exceptions.UserExistsException;

import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
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
	public UserDTO getFriend(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User friend = pm.getObjectById(User.class, id);
		UserDTO friendDTO = User.toDTO(friend);
		return friendDTO;
	}

	
	@Override
	public net.thesocialos.shared.model.User getLoggedUser(String[] ids) {
		Objectify ofy = ObjectifyService.begin();
		net.thesocialos.shared.model.User user;
		net.thesocialos.shared.model.Session session;
		
		try{
			
			user = UserHelper.getUserWithCookies(ids[0], ofy);
			session = UserHelper.getSessionWithCookies(ids[1],ofy);
			UserHelper.saveUsertohttpSession(session, user,perThreadRequest.get().getSession());
			return user.toDto();
		}catch (NotFoundException e) {
			return null;
		}
		/*User user = UserHelper.getLoggedUser(ids, getThreadLocalRequest());
		if (user!=null){
			
			return User.toDTO(user);
			
		}*/
		
	}
	
	@Override
	public void register(String email, String password, String name, String lastName) throws UserExistsException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		UserHelper.register(email, password, name, lastName, pm);
	}

	@Override
	public void logout() {
		getThreadLocalRequest().getSession().invalidate();
		return;
	}
	
	
	@Override
	public LoginResult login(String email, String password, boolean keptloged) {
		
		return UserHelper.login(email, password, keptloged, getThreadLocalRequest());
		
	}
	@Override
	public void createServerSession() {
		// TODO Auto-generated method stub
		
	}

}
