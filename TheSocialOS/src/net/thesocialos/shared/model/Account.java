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
	
	public Account(String userName) {
		username = userName;
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
	
	public static <T extends Account> Account toDTO(Account account) {
		if (account instanceof Facebook) return new Facebook(account.getUsername());
		if (account instanceof Google) return new Google(account.getUsername());
		if (account instanceof Twitter) return new Twitter(account.getUsername());
		if (account instanceof FlickR) return new FlickR(account.getUsername());
		return null;
	}
}
