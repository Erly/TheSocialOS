package net.thesocialos.client.presenter;

import net.thesocialos.client.view.profile.ProfilePanel;
import net.thesocialos.client.view.profile.TimelinePanel;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class UserProfilePresenter implements Presenter {
	
	public interface Display {
		Widget asWidget();
		
		Image getAvatar();
		
		/*
		 * HasClickHandlers getProfileButton(); HasClickHandlers getTimelineButton(); HasClickHandlers
		 * getPhotosButton(); HasClickHandlers getMusicButton(); HasClickHandlers getVideosButton(); HasClickHandlers
		 * getLinksButton(); void setData(UserDTO user);
		 */
		
		SimplePanel getMainPanel();
	}
	
	private final PopupPanel viewProfilePanel = new PopupPanel();
	
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
		
	}
	
	@Override
	public void go(final HasWidgets container) {
		
		// container.clear(); // Clear the screen
		viewProfilePanel.clear();
		
		viewProfilePanel.setGlassEnabled(true);
		viewProfilePanel.setModal(true);
		viewProfilePanel.setAnimationEnabled(true);
		viewProfilePanel.setStyleName("userProfile");
		// viewProfilePanel.addStyleName("userProfile");
		viewProfilePanel.add(display.asWidget());
		
		container.add(viewProfilePanel); // Print the userProfile in the screen
		viewProfilePanel.center();
		
		goProfile();
		bind();
	}
	
	/*
	 * public void fetchUserDTO() { new RPCXSRF<UserDTO>() {
	 * @Override protected void callService(AsyncCallback<UserDTO> cb) { } }; }
	 */
	
	/**
	 * Loads the main profile screen
	 */
	public void goProfile() {
		ProfilePanelPresenter presenter = new ProfilePanelPresenter(eventBus, new ProfilePanel());
		presenter.go(display.getMainPanel());
	}
	
	/**
	 * Loads the Links screen.
	 */
	public void goProfileLinks() {
		display.getMainPanel().clear();
		display.getMainPanel().add(new ProfilePanel());
	}
	
	/**
	 * Loads the Music screen.
	 */
	public void goProfileMusic() {
		display.getMainPanel().clear();
		display.getMainPanel().add(new ProfilePanel());
	}
	
	/**
	 * Loads the Photos screen.
	 */
	public void goProfilePhotos() {
		display.getMainPanel().clear();
		display.getMainPanel().add(new ProfilePanel());
	}
	
	/**
	 * Loads the Timeline screen.
	 */
	public void goProfileTimeline() {
		TimelinePresenter presenter = new TimelinePresenter(eventBus, new TimelinePanel());
		presenter.go(display.getMainPanel());
	}
	
	/**
	 * Loads the Videos screen.
	 */
	public void goProfileVideos() {
		display.getMainPanel().clear();
		display.getMainPanel().add(new ProfilePanel());
	}
}
