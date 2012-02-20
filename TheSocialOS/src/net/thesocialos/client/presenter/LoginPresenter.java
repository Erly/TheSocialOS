package net.thesocialos.client.presenter;

import javax.servlet.http.Cookie;

import net.thesocialos.client.helper.RPCCall;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.shared.UserDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class LoginPresenter implements Presenter {

	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	public interface Display {
		HasClickHandlers getLoginButton();
		
		HasValue<String> getEmail();
		
		HasValue<String> getPassword();
		
		HasText getIncorrectLabel();
		
		Widget asWidget();
	}
	
	private final Display display;
	
	public LoginPresenter(SimpleEventBus eventBus, Display display) {
		this.display = display;
	}
	
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
		container.clear();
		container.add(display.asWidget());
		bind();
	}
	
	private void doLogin() {
		new RPCCall<String>() {

			@Override
			protected void callService(AsyncCallback<String> cb) {
				userService.login(LoginPresenter.this.display.getEmail().getValue().trim(), LoginPresenter.this.display.getPassword().getValue().trim(), cb);
			}
			
			@Override
			public void onSuccess(String result) {
				if(result == null) {
					Label incorrect = (Label) LoginPresenter.this.display.getIncorrectLabel();
					incorrect.setVisible(true);
				} else {
					//setCurrentUser(result);
					Cookie cookie = new Cookie("sid", result);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error: " + caught.getMessage());
			}
			
		}.retry(3);
	}

}
