package net.thesocialos.client.view;

import net.thesocialos.client.presenter.LoginPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite implements Display {
	
	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}
	
	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);
	
	@UiField InputElement email;
	@UiField InputElement password;
	@UiField InputElement loginButton;
	
	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
		/*
		 * lblPass.setText(TheSocialOS.getConstants().password());
		 * keepLogged.setText(TheSocialOS.getConstants().keepLogged());
		 * forgot.setText(TheSocialOS.getConstants().forgotPassword());
		 * register.setText(TheSocialOS.getConstants().registerNow());
		 * loginButton.setText(TheSocialOS.getConstants().login()); lblIncorrect.setVisible(false);
		 */
	}
	
	@Override
	public String getEmail() {
		return email.getValue();
	}
	
	/*
	 * @Override public boolean getKeepLoged() { return keepLogged.getValue(); }
	 */
	
	@Override
	public InputElement getLoginButton() {
		return loginButton;
	}
	
	@Override
	public String getPassword() {
		return password.getValue();
	}
	
	@Override
	public boolean getKeepLoged() {
		// TODO Auto-generated method stub
		return false;
	}
}
