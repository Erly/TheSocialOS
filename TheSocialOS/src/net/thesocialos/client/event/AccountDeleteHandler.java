package net.thesocialos.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface AccountDeleteHandler extends EventHandler {
	void onCloudAccountDelete(AccountDeleteEvent event);
}
