package net.thesocialos.client.view;

import net.thesocialos.client.app.IApplication;
import net.thesocialos.client.view.window.DialogBoxExt;
import net.thesocialos.client.view.window.MyCaption;
import net.thesocialos.shared.App;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.FocusPanel;

public class StartMenuItem extends Composite {

	private static StartMenuItemUiBinder uiBinder = GWT
			.create(StartMenuItemUiBinder.class);

	interface StartMenuItemUiBinder extends UiBinder<Widget, StartMenuItem> {
	}

	public StartMenuItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Label text;
	@UiField Image image;
	@UiField HorizontalPanel itemHPanel;
	@UiField FocusPanel itemPanel;

	public StartMenuItem(String name, Image image) {
		initWidget(uiBinder.createAndBindUi(this));
		text.setText(name);
		this.image.setUrl(image.getUrl());
	}
	
	public StartMenuItem(final IApplication app) {
		initWidget(uiBinder.createAndBindUi(this));
		text.setText(app.getName());
		image.setUrl(app.getImage());
		//final String url = app.getUrl();
		//final Composite application = app.getApp();
		itemPanel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DialogBoxExt window = new DialogBoxExt(false, false, new MyCaption());
				window.setText(text.getText());
				//Frame frame = new Frame(url);
				
				window.add(app.run());
				//app.setSize("1024px", "600px");
				app.setSize(app.getWidth(),app.getHeight());
				window.show();
				window.setPopupPosition(10, 30);
			}
		});
	}
	
	public FocusPanel getFocusPanel() {
		return itemPanel;
	}
}
