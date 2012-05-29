package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChngPasswordPopUP extends Composite {
	
	private static ChngPasswordPopUPUiBinder uiBinder = GWT.create(ChngPasswordPopUPUiBinder.class);
	@UiField Label lblState;
	@UiField Button btnSave;
	@UiField PasswordTextBox txtOldPass;
	@UiField PasswordTextBox txtNewPass1;
	@UiField PasswordTextBox txtNewPass2;
	@UiField Label lblOldPass;
	@UiField Label lblNewPass1;
	@UiField Label lblNewPass2;
	@UiField HTMLPanel htmlPanel;
	private final UserServiceAsync userService = GWT.create(UserService.class);
	PopupPanel popUP = new PopupPanel(true, true);
	final Timer timer = new Timer() {
		
		@Override
		public void run() {
			
			popUP.hide();
			
		}
	};
	
	interface ChngPasswordPopUPUiBinder extends UiBinder<Widget, ChngPasswordPopUP> {
	}
	
	public ChngPasswordPopUP() {
		initWidget(uiBinder.createAndBindUi(this));
		lblState.setText("");
		btnSave.setText(TheSocialOS.getConstants().change());
		lblOldPass.setText(TheSocialOS.getConstants().oldPass());
		lblNewPass1.setText(TheSocialOS.getConstants().newPassword1());
		lblNewPass2.setText(TheSocialOS.getConstants().newPassword2());
		txtOldPass.setText("");
		txtNewPass1.setText("");
		txtNewPass1.setText("");
		bind();
		handler();
		
	}
	
	private void bind() {
		popUP.setWidth("297px");
		popUP.setHeight("204px");
		popUP.add(this);
		popUP.center();
		popUP.show();
	}
	
	private void handler() {
		btnSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (isPasswordsEquals()) changePassword(new AsyncCallback<Boolean>() {
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							lblState.setText(TheSocialOS.getConstants().passwordChangeCorretly());
							timer.schedule(2000);
						} else
							lblState.setText(TheSocialOS.getConstants().passworNotValid());
						
					}
				});
				else
					lblState.setText(TheSocialOS.getConstants().passwordDontMatch());
				
			}
		});
		
	}
	
	private boolean isPasswordsEquals() {
		if (txtNewPass1.getText().trim().isEmpty() || txtNewPass2.getText().trim().isEmpty()) return false;
		if (txtNewPass2.getText().trim().length() < 6) return false;
		return txtNewPass1.getText().trim().equals(txtNewPass2.getText().trim());
	}
	
	private void changePassword(final AsyncCallback<Boolean> callback) {
		
		new RPCXSRF<Boolean>(userService) {
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
			
			@Override
			public void onSuccess(Boolean result) { // User registered succesfully
				callback.onSuccess(result);
				timer.schedule(4000);
			}
			
			@Override
			protected void XSRFcallService(AsyncCallback<Boolean> cb) {
				
				userService.changePassWord(txtOldPass.getText().trim(), txtNewPass2.getText().trim(), cb);
			}
		}.retry(3);
	}
	
}
