package net.thesocialos.client.desktop.window.events;

public class WindowResizeEvent extends WindowEvent {
	
	@Override
	protected void dispatch(WindowEventHandler handler) {
		handler.onResize(this);
	}
	
	@Override
	public Type<WindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
