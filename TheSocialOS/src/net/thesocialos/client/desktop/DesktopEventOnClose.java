package net.thesocialos.client.desktop;

public class DesktopEventOnClose extends DesktopEvent {
	
	public DesktopEventOnClose(DesktopUnit desktopUnit) {
		super(desktopUnit);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DesktopEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(DesktopEventHandler handler) {
		handler.onClose(this);
		
	}
	
}
