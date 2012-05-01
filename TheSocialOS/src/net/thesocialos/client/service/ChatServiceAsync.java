package net.thesocialos.client.service;

import java.util.List;

import net.thesocialos.shared.Chat;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public interface ChatServiceAsync extends ServiceAsync {
	void examplePush(String text, AsyncCallback<List<Chat>> callback);
	
	void getText(AsyncCallback<List<Chat>> callback);
	
	void sendText(Key<User> contactUser, String message, AsyncCallback<Void> callback);
	
}
