package net.thesocialos.client.service;

import net.thesocialos.shared.ChannelApiEvents.ChApiEvent;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChannelServiceAsync {
	void getEvent(AsyncCallback<ChApiEvent> callback);
}
