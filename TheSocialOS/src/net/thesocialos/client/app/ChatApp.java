package net.thesocialos.client.app;



import java.util.List;

import net.thesocialos.client.event.MessageChatAvailableEvent;
import net.thesocialos.client.event.MessageChatAvailableEventHandler;
import net.thesocialos.client.helper.RPCCall;
import net.thesocialos.client.service.ChatService;
import net.thesocialos.client.service.ChatServiceAsync;
import net.thesocialos.client.view.chat.ChatPanel;
import net.thesocialos.shared.Chat;




import com.gargoylesoftware.htmlunit.javascript.host.KeyboardEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ChatApp extends Application {

	Display panel;
	String message= "hellow World";
	SimpleEventBus chatEventBus;
	private final ChatServiceAsync chatService = GWT.create(ChatService.class);
	private String height;
	private String width;
	
	public ChatApp(String appName, String appImageURL,SimpleEventBus chatEventBus, Display panel,String width,String height) {
		setName(appName);
		setImage(appImageURL);
		setHeight(height);
		setWidth(width);
		this.chatEventBus = chatEventBus;
		this.panel = panel;
		bind();
		
		
	}
	public interface Display {
		
		Button getSendButton();
		
		TextBox getSendText();
		
		TextArea getTextArea();
		
		ChatPanel getChatPanel();
	
		
	

		
		
		
	}
	@Override
	public Widget run() {

		
		return panel.getChatPanel();
		
		
		
		
	}
	@Override
	public void setSize(String width, String height) {
		
		panel.getChatPanel().setSize(width, height);
	}
	private void bind(){
		chatEventBus.addHandler(MessageChatAvailableEvent.TYPE, new MessageChatAvailableEventHandler(){

			@Override
			public void onContentAvailable(
					MessageChatAvailableEvent contentAvailableEvent) {
				
				//panel.getTextArea().setText("Push enviado correctamente " + contentAvailableEvent.getMessageChat().getType());
				getMessages();
			}
			
		});
		panel.getSendButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (!panel.getSendText().getText().isEmpty()){
					sendMessage(panel.getSendText().getText());
					
				}
				
			}
		});
		panel.getSendText().addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				// TODO Auto-generated method stub
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER){
					if (!panel.getSendText().getText().isEmpty()){
						sendMessage(panel.getSendText().getText());
						
					}
					
				}
				
			}
		});
		
	}
	
	
	
	
	
	
	private void sendMessage(String text){
		new RPCCall<Void>(){

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				chatService.sendText(panel.getSendText().getText(), new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						panel.getSendText().setText("");
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
				
		}.retry(3);
		
			
			
		
	}
	private void getMessages(){
		
		new RPCCall<Void>() {


			@Override
			protected void callService(AsyncCallback<Void> cb) {

				chatService.getText(new AsyncCallback<List<Chat>>() {
					
					@Override
					public void onSuccess(List<Chat> result) {
						String textToAdd = "";
						for (int i = 0; i < result.size(); i++) {
							textToAdd += result.get(i).getText() + "\n";
						}
						
						panel.getTextArea().setText(panel.getTextArea().getText() + textToAdd);
						
						panel.getTextArea().getElement().setScrollTop(panel.getTextArea().getElement().getScrollHeight());
						System.out.println(result.toArray());
					}
					
					@Override
					public void onFailure(Throwable caught) {

						
					}
				});
			}
		}.retry(3);
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}


}
