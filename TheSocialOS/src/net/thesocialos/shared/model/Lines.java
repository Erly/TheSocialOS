package net.thesocialos.shared.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;

public class Lines {
	
	private @Id Long id;
	private int date; // The date that has been written
	private @Unindexed String text; // The text of the line
	private @Parent Key<Conversation> converOwner;
	private Key<User> userOwner;
	
	public Lines() {
		
	}
	
	public Key<Conversation> getConverOwner() {
		return converOwner;
	}
	
	public int getDate() {
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
	
	public void setConverOwner(Key<Conversation> converOwner) {
		this.converOwner = converOwner;
	}
	
	public void setDate(int date) {
		this.date = date;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setUserOwner(Key<User> userOwner) {
		this.userOwner = userOwner;
	}
}
