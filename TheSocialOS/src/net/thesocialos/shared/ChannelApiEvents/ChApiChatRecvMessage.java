package net.thesocialos.shared.ChannelApiEvents;

import java.sql.Date;

/**
 * 
 * @author vssnake
 * 
 */
public class ChApiChatRecvMessage extends ChApiEvent {
	
	private Date date;
	private String contactComeFrom;
	private String message;
	
	public ChApiChatRecvMessage(Date date, String contactComeFrom, String message) {
		super();
		this.date = date;
		this.contactComeFrom = contactComeFrom;
		this.message = message;
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
