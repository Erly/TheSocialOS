package net.thesocialos.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ContactsChangeEventHandler extends EventHandler {
	void onContactsChange(ContactsChangeEvent event);
}
