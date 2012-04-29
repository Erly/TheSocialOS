package net.thesocialos.client.chat;

import java.util.HashMap;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.chat.events.ChatCloseConversation;
import net.thesocialos.client.chat.events.ChatEvent;
import net.thesocialos.client.chat.events.ChatEventHandler;
import net.thesocialos.client.chat.events.ChatHideConversation;
import net.thesocialos.client.chat.events.ChatOpenConversation;
import net.thesocialos.client.chat.events.ChatRecieveMessage;
import net.thesocialos.client.chat.events.ChatSendMessage;
import net.thesocialos.client.chat.events.ChatStateChange;
import net.thesocialos.client.chat.view.ChatMenuView;
import net.thesocialos.client.desktop.DesktopUnit;

public class ChatManager {
	
	private HashMap<String, DesktopUnit> ConversationsOpen;
	
	ChatMenuPresenter chatMenuPresenter;
	
	public ChatManager() {
		chatMenuPresenter = new ChatMenuPresenter(new ChatMenuView());
		bindHandlers();
	}
	
	private void bindHandlers() {
		TheSocialOS.getEventBus().addHandler(ChatEvent.TYPE, new ChatEventHandler() {
			
			@Override
			public void onSendMessage(ChatSendMessage event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onRecieveMessage(ChatRecieveMessage event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onConversationOpen(ChatOpenConversation event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onConversationHide(ChatHideConversation event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onConversationClose(ChatCloseConversation event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChangeState(ChatStateChange event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
