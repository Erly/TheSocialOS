package net.thesocialos.client.event;

import com.google.gwt.event.shared.EventHandler;



public interface ContactEventHandler extends EventHandler{

	void onContactsPetitionChange(ContactsPetitionChangeEvent event);
}