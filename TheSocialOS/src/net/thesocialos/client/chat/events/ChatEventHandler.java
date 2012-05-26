package net.thesocialos.client.chat.events;

import com.google.gwt.event.shared.EventHandler;

public interface ChatEventHandler extends EventHandler {
	
	void onConversationOpen(ChatOpenConversation event);
	
	void onConversationClose(ChatCloseConversation event);
	
	void onConversationHide(ChatHideConversation event);
	
	void onSendMessage(ChatSendMessage event);
	
	void onRecieveMessage(ChatRecieveMessage event);
	
	void onChangeState(ChatStateChange event);
	
	void onTopConversation(ChatTopConversations event);
	
	void onChatMenuHide(ChatMenuMinimize event);
	
}
