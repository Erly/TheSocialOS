package net.thesocialos.client.chat;

import java.util.HashMap;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.chat.events.ChatCloseConversation;
import net.thesocialos.client.chat.events.ChatEvent;
import net.thesocialos.client.chat.events.ChatEventHandler;
import net.thesocialos.client.chat.events.ChatHideConversation;
import net.thesocialos.client.chat.events.ChatOpenConversation;
import net.thesocialos.client.chat.events.ChatRecieveMessage;
import net.thesocialos.client.chat.events.ChatSendMessage;
import net.thesocialos.client.chat.events.ChatStateChange;
import net.thesocialos.client.chat.view.ChatConversationView;
import net.thesocialos.client.chat.view.ChatMenuView;
import net.thesocialos.client.desktop.DesktopEventOnOpen;
import net.thesocialos.client.desktop.window.Footer;
import net.thesocialos.client.desktop.window.MyCaption;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.ChatService;
import net.thesocialos.client.service.ChatServiceAsync;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.WindowPanelLayout;
import com.googlecode.objectify.Key;

public class ChatManager {
	
	private HashMap<String, ChatConversationPresenter> conversations = new HashMap<String, ChatConversationPresenter>();
	
	ChatMenuPresenter chatMenuPresenter;
	
	ChatServiceAsync chatService = GWT.create(ChatService.class);
	
	public ChatManager() {
		chatMenuPresenter = new ChatMenuPresenter(new ChatMenuView(), this);
		bindHandlers();
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(chatMenuPresenter));
		getContacts();
		init();
	}
	
	private void bindHandlers() {
		
		TheSocialOS.getEventBus().addHandler(ChatEvent.TYPE, new ChatEventHandler() {
			
			@Override
			public void onSendMessage(ChatSendMessage event) {
				sendText(event.getUserEmail(), event.getMessage());
				
			}
			
			@Override
			public void onRecieveMessage(final ChatRecieveMessage event) {
				// TODO Auto-generated method stub
				OpenWindow(event.getUserEmail(), new AsyncCallback<Boolean>() {
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSuccess(Boolean result) {
						// TODO Auto-generated method stub
						sendMessage(event.getUserEmail(), event.getText());
					}
				});
			}
			
			@Override
			public void onConversationOpen(final ChatOpenConversation event) {
				OpenWindow(event.getUserEmail(), new AsyncCallback<Boolean>() {
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSuccess(Boolean result) {
						
					}
				});
				
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
				chatMenuPresenter.changeContactState(event.getUserEmail(), event.getStateType(), event.getCustomState());
				// changeState(event.getUserEmail(), event.getStateType(), event.getCustomState());
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
	
	private ChatConversationPresenter getConversation(String email) {
		return conversations.get(email);
	}
	
	private void init() {
		changeState(STATETYPE.ONLINE, null);
	}
	
	/**
	 * Create a new Window of chat
	 * 
	 * @param userEmail
	 * @return
	 */
	private ChatConversationPresenter createConversation(String userEmail) {
		return new ChatConversationPresenter(AppConstants.CHAT, "Chat to" + userEmail, null, userEmail,
				new WindowPanelLayout(false, false, new MyCaption(), new Footer()), new ChatConversationView());
	}
	
	/**
	 * Send a message on one chat
	 * 
	 * @param display
	 * @param message
	 */
	private void sendMessage(String userEmail, String message) {
		conversations.get(userEmail).writeMessage(message);
	}
	
	private void OpenWindow(final String userEmail, final AsyncCallback<Boolean> callback) {
		if (CacheLayer.ContactCalls.isContact(userEmail)) {
			if (!conversations.containsKey(userEmail)) conversations.put(userEmail, createConversation(userEmail));
			TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(conversations.get(userEmail)));
			callback.onSuccess(true);
		}
		CacheLayer.ContactCalls.updateContacts(new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				if (result == false) callback.onSuccess(false);
				if (!conversations.containsKey(userEmail)) conversations.put(userEmail, createConversation(userEmail));
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(conversations.get(userEmail)));
				callback.onSuccess(true);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	/**
	 * Change user state
	 * 
	 * @param stateType
	 * @param customState
	 */
	protected void changeState(final STATETYPE stateType, String customState) {
		CacheLayer.UserCalls.setChatState(stateType, customState, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				chatMenuPresenter.changeUserState(stateType);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		/*
		 * ChatDisplay display = getConversation(email); if (display != null) display.setState(stateType, customState);
		 */
		
	}
	
	private void sendText(String toContact, final String message) {
		final Key<User> keyContact = Key.create(User.class, toContact);
		new RPCXSRF<Void>(chatService) {
			
			@Override
			protected void XSRFcallService(AsyncCallback<Void> cb) {
				// TODO Auto-generated method stub
				chatService.sendText(keyContact, message, cb);
			}
			
			@Override
			public void onSuccess(Void success) {
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught);
			}
		}.retry(3);
	}
	
}
