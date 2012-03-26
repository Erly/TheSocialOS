package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;

public class AccountText extends Composite {

	private static AccountTextUiBinder uiBinder = GWT
			.create(AccountTextUiBinder.class);
	@UiField Button btnAccount;
	@UiField Label lblAccount;

	interface AccountTextUiBinder extends UiBinder<Widget, AccountText> {
	}

	public AccountText() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public void setAccountText(String labelText){
		lblAccount.setText(labelText);
	}
	public void setAccounts(String text){
		btnAccount.setText(text);
	}

	
}
