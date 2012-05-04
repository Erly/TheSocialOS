package net.thesocialos.client.chat.events;

import net.thesocialos.shared.model.User;

import com.googlecode.objectify.Key;

public class ChatSendMessage extends ChatEvent {
	
	private String text;
	
	public ChatSendMessage(Key<User> userKey, String text) {
		super(userKey);
		this.text = text;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChatEventHandler> getAssociatedType() {
		
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChatEventHandler handler) {
		handler.onSendMessage(this);
		
	}
	
	/**
	 * @return the text
	 */
	public String getMessage() {
		return text;
	}
	
}
