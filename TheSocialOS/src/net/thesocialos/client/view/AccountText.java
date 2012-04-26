package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AccountText extends Composite {
	
	interface AccountTextUiBinder extends UiBinder<Widget, AccountText> {
	}
	private static AccountTextUiBinder uiBinder = GWT.create(AccountTextUiBinder.class);
	@UiField Button btnAccount;
	
	@UiField Label lblAccount;
	
	public AccountText() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setAccounts(String text) {
		btnAccount.setText(text);
	}
	
	public void setAccountText(String labelText) {
		lblAccount.setText(labelText);
	}
	
}
