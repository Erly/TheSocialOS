package net.thesocialos.client.service;

import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("chatService")
@XsrfProtect
public interface ChatService extends RemoteService {
	
	Long sendText(Key<User> contactUser, String message);
	
}
