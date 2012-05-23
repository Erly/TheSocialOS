package net.thesocialos.shared.model;

import javax.persistence.Id;

import net.thesocialos.shared.ChannelApiEvents.ChApiChatRecvMessage.CHATMESSAGETYPE;

import com.googlecode.objectify.Key;

/**
 * 
 * @author vssnake This class cannot be filled in the client and saved in the server.
 */
public class SharedHistory {
	
	@Id private Long Key;
	
	private String messageType;
	
	private String dataToSafe;
	
	private Key<User> sendUser;
	private long date; // The date that has been written
	
	public SharedHistory(CHATMESSAGETYPE messageType, String dataToSafe, Key<User> sendUser, long date) {
		this.messageType = messageType.name();
		this.sendUser = sendUser;
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
	public String getMessageType() {
		return messageType;
	}
	
	/**
	 * @return the dataToSafe
	 */
	public String getDataToSafe() {
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
