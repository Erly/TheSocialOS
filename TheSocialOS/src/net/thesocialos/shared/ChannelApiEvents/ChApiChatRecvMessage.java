package net.thesocialos.shared.ChannelApiEvents;

import java.sql.Date;

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
	private Date date;
	private String contactComeFrom;
	private String message;
	
	public ChApiChatRecvMessage(Date date, String contactComeFrom, String message) {
		super();
		
		this.date = date;
		this.contactComeFrom = contactComeFrom;
		this.message = message;
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
	public String getContactComeFrom() {
		return contactComeFrom;
	}
	
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
}
