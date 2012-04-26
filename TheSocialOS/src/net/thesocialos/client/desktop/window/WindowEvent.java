package net.thesocialos.client.desktop.window;

import com.google.gwt.event.shared.GwtEvent;

public abstract class WindowEvent extends GwtEvent<WindowEventHandler> {
	
	public static Type<WindowEventHandler> TYPE = new Type<WindowEventHandler>();
	
}
