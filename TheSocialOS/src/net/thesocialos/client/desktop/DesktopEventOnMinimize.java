package net.thesocialos.client.desktop;

public class DesktopEventOnMinimize extends DesktopEvent {
	
	public DesktopEventOnMinimize(DesktopUnit desktopUnit) {
		super(desktopUnit);
		// TODO Auto-generated constructor stub
	}
<<<<<<< HEAD
	
	@Override
	protected void dispatch(DesktopEventHandler handler) {
		handler.onMinimize(this);
		
	}
=======
>>>>>>> c3a2f59af0c481814bc40bcb16e13f31fbc04947
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DesktopEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
<<<<<<< HEAD
=======
	@Override
	protected void dispatch(DesktopEventHandler handler) {
		handler.onMinimize(this);
		
	}
	
>>>>>>> c3a2f59af0c481814bc40bcb16e13f31fbc04947
}
