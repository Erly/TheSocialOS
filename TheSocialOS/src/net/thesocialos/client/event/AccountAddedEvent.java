package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author Erlantz Avisar que una cuenta de una red social a sido añadida satisfactoriamente
 */
public class AccountAddedEvent extends GwtEvent<AccountAddedEventHandler> {
	
	public static Type<AccountAddedEventHandler> TYPE = new Type<AccountAddedEventHandler>();
	
	public AccountAddedEvent() {
	}
	
	@Override
	protected void dispatch(AccountAddedEventHandler handler) {
		handler.onAcountAdd(this);
	}
	
	@Override
	public Type<AccountAddedEventHandler> getAssociatedType() {
		return TYPE;
	}
	
}
