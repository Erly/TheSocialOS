package net.thesocialos.client.desktop.window.events;

public class WindowEndDragEvent extends WindowEvent {
	
	int xPosition;
	int yPosition;
	
	public WindowEndDragEvent() {
		super();
	}
	
	public WindowEndDragEvent(int xPosition, int yPosition) {
		super();
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	@Override
	protected void dispatch(WindowEventHandler handler) {
		handler.onEndDrag(this);
		
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WindowEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	public int getxPosition() {
		return xPosition;
	}
	
	public int getYPosition() {
		return yPosition;
	}
	
}
