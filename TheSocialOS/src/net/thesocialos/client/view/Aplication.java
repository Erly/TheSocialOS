package net.thesocialos.client.view;

import net.thesocialos.client.presenter.ApplicationManagerPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
	ApplicationManagerPresenter applicationManager;
	
	interface AplicationUiBinder extends UiBinder<Widget, Aplication> {
	}
	
	public Aplication() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public Aplication(String name, String url, ApplicationManagerPresenter applicationManager) {
		this();
		// setAplicationImage(url);
		setAplicationName(name);
		this.applicationManager = applicationManager;
		bind();
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
	
	private void bind() {
		getCloseButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		getApplicationPanel().addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				applicationManager.showHideApp(Aplication.this);
			}
		}, ClickEvent.getType());
	}
}
