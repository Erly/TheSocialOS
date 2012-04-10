package net.thesocialos.client.service;

import java.util.List;
import java.util.Map;

import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public interface ContacsServiceAsync extends ServiceAsync {

	void getFriendsList(AsyncCallback <Map<Key<User>,User>> callback);
	
	void getFriendsSuggestionList(String text, AsyncCallback <List<User>> callback);
	
	void getFriend(String email, AsyncCallback<User> callback);
	
	void getUsers(AsyncCallback <Map<Key<User>,User>> callback);
}
