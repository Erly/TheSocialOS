package net.thesocialos.shared.model;

import java.sql.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Parent;

@Cached
public class Session {

	@Id Long id;
	public Long getId() {
		return id;
	}

	public String getSessionID() {
		return SessionID;
	}

	

	String SessionID; //SessionID of the cookie
	Date expireDate; //Date to expire the session;
	
	
	public Session(){
		
	}
}
