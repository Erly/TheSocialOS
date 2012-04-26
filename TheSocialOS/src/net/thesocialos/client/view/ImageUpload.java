package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ImageUpload extends Composite {
	
	interface ImageUploadUiBinder extends UiBinder<Widget, ImageUpload> {
	}
	
	private static ImageUploadUiBinder uiBinder = GWT.create(ImageUploadUiBinder.class);
	@UiField Button cancelButton;
	
	@UiField Button uploadButton;
	
	public ImageUpload() {
		initWidget(uiBinder.createAndBindUi(this));
		cancelButton.setText(TheSocialOS.getConstants().cancel());
		uploadButton.setText(TheSocialOS.getConstants().upload());
	}
	
}
