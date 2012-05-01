package net.thesocialos.client.service;

import java.util.List;

import net.thesocialos.shared.Chat;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("chatService")
@XsrfProtect
public interface ChatService extends RemoteService {
	List<Chat> examplePush(String text);
	
	List<Chat> getText();
	
	void sendText(Key<User> contactUser, String message);
	
}
