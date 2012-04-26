package net.thesocialos.client.desktop;

public class DesktopEventOnTop extends DesktopEvent {
	
	public DesktopEventOnTop(DesktopUnit desktopUnit) {
		super(desktopUnit);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatch(DesktopEventHandler handler) {
		handler.onTop(this);
		
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DesktopEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
