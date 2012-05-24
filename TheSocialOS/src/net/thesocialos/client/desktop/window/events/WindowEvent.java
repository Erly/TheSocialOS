package net.thesocialos.client.desktop.window.events;

import com.google.gwt.event.shared.GwtEvent;

public abstract class WindowEvent extends GwtEvent<WindowEventHandler> {
	
	public static Type<WindowEventHandler> TYPE = new Type<WindowEventHandler>();
	
}
