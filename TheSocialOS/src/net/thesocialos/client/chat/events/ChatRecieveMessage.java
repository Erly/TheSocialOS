package net.thesocialos.client.chat.events;

import java.util.Date;

public class ChatRecieveMessage extends ChatEvent {
	private String text;
	private Date date;
	
	public ChatRecieveMessage(String userEmail, String text, Date date) {
		super(userEmail);
		this.text = text;
		this.date = date;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChatEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChatEventHandler handler) {
		handler.onRecieveMessage(this);
		
	}
	
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
}
