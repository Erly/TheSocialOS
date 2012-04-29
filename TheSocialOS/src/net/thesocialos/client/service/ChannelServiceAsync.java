package net.thesocialos.client.service;

import net.thesocialos.shared.ChannelApiEvents.ChApiEvent;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.Event;

public interface ChannelServiceAsync {
	void sendDummy(ChApiEvent chApiEvent, Event<?> event, GwtEvent<?> gwtEvent, AsyncCallback<Void> callback);
}
