package net.thesocialos.shared.ChannelApiEvents;

import java.io.Serializable;

import com.google.gwt.event.shared.GwtEvent;

@SuppressWarnings("serial")
public abstract class ChApiEvent extends GwtEvent<ChApiEventHandler> implements Serializable {
	
	/**
	 * 
	 */
	
	public static Type<ChApiEventHandler> TYPE = new Type<ChApiEventHandler>();
	
	public ChApiEvent() {
		
	}
}
