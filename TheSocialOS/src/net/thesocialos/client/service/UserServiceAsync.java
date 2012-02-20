package net.thesocialos.client.service;

import java.util.ArrayList;

import javax.jdo.PersistenceManager;

import com.google.gwt.user.client.rpc.AsyncCallback;

import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;

public interface UserServiceAsync {

	void getFriendSummaries(String sid, AsyncCallback<ArrayList<UserSummaryDTO>> callback);
	
	void getFriend(Long id, AsyncCallback<UserDTO> callback);
	
	void login(String email, String password, AsyncCallback<String> callback);
	
	void getLoggedUser(String sid, AsyncCallback<UserDTO> callback);
	
	void register(String email, String password, AsyncCallback<Void> callback);
	
	void logout(AsyncCallback<Void> callback);
}
