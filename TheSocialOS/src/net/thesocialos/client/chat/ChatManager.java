package net.thesocialos.client.chat;

import java.util.HashMap;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
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
import net.thesocialos.client.desktop.DesktopEventOnOpen;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ChatManager {
	
	private HashMap<String, ChatDisplay> conversations;
	
	ChatMenuPresenter chatMenuPresenter;
	
	public ChatManager() {
		chatMenuPresenter = new ChatMenuPresenter(new ChatMenuView());
		bindHandlers();
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(chatMenuPresenter));
		getContacts();
		init();
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
				recieveMessage(getConversation(event.getUserEmail()), event.getText());
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
				System.out.println(CacheLayer.UserCalls.getUser().getEmail() + "  Chamnge state  "
						+ event.getUserEmail() + " " + event.getStateType().toString() + " " + event.getCustomState());
				changeState(event.getUserEmail(), event.getStateType(), event.getCustomState());
			}
			
		});
	}
	
	private void getContacts() {
		
		CacheLayer.ContactCalls.getContacts(true, new AsyncCallback<Map<String, User>>() {
			
			@Override
			public void onSuccess(Map<String, User> result) {
				chatMenuPresenter.refeshContacts(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private ChatDisplay getConversation(String email) {
		return conversations.get(email);
	}
	
	private void init() {
		CacheLayer.UserCalls.setChatState(STATETYPE.ONLINE, null, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				System.out.println("llego");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * Send a message on one chat
	 * 
	 * @param display
	 * @param message
	 */
	private void recieveMessage(ChatDisplay display, String message) {
		if (display != null) display.setMessage(message);
		
	}
	
	/**
	 * Change the state in one conversation
	 * 
	 * @param email
	 * @param stateType
	 * @param customState
	 */
	private void changeState(String email, STATETYPE stateType, String customState) {
		chatMenuPresenter.changeContactState(email, stateType, customState);
		/*
		 * ChatDisplay display = getConversation(email); if (display != null) display.setState(stateType, customState);
		 */
		
	}
}
