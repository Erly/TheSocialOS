package net.thesocialos.client;

import net.thesocialos.client.event.LogoutEvent;
import net.thesocialos.client.event.LogoutEventHandler;
import net.thesocialos.client.helper.RPCCall;
import net.thesocialos.client.presenter.DesktopPresenter;
import net.thesocialos.client.presenter.Presenter;
import net.thesocialos.client.presenter.RegisterPresenter;
import net.thesocialos.client.presenter.UserProfilePresenter;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.client.view.DesktopView;
import net.thesocialos.client.view.RegisterView;
import net.thesocialos.client.view.profile.UserProfileView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class AppController implements ValueChangeHandler<String> {

	private final UserServiceAsync userService = GWT.create(UserService.class);

	private final SimpleEventBus eventBus;
	
	private String lastToken = "";
	
	public AppController(SimpleEventBus eventBus) {
		this.eventBus = eventBus;
		bind(); // Bind the appController to History to control its changes
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		if (token != null) {
			Presenter presenter = null;
			if(TheSocialOS.get().getCurrentUser() != null)
				if (token.equals("desktop") && !lastToken.contains("profile")) {
					presenter = new DesktopPresenter(eventBus, new DesktopView());
					presenter.go(TheSocialOS.get().root);
				} else if (token.equals("profile")) {
					loadProfile(presenter);
				} else if (token.equals("profile-timeline")) {
					loadProfileTimeline(presenter);
				} else if (token.equals("profile-photos")) {
					loadProfilePhotos(presenter);
				} else if (token.equals("profile-music")) {
					loadProfileMusic(presenter);
				} else if (token.equals("profile-videos")) {
					loadProfileVideos(presenter);
				} else if (token.equals("profile-links")) {
					loadProfileLinks(presenter);
				} else {
					History.newItem("desktop");
				}
			else
				if (token.equals("register")) { 
					presenter = new RegisterPresenter(eventBus, new RegisterView());
					presenter.go(TheSocialOS.get().root);
					return;
				} else if (token.equals("login")) {
					TheSocialOS.get().showLoginView();
					return;
				} else
					History.newItem("login");
			lastToken = token;
		}
	}
	
	private void loadProfileLinks(Presenter presenter) {
		if (lastToken.equals("desktop")) { // If on the desktop, create the profile window.
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		} else if (!lastToken.contains("profile")) { // If not on desktop or another profile screen, create the desktop and the profile window.
			presenter = new DesktopPresenter(eventBus, new DesktopView());
			presenter.go(TheSocialOS.get().root);
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		}
		TheSocialOS.profilePresenter.goProfileLinks(); // Finally load the links screen on the profile window.
	}

	private void loadProfileVideos(Presenter presenter) {
		if (lastToken.equals("desktop")) {
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		} else if (!lastToken.contains("profile")) {
			presenter = new DesktopPresenter(eventBus, new DesktopView());
			presenter.go(TheSocialOS.get().root);
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		}
		TheSocialOS.profilePresenter.goProfileVideos();
	}

	private void loadProfileMusic(Presenter presenter) {
		if (lastToken.equals("desktop")) {
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		} else if (!lastToken.contains("profile")) {
			presenter = new DesktopPresenter(eventBus, new DesktopView());
			presenter.go(TheSocialOS.get().root);
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		}
		TheSocialOS.profilePresenter.goProfileMusic();		
	}

	private void loadProfilePhotos(Presenter presenter) {
		if (lastToken.equals("desktop")) {
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		} else if (!lastToken.contains("profile")) {
			presenter = new DesktopPresenter(eventBus, new DesktopView());
			presenter.go(TheSocialOS.get().root);
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		}
		TheSocialOS.profilePresenter.goProfilePhotos();
	}

	private void loadProfileTimeline(Presenter presenter) {
		if (lastToken.equals("desktop")) {
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		} else if (!lastToken.contains("profile")) {
			presenter = new DesktopPresenter(eventBus, new DesktopView());
			presenter.go(TheSocialOS.get().root);
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		}
		TheSocialOS.profilePresenter.goProfileTimeline();
	}

	private void loadProfile(Presenter presenter) {
		if (lastToken.equals("desktop")) { // If on the desktop, create the profile window.
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		} else if (!lastToken.contains("profile")) { // If not on desktop or another profile part, create the desktop and the profile window.
			presenter = new DesktopPresenter(eventBus, new DesktopView());
			presenter.go(TheSocialOS.get().root);
			TheSocialOS.profilePresenter = new UserProfilePresenter(eventBus, new UserProfileView());
			TheSocialOS.profilePresenter.go(TheSocialOS.get().getDesktop());
		}
		TheSocialOS.profilePresenter.goProfile(); // Load the main profile screen in the profile window.
	}

	/**
	 * Binds the History class to this ValueChangeHandler. And adds the eventHandlers to the eventBus.
	 */
	private void bind() {
		History.addValueChangeHandler(this);
		eventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
			
			@Override
			public void onLogout(LogoutEvent event) {
				doLogout();
			}
		});
	}
	
	/**
	 * Deletes the cookies, the session and calls the method to delete the user  
	 */
	protected void doLogout() {
		Cookies.removeCookie("JSESSIONID");
		Cookies.removeCookie("sid");
		Cookies.removeCookie("uid");
		TheSocialOS.get().deleteCurrentUser();
		new RPCCall<Void>() {

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				userService.logout(cb);
			}
		};
		History.newItem("login");
	}

	/**
	 * Method called from the main class during loading to fire the current state of the history and do some minor fixes to errors that can occur during the first load. 
	 */
	public void go() {
		if (History.getToken().contains("profile")) // This method can only be called when the web is loaded so lastToken is replaced by "" so the profile window is correctly loaded. Just in case there is some garbage in it.  
			lastToken = "";
		if (History.getToken().equals("")) { // If there is no History token, then go to the desktop
			History.newItem("desktop");
		} else {
			History.fireCurrentHistoryState();
		}
	}
}
