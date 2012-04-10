package net.thesocialos.client.presenter;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;

import net.thesocialos.shared.LoginResult;
import net.thesocialos.shared.model.Account;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.HasRpcToken;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.XsrfToken;
import com.google.gwt.user.client.rpc.XsrfTokenService;
import com.google.gwt.user.client.rpc.XsrfTokenServiceAsync;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.objectify.Key;

public class LoginPresenter implements Presenter {

	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	public interface Display {
		HasClickHandlers getLoginButton();
		
		HasValue<String> getEmail();
		
		HasValue<String> getPassword();
		
		HasText getIncorrectLabel();
		
		Widget asWidget();

		boolean getKeepLoged();
	}
	
	private final Display display;
	
	public LoginPresenter(SimpleEventBus eventBus, Display display) {
		this.display = display;
	}
	
	/**
	 * Binds this presenter to its view and adds its handlers.
	 */
	public void bind() {
		this.display.getLoginButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doLogin();
			}
		});
	}
	
	@Override
	public void go(final HasWidgets container) {
		container.clear(); // Clear the screen
		container.add(display.asWidget()); // Print the login screen
		bind();
	}
	
	/**
	 * Make a request to the server to check if the user and password entered are correct and in case they are it checks if the checkbox to remember the user is checked,
	 * creates the necessary cookies, logs the user in and loads the desktop. 
	 */
	private void doLogin() {
		
		new RPCXSRF<LoginResult>(userService) {
			
			@Override
			protected void XSRFcallService(AsyncCallback<LoginResult> cb) {
				userService.login(LoginPresenter.this.display.getEmail().getValue().trim(),	
						LoginPresenter.this.display.getPassword().getValue().trim(), display.getKeepLoged(), cb);
			}
			public void onSuccess(LoginResult result) {
				if(result == null) {	// The user or password is incorrect 
					Label incorrect = (Label) LoginPresenter.this.display.getIncorrectLabel();
					incorrect.setVisible(true);
					
				} else { // The user exists and the password is correct
					//TheSocialOS.get().setCurrentUser(result.getUser());
					CacheLayer.UserCalls.setUser(result.getUser());
					if (result.getDuration() < 0){
						Cookies.setCookie("sid", result.getSessionID());
					}else{
						Date expires = new Date(System.currentTimeMillis() + result.getDuration());
						Cookies.setCookie("sid", result.getSessionID(), expires);
					}
					CacheLayer.UserCalls.getAccounts(false, new AsyncCallback<Map<Key<Account>, Account>>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(Map<Key<Account>, Account> result) {
							// TODO Auto-generated method stub
							
						}
					});
					
					History.newItem("desktop");
				}
			}
			public void onFailure(Throwable caught) {
				Window.alert("Error: " + caught.getMessage());
			}
		}.retry(3);
	}

}
