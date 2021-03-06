package net.thesocialos.client.desktop.window.events;

import com.google.gwt.user.client.ui.WindowPanelLayout;

public interface WindowDisplay {
	
	com.google.web.bindery.event.shared.HandlerRegistration addWindowEvents(WindowEventHandler handler);
	
	int getHeight();
	
	int getWidth();
	
	WindowPanelLayout getWindow();
	
	int getXposition();
	
	int getYPosition();
	
	void setMaximized(Boolean maximized);
	
	void setMinimized(Boolean minimized);
	
	void setResizable(Boolean resizable);
	
	void setPosition(int x, int y);
	
	void setSize(int width, int height);
	
	void setWindowTitle(String text);
	
	String getWindowTitle();
	
	void toback();
	
	void toFront();
	
	void hide();
	
	void show();
	
}
