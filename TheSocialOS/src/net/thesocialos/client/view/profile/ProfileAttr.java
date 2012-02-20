package net.thesocialos.client.view.profile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;

public class ProfileAttr extends Composite {

	private static ProfileAttrUiBinder uiBinder = GWT
			.create(ProfileAttrUiBinder.class);
	@UiField Label attrName;
	@UiField Label attrValue;
	@UiField FocusPanel focusPanel;

	interface ProfileAttrUiBinder extends UiBinder<FocusPanel, ProfileAttr> {
	}

	public ProfileAttr() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public FocusPanel getFocusPanel() {
		return focusPanel;
	}

	public void setAttrName(String name) {
		attrName.setText(name);
	}
	
	public void setAttrValue(String value) {
		attrValue.setText(value);
	}
}
