package net.thesocialos.client.desktop;

public class DesktopEventonEndDrag extends DesktopEvent {
	
	public DesktopEventonEndDrag(DesktopUnit desktopUnit) {
		super(desktopUnit);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatch(DesktopEventHandler handler) {
		handler.onEndDrag(this);
		
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DesktopEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
