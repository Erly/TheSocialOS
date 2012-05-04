package net.thesocialos.client.chat.events;

import java.util.Date;

import net.thesocialos.shared.model.Lines;

public class ChatRecieveMessage extends ChatEvent {
	private Lines line;
	
	public ChatRecieveMessage(Lines line) {
		super(line.getUserOwner());
		this.line = line;
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
		return line.getDate();
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return line.getText();
	}
	
	/**
	 * 
	 * @return the line of the message
	 */
	public Lines getLine() {
		return line;
	}
	
}
