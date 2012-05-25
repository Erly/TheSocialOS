package net.thesocialos.shared.ChannelApiEvents;

import java.io.Serializable;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @author vssnake
 * 
 */
public interface ChApiEventHandler extends EventHandler, Serializable {
	
	// Chat Event
	void onChatRcvMessage(ChApiChatRecvMessage event);
	
	void onChatUserChangeState(ChApiChatUserChngState event);
	
	void onChatUserConnected(ChApiChatUserConnected event);
	
	void onChatUserDisconnected(ChApiChatUserDisconnect event);
	
	// Contacts Events
	void onContactNew(ChApiContactNew event);
	
	// Petitions Events
	void onPetitionNew(ChApiContactPetition event);
}
