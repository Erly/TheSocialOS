package net.thesocialos.client.desktop.window;

public class WindowOnTopEvent extends WindowEvent {
	
	@Override
	protected void dispatch(WindowEventHandler handler) {
		handler.onTop(this);
		
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
