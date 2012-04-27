package net.thesocialos.shared.ChannelApiEvents;

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
	
	public ChApiChatUserChngState(STATETYPE state, String customsState) {
		customState = customsState;
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
	
}
