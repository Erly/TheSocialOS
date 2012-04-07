package net.thesocialos.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.thesocialos.client.service.ContacsService;
import net.thesocialos.shared.exceptions.FriendNotFoundException;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

@SuppressWarnings("serial")
public class Contactsimpl extends XsrfProtectedServiceServlet implements ContacsService {

	@Override
	public Map<Key<User>,User> getFriendsList() throws FriendNotFoundException {
		Objectify ofy = ObjectifyService.begin();
		User user = UserHelper.getUserfromSession(perThreadRequest.get().getSession());
		List<Key<User>> contacts = user.getContacts();
		
				
		if (contacts == null){
			throw new FriendNotFoundException("User not has Contacts");
		}
		
		Map<Key<User>, User> usuarios = ofy.get(contacts);
		//ArrayList<String> list = new ArrayList<String>();
		//Arrays.
		return usuarios;
	}
	
	@Override
	public List<User> getFriendsSuggestionList(String text)
			throws FriendNotFoundException {
		Objectify ofy = ObjectifyService.begin();
		StringTokenizer tokens = new StringTokenizer(text);
		List<String> userNames = new ArrayList<String>();
		User user = UserHelper.getUserfromSession(perThreadRequest.get().getSession());
		
		
		while (tokens.hasMoreTokens()){
			userNames.add(tokens.nextToken());
		}
		Query<User> queryContact;
		if (userNames.isEmpty()){
			throw new FriendNotFoundException("Not contacts found with these codes");
		}
		if (userNames.size()==1){
			queryContact = ofy.query(User.class).filter("firstName >=", userNames.get(0)).filter("firstName <", userNames.get(0) + "\uFFFD"); 
		}else{
			String nextString = "";
			for (int i = 1; i < userNames.size(); i++) {
				nextString += userNames.get(i) + " ";
			}
			queryContact = ofy.query(User.class).filter("firstName >=", userNames.get(0)).filter("firstName <", userNames.get(0) + "\uFFFD"); 
		}
		List <User> SearchContacts = new ArrayList<User>();
		for (User contact: queryContact) {
			Key<User> userKey = ObjectifyService.factory().getKey(contact);
			if (user.getContacts().contains(userKey))
			{
				SearchContacts.add(contact);
			}
			
		    
		}
		
		return SearchContacts;
	}
	
	@Override
	public User getFriend(String email) throws FriendNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
