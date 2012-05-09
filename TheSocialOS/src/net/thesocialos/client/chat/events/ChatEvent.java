package net.thesocialos.client.chat.events;

import net.thesocialos.shared.model.User;

import com.google.gwt.event.shared.GwtEvent;
import com.googlecode.objectify.Key;

public abstract class ChatEvent extends GwtEvent<ChatEventHandler> {
	
	public static Type<ChatEventHandler> TYPE = new Type<ChatEventHandler>();
	
	private Key<User> userKey;
	
	public ChatEvent(Key<User> userKey) {
		this.userKey = userKey;
	}
	
	public ChatEvent() {
		
	}
	
	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userKey.getName();
	}
	
	/**
	 * @return the userEmail
	 */
	public Key<User> getUserKey() {
		return userKey;
	}
	
}
