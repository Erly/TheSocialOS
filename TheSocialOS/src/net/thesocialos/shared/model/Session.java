package net.thesocialos.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;

@Cached
public class Session implements Serializable {
	
	@Id String SessionID; // SessionID of the cookie
	Date expireDate;
	private Key<User> user;
	
	public String getSessionID() {
		return SessionID;
	}
	
	public Session() {
		
	}
	
	public Session(String SessionID, Date expireDate, Key<User> user) {
		this.SessionID = SessionID;
		this.expireDate = expireDate;
		this.user = user;
	}
	
	public Key<User> getUser() {
		return user;
	}
}
