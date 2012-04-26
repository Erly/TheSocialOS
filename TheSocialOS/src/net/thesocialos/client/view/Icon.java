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
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FocusPanel;

public class Icon extends Composite implements HasText {
	
	public static String FOLDER_ICON = "./images/Folder.png";
	private static IconUiBinder uiBinder = GWT.create(IconUiBinder.class);
	
	interface IconUiBinder extends UiBinder<Widget, Icon> {
	}
	
	public Icon() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField Image image;
	@UiField Label text;
	@UiField FocusPanel panel;
	
	public Icon(Image image, String text) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image = image;
		this.text.setText(text);
	}
	
	public Icon(String imageURL, String text) {
		initWidget(uiBinder.createAndBindUi(this));
		this.image.setUrl(imageURL);
		this.text.setText(text);
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
	public String getText() {
		return text.getText();
	}
	
	public void addDoubleClickHandler(DoubleClickHandler handler) {
		panel.addDoubleClickHandler(handler);
	}
	
	/**
	 * @deprecated Not implemented yet, use addDoubleClickHandler()
	 *             <p>
	 *             It overrides the DoubleClickHandler in case it was added with the addDoubleClickHandler method
	 */
	public void addOpenFolderHandler() {
		
	}
	
}
