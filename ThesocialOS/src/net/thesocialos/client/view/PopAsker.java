package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class PopAsker extends Composite {

	private static PopAskerUiBinder uiBinder = GWT
			.create(PopAskerUiBinder.class);

	interface PopAskerUiBinder extends UiBinder<Widget, PopAsker> {
	}

	public PopAsker() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Label valueName;
	@UiField TextBox value;
	@UiField Button saveBtn;
	@UiField Button cancelBtn;

	public PopAsker(String valueName) {
		initWidget(uiBinder.createAndBindUi(this));
		this.valueName.setText(valueName);
	}

	public Button getSaveButton() {
		return saveBtn;
	}

	public Button getCancelButton() {
		return cancelBtn;
	}
	
	public String getValue() {
		return value.getValue();
	}
}
