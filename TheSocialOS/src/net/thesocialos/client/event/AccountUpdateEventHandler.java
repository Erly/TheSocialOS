package net.thesocialos.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AccountUpdateEventHandler extends EventHandler {
	
	void onUserUpdated(AccountUpdateEvent event);
}
