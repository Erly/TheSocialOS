package net.thesocialos.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginResult implements Serializable {

	private UserDTO user;
	private String sessionID;
	private String key;
	
	public LoginResult() {}
	
	public LoginResult(UserDTO user, String sessionID, String key) {
		this.user = user;
		this.sessionID = sessionID;
		this.key = key;
	}

	/**
	 * @return the user
	 */
	public UserDTO getUser() {
		return user;
	}

	/**
	 * @return the sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

}
