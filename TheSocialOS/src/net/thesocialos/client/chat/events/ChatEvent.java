package net.thesocialos.client.chat.events;

import com.google.gwt.event.shared.GwtEvent;

public abstract class ChatEvent extends GwtEvent<ChatEventHandler> {
	
	public static Type<ChatEventHandler> TYPE = new Type<ChatEventHandler>();
	
	private String userEmail;
	
	public ChatEvent(String userEmail) {
		this.userEmail = userEmail;
	}
	
	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}
	
}
