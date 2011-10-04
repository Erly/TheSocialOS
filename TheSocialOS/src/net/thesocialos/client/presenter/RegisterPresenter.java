package net.thesocialos.client.presenter;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.helper.RPCCall;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;

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

public class RegisterPresenter implements Presenter {
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	public interface Display {
		HasClickHandlers getRegisterButton();
		
		HasValue<String> getEmail();
		
		HasValue<String> getPassword();
		
		HasValue<String> getPassword2();
		
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
		container.clear();
		container.add(display.asWidget());
		bind();
	}

	private void doRegister() {
		if(!this.display.getPassword().getValue().equals(this.display.getPassword2().getValue())){
			Label incorrect = (Label) this.display.getIncorrect();
			incorrect.setText(TheSocialOS.getConstants().error_Password2());
			incorrect.setVisible(true);
			return;
		}
		new RPCCall<Void>() {

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				userService.register(RegisterPresenter.this.display.getEmail().getValue().trim(), RegisterPresenter.this.display.getPassword().getValue().trim(), cb);
			}
			
			@Override
			public void onSuccess(Void result) {
				Window.alert(TheSocialOS.getConstants().registerSuccesful());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage(), caught);
				Window.alert("Error: " + caught.getMessage());
				/*if(caught.getClass() == UserExistsException.class) {
					
				}*/
			}
		}.retry(3);
	}
	
}
