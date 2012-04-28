package net.thesocialos.client.desktop;

public class DesktopEventOnOpen extends DesktopEvent {
	
	public DesktopEventOnOpen(DesktopUnit desktopUnit) {
		super(desktopUnit);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void dispatch(DesktopEventHandler handler) {
		handler.onOpen(this);
		
	}
	
	@Override
	public Type<DesktopEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	
}
