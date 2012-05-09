package net.thesocialos.client.service;

import net.thesocialos.shared.ChannelApiEvents.ChApiEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("push_service")
public interface ChannelService extends RemoteService {
	ChApiEvent getEvent();
	
	/**
	 * Utility/Convenience class. Use PushService.App.getInstance() to access static instance of PushServiceAsync
	 */
	public static class App {
		private static final ChannelServiceAsync ourInstance = (ChannelServiceAsync) GWT.create(ChannelService.class);
		
		public static ChannelServiceAsync getInstance() {
			return ourInstance;
		}
	}
}
