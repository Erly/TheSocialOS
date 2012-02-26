package net.thesocialos.shared.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
@Cached
public class Group {
	
	@Id Long id;
	String name; //Group name
	Key<User> owner; //Administrator of this group

	
	public Group(){
		
	}
}
