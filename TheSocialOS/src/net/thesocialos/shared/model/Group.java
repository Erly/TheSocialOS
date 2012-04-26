package net.thesocialos.shared.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;

@Cached
public class Group {
	
	@Id Long id;
	private String name; // Group name
	private Key<User> owner; // Administrator of this group
	
	public Group() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Key<User> getOwner() {
		return owner;
	}
	
	public void setOwner(Key<User> owner) {
		this.owner = owner;
	}
	
}
