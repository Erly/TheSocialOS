package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.app.IApplication;
import net.thesocialos.client.desktop.DesktopEventOnOpen;
import net.thesocialos.client.desktop.DesktopUnit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class StartMenuItem extends Composite {
	
	interface StartMenuItemUiBinder extends UiBinder<Widget, StartMenuItem> {
	}
	
	private static StartMenuItemUiBinder uiBinder = GWT.create(StartMenuItemUiBinder.class);
	
	@UiField Label text;
	
	@UiField Image image;
	@UiField HorizontalPanel itemHPanel;
	@UiField FocusPanel itemPanel;
	
	public StartMenuItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public StartMenuItem(final DesktopUnit app) {
		initWidget(uiBinder.createAndBindUi(this));
		text.setText(((IApplication) app).getName());
		image.setUrl(((IApplication) app).getImage());
		
		// final Composite application = ((FrameApp) app).getApp();
		itemPanel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(app));
				// WindowPanelLayout window = new WindowPanelLayout(false, false, new MyCaption(), new Footer());
				// window.setText(text.getText());
				// Frame frame = new Frame(url);
				
				// window.add(((FrameApp) app).run());
				// app.setSize("1024px", "600px");
				// app.setSize(app.getWidth(),app.getHeight());
				// window.show();
				// window.setPopupPosition(10, 30);
			}
		});
	}
	
	public StartMenuItem(String name, Image image) {
		initWidget(uiBinder.createAndBindUi(this));
		text.setText(name);
		this.image.setUrl(image.getUrl());
	}
	
	public FocusPanel getFocusPanel() {
		return itemPanel;
	}
}
