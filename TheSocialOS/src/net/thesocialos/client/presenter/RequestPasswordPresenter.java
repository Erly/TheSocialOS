package net.thesocialos.client.presenter;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RequestPasswordPresenter implements Presenter {
	
	Display display;
	private final UserServiceAsync userService = GWT.create(UserService.class);
	PopupPanel panel = new PopupPanel(false, true);
	
	public RequestPasswordPresenter(Display display) {
		this.display = display;
		panel.setHeight("130px");
		panel.setWidth("271px");
	}
	
	public interface Display {
		Widget asWidget();
		
		Label getErrorLabel();
		
		Button getButton();
		
		TextBox getEmailText();
	}
	
	@Override
	public void go(HasWidgets container) {
		
		panel.add(display.asWidget());
		panel.center();
		panel.show();
		
		bind();
		
	}
	
	private native boolean isValidEmail(String email) /*-{
		var reg1 = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/; // not valid
		var reg2 = /^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,3}|[0-9]{1,3})(\]?)$/; // valid
		return !reg1.test(email) && reg2.test(email);
	}-*/;
	
	private void bind() {
		display.getErrorLabel().setText("");
		display.getEmailText().setText("");
		display.getButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (isValidEmail(display.getEmailText().getText())) {
					display.getErrorLabel().setText("");
					sendEmail(display.getEmailText().getText());
				} else
					display.getErrorLabel().setText(TheSocialOS.getConstants().error_Email());
			}
		});
	}
	
	private void sendEmail(final String email) {
		
		final Timer timer = new Timer() {
			
			@Override
			public void run() {
				
				panel.hide();
				History.newItem("login");
				display.asWidget().setVisible(false);
				
			}
		};
		
		new RPCXSRF<Void>(userService) {
			
			@Override
			public void onFailure(Throwable caught) {
				display.getErrorLabel().setText(TheSocialOS.getMessages().error_EmailnotFound(email));
				
			}
			
			@Override
			public void onSuccess(Void result) { // User registered succesfully
				display.getErrorLabel().setText(TheSocialOS.getConstants().passwordResetCorretly());
				timer.schedule(4000);
			}
			
			@Override
			protected void XSRFcallService(AsyncCallback<Void> cb) {
				
				userService.sendResetPass(email, cb);
			}
		}.retry(3);
	}
}
