package net.thesocialos.client.presenter;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.helper.RPCCall;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.shared.exceptions.UserExistsException;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class RegisterPresenter implements Presenter {
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	public interface Display {
		HasClickHandlers getRegisterButton();
		
		HasValue<String> getEmail();

		HasValue<String> getPassword();
		
		HasValue<String> getPassword2();

		HasValue<String> getName();
		
		HasValue<String> getLastName();
		
		HasText getIncorrect();
		
		Widget asWidget();
	}
	
	private Display display;

	public RegisterPresenter(SimpleEventBus eventBus, Display display) {
		this.display = display;
	}
	
	public void bind() {
		this.display.getRegisterButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doRegister();
			}
		});
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();	// Clear the screen
		container.add(display.asWidget());	// Print the register page in the screen
		bind();
	}

	private void doRegister() {
		Label incorrect = (Label) this.display.getIncorrect();
		if (this.display.getEmail().getValue().length() < 6 || (!this.display.getEmail().getValue().contains("@") || !this.display.getEmail().getValue().contains("."))) {
			incorrect.setText(TheSocialOS.getConstants().error_Email()); // Change the incorrect label text
			incorrect.setVisible(true);	// Make the incorrect label visible
			return;
		}
		if (this.display.getPassword().getValue().length() < 6) {
			incorrect.setText(TheSocialOS.getConstants().error_Password()); // Change the incorrect label text
			incorrect.setVisible(true);	// Make the incorrect label visible
			return;
		}
		if (!this.display.getPassword().getValue().equals(this.display.getPassword2().getValue())){ // Password and Retype password fields aren't equal
			incorrect.setText(TheSocialOS.getConstants().error_Password2()); // Change the incorrect label text
			incorrect.setVisible(true);	// Make the incorrect label visible 
			return;
		}
		if (this.display.getName().getValue().trim().isEmpty() || this.display.getLastName().getValue().trim().isEmpty()) {
			incorrect.setText(TheSocialOS.getConstants().error_emptyTxt()); // Change the incorrect label text
			incorrect.setVisible(true); // Make the incorrect label visible
			return;
		}
		new RPCCall<Void>() {

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				userService.register(display.getEmail().getValue().trim(), display.getPassword().getValue().trim(),
						display.getName().getValue().trim(), display.getLastName().getValue().trim(), cb);
			}
			
			@Override
			public void onSuccess(Void result) { // User registered succesfully
				Window.alert(TheSocialOS.getConstants().registerSuccesful());
				History.newItem("login"); // Redirect to login
			}
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage(), caught);
				if(caught.getClass() == UserExistsException.class)
					Window.alert(TheSocialOS.getMessages().error_UserExists(display.getEmail().getValue().trim()));
				else
					Window.alert("Error: " + caught.getMessage());
				
			}
		}.retry(3);
	}
}
