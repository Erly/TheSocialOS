package net.thesocialos.client.service;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;
import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.model.User;

public interface UserServiceXSRFAsync extends ServiceAsync{

	
	
	void login(String email, String password,boolean keptloged, AsyncCallback<LoginResult> callback);
	
	
	
	void register(User user, AsyncCallback<Void> callback);
	
	void logout(AsyncCallback<Void> callback);
	
	void createServerSession(AsyncCallback<Void> callback);
	
	void getLoggedUser(String[] ids, AsyncCallback<User> callback);

	
	
	
	
}
