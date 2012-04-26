package net.thesocialos.client.view.chat;

import net.thesocialos.client.app.ChatApp.Display;
import net.thesocialos.client.service.ChatService;
import net.thesocialos.client.service.ChatServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ChatPanel extends Composite implements Display {
	
	interface PanelUiBinder extends UiBinder<Widget, ChatPanel> {
	}
	private final ChatServiceAsync chatService = GWT.create(ChatService.class);
	private static PanelUiBinder uiBinder = GWT.create(PanelUiBinder.class);
	@UiField TextArea chatLog;
	@UiField Button btnSend;
	
	@UiField TextBox txtWrite;
	
	public ChatPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public ChatPanel(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	@Override
	public ChatPanel getChatPanel() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public Button getSendButton() {
		// TODO Auto-generated method stub
		return btnSend;
	}
	
	@Override
	public TextBox getSendText() {
		// TODO Auto-generated method stub
		return txtWrite;
	}
	
	@Override
	public TextArea getTextArea() {
		// TODO Auto-generated method stub
		return chatLog;
	}
	
}
