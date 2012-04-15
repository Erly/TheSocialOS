package net.thesocialos.client.presenter;

import net.thesocialos.client.api.TwitterAPI;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class TimelinePresenter implements Presenter {

	SimpleEventBus eventBus;
	Display display;
	
	public TimelinePresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}
	
	public interface Display {
		HasWidgets getMainPanel();
		
		Widget asWidget();
	}
	
	public void bind() {
		new TwitterAPI().loadHomeTimelineInPanel(this.display.getMainPanel());
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(this.display.asWidget());
		bind();
	}

}
