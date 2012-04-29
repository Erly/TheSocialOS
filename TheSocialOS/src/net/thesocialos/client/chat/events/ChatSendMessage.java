package net.thesocialos.client.chat.events;

public class ChatSendMessage extends ChatEvent {
	
	private String text;
	
	public ChatSendMessage(String userEmail, String text) {
		super(userEmail);
		this.text = text;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChatEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChatEventHandler handler) {
		handler.onSendMessage(this);
		
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
}
