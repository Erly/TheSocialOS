package net.thesocialos.client.view.profile;

import net.thesocialos.client.presenter.UserProfilePresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;

public class UserProfileView extends Composite implements Display{

	private static UserProfileViewUiBinder uiBinder = GWT.create(UserProfileViewUiBinder.class);
	@UiField Button closeButton;
	@UiField ProfileLeftView leftCol;
	@UiField SimplePanel mainPanel;

	interface UserProfileViewUiBinder extends UiBinder<Widget, UserProfileView> {
	}

	public UserProfileView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public Button getCloseButton() {
		return closeButton;
	}

	@Override
	public Image getAvatar() {
		return leftCol.photo;
	}

	@Override
	public SimplePanel getMainPanel() {
		return mainPanel;
	}
}
