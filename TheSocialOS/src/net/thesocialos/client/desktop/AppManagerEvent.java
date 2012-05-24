package net.thesocialos.client.desktop;

import com.google.gwt.event.shared.GwtEvent;

public abstract class AppManagerEvent extends GwtEvent<AppManagerEventHandler> {
	
	public static Type<AppManagerEventHandler> TYPE = new Type<AppManagerEventHandler>();
	
	DesktopUnit desktopUnit;
	
	public AppManagerEvent(DesktopUnit desktopUnit) {
		this.desktopUnit = desktopUnit;
	}
	
	public DesktopUnit getDesktopUnit() {
		return desktopUnit;
	}
}
