package net.thesocialos.client.presenter;

import java.util.Iterator;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.event.AvatarUpdateEvent;
import net.thesocialos.client.event.AvatarUpdateEventHandler;
import net.thesocialos.client.view.PopAsker;
import net.thesocialos.client.view.profile.ProfileAttr;
import net.thesocialos.client.view.profile.ProfileAttrArea;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Facebook;
import net.thesocialos.shared.model.FlickR;
import net.thesocialos.shared.model.Google;
import net.thesocialos.shared.model.Twitter;

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
import com.googlecode.objectify.Key;

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
		
		ProfileAttr getFacebook();
		
		ProfileAttr getTwitter();
		
		ProfileAttr getFlickR();
		
		ProfileAttr getGoogle();
		
		String getGoogleURL();
		
		String getFlickRURL();
		
		String getTwitterURL();
		
		String getFacebookURL();
		
		Button getButton();
		
		Image getAvatar();
		
		FileUpload getExamineButton();
		
		void setEditable(Boolean editable);
		
		boolean getEditable();
	}
	
	SimpleEventBus eventBus;
	Display display;
	
	public String value;
	
	Google googleAccount;
	Facebook facebookAccount;
	Twitter twitterAccount;
	FlickR flickrAccount;
	
	public ProfilePanelPresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		populateAccountsMap();
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
		
		display.getFlickR().getCloseButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				DeleteAccount(flickrAccount);
				
			}
		});
		
		display.getFacebook().getCloseButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				DeleteAccount(facebookAccount);
			}
		});
		display.getTwitter().getCloseButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				DeleteAccount(twitterAccount);
			}
		});
		display.getGoogle().getCloseButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				DeleteAccount(googleAccount);
			}
		});
	}
	
	private void DeleteAccount(Account account) {
		if (null != account)
			CacheLayer.UserCalls.deleteAccount(account, new AsyncCallback<Map<Key<Account>, Account>>() {
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onSuccess(Map<Key<Account>, Account> result) {
					// TODO Auto-generated method stub
					populateAccountsMap();
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
	
	private void populateAccountsMap() {
		// accounts =
		CacheLayer.UserCalls.getAccounts(true, new AsyncCallback<Map<Key<Account>, Account>>() {
			
			@Override
			public void onSuccess(Map<Key<Account>, Account> result) {
				
				Iterator<Account> it = result.values().iterator();
				while (it.hasNext()) {
					Account account = it.next();
					if (account instanceof Google) googleAccount = (Google) account;
					else if (account instanceof Facebook) facebookAccount = (Facebook) account;
					else if (account instanceof Twitter) twitterAccount = (Twitter) account;
					else if (account instanceof FlickR) flickrAccount = (FlickR) account;
					asingCloudAccounts();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void asingCloudAccounts() {
		display.getGoogle().setAttrName(TheSocialOS.getConstants().googleAccount());
		if (null != googleAccount) display.getGoogle().setAttrValue(googleAccount.getUsername()); // TODO change the
																									// email for
		// the Google Account email
		else {
			display.getGoogle().getAttrValue().addStyleName("hand");
			display.getGoogle().setAttrLink(display.getGoogleURL(), "Google Account login");
			
		}
		
		display.getFacebook().setAttrName(TheSocialOS.getConstants().facebookAccount());
		if (null != facebookAccount) display.getFacebook().setAttrValue(facebookAccount.getUsername()); // TODO change
																										// the
		// email for
		// the Google Account email
		else {
			display.getFacebook().getAttrValue().addStyleName("hand");
			display.getFacebook().setAttrLink(display.getFacebookURL(), "Facebook Account Login");
			
		}
		
		display.getTwitter().setAttrName(TheSocialOS.getConstants().twitterAccount());
		if (null != twitterAccount) display.getTwitter().setAttrValue(twitterAccount.getUsername()); // TODO change the
																										// email for
		// the Google Account email
		else {
			display.getTwitter().getAttrValue().addStyleName("hand");
			display.getTwitter().setAttrLink(display.getTwitterURL(), "Twitter Account Login");
			
		}
		
		display.getFlickR().setAttrName(TheSocialOS.getConstants().flickrAccount());
		if (null != flickrAccount) display.getFlickR().setAttrValue(flickrAccount.getUsername()); // TODO change the
																									// email for
		// the Google Account email
		else {
			display.getFlickR().getAttrValue().addStyleName("hand");
			display.getFlickR().setAttrLink(display.getFlickRURL(), "Flickr Account Login");
			
		}
		
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}
	
}
