package net.thesocialos.client.event;

public class ChannelClose extends ChannelEvent {
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChannelEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChannelEventHandler handler) {
		handler.onChannelDisconnect(this);
		
	}
	
}
