package net.thesocialos.client.service;

import java.util.Map;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.User;

public interface UserServiceAsync extends ServiceAsync {
	
	void login(String email, String password, boolean keeploged, AsyncCallback<LoginResult> callback);
	
	void register(User user, AsyncCallback<Void> callback);
	
	void logout(AsyncCallback<Void> callback);
	
	void createServerSession(AsyncCallback<Void> callback);
	
	void getLoggedUser(String sid, AsyncCallback<User> callback);
	
	void getCloudAccounts(AsyncCallback<Map<Key<Account>, Account>> callback);
	
	void removeDeletedAccounts(AsyncCallback<Void> callback);
}
