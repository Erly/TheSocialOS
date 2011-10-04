package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.PopupPanel;

public class UserProfileView extends PopupPanel {

	private static UserProfileViewUiBinder uiBinder = GWT.create(UserProfileViewUiBinder.class);

	interface UserProfileViewUiBinder extends UiBinder<PopupPanel, UserProfileView> {
	}

	public UserProfileView() {
		super(false );
		add(uiBinder.createAndBindUi(this));
	}
}
