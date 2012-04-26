package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class StartMenu extends Composite {
	
	interface StartMenuUiBinder extends UiBinder<Widget, StartMenu> {
	}
	
	private static StartMenuUiBinder uiBinder = GWT.create(StartMenuUiBinder.class);
	
	@UiField FocusPanel startPanel;
	
	@UiField VerticalPanel startVPanel;
	
	public StartMenu() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public FocusPanel getStartPanel() {
		return startPanel;
	}
	
	public VerticalPanel getStartVPanel() {
		return startVPanel;
	}
	
	public void setStartPanel(FocusPanel startPanel) {
		this.startPanel = startPanel;
	}
	
	public void setStartVPanel(VerticalPanel startVPanel) {
		this.startVPanel = startVPanel;
	}
}
