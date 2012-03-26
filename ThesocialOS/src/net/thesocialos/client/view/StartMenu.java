package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StartMenu extends Composite {

	private static StartMenuUiBinder uiBinder = GWT
			.create(StartMenuUiBinder.class);

	interface StartMenuUiBinder extends UiBinder<Widget, StartMenu> {
	}

	public StartMenu() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField FocusPanel startPanel;
	@UiField VerticalPanel startVPanel;
	
	public FocusPanel getStartPanel() {
		return startPanel;
	}
	public void setStartPanel(FocusPanel startPanel) {
		this.startPanel = startPanel;
	}
	public VerticalPanel getStartVPanel() {
		return startVPanel;
	}
	public void setStartVPanel(VerticalPanel startVPanel) {
		this.startVPanel = startVPanel;
	}
}
