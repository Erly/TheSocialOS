/**
 * 
 */
package net.thesocialos.shared.ChannelApiEvents;


/**
 * @author vssnake
 * 
 */
public class ChApiPetitionNew extends ChApiEvent {
	
	private String petitionNew;
	
	public ChApiPetitionNew(String ContactUser) {
		super();
		petitionNew = ContactUser;
	}
	
	public ChApiPetitionNew() {
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
