package net.thesocialos.shared.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;

/**
 * 
 * @author vssnake This class cannot be filled in the client and saved in the server.
 */
@Cached
public class SharedHistory {
	
	@Id private Long Key;
	
	private SHARETYPE messageType;
	
	private String dataToSafe;
	
	private Key<User> sendUser;
	private long date; // The date that has been written
	
	public static enum SHARETYPE {
		VIDEO, IMAGE
	}
	
	public SharedHistory(SHARETYPE shareType, String dataToSafe, Key<User> sendUser, long date) {
		messageType = shareType;
		this.sendUser = sendUser;
		this.dataToSafe = dataToSafe;
		this.date = date;
		
	}
	
	/**
	 * @return the key
	 */
	public Long getKey() {
		return Key;
	}
	
	/**
	 * @return the messageType
	 */
	public String getStringMessageType() {
		return messageType.name();
	}
	
	/**
	 * @return the dataToSafe
	 */
	public String getData() {
		return dataToSafe;
	}
	
	/**
	 * @return the sendUser
	 */
	public Key<User> getSendUser() {
		return sendUser;
	}
	
	/**
	 * @return the date
	 */
	public long getDate() {
		return date;
	}
	
}
