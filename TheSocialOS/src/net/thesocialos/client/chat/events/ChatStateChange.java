package net.thesocialos.client.chat.events;

public class ChatStateChange extends ChatEvent {
	
	public ChatStateChange(String userEmail) {
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
		handler.onChangeState(this);
		
	}
	
}
