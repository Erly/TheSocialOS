package net.thesocialos.client.service;

import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public interface ChatServiceAsync extends ServiceAsync {
	
	void sendText(Key<User> contactUser, String message, AsyncCallback<Long> callback);
	
}
