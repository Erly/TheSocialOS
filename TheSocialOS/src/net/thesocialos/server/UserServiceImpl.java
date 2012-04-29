package net.thesocialos.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.thesocialos.client.service.UserService;
import net.thesocialos.server.utils.BCrypt;
import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatRecvMessage;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserConnected;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserDisconnect;
import net.thesocialos.shared.ChannelApiEvents.ChApiContactNew;
import net.thesocialos.shared.ChannelApiEvents.ChApiPetitionNew;
import net.thesocialos.shared.exceptions.UserExistsException;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Columns;
import net.thesocialos.shared.model.Google;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.User;

import com.google.appengine.api.channel.ChannelFailureException;
import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyOpts;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class UserServiceImpl extends XsrfProtectedServiceServlet implements UserService {
	
	@Override
	public String getChannel() {
		try {
			Objectify ofy = ObjectifyService.begin();
			User user = UserHelper.getUserSession(perThreadRequest.get().getSession(), ofy);
			user.setTokenChannel(ChannelApiHelper.createChannel(perThreadRequest.get().getSession()));
			ofy.put(user);
			return user.getTokenChannel();
		} catch (ChannelFailureException e) {
			e.printStackTrace();
			
		}
		return null;
		
	}
	
	private String getOldChannel() {
		Objectify ofy = ObjectifyService.begin();
		User user = UserHelper.getUserSession(perThreadRequest.get().getSession(), ofy);
		return user.getTokenChannel();
	}
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public Map<Key<Account>, Account> getCloudAccounts() {
		Objectify ofy = ObjectifyService.begin();
		removeDeletedAccounts(ofy);
		User user = UserHelper
				.getUserWithEmail(UserHelper.getUserHttpSession(perThreadRequest.get().getSession()), ofy);
		List<Key<? extends Account>> accountsKeys = user.getAccounts();
		Map<Key<Account>, Account> accounts = ofy.get(accountsKeys);
		if (refreshAccountTokens(accounts, ofy)) return ofy.get(accountsKeys);
		return accounts;
	}
	
	@Override
	public Map<Key<Columns>, Columns> getDeckColumns() {
		Objectify ofy = ObjectifyService.begin();
		removeDeletedColumns(ofy);
		User user = UserHelper
				.getUserWithEmail(UserHelper.getUserHttpSession(perThreadRequest.get().getSession()), ofy);
		List<Key<Columns>> columns = user.getColumns();
		if (null == columns || columns.isEmpty()) populateDefaultColumns(ofy, user);
		else if (user.getEmail().equalsIgnoreCase("endika2@gmail.com")) {
			Map<Key<Columns>, Columns> columnas = ofy.get(columns);
			Iterator<Columns> it = columnas.values().iterator();
			boolean metrobilbao = false;
			while (it.hasNext()) {
				Columns c = it.next();
				if (c.getType() == Columns.TYPE.SEARCH && c.getValue().equalsIgnoreCase("metrobilbao")) {
					metrobilbao = true;
					break;
				}
			}
			if (!metrobilbao) {
				user.addColumn(ofy.put(new Columns(Columns.TYPE.SEARCH, "metrobilbao")));
				ofy.put(user);
			}
		}
		return ofy.get(user.getColumns());
	}
	
	@Override
	public User getLoggedUser(String sid) {
		ObjectifyOpts opts = new ObjectifyOpts().setSessionCache(true);
		Objectify ofy = ObjectifyService.begin(opts);
		
		User user;
		Session session;
		HttpSession httpSession = perThreadRequest.get().getSession();
		if ((session = UserHelper.getSesssionHttpSession(httpSession)) != null
				&& (user = UserHelper.getUserWithEmail(UserHelper.getUserHttpSession(httpSession), ofy)) != null)
			if (session.getSessionID().equalsIgnoreCase(sid)
					&& session.getUser().getName().equalsIgnoreCase(user.getEmail()))
				User.toDTO(user.getEmail(), user.getAvatar(), user.getBackground(), user.getName(), user.getLastName(),
						user.getRole(), user.getTokenChannel());
		try {
			session = UserHelper.getSessionWithCookies(sid, ofy);
			user = UserHelper.getUserWithSession(session, ofy);
			user.setLastTimeActive(new Date());
			
			UserHelper.saveUsertohttpSession(session, user.getEmail(), httpSession);
			ofy.put(user);
			return User.toDTO(user.getEmail(), user.getAvatar(), user.getBackground(), user.getName(),
					user.getLastName(), user.getRole(), user.getTokenChannel());
		} catch (NotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public LoginResult login(String email, String password, boolean keeploged) {
		// Duration remembering login. 30 days in this case.
		long duration = 2592000000L;// 1000l * 60l * 60l * 24l * 30l;
		
		Objectify ofy = ObjectifyService.begin();
		User user;
		HttpSession httpSession = perThreadRequest.get().getSession();
		
		try {
			user = UserHelper.getUserWithEmail(email, ofy);
		} catch (NotFoundException e) {
			return null;
		}
		
		if (!BCrypt.checkpw(password, user.getPassword())) return null;
		
		Key<User> userKey = ObjectifyService.factory().getKey(user);
		Session session = new Session(httpSession.getId(), new Date(System.currentTimeMillis() + duration), userKey);
		
		if (keeploged)
		// Add new session to user
		UserHelper.addSessiontoUser(user, session, duration, ofy);
		else
			duration = -1;
		
		user.setLastTimeActive(new Date()); // Set last time to user is login
		// Store user and session
		UserHelper.saveUsertohttpSession(session, user.getEmail(), httpSession);
		
		ofy.put(user); // Save user
		return new LoginResult(User.toDTO(user.getEmail(), user.getAvatar(), user.getBackground(), user.getName(),
				user.getLastName(), user.getRole(), user.getTokenChannel()), httpSession.getId(), duration);
	}
	
	@Override
	public void logout() {
		getThreadLocalRequest().getSession().invalidate();
		return;
	}
	
	private void populateDefaultColumns(Objectify ofy, User user) {
		user.setColumns(new ArrayList<Key<Columns>>());
		user.addColumn(ofy.put(new Columns(Columns.TYPE.TIMELINE, Columns.HOME)));
		user.addColumn(ofy.put(new Columns(Columns.TYPE.TIMELINE, Columns.USER)));
		if (user.getEmail().equalsIgnoreCase("endika2@gmail.com"))
			user.addColumn(ofy.put(new Columns(Columns.TYPE.SEARCH, "metrobilbao")));
		ofy.put(user);
	}
	
	private boolean refreshAccountTokens(Map<Key<Account>, Account> accounts, Objectify ofy) {
		boolean changed = false;
		Iterator<Account> it = accounts.values().iterator();
		while (it.hasNext()) {
			Account ac = it.next();
			if (ac instanceof Google) {
				Google gac = (Google) ac;
				if (null == gac.getExpireDate() || gac.getExpireDate().before(new Date())) {
					changed = true;
					ofy.put(RefreshTokens.refreshGoogle(gac));
				}
			}
		}
		return changed;
	}
	
	@Override
	public void register(User user) throws UserExistsException {
		Objectify ofy = ObjectifyService.begin();
		
		try {
			ofy.get(User.class, user.getEmail());
			throw new UserExistsException("Email '" + user.getEmail() + "' already registered");
		} catch (NotFoundException e) {
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			ofy.put(user); // Save
		}
		// user = new User(email, BCrypt.hashpw(password, BCrypt.gensalt()),
		// name, lastName); // Encrypt the password
	}
	
	public void removeDeletedAccounts(Objectify ofy) {
		HttpSession httpSession = perThreadRequest.get().getSession();
		User user = UserHelper.getUserWithEmail(UserHelper.getUserHttpSession(httpSession), ofy);
		List<Key<? extends Account>> accountsKeys = user.getAccounts();
		List<Key<? extends Account>> newAccountsKeys = new ArrayList<Key<? extends Account>>();
		Iterator<Key<? extends Account>> it = accountsKeys.iterator();
		while (it.hasNext()) {
			Key<? extends Account> accountKey = it.next();
			try {
				Account ac = ofy.get(accountKey);
				if (null != ac) newAccountsKeys.add(accountKey);
			} catch (NotFoundException ex) {
				ex.printStackTrace();
				System.out.println("Key not found proceeding to remove it from the user object");
			}
		}
		user.overwriteAccountsList(newAccountsKeys);
		ofy.put(user);
	}
	
	private void removeDeletedColumns(Objectify ofy) {
		HttpSession httpSession = perThreadRequest.get().getSession();
		User user = UserHelper.getUserWithEmail(UserHelper.getUserHttpSession(httpSession), ofy);
		List<Key<Columns>> columnsKeys = user.getColumns();
		List<Key<Columns>> newColumnsKeys = new ArrayList<Key<Columns>>();
		Iterator<Key<Columns>> it = columnsKeys.iterator();
		while (it.hasNext()) {
			Key<Columns> columnKey = it.next();
			try {
				Columns col = ofy.get(columnKey);
				if (null != col) newColumnsKeys.add(columnKey);
			} catch (NotFoundException ex) {
				ex.printStackTrace();
				System.out.println("Key not found proceeding to remove it from the user object");
			}
		}
		user.overwriteColumnsList(newColumnsKeys);
		ofy.put(user);
	}
	
	private void removeUserColumns(User user) {
		Objectify ofy = ObjectifyService.begin();
		ofy.delete(user.getColumns());
	}
	
	@Override
	public void setDeckColumns(ArrayList<Columns> columns) {
		Objectify ofy = ObjectifyService.begin();
		User user = UserHelper
				.getUserWithEmail(UserHelper.getUserHttpSession(perThreadRequest.get().getSession()), ofy);
		Map<Key<Columns>, Columns> mColumns = ofy.put(columns);
		removeUserColumns(user);
		user.setColumns(new ArrayList<Key<Columns>>(mColumns.keySet()));
	}
	
	@Override
	public void checkChannel(ChApiContactNew newContact) {
		
		ChannelApiHelper.sendMessage("unai@thesocialos.net",
				ChannelApiHelper.encodeMessage(new ChApiContactNew("perico@gmail.com")));
		ChannelApiHelper.sendMessage("unai@thesocialos.net",
				ChannelApiHelper.encodeMessage(new ChApiChatRecvMessage(null, "palomo@gmail.com", "hola")));
		ChannelApiHelper.sendMessage("unai@thesocialos.net",
				ChannelApiHelper.encodeMessage(new ChApiChatUserChngState(STATETYPE.ONLINE, null)));
		ChannelApiHelper.sendMessage("unai@thesocialos.net",
				ChannelApiHelper.encodeMessage(new ChApiChatUserConnected("palomo@gmail.com")));
		ChannelApiHelper.sendMessage("unai@thesocialos.net",
				ChannelApiHelper.encodeMessage(new ChApiChatUserDisconnect("palomo@gmail.com")));
		ChannelApiHelper.sendMessage("unai@thesocialos.net",
				ChannelApiHelper.encodeMessage(new ChApiPetitionNew("lolita5@hotmail.com")));
		
	}
}
