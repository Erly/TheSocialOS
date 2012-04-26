package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AccountAddedEvent extends GwtEvent<AccountAddedEventHandler> {
	
	public static Type<AccountAddedEventHandler> TYPE = new Type<AccountAddedEventHandler>();
	
	public AccountAddedEvent() {
	}
	
	@Override
	public Type<AccountAddedEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(AccountAddedEventHandler handler) {
		handler.onAcountAdd(this);
	}
	
}
