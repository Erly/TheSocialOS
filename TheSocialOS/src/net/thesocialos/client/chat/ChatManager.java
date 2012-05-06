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
import net.thesocialos.client.event.ContactsChangeEvent;
import net.thesocialos.client.event.ContactsChangeEventHandler;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.ChatService;
import net.thesocialos.client.service.ChatServiceAsync;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.model.Lines;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.WindowPanelLayout;
import com.googlecode.objectify.Key;

public class ChatManager {
	
	private HashMap<Key<User>, ChatConversationPresenter> conversations = new HashMap<Key<User>, ChatConversationPresenter>();
	
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
				sendText(event.getUserKey(), event.getMessage());
				
			}
			
			@Override
			public void onRecieveMessage(final ChatRecieveMessage event) {
				sendMessage(event.getUserKey(), event.getLine());
			}
			
			@Override
			public void onConversationOpen(final ChatOpenConversation event) {
				OpenWindow(event.getUserKey(), new AsyncCallback<Boolean>() {
					
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
				// changeState(event.getUserEmail(
			}
		});
		
		TheSocialOS.getEventBus().addHandler(ContactsChangeEvent.TYPE, new ContactsChangeEventHandler() {
			
			@Override
			public void onContactsChange(ContactsChangeEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void getContacts() {
		
		CacheLayer.ContactCalls.getContactsWithoutKey(true, new AsyncCallback<Map<String, User>>() {
			
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
	private ChatConversationPresenter createConversation(Key<User> userKey) {
		return new ChatConversationPresenter(AppConstants.CHAT, "Chat to" + userKey.getName(), null, userKey,
				new WindowPanelLayout(false, false, new MyCaption(), new Footer()), new ChatConversationView());
	}
	
	/**
	 * Send a message in one chat
	 * 
	 * @param display
	 * @param message
	 */
	private void sendMessage(final Key<User> contact, final Lines line) {
		
		OpenWindow(contact, new AsyncCallback<Boolean>() {
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
			
			@Override
			public void onSuccess(Boolean result) {
				
				if (result) conversations.get(contact).writeMessage(line);
			}
		});
		
	}
	
	private void OpenWindow(final Key<User> contact, final AsyncCallback<Boolean> callback) {
		if (CacheLayer.ContactCalls.isContact(contact)) {
			if (conversations.containsKey(contact)) {
				ChatConversationPresenter conversationPresenter = conversations.get(contact);
				if (conversationPresenter.isOpen) conversationPresenter.setOnTop();
				else {
					TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(conversationPresenter));
					callback.onSuccess(true);
				}
				
			} else {
				ChatConversationPresenter chatConversation = createConversation(contact);
				conversations.put(contact, chatConversation);
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(chatConversation));
				callback.onSuccess(true);
			}
			
		} else
			CacheLayer.ContactCalls.updateContacts(new AsyncCallback<Boolean>() {
				
				@Override
				public void onSuccess(Boolean result) {
					if (result == false) callback.onSuccess(false);
					if (CacheLayer.ContactCalls.isContact(contact))
						if (conversations.containsKey(contact)) conversations.get(contact).setOnTop();
						else {
							ChatConversationPresenter chatConversation = createConversation(contact);
							conversations.put(contact, chatConversation);
							TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(chatConversation));
							callback.onSuccess(true);
						}
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
	
	private void sendText(final Key<User> keyContact, final String message) {
		
		new RPCXSRF<Long>(chatService) {
			
			@Override
			protected void XSRFcallService(AsyncCallback<Long> cb) {
				// TODO Auto-generated method stub
				chatService.sendText(keyContact, message, cb);
			}
			
			@Override
			public void onSuccess(Long time) {
				conversations.get(keyContact).writeMessage(new Lines(message, null, time));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught);
			}
		}.retry(3);
	}
}
