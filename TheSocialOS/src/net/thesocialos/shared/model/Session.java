package net.thesocialos.shared.model;

import java.sql.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Parent;

@Cached
public class Session {

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
