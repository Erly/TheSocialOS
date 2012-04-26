package net.thesocialos.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

@SuppressWarnings("serial")
public class Chat implements Serializable {
	
	@Id Long id;
	String text;
	
	long date;
	
	String user;
	
	public Chat() {
		
	}
	
	public Chat(String text, String owner) {
		
		this.date = new Date().getTime();
		this.text = text;
		// this.user = new Key<User>(User.class, owner.getEmail());
		this.user = owner;
		
	}
	
	public long getDate() {
		return date;
	}
	
	public String getText() {
		return text;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
}
