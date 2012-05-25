package net.thesocialos.client.view.profile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ProfileAttrArea extends Composite {
	
	private static ProfileAttrAreaUiBinder uiBinder = GWT.create(ProfileAttrAreaUiBinder.class);
	@UiField TextArea attrValue;
	@UiField Label attrName;
	@UiField FocusPanel focusPanel;
	
	interface ProfileAttrAreaUiBinder extends UiBinder<Widget, ProfileAttrArea> {
	}
	
	public ProfileAttrArea() {
		initWidget(uiBinder.createAndBindUi(this));
		attrValue.getElement().setAttribute("maxlength", "50");
	}
	
	public void setAttrName(String name) {
		attrName.setText(name);
	}
	
	public void setAttrValue(String value) {
		attrValue.setText(value);
	}
	
	public void setEditable(Boolean editable) {
		attrValue.setEnabled(editable);
		focusPanel.setStyleName("profileAttr-editable", editable);
		focusPanel.setStyleName("profileAttr", !editable);
		focusPanel.setStyleName("profileAttr-error", false);
	}
	
	public void setError() {
		focusPanel.setStyleName("profileAttr-editable", false);
		focusPanel.setStyleName("profileAttr-error", true);
		focusPanel.setStyleName("profileAttr", false);
	}
	
	public FocusPanel getFocusPanel() {
		return focusPanel;
	}
	
	public TextArea getAttrValue() {
		return attrValue;
	}
	
}
