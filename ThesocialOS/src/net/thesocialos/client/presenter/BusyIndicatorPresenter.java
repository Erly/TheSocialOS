package net.thesocialos.client.presenter;

import net.thesocialos.client.event.RPCInEvent;
import net.thesocialos.client.event.RPCInEventHandler;
import net.thesocialos.client.event.RPCOutEvent;
import net.thesocialos.client.event.RPCOutEventHandler;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class BusyIndicatorPresenter implements Presenter {

	public interface Display {
		void show();
		
		void hide();
		
		Widget asWidget();
	}
	
	int outCount = 0; // # of RPC calls sent by the app. If > 0 we'll show a 'loading' indicator.
	private final SimpleEventBus eventBus;
	private Display display;
	
	public BusyIndicatorPresenter(SimpleEventBus eventBus, Display view) {
		this.eventBus = eventBus;
		this.display = view;
		
		bind();
	}

	public void bind() {
		eventBus.addHandler(RPCInEvent.TYPE, new RPCInEventHandler() {

			@Override
			public void onRPCIn(RPCInEvent event) {
				outCount = outCount > 0 ? --outCount : 0; // if outCount > 0 reduce 1 else put it to 0.
				if (outCount <= 0) {
					display.hide(); // When outCount is 0 hide the loading indicator
				}
			}
			
		});
		eventBus.addHandler(RPCOutEvent.TYPE, new RPCOutEventHandler() {

			@Override
			public void onRPCOut(RPCOutEvent event) {
				outCount++;
				display.show();
			}
			
		});
	}

	@Override
	public void go(HasWidgets container) {
		// nothing to do
	}

}
