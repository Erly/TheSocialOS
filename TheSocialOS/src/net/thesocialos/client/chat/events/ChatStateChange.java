package net.thesocialos.client.chat.events;

import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.model.User;

import com.googlecode.objectify.Key;

public class ChatStateChange extends ChatEvent {
	private STATETYPE stateType;
	private String customState;
	
	public ChatStateChange(Key<User> userKey, STATETYPE stateType, String customState) {
		super(userKey);
		this.stateType = stateType;
		this.customState = customState;
		
	}
	
	public ChatStateChange() {
		super();
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChatEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChatEventHandler handler) {
		handler.onChangeState(this);
	}
	
	/**
	 * @return the stateType
	 */
	public STATETYPE getStateType() {
		return stateType;
	}
	
	/**
	 * @return the customState
	 */
	public String getCustomState() {
		return customState;
	}
	
}
