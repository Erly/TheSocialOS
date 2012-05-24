package net.thesocialos.client.desktop;

public class AppManagerOpenEvent extends AppManagerEvent {
	
	public AppManagerOpenEvent(DesktopUnit desktopUnit) {
		super(desktopUnit);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AppManagerEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
	@Override
	protected void dispatch(AppManagerEventHandler handler) {
		handler.onOpen(this);
	}
	
}
