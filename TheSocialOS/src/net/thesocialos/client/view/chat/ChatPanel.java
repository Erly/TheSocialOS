package net.thesocialos.client.view.chat;

import java.util.List;

import net.thesocialos.client.app.ChatApp.Display;
import net.thesocialos.client.desktop.window.button;
import net.thesocialos.client.helper.RPCCall;
import net.thesocialos.client.service.ChatService;
import net.thesocialos.client.service.ChatServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.event.dom.client.KeyPressEvent;

public class ChatPanel extends Composite implements Display {
	
	private final ChatServiceAsync chatService = GWT.create(ChatService.class);
	private static PanelUiBinder uiBinder = GWT.create(PanelUiBinder.class);
	@UiField TextArea chatLog;
	@UiField Button btnSend;
	@UiField TextBox txtWrite;
	
	interface PanelUiBinder extends UiBinder<Widget, ChatPanel> {
	}
	
	public ChatPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public ChatPanel(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		
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
	
	@Override
	public ChatPanel getChatPanel() {
		// TODO Auto-generated method stub
		return this;
	}
	
}
