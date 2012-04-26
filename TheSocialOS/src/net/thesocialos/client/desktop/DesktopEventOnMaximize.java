package net.thesocialos.client.desktop;

public class DesktopEventOnMaximize extends DesktopEvent {
	
	public DesktopEventOnMaximize(DesktopUnit desktopUnit) {
		super(desktopUnit);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatch(DesktopEventHandler handler) {
		handler.onMaximize(this);
		
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DesktopEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
