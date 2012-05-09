package net.thesocialos.client.chat.events;

import net.thesocialos.shared.model.User;

import com.googlecode.objectify.Key;

public class ChatTopConversation extends ChatEvent {
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChatEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChatEventHandler handler) {
		handler.onTopConversation(this);
		
	}
	
	public ChatTopConversation(Key<User> userKey) {
		super(userKey);
	}
	
}
