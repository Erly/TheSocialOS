package net.thesocialos.server;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.thesocialos.client.service.ContacsService;
import net.thesocialos.shared.exceptions.ContactException;
import net.thesocialos.shared.exceptions.FriendNotFoundException;
import net.thesocialos.shared.exceptions.UsersNotFoundException;
import net.thesocialos.shared.model.User;


import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

@SuppressWarnings("serial")
public class ContactsServiceimpl extends XsrfProtectedServiceServlet implements ContacsService {

	@Override
	public Map<Key<User>,User> getFriendsList() throws FriendNotFoundException {
		Objectify ofy = ObjectifyService.begin();
		
		
		User user = UserHelper.getUserSession(perThreadRequest.get().getSession(), ofy);
		List<Key<User>> contacts = user.getContacts();
		
			
		if (contacts == null || contacts.isEmpty()){
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
		
		User user = UserHelper.getUserSession(perThreadRequest.get().getSession(), ofy);
		
		
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

	@Override
	public Map<String, User> getUsers() throws UsersNotFoundException {
		// TODO Auto-generated method stub
		Objectify ofy = ObjectifyService.begin();
		Query<User> queryusers = ofy.query(User.class);
		Map<String,User> users = new LinkedHashMap<String, User>();
		if (queryusers.count() == 0){
			throw new UsersNotFoundException("No users in the database");
		}
		for (User user: queryusers){
			users.put(user.getEmail(), User.toDTO(user));
		}
		
		return users;
	}
	
	
	/**
	 * A�ade una peticion nueva al contacto
	 */
	@Override
	public Boolean addPetitionContact(User contactUser) throws ContactException {
		Objectify ofy = ObjectifyService.begin();
		User userPetition;
		Key<User> contactKey;
		try {
			userPetition = ofy.get(User.class,contactUser.getEmail());
			contactKey = ObjectifyService.factory().getKey(UserHelper.getUserHttpSession(perThreadRequest.get().getSession()));
			
		} catch (Exception e) {
			throw new ContactException("Fail to parse the key");
		}
		
		if (userPetition.addPetitionContacts(contactKey)){
			ofy.put(userPetition);
			//UserHelper.saveUser(user, perThreadRequest.get().getSession(), ofy);
			return true;
		}
		throw new ContactException("key duplicated");
	}

	@Override
	public Map<String, User> getPetitionContact() throws ContactException {
		Objectify ofy =  ObjectifyService.begin();
		
		User user = UserHelper.getUserSession(perThreadRequest.get().getSession(), ofy);
		Map<String, User> petitions;
		Map<Key<User>,User> keyContacts = null;
		
		try{
			keyContacts = ofy.get(user.getpetitionsContacts());
			
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
			//throw new ContactException("No Petitions");
		}
		Iterator<User> iteratorPetitions = keyContacts.values().iterator();
		petitions = new LinkedHashMap<String, User>();
		while (iteratorPetitions.hasNext()) {
			User userPetition = iteratorPetitions.next();
			petitions.put(userPetition.getEmail(), userPetition);
		}

		return petitions;
	}

	@Override
	public Boolean acceptContact(String email) throws ContactException {
		// TODO Auto-generated method stub
		Objectify ofy = ObjectifyService.begin();
		User contactToAccept; 
		User userLoged = null;
		try{
			contactToAccept = ofy.get(User.class,email);
			userLoged = UserHelper.getUserSession(perThreadRequest.get().getSession(), ofy);
		}catch (NotFoundException e) {
			throw new ContactException("User or contact not found");
		}
		
		Key<User> contactKey = ObjectifyService.factory().getKey(contactToAccept);
		if (userLoged.getpetitionsContacts().contains(ObjectifyService.factory().getKey(contactKey))){
			Key<User> userKey = ObjectifyService.factory().getKey(userLoged);
			userLoged.addPetitionContactTOContact(contactKey);
			
			contactToAccept.addContact(userKey);
			ofy.put(userLoged);
			ofy.put(contactToAccept);
			return true;
		}
		return false;
	}

	@Override
	public Boolean denyContact(String email) throws ContactException {
		Objectify ofy = ObjectifyService.begin();
		User contactToAccept; 
		User userLoged = null;
		try {
			contactToAccept = ofy.get(User.class,email);
			userLoged = UserHelper.getUserSession(perThreadRequest.get().getSession(), ofy);
		} catch (NotFoundException e) {
			throw new ContactException("User or contact not found");
		}
		Key<User> contactKey = ObjectifyService.factory().getKey(contactToAccept);
			Boolean erase = userLoged.getpetitionsContacts().remove(contactKey);
			userLoged.getContacts().add(contactKey);
			ofy.put(userLoged);
			return erase;
	}

}
