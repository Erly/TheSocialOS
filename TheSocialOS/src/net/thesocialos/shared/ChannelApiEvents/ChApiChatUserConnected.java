package net.thesocialos.shared.ChannelApiEvents;

import net.thesocialos.shared.model.User;

import com.googlecode.objectify.Key;

public class ChApiChatUserConnected extends ChApiEvent {
	
	/**
	 * 
	 */
	private Key<User> userKey;
	
	public ChApiChatUserConnected(Key<User> userKey) {
		super();
		this.userKey = userKey;
	}
	
	public ChApiChatUserConnected() {
		super();
	}
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChApiEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChApiEventHandler handler) {
		handler.onChatUserConnected(this);
		
	}
	
	/**
	 * @return the userEmail
	 */
	public Key<User> getKeyUser() {
		return userKey;
	}
	
}
