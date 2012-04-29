package net.thesocialos.shared.ChannelApiEvents;

public class ChApiChatUserConnected extends ChApiEvent {
	
	/**
	 * 
	 */
	private String userEmail;
	
	public ChApiChatUserConnected(String userEmail) {
		super();
		this.userEmail = userEmail;
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
	public String getContactUser() {
		return userEmail;
	}
	
}
