/**
 * 
 */
package net.thesocialos.shared.ChannelApiEvents;

/**
 * @author vssnake
 * 
 */
public class ChApiChatUserDisconnect extends ChApiEvent {
	
	private String contactDisconnect;
	
	public ChApiChatUserDisconnect(String ContactUser) {
		contactDisconnect = ContactUser;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(ChApiEventHandler handler) {
		handler.onChatUserDisconnected(this);
		
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
	 * @return the contactDisconnect
	 */
	public String getContactUser() {
		return contactDisconnect;
	}
	
}
