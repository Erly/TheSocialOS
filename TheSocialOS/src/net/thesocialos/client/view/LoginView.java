package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.presenter.LoginPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class LoginView extends Composite implements Display {

	private static LoginViewUiBinder uiBinder = GWT
			.create(LoginViewUiBinder.class);
	@UiField Button loginButton;
	@UiField Hyperlink register;
	@UiField Hyperlink forgot;
	@UiField CheckBox keepLogged;
	@UiField Label lblEmail;
	@UiField Label lblPass;
	@UiField TextBox txtEmail;
	@UiField PasswordTextBox txtPass;
	@UiField Label lblIncorrect;

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}

	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
		lblPass.setText(TheSocialOS.getConstants().password());
		keepLogged.setText(TheSocialOS.getConstants().keepLogged());
		forgot.setText(TheSocialOS.getConstants().forgotPassword());
		register.setText(TheSocialOS.getConstants().registerNow());
		loginButton.setText(TheSocialOS.getConstants().login());
		lblIncorrect.setVisible(false);
	}

	@Override
	public HasClickHandlers getLoginButton() {
		return loginButton;
	}

	@Override
	public HasValue<String> getEmail() {
		return txtEmail;
	}

	@Override
	public HasValue<String> getPassword() {
		return txtPass;
	}

	@Override
	public HasText getIncorrectLabel() {
		return lblIncorrect;
	}

}
