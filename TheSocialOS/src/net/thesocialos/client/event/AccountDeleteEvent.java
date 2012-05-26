package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AccountDeleteEvent extends GwtEvent<AccountDeleteHandler> {
	
	public static Type<AccountDeleteHandler> TYPE = new Type<AccountDeleteHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AccountDeleteHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(AccountDeleteHandler handler) {
		handler.onCloudAccountDelete(this);
		
	}
	
}
