package net.thesocialos.client.service;

import java.util.ArrayList;

import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;
import net.thesocialos.shared.exceptions.UserExistsException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {
	
	ArrayList<UserSummaryDTO> getFriendSummaries(String sid);
	
	UserDTO getFriend(Long id);
	
	LoginResult login(String email, String password,boolean keptloged);
	
	UserDTO getLoggedUser(String[] ids);
	
	void register(String email, String password, String name, String lastName) throws UserExistsException;
	
	void logout();

	Boolean checkIds(String jsid, String sid, String uid);
}
