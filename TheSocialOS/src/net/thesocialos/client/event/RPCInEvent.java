package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RPCInEvent extends GwtEvent<RPCInEventHandler> {
	
	public static Type<RPCInEventHandler> TYPE = new Type<RPCInEventHandler>();
	
	@Override
	protected void dispatch(RPCInEventHandler handler) {
		handler.onRPCIn(this);
	}
	
	@Override
	public Type<RPCInEventHandler> getAssociatedType() {
		return TYPE;
	}
	
}
