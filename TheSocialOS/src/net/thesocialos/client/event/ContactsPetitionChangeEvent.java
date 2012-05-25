package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ContactsPetitionChangeEvent extends GwtEvent<ContactPetitionChangeEventHandler> {
	
	public static Type<ContactPetitionChangeEventHandler> TYPE = new Type<ContactPetitionChangeEventHandler>();
	
	@Override
	protected void dispatch(ContactPetitionChangeEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onContactsPetitionChange(this);
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ContactPetitionChangeEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
