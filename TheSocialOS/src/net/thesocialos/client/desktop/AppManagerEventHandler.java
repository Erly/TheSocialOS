package net.thesocialos.client.desktop;

import com.google.gwt.event.shared.EventHandler;

public interface AppManagerEventHandler extends EventHandler {
	
	void onClose(AppManagerCloseEvent event);
	
	void onOpen(AppManagerOpenEvent event);
}
