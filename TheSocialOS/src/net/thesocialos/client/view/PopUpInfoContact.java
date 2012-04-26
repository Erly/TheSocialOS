package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class PopUpInfoContact extends PopupPanel {
	
	interface Binder extends UiBinder<Widget, PopUpInfoContact> {
	}
	
	interface PopUpInfoContactUiBinder extends UiBinder<Widget, PopUpInfoContact> {
	}
	private static final Binder binder = GWT.create(Binder.class);
	@UiField Label lblEmail;
	@UiField Label lblEmailText;
	@UiField LabelText lblName;
	
	@UiField LabelText lblSurname;
	
	public PopUpInfoContact(String email, String name, String surname) {
		super(true);
		add(binder.createAndBindUi(this));
		// initWidget(uiBinder.createAndBindUi(this));
		
		lblEmailText.setText(email);
		lblName.setText(name);
		lblSurname.setText(surname);
		
	}
	
	public Label getEmailLabel() {
		return lblEmail;
	}
	
	public Label getLblEmailText() {
		return lblEmailText;
	}
	
	public LabelText getLblTxtName() {
		return lblName;
	}
	
	public LabelText getLblTxtSurname() {
		return lblSurname;
	}
	
}
