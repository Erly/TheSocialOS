package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AccountUpdateEvent extends GwtEvent<AccountUpdateEventHandler> {
	
	public static Type<AccountUpdateEventHandler> TYPE = new Type<AccountUpdateEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AccountUpdateEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(AccountUpdateEventHandler handler) {
		handler.onUserUpdated(this);
		
	}
	
}
