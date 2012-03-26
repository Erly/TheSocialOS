package net.thesocialos.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.thesocialos.client.helper.Comet;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.i18n.SocialOSConstants;
import net.thesocialos.client.i18n.SocialOSMessages;
import net.thesocialos.client.presenter.BusyIndicatorPresenter;
import net.thesocialos.client.presenter.LoginPresenter;
import net.thesocialos.client.presenter.UserProfilePresenter;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.client.view.BusyIndicatorView;
import net.thesocialos.client.view.LoginView;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.model.User;
//import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.objectify.Key;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TheSocialOS implements EntryPoint {
	
	RootLayoutPanel root = RootLayoutPanel.get();
	
	private static TheSocialOS singleton;
	private AppController appControler;
	private AbsolutePanel desktop;
	private SimpleEventBus eventBus = new SimpleEventBus();

	BusyIndicatorPresenter busyIndicator = new BusyIndicatorPresenter(eventBus, new BusyIndicatorView());
	static UserProfilePresenter profilePresenter = null;
	private User user;
	private Map<Key<Account>, Account> accounts = new HashMap<Key<Account>, Account>();
	private Comet comet;
	
	// i18n initialization
	private static SocialOSConstants constants = GWT.create(SocialOSConstants.class);
	private static SocialOSMessages messages = GWT.create(SocialOSMessages.class);
	
	//SessionID and UserID
	private String jSessionID, sessionID, userID;
	
	// RPC Services
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		singleton = this;
		
		appControler = new AppController(eventBus);
		getLoggedUser();
	}
	
	/**
	 * Verifies if the user has a valid SessionID in a cookie.
	 * If not it checks if the token is 'login' or 'register', else it changes the token to 'login'.
	 * Finally it loads the view that corresponds with the token whether the user is logged or not.
	 */
	private void getLoggedUser() {
		jSessionID = Cookies.getCookie("JSESSIONID");
		sessionID = Cookies.getCookie("sid");
		//userID = Cookies.getCookie("uid");
		//final String[] ids = {sessionID, userID};
		Cookies.setCookie("XSRF", "buu");
		
		//Log.debug("CookieID -->" + sessionID);
		/*
		if(sessionID == null && Window.Location.getProtocol() != "https:" && !Window.Location.getHostName().trim().equals("127.0.0.1")) {
			System.out.println(Window.Location.getProtocol());
			String url = Window.Location.getHref();
			url = "https" + url.substring(url.indexOf(':'));
			Window.Location.assign(url);
		} else {
			appControler.go();
		}
		*/
		new RPCXSRF<User>(userService) {
			
			@Override
			protected void XSRFcallService(AsyncCallback<User> cb) {
				userService.getLoggedUser(sessionID, cb);
			}
			
			@Override
			public void onSuccess(User result) {
				if (result == null) {
					// User is NOT logged on
					if(History.getToken().equals("register")) 
						appControler.go();
					else if(History.getToken().equals("login"))
						showLoginView();
					else // If the token is not 'register' or 'login' then go to login, because a not logged user can't access the rest of the webapp
						History.newItem("login");
				} else {
					// User is loged in
					CacheLayer.setUser(result);
					//Window.alert("Cargando cuentas");
					//refreshCloudAccounts();
					new RPCXSRF<Map<Key<Account>, Account>>(userService) {

						@Override
						protected void XSRFcallService(AsyncCallback<Map<Key<Account>, Account>> cb) {
							userService.getCloudAccounts(cb);
						}
						
						@Override
						public void onSuccess(Map<Key<Account>, Account> accounts) {
							setCurrentUserAccounts(accounts);
							createUI();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							GWT.log(caught.getMessage());
							Window.alert("An error ocurred loading your third party srvies accounts. Please contact with support@thesocialos.net so we can resolve it");
							createUI();
						}
					}.retry(3);
					
					//createUI();
					
					//User listening to the channel push
					
					//comet = new Comet(eventBus);
					//comet.listenToChannel(user);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert(caught.getMessage());
			}			
		}.retry(3);
		
	}

	/**
	 * This method is called when the user is logged on and calls the app controller so it can create the UI that corresponds to the current token.
	 */
	protected void createUI() {
		if(History.getToken().equals("login") || History.getToken().equals("register")) //User can't go to login register page because he is already logged in.
			History.newItem("desktop");
		appControler.go();
		
	}
	
	/**
	 * Loads the login view.
	 */
	protected void showLoginView() {
		LoginPresenter loginPresenter = new LoginPresenter(eventBus, new LoginView());
		loginPresenter.go(root);
	}
	
	/**
	 * @return An instance of the main class.
	 */
	public static TheSocialOS get() {
		return singleton;
	}
	
	/**
	 * @return The class instance containing constant messages in different languages. 
	 */
	public static SocialOSConstants getConstants() {
		return constants;
	}
	
	/**
	 * @return The class instance containing dynamic messages in different languages.
	 */
	public static SocialOSMessages getMessages() {
		return messages;
	}
	
	/**
	 * @return The main eventBus instance.
	 */
	public SimpleEventBus getEventBus() {
		return eventBus;
	}
	
	/**
	 * @return the jSessionID
	 */
	public String getJSessionID() {
		return jSessionID;
	}
	
	/**
	 * @return the sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}
	
	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}
	
	/**
	 * @return The main DesktopView.
	 */
	public AbsolutePanel getDesktop() {
		return desktop;
	}
	
	/**
	 * Saves a desktop as the main desktop variable to be accessed later by other classes. 
	 * @param desktop The desktop to be saved
	 */
	public void setDesktop(AbsolutePanel desktop) {
		this.desktop = desktop;
	}
	
	public void refreshCloudAccounts() {
		new RPCXSRF<Map<Key<Account>, Account>>(userService) {

			@Override
			protected void XSRFcallService(AsyncCallback<Map<Key<Account>, Account>> cb) {
				userService.getCloudAccounts(cb);
			}
			
			@Override
			public void onSuccess(Map<Key<Account>, Account> accounts) {
				setCurrentUserAccounts(accounts);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert(caught.getMessage());
			}
		}.retry(3);
	}
	
	/**
	 * @param accounts the accounts to set
	 */
	public void setCurrentUserAccounts(Map<Key<Account>, Account> accounts) {
		this.accounts = accounts;
	}

	/**
	 * @return the accounts
	 */
	public Map<Key<Account>, Account> getCurrentUserAccounts() {
		return accounts;
	}
}
