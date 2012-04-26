package net.thesocialos.client.view.profile;

import net.thesocialos.client.presenter.UserProfilePresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;

public class UserProfileView extends Composite implements Display {
	
	interface UserProfileViewUiBinder extends UiBinder<Widget, UserProfileView> {
	}
	private static UserProfileViewUiBinder uiBinder = GWT.create(UserProfileViewUiBinder.class);
	@UiField Button closeButton;
	@UiField ProfileLeftView leftCol;
	
	@UiField SimplePanel mainPanel;
	
	public UserProfileView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Image getAvatar() {
		return leftCol.photo;
	}
	
	@Override
	public Button getCloseButton() {
		return closeButton;
	}
	
	@Override
	public SimplePanel getMainPanel() {
		return mainPanel;
	}
}
