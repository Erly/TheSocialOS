package net.thesocialos.shared.ChannelApiEvents;

@SuppressWarnings("serial")
public class ChApiPushDisconnect extends ChApiEvent {
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChApiEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChApiEventHandler handler) {
		handler.chApiDisconnect();
		
	}
}
