package net.thesocialos.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.UserSummaryDTO;
import net.thesocialos.shared.model.User;

public interface UserServiceXSRFAsync extends ServiceAsync{

	void getFriendSummaries(String sid, AsyncCallback<ArrayList<UserSummaryDTO>> callback);
	
	void getFriend(Long id, AsyncCallback<UserDTO> callback);
	
	void login(String email, String password,boolean keptloged, AsyncCallback<LoginResult> callback);
	
	
	
	void register(String email, String password, String name, String lastName, AsyncCallback<Void> callback);
	
	void logout(AsyncCallback<Void> callback);
	
	void createServerSession(AsyncCallback<Void> callback);
	
	void getLoggedUser(String[] ids, AsyncCallback<User> callback);

	
	
	
	
}
