package net.thesocialos.shared.ChannelApiEvents;

/**
 * @author vssnake
 * 
 */

public class ChApiChatUserConnected extends ChApiEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9062896989802463430L;
	private String contactConnect;
	
	public ChApiChatUserConnected(String ContactUser) {
		super();
		contactConnect = ContactUser;
	}
	
	public ChApiChatUserConnected() {
		super();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(ChApiEventHandler handler) {
		handler.onChatUserConnected(this);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChApiEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	/**
	 * @return the contactConnect
	 */
	public String getContactUser() {
		return contactConnect;
	}
	
}
