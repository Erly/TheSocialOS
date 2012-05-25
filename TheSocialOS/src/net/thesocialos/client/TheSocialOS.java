package net.thesocialos.client;

import java.util.Map;

import net.thesocialos.client.helper.ChannelApiHelper;
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
import net.thesocialos.shared.model.User;

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
	 * @return The main eventBus instance.
	 */
	public static SimpleEventBus getEventBus() {
		return eventBus;
	}
	
	/**
	 * @return The class instance containing dynamic messages in different languages.
	 */
	public static SocialOSMessages getMessages() {
		return messages;
	}
	
	RootLayoutPanel root = RootLayoutPanel.get();
	
	private static TheSocialOS singleton;
	private AppController appControler;
	private AbsolutePanel desktop;
	private static SimpleEventBus eventBus = new SimpleEventBus();
	
	BusyIndicatorPresenter busyIndicator = new BusyIndicatorPresenter(eventBus, new BusyIndicatorView());
	
	static UserProfilePresenter profilePresenter = null;
	
	// i18n initialization
	private static SocialOSConstants constants = GWT.create(SocialOSConstants.class);
	private static SocialOSMessages messages = GWT.create(SocialOSMessages.class);
	
	// SessionID and UserID
	private String jSessionID, sessionID, userID;
	
	// RPC Services
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	/**
	 * This method is called when the user is logged on and calls the app controller so it can create the UI that
	 * corresponds to the current token.
	 */
	protected void createUI() {
		// User can't go to login register page because he is already logged in.
		if (History.getToken().equals("login") || History.getToken().equals("register")) History.newItem("desktop");
		appControler.go();
	}
	
	/**
	 * @return The main DesktopView.
	 */
	public AbsolutePanel getDesktop() {
		return desktop;
	}
	
	/**
	 * @return the jSessionID
	 */
	public String getJSessionID() {
		return jSessionID;
	}
	
	public static void startChannelApi() {
		ChannelApiHelper.listenToChannel(CacheLayer.UserCalls.getUser());
	}
	
	/**
	 * Verifies if the user has a valid SessionID in a cookie. If not it checks if the token is 'login' or 'register',
	 * else it changes the token to 'login'. Finally it loads the view that corresponds with the token whether the user
	 * is logged or not.
	 */
	private void getLoggedUser() {
		jSessionID = Cookies.getCookie("JSESSIONID");
		sessionID = Cookies.getCookie("sid");
		// userID = Cookies.getCookie("uid");
		// final String[] ids = {sessionID, userID};
		Cookies.setCookie("XSRF", "buu");
		
		// Log.debug("CookieID -->" + sessionID);
		/*
		 * if(sessionID == null && Window.Location.getProtocol() != "https:" &&
		 * !Window.Location.getHostName().trim().equals("127.0.0.1")) {
		 * System.out.println(Window.Location.getProtocol()); String url = Window.Location.getHref(); url = "https" +
		 * url.substring(url.indexOf(':')); Window.Location.assign(url); } else { appControler.go(); }
		 */
		new RPCXSRF<User>(userService) {
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
				Window.alert(caught.getMessage());
			}
			
			@Override
			public void onSuccess(User result) {
				if (result == null) {
					// User is NOT logged on
					if (History.getToken().equals("register")) appControler.go();
					else if (History.getToken().equals("login")) showLoginView();
					else
						// If the token is not 'register' or 'login' then go to login, because a not logged user can't
						// access the rest of the webapp
						History.newItem("login");
				} else {
					// User is loged in
					CacheLayer.UserCalls.setUser(result);
					CacheLayer.UserCalls.refreshColumns();
					new RPCXSRF<Map<Key<Account>, Account>>(userService) {
						
						@Override
						public void onFailure(Throwable caught) {
							GWT.log(caught.getMessage());
							Window.alert("An error ocurred loading your third party services accounts. Please contact with support@thesocialos.net so we can resolve it");
							createUI();
						}
						
						@Override
						public void onSuccess(Map<Key<Account>, Account> accounts) {
							CacheLayer.UserCalls.setAccounts(accounts);
							createUI();
						}
						
						@Override
						protected void XSRFcallService(AsyncCallback<Map<Key<Account>, Account>> cb) {
							userService.getCloudAccounts(cb);
						}
					}.retry(3);
					
					// User listening to the channel push
					startChannelApi();
					// comet = new Comet(eventBus);
					// comet.listenToChannel(user);
				}
			}
			
			@Override
			protected void XSRFcallService(AsyncCallback<User> cb) {
				userService.getLoggedUser(sessionID, cb);
			}
		}.retry(3);
		
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
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		singleton = this;
		
		loadLanguage();
		appControler = new AppController(eventBus);
		getLoggedUser();
	}
	
	private void loadLanguage() {
		if (null != Cookies.getCookie("_lang")) {
			String lang = Cookies.getCookie("_lang");
			if (null == Window.Location.getParameter("locale")) {
				String url = Window.Location.getHref();
				if (url.contains("#")) url = url.substring(0, Window.Location.getHref().indexOf('#'));
				if (url.contains("?")) Window.Location.assign(url + "&locale=" + lang);
				else
					Window.Location.assign(url + "?locale=" + lang);
			}
		}
	}
	
	/**
	 * Saves a desktop as the main desktop variable to be accessed later by other classes.
	 * 
	 * @param desktop
	 *            The desktop to be saved
	 */
	public void setDesktop(AbsolutePanel desktop) {
		this.desktop = desktop;
	}
	
	/**
	 * Loads the login view.
	 */
	protected void showLoginView() {
		LoginPresenter loginPresenter = new LoginPresenter(eventBus, new LoginView());
		loginPresenter.go(root);
	}
}
