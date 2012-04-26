package net.thesocialos.client.view.profile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PhotosPanel extends Composite {
	
	interface PhotosPanelUiBinder extends UiBinder<Widget, PhotosPanel> {
	}
	
	private static PhotosPanelUiBinder uiBinder = GWT.create(PhotosPanelUiBinder.class);
	
	public PhotosPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public PhotosPanel(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
