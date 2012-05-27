package net.thesocialos.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ChannelApiEventHandler extends EventHandler {
	void onChannelOn(ChannelApiOnEvent event);
	
	void onChannelOff(ChannelApiOffEvent event);
}
