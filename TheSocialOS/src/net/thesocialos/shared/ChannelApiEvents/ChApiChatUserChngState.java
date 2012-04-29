package net.thesocialos.shared.ChannelApiEvents;

/**
 * 
 * @author vssnake
 * 
 */
public class ChApiChatUserChngState extends ChApiEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2134913159491146422L;
	
	public static enum STATETYPE {
		ONLINE, OFFLINE, BUSY, AFK, CUSTOM
	};
	
	private STATETYPE state;
	private String customState;
	private String userEmail;
	
	public ChApiChatUserChngState(STATETYPE state, String customsState, String userEmail) {
		super();
		this.state = state;
		customState = customsState;
		this.userEmail = userEmail;
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
	public String getUserEmail() {
		return userEmail;
	}
	
}
