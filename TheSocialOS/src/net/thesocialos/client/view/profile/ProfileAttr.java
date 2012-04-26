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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class ProfileAttr extends Composite {
	
	interface ProfileAttrUiBinder extends UiBinder<FocusPanel, ProfileAttr> {
	}
	
	private static ProfileAttrUiBinder uiBinder = GWT.create(ProfileAttrUiBinder.class);
	@UiField Label attrName;
	@UiField Label attrValue;
	
	@UiField FocusPanel focusPanel;
	
	public ProfileAttr() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public FocusPanel getFocusPanel() {
		return focusPanel;
	}
	
	public void setAttrLink(final String url, final String name) {
		attrValue.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				PopupPanel popup = new PopupPanel(false, true);
				popup.addCloseHandler(new CloseHandler<PopupPanel>() {
					
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						TheSocialOS.get();
						TheSocialOS.getEventBus().fireEvent(new AccountAddedEvent());
					}
				});
				Window.open(url, name, "status=0,toolbar=0,location=0,menubar=0,height=600,width=700");
			}
		});
	}
	
	public void setAttrName(String name) {
		attrName.setText(name);
	}
	
	public void setAttrValue(String value) {
		attrValue.setText(value);
	}
}
