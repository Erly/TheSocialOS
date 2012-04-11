package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Thumbnail extends Composite implements HasText {

	public enum TYPE {
		ALBUM, PICTURE,	VIDEO, MUSIC;
	}
	
	public enum SERVICE {
		PICASA		("images/badges/picasa.png"),
		YOUTUBE		("images/badges/youtube.png"),
		FACEBOOK	("images/badges/facebook.png"),
		FLICKR		("images/badges/flickr.png"),
		TWITTER		("images/badges/twitter.png");
		
		private final String iconUrl;
		
		private SERVICE (String iconUrl) {
			this.iconUrl = iconUrl;
		}

		/**
		 * @return the iconUrl
		 */
		public String getIconUrl() {
			return iconUrl;
		}
	}
	
	private static ThumbnailUiBinder uiBinder = GWT
			.create(ThumbnailUiBinder.class);

	interface ThumbnailUiBinder extends UiBinder<Widget, Thumbnail> {
	}

	public Thumbnail() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Image image;
	@UiField Image badge;
	@UiField Label text;
	@UiField Label subText;
	@UiField FocusPanel panel;
	private TYPE type;
	private SERVICE service;
	
	/**
	 * 
	 * @param image
	 * @param text
	 * @param type a constant from Thumbnail.TYPE
	 * @param service a constant from Thumbnail.SERVICE
	 */
	public Thumbnail(Image image, String text, TYPE type, SERVICE service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image = image;
		this.text.setText(text);
		this.setType(type);
		this.setService(service);
	}

	/**
	 * 
	 * @param image
	 * @param text
	 * @param subText
	 * @param type a constant from Thumbnail.TYPE
	 * @param service a constant from Thumbnail.SERVICE
	 */
	public Thumbnail(Image image, String text, String subText, TYPE type, SERVICE service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image = image;
		this.text.setText(text);
		this.subText.setText(subText);
		this.setType(type);
		this.setService(service);
	}
	
	/**
	 * 
	 * @param imageURL
	 * @param text
	 * @param type a constant from Thumbnail.TYPE
	 * @param service a constant from Thumbnail.SERVICE
	 */
	public Thumbnail(String imageURL, String text, TYPE type, SERVICE service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image.setUrl(imageURL);
		this.text.setText(text);
		this.setType(type);
		this.setService(service);
	}
	
	/**
	 * 
	 * @param imageURL
	 * @param text
	 * @param subText
	 * @param type a constant from Thumbnail.TYPE
	 * @param service a constant from Thumbnail.SERVICE
	 */
	public Thumbnail(String imageURL, String text, String subText, TYPE type, SERVICE service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image.setUrl(imageURL);
		this.text.setText(text);
		this.subText.setText(subText);
		this.setType(type);
		this.setService(service);
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

	/**
	 * @return the type
	 */
	public TYPE getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TYPE type) {
		this.type = type;
	}

	/**
	 * @return the service
	 */
	public SERVICE getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(SERVICE service) {
		this.service = service;
		setBadge(service);
	}
	
	private void setBadge(SERVICE service) {
		badge.setUrl(service.getIconUrl());
	}
}