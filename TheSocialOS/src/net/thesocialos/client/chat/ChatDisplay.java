package net.thesocialos.client.chat;

import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;

public interface ChatDisplay {
	void setMessage(String message);
	
	void setState(STATETYPE statetype, String customState);
	
}
