/**
 * 
 */
package net.thesocialos.shared.ChannelApiEvents;

/**
 * @author vssnake
 * 
 */
public class ChApiContactNew extends ChApiEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6369085840506019531L;
	private String ContactNew;
	
	public ChApiContactNew(String ContactUser) {
		super();
		ContactNew = ContactUser;
	}
	
	public ChApiContactNew() {
		super();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(ChApiEventHandler handler) {
		handler.onContactNew(this);
		
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
	 * @return the ContactNew
	 */
	public String getContactUser() {
		return ContactNew;
	}
	
}
