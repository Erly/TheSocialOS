package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public abstract class ChannelEvent extends GwtEvent<ChannelEventHandler> {
	
	public static Type<ChannelEventHandler> TYPE = new Type<ChannelEventHandler>();
	
}
