package net.thesocialos.client.service;

import java.util.List;

import net.thesocialos.shared.Chat;



import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChatServiceAsync {
	void examplePush( String text,AsyncCallback<List<Chat>> callback);
	
	void getText(AsyncCallback<List<Chat>> callback);
	
	void sendText(String text,AsyncCallback<Boolean> callback);
}
