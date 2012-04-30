package net.thesocialos.client.presenter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.api.TwitterAPI;
import net.thesocialos.shared.model.Columns;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.objectify.Key;

public class SocialDeckPresenter implements Presenter {
	
	public interface Display {
		Widget asWidget();
		
		HasWidgets getAllPostColumnsPanel();
		
		HasClickHandlers getPostButton();
		
		TextArea getTextField();
	}
	
	private final Display display;
	
	public SocialDeckPresenter(Display display) {
		this.display = display;
	}
	
	private void bind() {
		display.getPostButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String postText = display.getTextField().getValue();
				// Tweet it
				new TwitterAPI().post(postText);
				// Post it in Facebook
				display.getTextField().setValue("");
			}
		});
	}
	
	@Override
	public void go(HasWidgets container) {
		container.add(display.asWidget());
		display.asWidget().setVisible(false);
		switchVisible();
		loadColumns();
		bind();
	}
	
	private void loadColumns() {
		Map<Key<Columns>, Columns> columns = CacheLayer.UserCalls.getColumns();
		// Iterator<Columns> it = columns.values().iterator();
		TwitterAPI twitter = new TwitterAPI();
		Set<Columns> columnsSet = new HashSet<Columns>(columns.values());
		twitter.loadColumns(display, columnsSet);
		/*
		 * while (it.hasNext()) { Columns c = it.next(); DeckColumn col = new DeckColumn(); if (c.getType() ==
		 * Columns.TYPE.TIMELINE) { if (c.getValue().equals(Columns.HOME)) { col.setTitle("Timeline");
		 * twitter.loadHomeTimelineInPanel(col); } else if (c.getValue().equals(Columns.USER)) { col.setTitle("Me");
		 * twitter.loadUserTimelineInPanel(col); } else col.setTitle(c.getValue()); // new
		 * TwitterAPI().loadUserTimelineInPanel(col); } else if (c.getType() == Columns.TYPE.SEARCH)
		 * col.setTitle(c.getValue()); // new TwitterAPI().loadUserTimelineInPanel(col); else if (c.getType() ==
		 * Columns.TYPE.LIST) col.setTitle(c.getValue()); // new TwitterAPI().loadUserTimelineInPanel(col);
		 * display.getAllPostColumnsPanel().add(col); col.getParent().setWidth("300px"); }
		 */
	}
	
	public void switchVisible() {
		
		if (display.asWidget().isVisible()) {
			final int minus = display.asWidget().getOffsetHeight() / 20;
			new Timer() {
				
				@Override
				public void run() {
					int oldHeight = display.asWidget().getOffsetHeight();
					display.asWidget().setHeight(display.asWidget().getOffsetHeight() - minus + "px");
					if (display.asWidget().getOffsetHeight() <= 0 || oldHeight == display.asWidget().getOffsetHeight()) {
						display.asWidget().setHeight("0px");
						display.asWidget().setVisible(false);
						cancel();
					}
				}
			}.scheduleRepeating(10);
		} else {
			display.asWidget().setVisible(true);
			display.asWidget().setHeight("100%");
			final int max = display.asWidget().getOffsetHeight();
			final int plus = max / 20;
			display.asWidget().setHeight("0px");
			new Timer() {
				
				@Override
				public void run() {
					display.asWidget().setHeight(display.asWidget().getOffsetHeight() + plus + "px");
					if (display.asWidget().getOffsetHeight() >= max) {
						display.asWidget().setHeight("100%");
						cancel();
					}
				}
			}.scheduleRepeating(10);
		}
	}
}
