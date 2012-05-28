package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
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
	@UiField Image avatarImg;
	
	public PopUpInfoContact(String email, String name, String surname, String image) {
		super(true);
		add(binder.createAndBindUi(this));
		// initWidget(uiBinder.createAndBindUi(this));
		
		lblEmailText.setText(email);
		lblName.setText(name);
		lblSurname.setText(surname);
		lblName.setLabelText(TheSocialOS.getConstants().name());
		lblSurname.setLabelText(TheSocialOS.getConstants().lastName());
		if (image == null) avatarImg.setUrl("images/anonymous_avatar.png");
		else
			avatarImg.setUrl(image);
		
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
