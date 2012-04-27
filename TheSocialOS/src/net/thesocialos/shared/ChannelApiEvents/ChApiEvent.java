package net.thesocialos.shared.ChannelApiEvents;

import com.google.gwt.event.shared.GwtEvent;

public abstract class ChApiEvent extends GwtEvent<ChApiEventHandler> {
	
	public static Type<ChApiEventHandler> TYPE = new Type<ChApiEventHandler>();
	
	public ChApiEvent() {
		
	}
}
