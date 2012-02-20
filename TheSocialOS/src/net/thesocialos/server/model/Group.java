package net.thesocialos.server.model;

import java.util.ArrayList;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Group {
	public Group(String value){
		this.name = value;
	}
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	// Id that autogenerates when a user is inserted in the Datastore
	private Key ID;
	
	@Persistent
	private String name;
	
	@Persistent
	private ArrayList<Key> friends = new ArrayList<Key>();

	public String getValue() {
		return name;
	}

	public void setValue(String value) {
		this.name = value;
	}

	public ArrayList<Key> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<Key> friends) {
		this.friends = friends;
	}
	
}
