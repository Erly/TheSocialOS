package net.thesocialos.client.desktop.window;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class Toolbar extends Composite {
	
	private static ToolbarUiBinder uiBinder = GWT.create(ToolbarUiBinder.class);
	
	interface ToolbarUiBinder extends UiBinder<Widget, Toolbar> {
	}
	
	public Toolbar() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField FocusPanel back;
	@UiField FocusPanel forward;
	@UiField FocusPanel refresh;
	@UiField Image backImage;
	@UiField Image forwardImage;
	@UiField Image refreshImage;
	
	public FocusPanel getBackButton() {
		return back;
	}
	
	public FocusPanel getForwardButton() {
		return forward;
	}
	
	public FocusPanel getRefreshButton() {
		return refresh;
	}
	
	public void setBackImage(String url) {
		backImage.setUrl(url);
	}
	
	public void setForwardImage(String url) {
		forwardImage.setUrl(url);
	}
	
	public void setRefreshImage(String url) {
		refreshImage.setUrl(url);
	}
}
