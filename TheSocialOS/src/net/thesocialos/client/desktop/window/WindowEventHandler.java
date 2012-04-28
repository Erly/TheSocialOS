package net.thesocialos.client.desktop.window;

import com.google.gwt.event.shared.EventHandler;

public interface WindowEventHandler extends EventHandler {
	
	void onClose(WindowCloseEvent event);
	
	void onEndDrag(WindowEndDragEvent event);
	
	void onMaximize(WindowMaximizeEvent windowMaximizeEvent);
	
	void onMinimize(WindowMinimizeEvent windowMinimizeEvent);
	
	void onTop(WindowOnTopEvent event);
	
	void onResize(WindowResizeEvent event);
}
