package net.thesocialos.client.desktop;

public class DesktopEventOnMinimize extends DesktopEvent {
	
	private boolean minimized;
	
	public DesktopEventOnMinimize(DesktopUnit desktopUnit, boolean forceMinimized) {
		super(desktopUnit);
		minimized = forceMinimized;
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
	
	/**
	 * @return the minimized
	 */
	public boolean isForceMinimized() {
		return minimized;
	}
	
}
