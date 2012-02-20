package net.thesocialos.client;

import net.thesocialos.client.helper.RPCCall;
import net.thesocialos.client.i18n.SocialOSConstants;
import net.thesocialos.client.i18n.SocialOSMessages;
import net.thesocialos.client.presenter.BusyIndicatorPresenter;
import net.thesocialos.client.presenter.LoginPresenter;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.client.view.BusyIndicatorView;
import net.thesocialos.client.view.LoginView;
import net.thesocialos.shared.UserDTO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TheSocialOS implements EntryPoint {
	
	RootLayoutPanel root = RootLayoutPanel.get();
	
	private static TheSocialOS singleton;
	private AppController appControler;
	private SimpleEventBus eventBus = new SimpleEventBus();
	BusyIndicatorPresenter busyIndicator = new BusyIndicatorPresenter(eventBus, new BusyIndicatorView());
	
	// i18n initialization
	private static SocialOSConstants constants = GWT.create(SocialOSConstants.class);
	private static SocialOSMessages messages = GWT.create(SocialOSMessages.class);
	
	// RPC Services
	private UserServiceAsync userService = GWT.create(UserService.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		singleton = this;
		
		appControler = new AppController(eventBus);
		getLoggedUser();
	}
	
	private void getLoggedUser() {
		final String sessionID = Cookies.getCookie("sid");
		
		
		if(sessionID == null && Window.Location.getProtocol() != "https:" && !Window.Location.getHostName().trim().equals("127.0.0.1")) {
			System.out.println(Window.Location.getProtocol());
			String url = Window.Location.getHref();
			url = "https" + url.substring(url.indexOf(':'));
			Window.Location.assign(url);
		} else {
			Window.alert("El ID de sesión es: " + sessionID);
		}
		
		new RPCCall<UserDTO>() {

			@Override
			protected void callService(AsyncCallback<UserDTO> cb) {
				userService.getLoggedUser(sessionID, cb);
			}
			
			@Override public void onSuccess(UserDTO loggedUserDTO) {
				if (loggedUserDTO == null) {
					if(History.getToken().equals("register")){
						appControler.go();
					}
					showLoginView();
				} else {
					// User is loged in
					setCurrentUser(loggedUserDTO);
					createUI();
					
				}
			}
			
			@Override public void onFailure(Throwable caught) {
				Window.alert("Error: " + caught.getMessage());
			}
		}.retry(3);
	}

	protected void createUI() {
		// TODO Auto-generated method stub
		if(!History.getToken().equals("register")){
			appControler.go();
		}
	}

	protected void setCurrentUser(UserDTO loggedUserDTO) {
		// TODO Auto-generated method stub
		Window.alert("Tu usuario es: " + loggedUserDTO.getEmail());
	}

	protected void showLoginView() {
		LoginPresenter loginPresenter = new LoginPresenter(eventBus, new LoginView());
		loginPresenter.go(root);
	}

	public static TheSocialOS get() {
		return singleton;
	}
	
	public static SocialOSConstants getConstants() {
		return constants;
	}
	
	public static SocialOSMessages getMessages() {
		return messages;
	}
	
	public SimpleEventBus getEventBus() {
		return eventBus;
	}
}
