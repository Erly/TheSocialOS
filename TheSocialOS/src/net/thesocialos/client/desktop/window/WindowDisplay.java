package net.thesocialos.client.desktop.window;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.WindowPanelLayout;

public interface WindowDisplay {
	
	WindowPanelLayout getWindow();
	
	com.google.web.bindery.event.shared.HandlerRegistration addWindowEvents(WindowEventHandler handler);
	
	void setWindowTitle(String text);
	
	void setPosition(int x, int y);
	
	void setMinimized(Boolean minimized);
	
	void setMaximized(Boolean maximized);
	
	int getXposition();
	
	int getYPosition();
	
	void setSize(int width, int height);
	
	int getHeight();
	
	int getwidth();
	
	void toback();
	
	void toFront();
	
}
