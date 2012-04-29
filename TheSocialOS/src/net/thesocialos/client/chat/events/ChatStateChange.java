package net.thesocialos.client.chat.events;

import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;

public class ChatStateChange extends ChatEvent {
	private STATETYPE stateType;
	private String customState;
	
	public ChatStateChange(String userEmail, STATETYPE stateType, String customState) {
		super(userEmail);
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
