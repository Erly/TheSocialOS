package net.thesocialos.client.service;

import java.util.ArrayList;


import net.thesocialos.server.UserHelper;
import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;
import net.thesocialos.shared.exceptions.UserExistsException;
import net.thesocialos.shared.model.User;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;

@RemoteServiceRelativePath("RPCXSRF")

@XsrfProtect
public interface UserServiceXSRF extends RemoteService {
	
	ArrayList<UserSummaryDTO> getFriendSummaries(String sid);
	
	UserDTO getFriend(Long id);
	
	LoginResult login(String email, String password,boolean keptloged);
	
	
	
	void register(String email, String password, String name, String lastName) throws UserExistsException;
	
	void logout();
	
	void createServerSession();
	
	User getLoggedUser(String[] ids);
	
	

	
}
