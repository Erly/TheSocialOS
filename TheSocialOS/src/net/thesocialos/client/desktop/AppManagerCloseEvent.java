package net.thesocialos.client.desktop;

public class AppManagerCloseEvent extends AppManagerEvent {
	
	public AppManagerCloseEvent(DesktopUnit desktopUnit) {
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
		handler.onClose(this);
		
	}
	
}
