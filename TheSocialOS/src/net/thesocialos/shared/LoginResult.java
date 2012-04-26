package net.thesocialos.shared;

import java.io.Serializable;

import net.thesocialos.shared.model.User;

@SuppressWarnings("serial")
public class LoginResult implements Serializable {
	
	private User user;
	private String sessionID;
	private Long duration;
	
	public LoginResult() {
	}
	
	public LoginResult(User user, String sessionID, Long duration) {
		this.user = user;
		this.sessionID = sessionID;
		this.duration = duration;
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * @return the sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}
	
	public Long getDuration() {
		return duration;
	}
}
