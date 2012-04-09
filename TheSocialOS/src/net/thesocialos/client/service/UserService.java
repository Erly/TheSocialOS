package net.thesocialos.client.service;

import java.util.Map;
import net.thesocialos.shared.LoginResult;
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
	
	LoginResult login(String email, String password, boolean keeploged);
	
	void register(User user) throws UserExistsException;
	
	void logout();
	
	void createServerSession();
	
	User getLoggedUser(String sid);
	
	Map<Key<Account>, Account> getCloudAccounts();
	
	void removeDeletedAccounts();
}
