package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Aplication extends Composite {
	
	private static AplicationUiBinder uiBinder = GWT.create(AplicationUiBinder.class);
	@UiField Label lblAplicationName;
	@UiField Image lblAplicationImage;
	@UiField Image closeButton;
	@UiField HorizontalPanel AplicationPanel;
	
	interface AplicationUiBinder extends UiBinder<Widget, Aplication> {
	}
	
	public Aplication() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setAplicationImage(String url) {
		lblAplicationImage.setUrl(url);
	}
	
	public void setAplicationName(String name) {
		lblAplicationName.setText(name);
	}
	
	public Image getCloseButton() {
		return closeButton;
	}
	
	public HorizontalPanel getApplicationPanel() {
		return AplicationPanel;
	}
}
