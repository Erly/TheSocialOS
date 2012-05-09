package net.thesocialos.client.presenter;

import java.util.Date;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.helper.ElementWrapper;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.shared.LoginResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class LoginPresenter implements Presenter {
	
	public interface Display {
		Widget asWidget();
		
		String getEmail();
		
		String getPassword();
		
		boolean getKeepLoged();
		
		InputElement getLoginButton();
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
		ElementWrapper wrapper = new ElementWrapper(display.getLoginButton());
		wrapper.onAttach();
		wrapper.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doLogin();
			}
		});
	}
	
	/**
	 * Make a request to the server to check if the user and password entered are correct and in case they are it checks
	 * if the checkbox to remember the user is checked, creates the necessary cookies, logs the user in and loads the
	 * desktop.
	 */
	private void doLogin() {
		Window.alert("buuuu");
		new RPCXSRF<LoginResult>(userService) {
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error: " + caught.getMessage());
			}
			
			@Override
			public void onSuccess(LoginResult result) {
				if (result == null) { // The user or password is incorrect
					// Label incorrect = (Label) display.getIncorrectLabel();
					// incorrect.setVisible(true);
					
				} else { // The user exists and the password is correct
					CacheLayer.UserCalls.setUser(result.getUser());
					if (result.getDuration() < 0) Cookies.setCookie("sid", result.getSessionID());
					else {
						Date expires = new Date(System.currentTimeMillis() + result.getDuration());
						Cookies.setCookie("sid", result.getSessionID(), expires);
					}
					CacheLayer.UserCalls.refreshAccounts();
					CacheLayer.UserCalls.refreshColumns();
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
	
}
