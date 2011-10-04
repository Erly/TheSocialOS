package net.thesocialos.server;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpSession;

import net.thesocialos.client.service.UserService;
import net.thesocialos.server.utils.BCrypt;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserServiceImpl extends RemoteServiceServlet implements
		UserService {

	boolean localPM = false;
	
	public UserServiceImpl() {
		
	}

	@Override
	public ArrayList<UserSummaryDTO> getFriendSummaries(String sid) {
		ArrayList<UserSummaryDTO> userSummaries = new ArrayList<UserSummaryDTO>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		// Get the current User
		UserDTO user = getLoggedUser(sid);
		// Get the user friends
		
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
	public String login(String email, String password) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		Query q = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE email == :userEmail");
		q.setUnique(true);
		
		try{
			User user = (User) q.execute(email);
			if (user != null) {
				if(BCrypt.checkpw(password, user.getPassword())) {
					HttpSession session = getThreadLocalRequest().getSession();
					//session.setAttribute("userId", user.getId());
					//session.setAttribute("p", user)
					user.setLastActive(new Date());
					//return User.toDTO(user);
					return session.getId();
				} else {
					return null;
				}
			} else {
				return null;
			}
		} finally {
			pm.close();
		}
	}

	@Override
	public UserDTO getLoggedUser(String sid) {
		if(sid == null) {
			return null;
		}
		HttpSession session = getThreadLocalRequest().getSession();
		if(session.getId().equals(sid)) {
			/*Long id = Long.parseLong(((String) session.getAttribute("userId")).trim());
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Query q = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE id == :userId");
			q.setUnique(true);
			try {
				User user = (User) q.execute(id);
				//user.setLastActive(new Date());
				return User.toDTO(user);
			} finally {
				pm.close();
			}*/
		}
		return null;
	}
	
	@Override
	public void register(String email, String password) {
		System.out.println("0");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		System.out.println("1");
		
		Query q = pm.newQuery("SELECT FROM " + User.class.getName() + " WHERE email == :userEmail");
		q.setUnique(true);
		System.out.println("2");
		User user = (User) q.execute();
		if(user!=null){
			System.out.println("User!=null");
			//Throw new UserExistsException();
		} else {
			System.out.println("User==null");
			user = new User(email, BCrypt.hashpw(password, BCrypt.gensalt()));
			pm.makePersistent(user);
		}
	}

	@Override
	public void logout() {
		getThreadLocalRequest().getSession().invalidate();
		//Throw new NotLoggedInException("Logged out");
	}

}
