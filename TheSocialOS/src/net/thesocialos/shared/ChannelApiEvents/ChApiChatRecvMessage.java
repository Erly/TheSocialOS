package net.thesocialos.shared.ChannelApiEvents;

import net.thesocialos.shared.model.Lines;
import net.thesocialos.shared.model.User;

import com.googlecode.objectify.Key;

/**
 * 
 * @author vssnake
 * 
 */
public class ChApiChatRecvMessage extends ChApiEvent {
	
	private Lines line;
	
	public enum CHATMESSAGETYPE {
		MESSAGE, IMAGE, VIDEO
	};
	
	private CHATMESSAGETYPE messageType = CHATMESSAGETYPE.MESSAGE;
	
	public ChApiChatRecvMessage(long date, Key<User> contactComeFrom, String message) {
		super();
		
		line = new Lines(message, contactComeFrom, date);
	}
	
	public ChApiChatRecvMessage(long date, Key<User> contactComeFrom, String message, CHATMESSAGETYPE messageType) {
		super();
		this.messageType = messageType;
		line = new Lines(message, contactComeFrom, date);
	}
	
	public ChApiChatRecvMessage() {
		super();
	}
	
	@Override
	protected void dispatch(ChApiEventHandler handler) {
		handler.onChatRcvMessage(this);
		
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChApiEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	/**
	 * @return the contactComeFrom
	 */
	public Key<User> getContact() {
		return line.getUserOwner();
	}
	
	/**
	 * @return the date in milliseconds
	 */
	public long getDate() {
		return line.getDate();
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return line.getText();
	}
	
	/**
	 * 
	 * @return the line of the message
	 */
	public Lines getLine() {
		return line;
	}
	
	/**
	 * @return the messageType
	 */
	public CHATMESSAGETYPE getMessageType() {
		return messageType;
	}
	
}
