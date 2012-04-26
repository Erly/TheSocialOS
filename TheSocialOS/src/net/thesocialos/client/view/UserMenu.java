package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserMenu extends Composite {
	
	interface UserMenuUiBinder extends UiBinder<Widget, UserMenu> {
	}
	private static UserMenuUiBinder uiBinder = GWT.create(UserMenuUiBinder.class);
	@UiField Image avatar;
	@UiField Label name;
	@UiField Label title;
	@UiField Label email;
	@UiField Label editProfile;
	@UiField Label logout;
	@UiField FocusPanel editProfilePanel;
	@UiField FocusPanel logoutPanel;
	@UiField FocusPanel mainFocusPanel;
	
	@UiField VerticalPanel mainVerticalPanel;
	
	public UserMenu() {
		initWidget(uiBinder.createAndBindUi(this));
		// mainFocusPanel.setSize(mainVerticalPanel.getOffsetWidth() + "px" , mainVerticalPanel.getOffsetHeight() +
		// "px");
	}
	
}
