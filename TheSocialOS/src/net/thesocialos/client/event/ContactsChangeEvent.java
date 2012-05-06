package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ContactsChangeEvent extends GwtEvent<ContactsChangeEventHandler> {
	
	public static Type<ContactsChangeEventHandler> TYPE = new Type<ContactsChangeEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ContactsChangeEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ContactsChangeEventHandler handler) {
		handler.onContactsChange(this);
		
	}
	
}
