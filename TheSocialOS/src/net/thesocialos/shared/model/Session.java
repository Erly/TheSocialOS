package net.thesocialos.shared.model;

import java.io.Serializable;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;

@Cached
public class Session implements Serializable {

	@Id String SessionID; //SessionID of the cookie
	

	public String getSessionID() {
		return SessionID;
	}

	private Key<User> user;

	//String SessionID; 
	Long expireDate; //Date to expire the session;
	
	
	public Session(){
		
	}
	
	public Session(String SessionID, Long expireDate,Key<User> user){
		this.SessionID = SessionID;
		this.expireDate = expireDate;
		this.user = user;
	}
	
	public Key<User> getUser(){
		return user;
	}
}
