/**
 * 
 */
package net.thesocialos.shared.ChannelApiEvents;


/**
 * @author vssnake
 * 
 */
public class ChApiContactPetition extends ChApiEvent {
	
	private String petitionNew;
	
	public ChApiContactPetition(String ContactUser) {
		super();
		petitionNew = ContactUser;
	}
	
	public ChApiContactPetition() {
		super();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(ChApiEventHandler handler) {
		handler.onPetitionNew(this);
		
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
		return petitionNew;
	}
	
}
