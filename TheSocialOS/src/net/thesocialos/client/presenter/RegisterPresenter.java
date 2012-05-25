package net.thesocialos.client.presenter;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.helper.ElementWrapper;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.shared.exceptions.UserExistsException;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class RegisterPresenter implements Presenter {
	
	HandlerRegistration handlerRegistration;
	
	public interface Display {
		Widget asWidget();
		
		String getEmail();
		
		HasText getIncorrect();
		
		String getLastName();
		
		String getName();
		
		String getPassword();
		
		String getPassword2();
		
		InputElement getRegisterButton();
	}
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	private Display display;
	
	public RegisterPresenter(SimpleEventBus eventBus, Display display) {
		this.display = display;
	}
	
	public void bind() {
		ElementWrapper wrapper = new ElementWrapper(display.getRegisterButton());
		wrapper.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doRegister();
			}
		});
		handlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
			
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				// TODO Auto-generated method stub
				if (event.getNativeEvent().getKeyCode() == 13) {
					display.getRegisterButton().click();
					handlerRegistration.removeHandler();
				}
				
			}
		});
		wrapper.onAttach();
	}
	
	private void doRegister() {
		Label incorrect = (Label) display.getIncorrect();
		if (display.getEmail().length() < 6 || (!display.getEmail().contains("@") || !display.getEmail().contains("."))) {
			incorrect.setText(TheSocialOS.getConstants().error_Email()); // Change the incorrect label text
			incorrect.setVisible(true); // Make the incorrect label visible
			return;
		}
		if (display.getPassword().length() < 6) {
			incorrect.setText(TheSocialOS.getConstants().error_Password()); // Change the incorrect label text
			incorrect.setVisible(true); // Make the incorrect label visible
			return;
		}
		if (!display.getPassword().equals(display.getPassword2())) { // Password and
																		// Retype
																		// password
																		// fields aren't
																		// equal
			incorrect.setText(TheSocialOS.getConstants().error_Password2()); // Change the incorrect label text
			incorrect.setVisible(true); // Make the incorrect label visible
			return;
		}
		if (display.getName().trim().isEmpty() || display.getLastName().trim().isEmpty()) {
			incorrect.setText(TheSocialOS.getConstants().error_emptyTxt()); // Change the incorrect label text
			incorrect.setVisible(true); // Make the incorrect label visible
			
			return;
		}
		new RPCXSRF<Void>(userService) {
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage(), caught);
				if (caught.getClass() == UserExistsException.class) Window.alert(TheSocialOS.getMessages()
						.error_UserExists(display.getEmail().trim()));
				else
					Window.alert("Error: " + caught.getMessage());
				
			}
			
			@Override
			public void onSuccess(Void result) { // User registered succesfully
				Window.alert(TheSocialOS.getConstants().registerSuccesful());
				History.newItem("login"); // Redirect to login
			}
			
			@Override
			protected void XSRFcallService(AsyncCallback<Void> cb) {
				
				userService.register(new User(display.getEmail().trim(), null, display.getPassword().trim(), null,
						display.getName().trim(), display.getLastName().trim(), "User", null), cb);
			}
		}.retry(3);
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear(); // Clear the screen
		container.add(display.asWidget()); // Print the register page in the screen
		bind();
	}
}
