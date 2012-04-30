package net.thesocialos.client.app;

import java.util.List;

import net.thesocialos.client.chat.view.ChatConversationView;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.ChatService;
import net.thesocialos.client.service.ChatServiceAsync;
import net.thesocialos.shared.Chat;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ChatApp implements IApplication {
	
	public interface Display {
		
		ChatConversationView getChatPanel();
		
		Button getSendButton();
		
		TextArea getSendText();
		
		TextArea getTextArea();
	}
	
	private String name;
	private String image;
	Display panel;
	String message = "hellow World";
	SimpleEventBus chatEventBus;
	private final ChatServiceAsync chatService = GWT.create(ChatService.class);
	private String height;
	
	private String width;
	
	public ChatApp(String appName, String appImageURL, SimpleEventBus chatEventBus, Display panel, String width,
			String height) {
		setName(appName);
		setImage(appImageURL);
		setHeight(height);
		setWidth(width);
		this.chatEventBus = chatEventBus;
		this.panel = panel;
		bind();
	}
	
	private void bind() {
		
		panel.getSendButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (!panel.getSendText().getText().isEmpty()) sendMessage(panel.getSendText().getText());
				
			}
		});
		panel.getSendText().addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				// TODO Auto-generated method stub
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER)
					if (!panel.getSendText().getText().isEmpty()) sendMessage(panel.getSendText().getText());
				
			}
		});
		
	}
	
	public String getHeight() {
		return height;
	}
	
	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return image;
	}
	
	private void getMessages() {
		
		new RPCXSRF<Void>(chatService) {
			
			@Override
			protected void XSRFcallService(AsyncCallback<Void> cb) {
				
				chatService.getText(new AsyncCallback<List<Chat>>() {
					
					@Override
					public void onFailure(Throwable caught) {
						
					}
					
					@Override
					public void onSuccess(List<Chat> result) {
						String textToAdd = "";
						for (int i = 0; i < result.size(); i++)
							textToAdd += result.get(i).getText() + "\n";
						panel.getTextArea().setText(panel.getTextArea().getText() + textToAdd);
						panel.getTextArea().getElement()
								.setScrollTop(panel.getTextArea().getElement().getScrollHeight());
						System.out.println(result.toArray());
					}
				});
			}
		}.retry(3);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public String getWidth() {
		return width;
	}
	
	private void sendMessage(String text) {
		/*
		 * new RPCXSRF<Void>(chatService) {
		 * @Override protected void XSRFcallService(AsyncCallback<Void> cb) {
		 * chatService.sendText(panel.getSendText().getText(), new AsyncCallback<Boolean>() {
		 * @Override public void onFailure(Throwable caught) { // TODO Auto-generated method stub }
		 * @Override public void onSuccess(Boolean result) { panel.getSendText().setText(""); } }); } }.retry(3);
		 */
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	
	@Override
	public void setImage(String image) {
		this.image = image;
		
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
		
	}
	
	public void setWidth(String width) {
		this.width = width;
	}
}