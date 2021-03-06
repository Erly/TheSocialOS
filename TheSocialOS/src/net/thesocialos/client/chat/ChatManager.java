package net.thesocialos.client.chat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.chat.events.ChatCloseConversation;
import net.thesocialos.client.chat.events.ChatEvent;
import net.thesocialos.client.chat.events.ChatEventHandler;
import net.thesocialos.client.chat.events.ChatHideConversation;
import net.thesocialos.client.chat.events.ChatMenuMinimize;
import net.thesocialos.client.chat.events.ChatOpenConversation;
import net.thesocialos.client.chat.events.ChatRecieveMessage;
import net.thesocialos.client.chat.events.ChatSendMessage;
import net.thesocialos.client.chat.events.ChatStateChange;
import net.thesocialos.client.chat.events.ChatTopConversations;
import net.thesocialos.client.chat.view.ChatConversationView;
import net.thesocialos.client.chat.view.ChatMenuView;
import net.thesocialos.client.chat.view.ListChatBlocks;
import net.thesocialos.client.desktop.DesktopEventOnMinimize;
import net.thesocialos.client.desktop.DesktopEventOnOpen;
import net.thesocialos.client.desktop.window.Footer;
import net.thesocialos.client.desktop.window.MyCaption;
import net.thesocialos.client.event.ChannelClose;
import net.thesocialos.client.event.ChannelEvent;
import net.thesocialos.client.event.ChannelEventHandler;
import net.thesocialos.client.event.ChannelOpen;
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
	ListChatBlockPresenter chatListChatBlocksPresenter;
	
	ChatServiceAsync chatService = GWT.create(ChatService.class);
	
	/** If the chat is hide **/
	boolean isHide = false;
	
	boolean disconnect = false;
	
	public ChatManager() {
		chatMenuPresenter = new ChatMenuPresenter(new ChatMenuView(), this);
		chatListChatBlocksPresenter = new ListChatBlockPresenter(new ListChatBlocks());
		bindHandlers();
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(chatMenuPresenter));
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(chatListChatBlocksPresenter));
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
				if (isHide) {
					TheSocialOS.getEventBus().fireEvent(
							new DesktopEventOnMinimize(conversations.get(event.getUserKey()), true));
					chatMenuPresenter.addUnreadMessage();
				}
				chatListChatBlocksPresenter.messagePending(event.getUserKey());
				
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
				hideRestoreWindow(event.getUserKey());
				
			}
			
			@Override
			public void onConversationClose(ChatCloseConversation event) {
				chatListChatBlocksPresenter.removeConversationBlock(event.getUserKey());
				
			}
			
			@Override
			public void onChangeState(ChatStateChange event) {
				// TODO Auto-generated method stub
				chatMenuPresenter.changeContactState(event.getUserEmail(), event.getStateType(), event.getCustomState());
				// changeState(event.getUserEmail(
			}
			
			@Override
			public void onTopConversation(ChatTopConversations event) {
				activateConversationsBlock(event.getUserKey());
				
			}
			
			@Override
			public void onChatMenuHide(ChatMenuMinimize event) {
				if (isHide) {
					chatListChatBlocksPresenter.hide(false);
					chatMenuPresenter.hideRestore();
					
				} else {
					chatListChatBlocksPresenter.hide(true);
					chatMenuPresenter.hideRestore();
					hideAllWindows();
				}
				
			}
		});
		
		TheSocialOS.getEventBus().addHandler(ContactsChangeEvent.TYPE, new ContactsChangeEventHandler() {
			
			@Override
			public void onContactsChange(ContactsChangeEvent event) {
				getContacts();
				
			}
		});
		
		TheSocialOS.getEventBus().addHandler(ChannelEvent.TYPE, new ChannelEventHandler() {
			
			@Override
			public void onChannelDisconnect(ChannelClose event) {
				// TODO Auto-generated method stub
				disconnect = true;
				hideAllWindows();
				chatListChatBlocksPresenter.hide(true);
				chatMenuPresenter.disconnected(true);
			}
			
			@Override
			public void onChannelConnect(ChannelOpen event) {
				disconnect = false;
				chatListChatBlocksPresenter.hide(false);
				chatMenuPresenter.disconnected(false);
				
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
				
				if (conversationPresenter.isMinimized()) callback.onSuccess(true);
				else {
					chatListChatBlocksPresenter.addConvesationBlock(contact);
					TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(conversationPresenter));
					
					callback.onSuccess(true);
				}
				
			} else {
				ChatConversationPresenter chatConversation = createConversation(contact);
				conversations.put(contact, chatConversation);
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnOpen(chatConversation));
				chatListChatBlocksPresenter.addConvesationBlock(contact);
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
							chatListChatBlocksPresenter.addConvesationBlock(contact);
							callback.onSuccess(true);
						}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		
	}
	
	private void hideRestoreWindow(Key<User> userKey) {
		if (!conversations.containsKey(userKey)) return;
		if (conversations.get(userKey).isMinimized()) activateConversationsBlock(userKey);
		else {
			activateConversationsBlock(userKey);
			chatListChatBlocksPresenter.setActivateConversationBlock(userKey, true);
		}
		
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMinimize(conversations.get(userKey), false));
	}
	
	private void hideAllWindows() {
		Set<Entry<Key<User>, ChatConversationPresenter>> set = conversations.entrySet();
		Iterator<Entry<Key<User>, ChatConversationPresenter>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry<Key<User>, ChatConversationPresenter> entry = iterator.next();
			// activateConversationsBlock(entry.getKey());
			chatListChatBlocksPresenter.setActivateConversationBlock(entry.getKey(), true);
			TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMinimize(entry.getValue(), true));
		}
		
	}
	
	private void activateConversationsBlock(Key<User> userKey) {
		chatListChatBlocksPresenter.modifyConversationsBlock(userKey);
		
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
