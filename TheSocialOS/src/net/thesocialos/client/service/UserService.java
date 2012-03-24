package net.thesocialos.client.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


import net.thesocialos.server.UserHelper;
import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;
import net.thesocialos.shared.exceptions.UserExistsException;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.User;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("userService")
@XsrfProtect
public interface UserService extends RemoteService {
	
	ArrayList<UserSummaryDTO> getFriendSummaries(String sid);
	
	User getFriend(String id);
	
	LoginResult login(String email, String password, boolean keeploged);
	
	void register(User user) throws UserExistsException;
	
	void logout();
	
	void createServerSession();
	
	User getLoggedUser(String sid);
	
	Map<Key<Account>, Account> getCloudAccounts();
}