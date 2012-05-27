package net.thesocialos.shared.ChannelApiEvents;

public class ChApiShareChange extends ChApiEvent {
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChApiEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(ChApiEventHandler handler) {
		handler.onShareChange(this);
		
	}
	
}
