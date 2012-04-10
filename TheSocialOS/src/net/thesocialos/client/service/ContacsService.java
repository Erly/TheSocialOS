package net.thesocialos.client.service;

import java.util.List;
import java.util.Map;

import net.thesocialos.shared.exceptions.FriendNotFoundException;
import net.thesocialos.shared.exceptions.UsersNotFoundException;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;
import com.google.gwt.user.client.rpc.RemoteService;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("ContactsRPC")

@XsrfProtect
public interface ContacsService extends RemoteService {


	Map<Key<User>,User> getFriendsList() throws FriendNotFoundException;
	
	List<User> getFriendsSuggestionList(String text) throws FriendNotFoundException;
	
	User getFriend(String email) throws FriendNotFoundException;
	
	Map<Key<User>,User> getUsers() throws UsersNotFoundException;
}
