package net.thesocialos.client.desktop;


import com.google.gwt.event.shared.GwtEvent;


public abstract class DesktopEvent extends GwtEvent<DesktopEventHandler>{


	private DesktopUnit desktopUnit;
	
	public static Type<DesktopEventHandler> TYPE = new Type<DesktopEventHandler>();

	
	public DesktopEvent(DesktopUnit desktopUnit){
		this.desktopUnit = desktopUnit;
	}
	
	public DesktopUnit getDesktopUnit(){
		return desktopUnit;
	}

}
