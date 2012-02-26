package net.thesocialos.shared.model;

import java.sql.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Parent;

@Cached
public class Session {

	@Id Long id;
	String SessionID; //SessionID of the cookie
	Date expireDate; //Date to expire the session;
	
	@Parent Key<User> owner; //One user has this session
	
	public Session(){
		
	}
}
