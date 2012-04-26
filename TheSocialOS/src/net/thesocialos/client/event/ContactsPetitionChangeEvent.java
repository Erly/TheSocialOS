package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ContactsPetitionChangeEvent extends GwtEvent<ContactEventHandler> {
	
	public static Type<ContactEventHandler> TYPE = new Type<ContactEventHandler>();
	
	@Override
	protected void dispatch(ContactEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onContactsPetitionChange(this);
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ContactEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
