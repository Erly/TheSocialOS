package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;

public class ImageUpload extends Composite {

	private static ImageUploadUiBinder uiBinder = GWT
			.create(ImageUploadUiBinder.class);
	@UiField Button cancelButton;
	@UiField Button uploadButton;

	interface ImageUploadUiBinder extends UiBinder<Widget, ImageUpload> {
	}

	public ImageUpload() {
		initWidget(uiBinder.createAndBindUi(this));
		cancelButton.setText(TheSocialOS.getConstants().cancel());
		uploadButton.setText(TheSocialOS.getConstants().upload());
	}

}
