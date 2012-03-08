package net.thesocialos.client.view.profile;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.presenter.ProfilePanelPresenter.Display;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;

public class ProfilePanel extends Composite implements Display {

	private static ProfilePanelUiBinder uiBinder = GWT
			.create(ProfilePanelUiBinder.class);
	@UiField ProfileAttr name;
	@UiField ProfileAttr title;
	@UiField ProfileAttr email;
	@UiField ProfileAttr mobile;
	@UiField ProfileAttr address;

	interface ProfilePanelUiBinder extends UiBinder<Widget, ProfilePanel> {
	}

	public ProfilePanel() {
		initWidget(uiBinder.createAndBindUi(this));
		User user = TheSocialOS.get().getCurrentUser();
		name.attrName.setText(TheSocialOS.getConstants().name());
		name.attrValue.setText(user.getName() + " " + user.getLastName());
		title.attrName.setText(TheSocialOS.getConstants().title());
		title.attrValue.setText(user.getRole());
		//email.attrName.setText(TheSocialOS.getConstants()); Email is always email
		email.attrValue.setText(user.getEmail());
		mobile.attrName.setText(TheSocialOS.getConstants().mobile());
		mobile.attrValue.setText(user.getMobilePhone());
		address.attrName.setText(TheSocialOS.getConstants().address());
		address.attrValue.setText(user.getAddress());
		
	}

	@Override
	public ProfileAttr getName() {
		return name;
	}
	
	@Override
	public ProfileAttr getUserTitle() {
		return title;
	}

	@Override
	public ProfileAttr getEmail() {
		return email;
	}

	@Override
	public ProfileAttr getMobile() {
		return mobile;
	}

	@Override
	public ProfileAttr getAddress() {
		return address;
	}
}
