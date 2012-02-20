package net.thesocialos.client.service;

import java.util.ArrayList;

import javax.jdo.PersistenceManager;

import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {
	
	ArrayList<UserSummaryDTO> getFriendSummaries(String sid);
	
	UserDTO getFriend(Long id);
	
	String login(String email, String passwordm);
	
	UserDTO getLoggedUser(String sid);
	
	void register(String email, String password);
	
	void logout();
}
