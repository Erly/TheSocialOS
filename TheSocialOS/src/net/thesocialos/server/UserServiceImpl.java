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
import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;
import net.thesocialos.shared.exceptions.UserExistsException;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

	public UserServiceImpl() {
		
	}
	@Override
	public void destroy(){
		
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
	
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE email == :userEmail");
		q.setUnique(true);
		
		try{
			User user = (User) q.execute(email);
			if (user != null)
				if (BCrypt.checkpw(password, user.getPassword())) { // Encrypt the entered password and compare it with the stored one
					HttpSession session = getThreadLocalRequest().getSession();
					//user.setSessionID(session.getId());
					session.setAttribute("uid", user.getKey());
					if (checkLoged) {
						final long DURATION = 1000l * 60l * 60l * 24l * 30l; // Duration remembering login. 30 days in this case.
						Date expires = new Date(System.currentTimeMillis() + DURATION);
						user.getSessions().add(new Session(session.getId(), user, expires));
					}
					//pm.makePersistent(user);//Save the session into the datastore
					user.setLastActive(new Date());
					UserDTO userDTO = User.toDTO(user);
					// Encapsulate the UserDTO and the encrypted key in a LoginResult object and send it to the client.
					LoginResult result = new LoginResult(userDTO, session.getId(), user.getKey());
					return result;
				} else
					return null;
			else
				return null;
		} finally {
			pm.close();
		}
	}

	@Override
	public UserDTO getLoggedUser(String[] ids) {
		User user;
		if ((user = checkSession(ids[0])) == null) // Verifying the session IDs
			if ((user = checkSession(ids[1], ids[2])) != null){ // Verifying the cookies IDs
				getThreadLocalRequest().getSession().setAttribute("uid", ids[2]);
			} else
				return null;
		return User.toDTO(user);
	}
	
	@Override
	public void register(String email, String password, String name, String lastName) throws UserExistsException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE email == :userEmail");
		q.setUnique(true);
		try{
			User user = (User) q.execute(email);
			if(user!=null){
				System.out.println("User!=null");
				throw new UserExistsException("Email '" + email + "' already registered");
			} else {
				System.out.println("User==null");
				user = new User(email, BCrypt.hashpw(password, BCrypt.gensalt()), name, lastName); // Encrypt the password
				pm.makePersistent(user);	// Save the user in the datastore
			}
		} finally {
			pm.close();
		}
	}

	@Override
	public void logout() {
		getThreadLocalRequest().getSession().invalidate();
		return;
	}
	


	public Boolean checkIds(String jsid, String sid, String uid) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		User user;
		
		try{
			String sid1 = (String) getThreadLocalRequest().getSession().getId();
			if (sid1.equals(jsid)) {
				String uid1 =(String) getThreadLocalRequest().getSession().getAttribute("uid");
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
				getThreadLocalRequest().getSession().setAttribute("uid", uid);
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
	protected User checkSession(String sessionID){

			String sid = (String) getThreadLocalRequest().getSession().getId();
			if (sid.equals(sessionID)) {
				String uid = (String) getThreadLocalRequest().getSession().getAttribute("uid");
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
	protected User checkSession(String sid, String uid){
		
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
}