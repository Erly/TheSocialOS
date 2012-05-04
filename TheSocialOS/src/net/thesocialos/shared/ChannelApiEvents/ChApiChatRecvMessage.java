package net.thesocialos.shared.ChannelApiEvents;

import java.util.Date;

import net.thesocialos.shared.model.Lines;
import net.thesocialos.shared.model.User;

import com.googlecode.objectify.Key;

/**
 * 
 * @author vssnake
 * 
 */
public class ChApiChatRecvMessage extends ChApiEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4016806987952443222L;
	private Lines line;
	
	public ChApiChatRecvMessage(Date date, Key<User> contactComeFrom, String message) {
		super();
		
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
	 * @return the date
	 */
	public Date getDate() {
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
	
}
