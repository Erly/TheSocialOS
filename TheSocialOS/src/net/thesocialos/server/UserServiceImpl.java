package net.thesocialos.server;

import java.util.ArrayList;
import java.util.Date;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpSession;

import net.thesocialos.client.service.UserService;
import net.thesocialos.server.jdo.SearchJDO;
import net.thesocialos.server.model.Session;
import net.thesocialos.server.model.User;
import net.thesocialos.server.utils.BCrypt;
import net.thesocialos.server.utils.ChannelServer;
import net.thesocialos.shared.Chat;
import net.thesocialos.shared.LineChat;
import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;
import net.thesocialos.shared.exceptions.UserExistsException;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;


@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

	public UserServiceImpl() {
		
	}
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
	public LoginResult login(String email, String password, boolean checkLoged) {
	
		return UserHelper.login(email, password, checkLoged, getThreadLocalRequest());
	}

	@Override
	public UserDTO getLoggedUser(String[] ids) {
		User user = UserHelper.getLoggedUser(ids, getThreadLocalRequest());
		if (user!=null){
			
			return User.toDTO(user);
			
		}
		return null;
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
	public Boolean checkIds(String jsid, String sid, String uid) {
		// TODO Auto-generated method stub
		return UserHelper.checkIds(jsid, sid, uid, getThreadLocalRequest());
	}
	


	
}