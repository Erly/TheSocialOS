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
import com.google.gwt.user.client.ui.PopupPanel;

public class PopUpInfoContact extends PopupPanel {

	private static PopUpInfoContactUiBinder uiBinder = GWT
			.create(PopUpInfoContactUiBinder.class);
	interface Binder extends UiBinder<Widget, PopUpInfoContact> { } 
	private static final Binder binder = GWT.create(Binder.class); 
	@UiField Label lblEmail;
	@UiField Label lblEmailText;
	@UiField LabelText lblName;
	@UiField LabelText lblSurname;


	interface PopUpInfoContactUiBinder extends
			UiBinder<Widget, PopUpInfoContact> {
	}

	public PopUpInfoContact(String email,String name,String surname) {
		super(true);
		add(binder.createAndBindUi(this));
		//initWidget(uiBinder.createAndBindUi(this));
		
		lblEmailText.setText(email);
		lblName.setText(name);
		lblSurname.setText(surname);
		
	}


	public Label getEmailLabel(){
		return lblEmail;
	}
	public Label getLblEmailText(){
		return lblEmailText;
	}
	public LabelText getLblTxtName(){
		return lblName;
	}
	
	public LabelText getLblTxtSurname(){
		return lblSurname;
	}

	


}
