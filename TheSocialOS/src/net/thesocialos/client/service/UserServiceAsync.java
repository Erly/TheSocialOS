package net.thesocialos.client.service;

import java.util.ArrayList;
import java.util.Map;

import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.ChannelApiEvents.ChApiContactNew;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Columns;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public interface UserServiceAsync extends ServiceAsync {
	
	void getChannel(AsyncCallback<String> callback);
	
	void getCloudAccounts(AsyncCallback<Map<Key<Account>, Account>> callback);
	
	void getDeckColumns(AsyncCallback<Map<Key<Columns>, Columns>> callback);
	
	void getLoggedUser(String sid, AsyncCallback<User> callback);
	
	void login(String email, String password, boolean keeploged, AsyncCallback<LoginResult> callback);
	
	void logout(AsyncCallback<Void> callback);
	
	void register(User user, AsyncCallback<Void> callback);
	
	void updateUser(User user, AsyncCallback<User> callback);
	
	void getAvatar(AsyncCallback<String> callback);
	
	void setDeckColumns(ArrayList<Columns> columns, AsyncCallback<Void> callback);
	
	void addDeckColumn(Columns column, AsyncCallback<Void> callback);
	
	void checkChannel(ChApiContactNew newContact, AsyncCallback<Void> callback);
	
	void setState(STATETYPE stateType, String customMsg, AsyncCallback<Void> callback);
}
