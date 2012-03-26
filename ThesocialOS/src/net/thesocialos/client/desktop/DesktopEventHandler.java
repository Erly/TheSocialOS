package net.thesocialos.client.desktop;



import com.google.gwt.event.shared.EventHandler;

public interface DesktopEventHandler extends EventHandler {
	void onClose(DesktopEventOnClose event);
	void onOpen(DesktopEventOnOpen event);
	void onMaximize(DesktopEventOnMaximize event);
	void onMinimize(DesktopEventOnMinimize event);
}
