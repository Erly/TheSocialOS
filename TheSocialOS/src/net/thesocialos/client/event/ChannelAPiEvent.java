package net.thesocialos.client.event;

import com.google.gwt.event.shared.GwtEvent;

public abstract class ChannelAPiEvent extends GwtEvent<ChannelApiEventHandler> {
	public static Type<ChannelApiEventHandler> TYPE = new Type<ChannelApiEventHandler>();
}
