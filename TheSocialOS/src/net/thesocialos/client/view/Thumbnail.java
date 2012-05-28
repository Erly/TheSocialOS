package net.thesocialos.client.view;

import net.thesocialos.client.advanced.AdvClickListener;
import net.thesocialos.client.advanced.AdvFocusPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Thumbnail extends Composite implements HasText {
	
	public enum SERVICE {
		PICASA("images/badges/picasa.png"), YOUTUBE("images/badges/youtube.png"), FACEBOOK("images/badges/facebook.png"), FLICKR(
				"images/badges/flickr.png"), TWITTER("images/badges/twitter.png"), DRIVE("images/badges/drive.png");
		
		private final String iconUrl;
		
		private SERVICE(String iconUrl) {
			this.iconUrl = iconUrl;
		}
		
		/**
		 * @return the iconUrl
		 */
		public String getIconUrl() {
			return iconUrl;
		}
	}
	
	interface ThumbnailUiBinder extends UiBinder<Widget, Thumbnail> {
	}
	
	public enum TYPE {
		ALBUM, PICTURE, VIDEO, MUSIC, OTHER, FOLDER;
	}
	
	private static ThumbnailUiBinder uiBinder = GWT.create(ThumbnailUiBinder.class);
	
	@UiField Image image;
	
	@UiField Image badge;
	@UiField Label text;
	@UiField Label subText;
	@UiField AdvFocusPanel panel;
	private TYPE type;
	private SERVICE service;
	
	public Thumbnail() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	/**
	 * 
	 * @param image
	 * @param text
	 * @param subText
	 * @param type
	 *            a constant from Thumbnail.TYPE
	 * @param service
	 *            a constant from Thumbnail.SERVICE
	 */
	public Thumbnail(Image image, String text, String subText, TYPE type, SERVICE service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image = image;
		this.text.setText(text);
		this.subText.setText(subText);
		setType(type);
		setService(service);
	}
	
	/**
	 * 
	 * @param image
	 * @param text
	 * @param type
	 *            a constant from Thumbnail.TYPE
	 * @param service
	 *            a constant from Thumbnail.SERVICE
	 */
	public Thumbnail(Image image, String text, TYPE type, SERVICE service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image = image;
		this.text.setText(text);
		setType(type);
		setService(service);
	}
	
	/**
	 * 
	 * @param imageURL
	 * @param text
	 * @param subText
	 * @param type
	 *            a constant from Thumbnail.TYPE
	 * @param service
	 *            a constant from Thumbnail.SERVICE
	 */
	public Thumbnail(String imageURL, String text, String subText, TYPE type, SERVICE service) {
		initWidget(uiBinder.createAndBindUi(this));
		image.setUrl(imageURL);
		this.text.setText(text);
		this.subText.setText(subText);
		setType(type);
		setService(service);
	}
	
	/**
	 * 
	 * @param imageURL
	 * @param text
	 * @param type
	 *            a constant from Thumbnail.TYPE
	 * @param service
	 *            a constant from Thumbnail.SERVICE
	 */
	public Thumbnail(String imageURL, String text, TYPE type, SERVICE service) {
		initWidget(uiBinder.createAndBindUi(this));
		image.setUrl(imageURL);
		this.text.setText(text);
		setType(type);
		setService(service);
	}
	
	public void addAdvClickListener(AdvClickListener listener) {
		panel.addClickListener(listener);
	}
	
	public void addDoubleClickHandler(DoubleClickHandler handler) {
		panel.addDoubleClickHandler(handler);
	}
	
	/**
	 * @return the service
	 */
	public SERVICE getService() {
		return service;
	}
	
	/**
	 * @return the subText
	 */
	public Label getSubText() {
		return subText;
	}
	
	@Override
	public String getText() {
		return text.getText();
	}
	
	/**
	 * @return the type
	 */
	public TYPE getType() {
		return type;
	}
	
	private void setBadge(SERVICE service) {
		badge.setUrl(service.getIconUrl());
	}
	
	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(SERVICE service) {
		this.service = service;
		setBadge(service);
	}
	
	/**
	 * @param subText
	 *            the subText to set
	 */
	public void setSubText(Label subText) {
		this.subText = subText;
	}
	
	@Override
	public void setText(String text) {
		this.text.setText(text);
	}
	
	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(TYPE type) {
		this.type = type;
	}
}