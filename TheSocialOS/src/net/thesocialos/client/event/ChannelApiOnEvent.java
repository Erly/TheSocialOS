package net.thesocialos.client.event;

public class ChannelApiOnEvent extends ChannelAPiEvent {
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChannelApiEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChannelApiEventHandler handler) {
		handler.onChannelOn(this);
		
	}
	
}
