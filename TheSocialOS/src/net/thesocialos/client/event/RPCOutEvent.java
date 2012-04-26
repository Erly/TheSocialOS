package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RPCOutEvent extends GwtEvent<RPCOutEventHandler> {
	
	public static Type<RPCOutEventHandler> TYPE = new Type<RPCOutEventHandler>();
	
	@Override
	public Type<RPCOutEventHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(RPCOutEventHandler handler) {
		handler.onRPCOut(this);
	}
	
}
