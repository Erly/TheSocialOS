package net.thesocialos.client.desktop;

public class DesktopEventOnMinimize extends DesktopEvent {
	
	public DesktopEventOnMinimize(DesktopUnit desktopUnit) {
		super(desktopUnit);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatch(DesktopEventHandler handler) {
		handler.onMinimize(this);
		
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DesktopEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
