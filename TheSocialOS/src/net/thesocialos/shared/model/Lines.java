package net.thesocialos.shared.model;

import java.util.Date;

import javax.persistence.Id;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;

public class Lines implements IsSerializable {
	
	private @Id Long id;
	private Date date; // The date that has been written
	private @Unindexed String text; // The text of the line
	private @Parent Key<Conversation> converOwner;
	private Key<User> userOwner;
	
	public Lines(String text, Key<User> userOwner, Date date) {
		this.text = text;
		this.userOwner = userOwner;
		this.date = date;
	}
	
	public Lines() {
		
	}
	
	public Key<Conversation> getConverOwner() {
		return converOwner;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public Key<User> getUserOwner() {
		return userOwner;
	}
	
}
