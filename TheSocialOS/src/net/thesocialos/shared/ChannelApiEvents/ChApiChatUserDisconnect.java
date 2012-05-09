package net.thesocialos.shared.ChannelApiEvents;


public class ChApiChatUserDisconnect extends ChApiEvent {
	
	/**
	 * 
	 */
	private String userEmail;
	
	public ChApiChatUserDisconnect(String userEmail) {
		super();
		this.userEmail = userEmail;
	}
	
	public ChApiChatUserDisconnect() {
		super();
	}
	
	private static final long serialVersionUID = 7074318690223079108L;
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChApiEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChApiEventHandler handler) {
		handler.onChatUserDisconnected(this);
		
	}
	
	/**
	 * @return the userEmail
	 */
	public String getContactUser() {
		return userEmail;
	}
	
}
