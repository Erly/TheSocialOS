package net.thesocialos.client.view.profile;

import net.thesocialos.client.presenter.UserProfilePresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class UserProfileView extends Composite implements Display {
	
	interface UserProfileViewUiBinder extends UiBinder<Widget, UserProfileView> {
	}
	
	private static UserProfileViewUiBinder uiBinder = GWT.create(UserProfileViewUiBinder.class);
	
	@UiField SimplePanel mainPanel;
	@UiField Button closeButton;
	
	public UserProfileView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Image getAvatar() {
		return null;
	}
	
	@Override
	public SimplePanel getMainPanel() {
		return mainPanel;
	}
	
	@Override
	public HasClickHandlers getCloseButton() {
		return closeButton;
	}
}
