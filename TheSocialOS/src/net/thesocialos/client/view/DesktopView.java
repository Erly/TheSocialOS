package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.presenter.DesktopPresenter.Display;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;

public class DesktopView extends Composite implements Display {

	private static DesktopUiBinder uiBinder = GWT.create(DesktopUiBinder.class);
	@UiField DesktopBar desktopBar;
	@UiField StartMenu startMenu;
	@UiField UserMenu userMenu;
	@UiField AbsolutePanel desktop;
	@UiField Image background;

	interface DesktopUiBinder extends UiBinder<Widget, DesktopView> {
	}

	public DesktopView() {
		initWidget(uiBinder.createAndBindUi(this));
		userMenu.editProfile.setText(TheSocialOS.getConstants().editProfile());
		userMenu.logout.setText(TheSocialOS.getConstants().logout());
		User user = TheSocialOS.get().getCurrentUser();
		// desktop.getElement().getStyle().setBackgroundImage("url(/images/defaultBG.png) no-repeat");
		if (!user.getBackground().equals("data:image/png;base64,null"))
			background.setUrl(user.getBackground());
	}

	@Override
	public HasClickHandlers getUserButton() {
		return desktopBar.userPanel;
	}

	@Override
	public Widget getUserMenu() {
		return userMenu.asWidget();
	}

	@Override
	public HasText getUserName() {
		return desktopBar.username;
	}

	@Override
	public Image getAvatar() {
		return userMenu.avatar;
	}

	@Override
	public HasText getNameLabel() {
		return userMenu.name;
	}

	@Override
	public HasText getEmail() {
		return userMenu.email;
	}

	@Override
	public HasText getEditProfile() {
		return userMenu.editProfile;
	}

	@Override
	public HasText getLogout() {
		return userMenu.logout;
	}

	@Override
	public HasText getTitleLabel() {
		return userMenu.title;
	}

	@Override
	public HasText getClock() {
		return desktopBar.clock;
	}

	@Override
	public HasClickHandlers getStartButton() {
		return desktopBar.startButton;
	}

	@Override
	public AbsolutePanel getDesktop() {
		return desktop;
	}

	@Override
	public FocusPanel getLogoutPanel() {
		return userMenu.logoutPanel;
	}
	
	@Override
	public HasClickHandlers getEditProfilePanel() {
		return userMenu.editProfilePanel;
	}

	@Override
	public HasBlurHandlers getUserMenuPanel() {
		return userMenu.mainFocusPanel;
	}

	@Override
	public Widget getStartMenu() {
		return startMenu;
	}

	@Override
	public HasBlurHandlers getStartMenuPanel() {
		return startMenu.getStartPanel();
	}

	@Override
	public HasClickHandlers getSocialOSButton() {
		return desktopBar.socialOSButton;
	}
}
