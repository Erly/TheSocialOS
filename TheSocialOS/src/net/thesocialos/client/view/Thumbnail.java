package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Thumbnail extends Composite implements HasText {

	private static ThumbnailUiBinder uiBinder = GWT
			.create(ThumbnailUiBinder.class);

	interface ThumbnailUiBinder extends UiBinder<Widget, Thumbnail> {
	}

	public Thumbnail() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Image image;
	@UiField Label text;
	@UiField Label subText;
	@UiField FocusPanel panel;

	public Thumbnail(Image image, String text) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image = image;
		this.text.setText(text);
	}
	
	public Thumbnail(Image image, String text, String subText) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image = image;
		this.text.setText(text);
		this.subText.setText(subText);
	}
	
	public Thumbnail(String imageURL, String text) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image.setUrl(imageURL);
		this.text.setText(text);
	}
	
	public Thumbnail(String imageURL, String text, String subText) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image.setUrl(imageURL);
		this.text.setText(text);
		this.subText.setText(subText);
	}

	public void setText(String text) {
		this.text.setText(text);
	}

	public String getText() {
		return text.getText();
	}
	
	/**
	 * @return the subText
	 */
	public Label getSubText() {
		return subText;
	}

	/**
	 * @param subText the subText to set
	 */
	public void setSubText(Label subText) {
		this.subText = subText;
	}

	public void addDoubleClickHandler(DoubleClickHandler handler) {
		panel.addDoubleClickHandler(handler);
	}

}
