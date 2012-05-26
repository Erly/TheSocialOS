package net.thesocialos.client.chat.events;

public class ChatMenuMinimize extends ChatEvent {
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChatEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChatEventHandler handler) {
		handler.onChatMenuHide(this);
		
	}
	
}
