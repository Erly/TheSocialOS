package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.presenter.RegisterPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class RegisterView extends Composite implements Display {
	
	private static RegisterViewUiBinder uiBinder = GWT.create(RegisterViewUiBinder.class);
	@UiField Label lblIncorrect;
	@UiField Label lblEmail;
	@UiField Label lblPass;
	@UiField Label lblPass2;
	@UiField Label lblName;
	@UiField Label lblLastName;
	@UiField TextBox txtEmail;
	@UiField PasswordTextBox txtPass;
	@UiField PasswordTextBox txtPass2;
	@UiField TextBox txtName;
	@UiField TextBox txtLastName;
	@UiField Button registerButton;
	
	interface RegisterViewUiBinder extends UiBinder<DockLayoutPanel, RegisterView> {
	}
	
	public RegisterView() {
		initWidget(uiBinder.createAndBindUi(this));
		lblPass.setText(TheSocialOS.getConstants().password());
		lblPass2.setText(TheSocialOS.getConstants().password2());
		lblName.setText(TheSocialOS.getConstants().name());
		lblLastName.setText(TheSocialOS.getConstants().lastName());
		registerButton.setText(TheSocialOS.getConstants().register());
		lblIncorrect.setVisible(false);
	}
	
	@Override
	public HasClickHandlers getRegisterButton() {
		return registerButton;
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
	public HasValue<String> getPassword2() {
		return txtPass2;
	}
	
	@Override
	public HasText getIncorrect() {
		return lblIncorrect;
	}
	
	@Override
	public HasValue<String> getName() {
		return txtName;
	}
	
	@Override
	public HasValue<String> getLastName() {
		return txtLastName;
	}
	
}
