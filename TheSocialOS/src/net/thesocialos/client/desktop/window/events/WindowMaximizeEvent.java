package net.thesocialos.client.desktop.window.events;

public class WindowMaximizeEvent extends WindowEvent {
	
	@Override
	protected void dispatch(WindowEventHandler handler) {
		handler.onMaximize(this);
		
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
