package net.thesocialos.shared.model;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;

@Entity
@Cached
public abstract class Account implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id Long id;
	
	private String username;
	
	public Account() {
		
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	public abstract void refresh();
	
	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}
	
}
