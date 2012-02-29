package net.thesocialos.shared.model;

import java.io.Serializable;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

@SuppressWarnings("serial")
@Cached
public class User implements Serializable {

	@Id Long id;
	String email; //Email of the user
	@Unindexed String password; //Password of the user
	String avatar; //Avatar of the user
	String background; //Background of the user
	String firstName; //firstname
	

	String lastName; //lastname
	
	Key<Group> groups[];
	
	Key<Conversation> conversations[];
	
	Key<OutConversation> offlineConversations[];
	
	Key<Session> sessions[];
	
	Key<Account> accounts[];
	
	UserDetails details;
	
	public User(String email, String password, String picture, String background,String firstName,String lastName){
		this.email = email;
		this.password = password;
		this.avatar = picture;
		this.background = background;
		this.firstName = firstName;
		this.lastName = lastName;
		
	}
	
	public User(String email,String picture,String background,String firstName,String lastName){
		this.email = email;
		
		this.avatar = picture;
		this.background = background;
		this.firstName = firstName;
		this.lastName = lastName;
		
	}
	
	public Key<Group>[] getGroups() {
		return groups;
	}

	public void setGroups(Key<Group>[] groups) {
		this.groups = groups;
	}

	public Key<Conversation>[] getConversations() {
		return conversations;
	}

	public void setConversations(Key<Conversation>[] conversations) {
		this.conversations = conversations;
	}

	public Key<OutConversation>[] getOfflineConversations() {
		return offlineConversations;
	}

	public void setOfflineConversations(Key<OutConversation>[] offlineConversations) {
		this.offlineConversations = offlineConversations;
	}

	public Key<Session>[] getSessions() {
		return sessions;
	}

	public void setSessions(Key<Session>[] sessions) {
		this.sessions = sessions;
	}

	public Key<Account>[] getAccounts() {
		return accounts;
	}

	public void setAccounts(Key<Account>[] accounts) {
		this.accounts = accounts;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getBackground() {
		return background;
	}
	public String getName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	

	public User toDto(){
		return new User(email, avatar, background, firstName, lastName);
	}
	
	
	
	
	
}
