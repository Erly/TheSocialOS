package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.presenter.RequestPasswordPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RequestPassWordView extends Composite implements Display {
	
	private static RequestPassWordViewUiBinder uiBinder = GWT.create(RequestPassWordViewUiBinder.class);
	@UiField Button btnSend;
	@UiField TextBox textEmail;
	@UiField Label lblNotValid;
	
	interface RequestPassWordViewUiBinder extends UiBinder<Widget, RequestPassWordView> {
	}
	
	public RequestPassWordView() {
		initWidget(uiBinder.createAndBindUi(this));
		btnSend.setText(TheSocialOS.getConstants().send());
	}
	
	@Override
	public Label getErrorLabel() {
		// TODO Auto-generated method stub
		return lblNotValid;
	}
	
	@Override
	public Button getButton() {
		// TODO Auto-generated method stub
		return btnSend;
	}
	
	@Override
	public TextBox getEmailText() {
		// TODO Auto-generated method stub
		return textEmail;
	}
	
}
