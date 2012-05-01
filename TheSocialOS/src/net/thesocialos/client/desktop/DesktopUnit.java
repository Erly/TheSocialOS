package net.thesocialos.client.desktop;

import net.thesocialos.client.desktop.window.WindowDisplay;

import com.google.gwt.user.client.ui.AbsolutePanel;

public abstract class DesktopUnit {
	
	public enum TypeUnit {
		WINDOW, INFO, POPUP, STATIC
	}
	
	private int programID;
	private boolean isSubApplication = false;
	
	protected int x;
	protected int y;
	protected int zPosition;
	
	private boolean minimized = false;
	private boolean maximized = false;
	private boolean isMaximizable = true;
	private boolean isMinimizable = true;
	
	private int beforeWidth = 0;
	private int beforeHeight = 0;
	
	private int beforeX = 0;
	private int beforeY = 0;
	
	protected TypeUnit typeUnit;
	protected WindowDisplay windowDisplay;
	
	public DesktopUnit(int programID, WindowDisplay display, TypeUnit typeUnit, boolean isSubApplication) {
		this.programID = programID;
		windowDisplay = display;
		this.typeUnit = typeUnit;
		this.isSubApplication = isSubApplication;
	}
	
	public abstract void close(AbsolutePanel absolutePanel);
	
	public int getAbsoluteLeft() {
		
		return windowDisplay.getXposition();
	}
	
	public int getAbsoluteTop() {
		
		return windowDisplay.getYPosition();
	}
	
	/**
	 * Return Height of the window in pixels
	 * 
	 * @return int
	 */
	public int getHeight() {
		
		return windowDisplay.getHeight();
	}
	
	/**
	 * Return the identification of the application
	 * 
	 * @return
	 */
	public int getID() {
		return programID;
	}
	
	/**
	 * Return Width of the window in pixels
	 * 
	 * @return int
	 */
	public int getWidth() {
		return windowDisplay.getwidth();
	}
	
	public abstract int getZposition();
	
	/**
	 * Return if the window is Maximized
	 * 
	 * @return boolean
	 */
	public boolean isMaximizable() {
		return isMaximizable;
	}
	
	/**
	 * Return if the window is now Maximized
	 * 
	 * @return boolean
	 */
	public boolean isMaximized() {
		return maximized;
	}
	
	/**
	 * Return if the window is Minimized
	 * 
	 * @return boolean
	 */
	public boolean isMinimizable() {
		return isMinimizable;
	}
	
	/**
	 * Return if the window is now Minimized
	 * 
	 * @return boolean
	 */
	public boolean isMinimized() {
		return minimized;
	}
	
	public abstract void open(AbsolutePanel absolutePanel);
	
	/**
	 * Set Windows maximized. If maximized is false the window restore to normal view
	 * 
	 * @param maximized
	 * @param height
	 * @param width
	 */
	public void setMaximized(Boolean maximized, int width, int height, int top, int left) {
		if (this.maximized) {
			
			windowDisplay.setPosition(beforeX, beforeY);
			windowDisplay.setSize(beforeWidth, beforeHeight);
			this.maximized = false;
			windowDisplay.setMaximized(false);
		} else {
			this.maximized = maximized;
			windowDisplay.setMaximized(true);
			beforeX = windowDisplay.getXposition();
			beforeY = windowDisplay.getYPosition();
			beforeHeight = windowDisplay.getHeight();
			beforeWidth = windowDisplay.getwidth();
			windowDisplay.setPosition(left, top);
			windowDisplay.setSize(width, height);
		}
		
	}
	
	/**
	 * Set the window minimized if the minimized is true or restore if minimized is true
	 * 
	 * @param minimized
	 */
	public void setMinimized(Boolean minimized) {
		
		this.minimized = minimized;
		windowDisplay.setMinimized(minimized);
	}
	
	public void setPosition(int x, int y) {
		windowDisplay.setPosition(x, y);
	}
	
	/**
	 * Set size of the window
	 * 
	 * @param height
	 *            in pixels
	 * @param width
	 *            in pixels
	 */
	public void setSize(int width, int height) {
		
		windowDisplay.setSize(width, height);
	}
	
	public void toBack() {
		windowDisplay.toback();
	}
	
	public void toFront() {
		windowDisplay.toFront();
	}
	
	public abstract void toZPosition(int position);
	
	/**
	 * @return the programID
	 */
	public Integer getProgramID() {
		return programID;
	}
	
	/**
	 * @return the isSubApplication
	 */
	public boolean isSubApplication() {
		return isSubApplication;
	}
	
	/**
	 * Hide the desktop Unit
	 */
	public void hide() {
		windowDisplay.hide();
	}
	
	/**
	 * Show the desktop Unit
	 */
	protected void show() {
		windowDisplay.show();
	}
	
}
