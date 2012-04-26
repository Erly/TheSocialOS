package net.thesocialos.client.desktop.window;

public class WindowMinimizeEvent extends WindowEvent {
	
	@Override
	protected void dispatch(WindowEventHandler handler) {
		handler.onMinimize(this);
		
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
