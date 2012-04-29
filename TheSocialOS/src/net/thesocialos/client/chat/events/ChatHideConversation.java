package net.thesocialos.client.chat.events;

public class ChatHideConversation extends ChatEvent {
	
	public ChatHideConversation(String userEmail) {
		super(userEmail);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChatEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChatEventHandler handler) {
		handler.onConversationHide(this);
		
	}
	
}
