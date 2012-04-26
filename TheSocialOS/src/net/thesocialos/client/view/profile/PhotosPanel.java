package net.thesocialos.client.view.profile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class PhotosPanel extends Composite {
	
	private static PhotosPanelUiBinder uiBinder = GWT.create(PhotosPanelUiBinder.class);
	
	interface PhotosPanelUiBinder extends UiBinder<Widget, PhotosPanel> {
	}
	
	public PhotosPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public PhotosPanel(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
