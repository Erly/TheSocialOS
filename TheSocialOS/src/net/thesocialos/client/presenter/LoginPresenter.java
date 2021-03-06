package net.thesocialos.client.presenter;

import java.util.Date;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.helper.ElementWrapper;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.shared.LoginResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class LoginPresenter implements Presenter {
	ElementWrapper wrapper;
	HandlerRegistration handlerRegistration;
	
	public interface Display {
		Widget asWidget();
		
		String getEmail();
		
		String getPassword();
		
		boolean getKeepLoged();
		
		InputElement getLoginButton();
		
		DivElement getError();
		
		Anchor getSpainFlag();
		
		Anchor getUsaFlag();
	}
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	private final Display display;
	
	public LoginPresenter(SimpleEventBus eventBus, Display display) {
		this.display = display;
	}
	
	/**
	 * Binds this presenter to its view and adds its handlers.
	 */
	public void bind() {
		wrapper = new ElementWrapper(display.getLoginButton());
		wrapper.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (display.getEmail().isEmpty() && display.getPassword().isEmpty()) {
					display.getError().getStyle().setDisplay(com.google.gwt.dom.client.Style.Display.BLOCK);
					display.getError().setInnerText(TheSocialOS.getConstants().error_Email());
				}
				
				doLogin();
			}
		});
		handlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
			
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				// TODO Auto-generated method stub
				if (event.getNativeEvent().getKeyCode() == 13) {
					display.getLoginButton().click();
					handlerRegistration.removeHandler();
				}
				
			}
		});
		wrapper.onAttach();
		display.getSpainFlag().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				changeLanguage("es");
			}
		});
		display.getUsaFlag().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				changeLanguage("en");
			}
		});
	}
	
	/**
	 * Make a request to the server to check if the user and password entered are correct and in case they are it checks
	 * if the checkbox to remember the user is checked, creates the necessary cookies, logs the user in and loads the
	 * desktop.
	 */
	private void doLogin() {
		new RPCXSRF<LoginResult>(userService) {
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error: " + caught.getMessage());
			}
			
			@Override
			public void onSuccess(LoginResult result) {
				if (result == null) {
					display.getError().getStyle().setDisplay(com.google.gwt.dom.client.Style.Display.BLOCK);
					display.getError().setInnerText(TheSocialOS.getConstants().error_login());
				} else { // The user exists and the password is correct
					CacheLayer.UserCalls.setUser(result.getUser());
					if (result.getDuration() < 0) Cookies.setCookie("sid", result.getSessionID());
					else {
						Date expires = new Date(System.currentTimeMillis() + result.getDuration());
						Cookies.setCookie("sid", result.getSessionID(), expires);
					}
					CacheLayer.UserCalls.refreshAccounts();
					CacheLayer.UserCalls.refreshColumns();
					TheSocialOS.startChannelApi();
					History.newItem("desktop");
				}
			}
			
			@Override
			protected void XSRFcallService(AsyncCallback<LoginResult> cb) {
				userService.login(display.getEmail().trim(), display.getPassword().trim(), display.getKeepLoged(), cb);
			}
		}.retry(3);
	}
	
	@Override
	public void go(final HasWidgets container) {
		container.clear(); // Clear the screen
		container.add(display.asWidget()); // Print the login screen
		bind();
	}
	
	public void changeLanguage(String language) {
		Cookies.setCookie("_lang", language);
		String url = Window.Location.getHref();
		if (url.contains("locale")) url = url.substring(0, url.indexOf("locale") - 1);
		else
			url = url.substring(0, url.indexOf("#"));
		Window.Location.assign(url);
	}
}
