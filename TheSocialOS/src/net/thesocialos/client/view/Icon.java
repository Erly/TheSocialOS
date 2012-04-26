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

public class Icon extends Composite implements HasText {
	
	interface IconUiBinder extends UiBinder<Widget, Icon> {
	}
	
	public static String FOLDER_ICON = "./images/Folder.png";
	
	private static IconUiBinder uiBinder = GWT.create(IconUiBinder.class);
	
	@UiField Image image;
	
	@UiField Label text;
	@UiField FocusPanel panel;
	
	public Icon() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
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
	
	public void addDoubleClickHandler(DoubleClickHandler handler) {
		panel.addDoubleClickHandler(handler);
	}
	
	/**
	 * @deprecated Not implemented yet, use addDoubleClickHandler()
	 *             <p>
	 *             It overrides the DoubleClickHandler in case it was added with the addDoubleClickHandler method
	 */
	@Deprecated
	public void addOpenFolderHandler() {
		
	}
	
	@Override
	public String getText() {
		return text.getText();
	}
	
	@Override
	public void setText(String text) {
		this.text.setText(text);
	}
	
}
