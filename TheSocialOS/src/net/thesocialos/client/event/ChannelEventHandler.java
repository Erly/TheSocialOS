package net.thesocialos.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ChannelEventHandler extends EventHandler {
	void onChannelDisconnect(ChannelClose event);
	
	void onChannelConnect(ChannelOpen event);
}
