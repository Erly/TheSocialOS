package net.thesocialos.client.presenter;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.helper.RPCCall;
import net.thesocialos.client.view.profile.ProfilePanel;
import net.thesocialos.client.view.profile.TimelinePanel;
import net.thesocialos.shared.UserDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class UserProfilePresenter implements Presenter {
	
	private UserDTO user;
	private final PopupPanel viewProfilePanel = new PopupPanel();
	
	public interface Display {
		/*HasClickHandlers getProfileButton();
		HasClickHandlers getTimelineButton();
		HasClickHandlers getPhotosButton();
		HasClickHandlers getMusicButton();
		HasClickHandlers getVideosButton();
		HasClickHandlers getLinksButton();
		void setData(UserDTO user);
		*/
		HasClickHandlers getCloseButton();
		
		Image getAvatar();
		
		SimplePanel getMainPanel();
		
		Widget asWidget();
	}

	private SimpleEventBus eventBus;
	private final Display display;
	
	public UserProfilePresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}
	
	/**
	 * Binds this presenter to its view, loads data on its elements and adds its handlers.
	 */
	public void bind() {
		String imageUrl = TheSocialOS.get().getCurrentUser().getAvatar();
		if (imageUrl.equals("data:image/png;base64,null"))
			this.display.getAvatar().setUrl("./images/anonymous_avatar.png");
		else
			this.display.getAvatar().setUrl(imageUrl);
		this.display.getAvatar().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
			}
		});
	}
	
	@Override
	public void go(final HasWidgets container) {
		GWT.log("UserProfilePresenter.go");
		//container.clear();	// Clear the screen
		viewProfilePanel.clear();
		viewProfilePanel.setGlassEnabled(true);
		viewProfilePanel.setModal(true);
		viewProfilePanel.setAnimationEnabled(true);
		viewProfilePanel.addStyleName("userProfile");
		viewProfilePanel.add(display.asWidget());
		container.add(viewProfilePanel); // Print the userProfile in the screen
		viewProfilePanel.center();
		display.getCloseButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				viewProfilePanel.hide();
				History.newItem("desktop");
			}
		});
		goProfile();
		bind();
	}
	/*
	public void fetchUserDTO() {
		new RPCXSRF<UserDTO>() {

			@Override
			protected void callService(AsyncCallback<UserDTO> cb) {
			}
			
		};
	}*/
	
	/**
	 * Loads the main profile screen
	 */
	public void goProfile() {
		ProfilePanelPresenter presenter = new ProfilePanelPresenter(eventBus, new ProfilePanel());
		presenter.go(this.display.getMainPanel());
	}
	
	/**
	 * Loads the Timeline screen.
	 */
	public void goProfileTimeline() {
		TimelinePresenter presenter = new TimelinePresenter(eventBus, new TimelinePanel());
		presenter.go(this.display.getMainPanel());
	}
	
	/**
	 * Loads the Music screen.
	 */
	public void goProfileMusic() {
		this.display.getMainPanel().clear();
		this.display.getMainPanel().add(new ProfilePanel());
	}
	
	/**
	 * Loads the Photos screen.
	 */
	public void goProfilePhotos() {
		this.display.getMainPanel().clear();
		this.display.getMainPanel().add(new ProfilePanel());
	}
	
	/**
	 * Loads the Videos screen.
	 */
	public void goProfileVideos() {
		this.display.getMainPanel().clear();
		this.display.getMainPanel().add(new ProfilePanel());
	}
	
	/**
	 * Loads the Links screen.
	 */
	public void goProfileLinks() {
		this.display.getMainPanel().clear();
		this.display.getMainPanel().add(new ProfilePanel());
	}
}
