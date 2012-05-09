package net.thesocialos.client.view;

import net.thesocialos.client.presenter.LoginPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class Newlogin extends UIObject implements Display {
	
	private static NewloginUiBinder uiBinder = GWT.create(NewloginUiBinder.class);
	
	interface NewloginUiBinder extends UiBinder<Element, Newlogin> {
	}
	
	@UiField InputElement email;
	@UiField InputElement password;
	@UiField ButtonElement loginButton;
	
	public Newlogin(String firstName) {
		setElement(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public String getEmail() {
		return email.getValue();
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
	
	@Override
	public InputElement getLoginButton() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return null;
	}
}
