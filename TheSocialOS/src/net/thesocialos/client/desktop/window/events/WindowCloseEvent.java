package net.thesocialos.client.desktop.window.events;

public class WindowCloseEvent extends WindowEvent {
	
	@Override
	protected void dispatch(WindowEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onClose(this);
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
