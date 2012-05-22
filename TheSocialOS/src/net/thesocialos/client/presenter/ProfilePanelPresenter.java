package net.thesocialos.client.presenter;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.event.AvatarUpdateEvent;
import net.thesocialos.client.event.AvatarUpdateEventHandler;
import net.thesocialos.client.view.PopAsker;
import net.thesocialos.client.view.profile.ProfileAttr;
import net.thesocialos.client.view.profile.ProfileAttrArea;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ProfilePanelPresenter implements Presenter {
	
	public interface Display {
		Widget asWidget();
		
		ProfileAttr getAddress();
		
		ProfileAttr getEmail();
		
		ProfileAttr getMobile();
		
		ProfileAttr getName();
		
		ProfileAttr getSurname();
		
		ProfileAttr getUserTitle();
		
		ProfileAttrArea getBio();
		
		Button getButton();
		
		Image getAvatar();
		
		FileUpload getExamineButton();
		
		void setEditable(Boolean editable);
		
		boolean getEditable();
	}
	
	SimpleEventBus eventBus;
	Display display;
	
	public String value;
	
	public ProfilePanelPresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}
	
	public void bind() {
		
		display.getButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (display.getEditable() == true) checkFields(new AsyncCallback<Boolean>() {
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSuccess(Boolean result) {
						// TODO Auto-generated method stub
						if (result) display.setEditable(!display.getEditable());
					}
				});
				else
					display.setEditable(true);
			}
		});
		
		TheSocialOS.getEventBus().addHandler(AvatarUpdateEvent.TYPE, new AvatarUpdateEventHandler() {
			
			@Override
			public void onAvatarUpdate(AvatarUpdateEvent event) {
				if (CacheLayer.UserCalls.getAvatar() != null) ;
				display.getAvatar().setUrl(CacheLayer.UserCalls.getUser().getUrlAvatar());
			}
		});
	}
	
	private void checkFields(AsyncCallback<Boolean> callback) {
		boolean check = true;
		
		if (display.getName().getAttrValue().getText().trim().isEmpty()) {
			display.getName().setError();
			check = false;
		} else
			display.getName().setEditable(true);
		if (display.getSurname().getAttrValue().getText().trim().isEmpty()) {
			display.getSurname().setError();
			check = false;
		} else
			display.getSurname().setEditable(true);
		if (display.getMobile().getAttrValue().getText().trim().isEmpty()) {
			display.getMobile().setError();
			check = false;
		} else
			display.getMobile().setEditable(true);
		if (display.getAddress().getAttrValue().getText().trim().isEmpty()) {
			display.getAddress().setError();
			check = false;
		} else
			display.getAddress().setEditable(true);
		if (display.getBio().getAttrValue().getText().trim().isEmpty()) {
			display.getBio().setError();
			check = false;
		} else
			display.getBio().setEditable(true);
		if (check) CacheLayer.UserCalls.updateUser(display.getName().getAttrValue().getText(), display.getSurname()
				.getAttrValue().getText(), display.getAddress().getAttrValue().getText(), display.getMobile()
				.getAttrValue().getText(), display.getBio().getAttrValue().getText(), callback);
		else
			callback.onSuccess(false);
	}
	
	public PopAsker getNewValueAsker(String valueName) {
		value = null;
		final PopupPanel popup = new PopupPanel(true);
		popup.setWidth("40%");
		PopAsker asker = new PopAsker(valueName);
		popup.add(asker);
		asker.setWidth("100%");
		asker.getCancelButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				popup.hide();
			}
		});
		popup.center();
		return asker;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}
	
}
