package net.thesocialos.shared.ChannelApiEvents;

import java.io.Serializable;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ChApiEvent extends GwtEvent<ChApiEventHandler> implements Serializable, IsSerializable {
	
	/**
	 * 
	 */
	
	public static Type<ChApiEventHandler> TYPE = new Type<ChApiEventHandler>();
	
	public ChApiEvent() {
		super();
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChApiEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChApiEventHandler handler) {
		// TODO Auto-generated method stub
		
	}
	
}
