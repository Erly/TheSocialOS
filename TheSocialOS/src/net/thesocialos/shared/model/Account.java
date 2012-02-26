package net.thesocialos.shared.model;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;
@SuppressWarnings("serial")
@Entity
@Cached
public abstract class Account implements Serializable {
	
	@Id Long id;
	@Parent Key<User> owner; //One user has this account
	
	abstract void refresh();
	
	public Account(){
		
	}

}
