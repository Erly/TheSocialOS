package net.thesocialos.client.event;

public class ChannelOpen extends ChannelEvent {
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChannelEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChannelEventHandler handler) {
		handler.onChannelConnect(this);
		
	}
	
}
