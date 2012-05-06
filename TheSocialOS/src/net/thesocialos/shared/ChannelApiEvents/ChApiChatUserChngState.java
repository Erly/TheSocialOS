package net.thesocialos.shared.ChannelApiEvents;

import net.thesocialos.shared.model.User;

import com.googlecode.objectify.Key;

/**
 * 
 * @author vssnake
 * 
 */
public class ChApiChatUserChngState extends ChApiEvent {
	
	public static enum STATETYPE {
		ONLINE, OFFLINE, BUSY, AFK, CUSTOM
	};
	
	private STATETYPE state;
	private String customState;
	private Key<User> userKey;
	
	public ChApiChatUserChngState(STATETYPE state, String customsState, Key<User> userKey) {
		super();
		this.state = state;
		customState = customsState;
		this.userKey = userKey;
	}
	
	public ChApiChatUserChngState() {
		super();
	}
	
	@Override
	protected void dispatch(ChApiEventHandler handler) {
		handler.onChatUserChangeState(this);
		
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChApiEventHandler> getAssociatedType() {
		
		return TYPE;
	}
	
	/**
	 * @return the customState
	 */
	public String getCustomState() {
		return customState;
	}
	
	/**
	 * @return the state
	 */
	public STATETYPE getState() {
		return state;
	}
	
	/**
	 * @return the userEmail
	 */
	public Key<User> getUserKey() {
		return userKey;
	}
	
}
