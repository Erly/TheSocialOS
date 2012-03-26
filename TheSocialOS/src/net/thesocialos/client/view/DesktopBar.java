package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FocusPanel;

public class DesktopBar extends Composite {

	private static DesktopBarUiBinder uiBinder = GWT
			.create(DesktopBarUiBinder.class);

	interface DesktopBarUiBinder extends UiBinder<HorizontalPanel, DesktopBar> {
	}

	public DesktopBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Label clock;
	@UiField Label username;
	@UiField FocusPanel userPanel;
	@UiField FocusPanel startButton;
	@UiField FocusPanel socialOSButton;
	@UiField FocusPanel focusContacts;
	@UiField Label lblContacts;

	public FocusPanel getFocusContact(){
		return focusContacts;
	}
}
