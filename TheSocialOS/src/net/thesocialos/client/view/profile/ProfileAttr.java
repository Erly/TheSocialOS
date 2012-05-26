package net.thesocialos.client.view.profile;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.event.AccountAddedEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

public class ProfileAttr extends Composite {
	
	interface ProfileAttrUiBinder extends UiBinder<FocusPanel, ProfileAttr> {
	}
	
	private static ProfileAttrUiBinder uiBinder = GWT.create(ProfileAttrUiBinder.class);
	@UiField Label attrName;
	@UiField TextBox attrValue;
	
	@UiField FocusPanel focusPanel;
	@UiField Image closeButton;
	@UiField HorizontalPanel hPanel;
	
	public ProfileAttr() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public FocusPanel getFocusPanel() {
		return focusPanel;
	}
	
	public void setAttrLink(final String url, final String name) {
		attrValue.setEnabled(true);
		
		attrValue.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				PopupPanel popup = new PopupPanel(false, true);
				popup.addCloseHandler(new CloseHandler<PopupPanel>() {
					
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						
						TheSocialOS.getEventBus().fireEvent(new AccountAddedEvent());
					}
				});
				Window.open(url, name, "status=0,toolbar=0,location=0,menubar=0,height=600,width=700");
				attrValue.setFocus(false);
			}
		});
	}
	
	public void setAttrName(String name) {
		attrName.setText(name);
	}
	
	public void setAttrValue(String value) {
		attrValue.setText(value);
	}
	
	public TextBox getAttrValue() {
		return attrValue;
	}
	
	public Label getAttrName() {
		return attrName;
	}
	
	public void setEditable(Boolean editable) {
		attrValue.setEnabled(editable);
		focusPanel.setStyleName("profileAttr-editable", editable);
		focusPanel.setStyleName("profileAttr", !editable);
		focusPanel.setStyleName("profileAttr-error", false);
	}
	
	public void setCloseEnable(boolean enable) {
		if (!enable) hPanel.remove(closeButton);
	}
	
	public void setError() {
		focusPanel.setStyleName("profileAttr-error", true);
		focusPanel.setStyleName("profileAttr-editable", false);
		
		focusPanel.setStyleName("profileAttr", false);
	}
	
	public boolean getEditable() {
		// TODO Auto-generated method stub
		return attrValue.isEnabled();
	}
	
	public Image getCloseButton() {
		return closeButton;
	}
}
