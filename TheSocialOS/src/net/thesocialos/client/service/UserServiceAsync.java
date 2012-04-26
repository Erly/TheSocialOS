package net.thesocialos.client.service;

import java.util.ArrayList;
import java.util.Map;

import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Columns;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync extends ServiceAsync {
	
	void createServerSession(AsyncCallback<Void> callback);
	
	void getCloudAccounts(AsyncCallback<Map<Key<Account>, Account>> callback);
	
	void getDeckColumns(AsyncCallback<Map<Key<Columns>, Columns>> callback);
	
	void getLoggedUser(String sid, AsyncCallback<User> callback);
	
	void login(String email, String password, boolean keeploged, AsyncCallback<LoginResult> callback);
	
	void logout(AsyncCallback<Void> callback);
	
	void register(User user, AsyncCallback<Void> callback);
	
	void setDeckColumns(ArrayList<Columns> columns, AsyncCallback<Void> callback);
}
