package net.thesocialos.client.service;

import net.thesocialos.shared.ChannelApiEvents.ChApiEvent;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.web.bindery.event.shared.Event;

public interface ChannelService extends RemoteService {
	void sendDummy(ChApiEvent chApiEvent, Event<?> event, GwtEvent<?> gwtEvent);
}
