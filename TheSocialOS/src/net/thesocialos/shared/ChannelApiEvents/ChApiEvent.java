package net.thesocialos.shared.ChannelApiEvents;

import java.io.Serializable;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public abstract class ChApiEvent extends GwtEvent<ChApiEventHandler> implements Serializable, IsSerializable {
	
	/**
	 * 
	 */
	
	public static Type<ChApiEventHandler> TYPE = new Type<ChApiEventHandler>();
	
	public ChApiEvent() {
		super();
	}
	
}
