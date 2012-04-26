package net.thesocialos.client.service;

import java.util.List;
import java.util.Map;

import net.thesocialos.shared.exceptions.ContactException;
import net.thesocialos.shared.exceptions.FriendNotFoundException;
import net.thesocialos.shared.exceptions.UsersNotFoundException;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;
import com.googlecode.objectify.Key;

@RemoteServiceRelativePath("ContactsRPC")
@XsrfProtect
public interface ContacsService extends RemoteService {
	
	Boolean acceptContact(String email) throws ContactException;
	
	Boolean addPetitionContact(User contactUser) throws ContactException;
	
	Boolean denyContact(String email) throws ContactException;
	
	User getFriend(String email) throws FriendNotFoundException;
	
	Map<Key<User>, User> getFriendsList() throws FriendNotFoundException;
	
	List<User> getFriendsSuggestionList(String text) throws FriendNotFoundException;
	
	Map<String, User> getPetitionContact() throws ContactException;
	
	Map<String, User> getUsers() throws UsersNotFoundException;
	
}
