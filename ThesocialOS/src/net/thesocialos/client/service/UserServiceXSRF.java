package net.thesocialos.client.service;

import java.util.List;
import java.util.Map;


import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.exceptions.FriendNotFoundException;
import net.thesocialos.shared.exceptions.UserExistsException;
import net.thesocialos.shared.model.User;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("RPCXSRF")

@XsrfProtect
public interface UserServiceXSRF extends RemoteService {
	
	
	
	LoginResult login(String email, String password,boolean keptloged);
	
	void register(User user) throws UserExistsException;
	
	void logout();
	
	void createServerSession();
	
	User getLoggedUser(String[] ids);
	
	

	
}
