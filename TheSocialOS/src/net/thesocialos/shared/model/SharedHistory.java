package net.thesocialos.shared.model;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;

/**
 * 
 * @author vssnake This class cannot be filled in the client and saved in the server.
 */
@Cached
public class SharedHistory implements Serializable {
	
	@Id private Long Key;
	
	private SHARETYPE messageType;
	
	private String dataToSafe;
	private String tittle;
	
	private Key<User> sendUser;
	private long date; // The date that has been written
	
	public static enum SHARETYPE {
		VIDEO, IMAGE
	}
	
	public SharedHistory() {
		
	}
	
	public SharedHistory(SHARETYPE shareType, String dataToSafe, String title, Key<User> sendUser, long date) {
		messageType = shareType;
		this.sendUser = sendUser;
		this.dataToSafe = dataToSafe;
		this.date = date;
		tittle = title;
		
	}
	
	/**
	 * @return the key
	 */
	public Long getKey() {
		return Key;
	}
	
	/**
	 * @return the messageType in String
	 */
	public String getStringMessageType() {
		return messageType.name();
	}
	
	/**
	 * 
	 * @return the messageType
	 */
	public SHARETYPE getMessageType() {
		return messageType;
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
	
	/**
	 * @return the tittle
	 */
	public String getTittle() {
		return tittle;
	}
	
}
