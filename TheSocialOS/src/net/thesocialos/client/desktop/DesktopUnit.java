package net.thesocialos.client.desktop;

import net.thesocialos.client.desktop.window.WindowDisplay;

import com.google.gwt.user.client.ui.AbsolutePanel;

public abstract class DesktopUnit {

	protected int programID;
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
	
	public enum TypeUnit  {APPLICATION,INFO,POPUP,STATIC}
	protected  TypeUnit typeUnit;
	protected WindowDisplay display;
	public  void toFront(){
		display.toFront();
	}
	
	public  void toBack(){
		display.toback();
	}
	
	public abstract void toZPosition(int position);
	
	public abstract int getZposition();
	
	public  void setPosition(int x, int y){
		display.setPosition(x, y);
	}
	
	
	public int getAbsoluteLeft() {
		
		return display.getXposition();
	}

	public int getAbsoluteTop() {
	
		return display.getYPosition();
	}
	
	public abstract void close(AbsolutePanel absolutePanel);
	
	public abstract void open(AbsolutePanel absolutePanel);
	/**
	 * Set the window minimized if the  minimized is true or restore if minimized is true
	 * @param minimized 
	 */
	public  void setMinimized(Boolean minimized){
		
		this.minimized = minimized;
		display.setMinimized(minimized);
	}
	/**
	 * Set Windows maximized. If maximized is false the window restore to normal view
	 * @param maximized
	 * @param height
	 * @param width
	 */
	public  void setMaximized(Boolean maximized,int width, int height,int top, int left){
		if (this.maximized){

			display.setPosition(beforeX, beforeY);
			display.setSize(beforeWidth, beforeHeight);
			this.maximized = false;
			display.setMaximized(false);
		}else{
			this.maximized = maximized;
			display.setMaximized(true);
			beforeX = display.getXposition();
			beforeY = display.getYPosition();
			beforeHeight = display.getHeight();
			beforeWidth = display.getwidth();
			display.setPosition(left, top);
			display.setSize(width, height);
		}
		
	}
	
	
	/**
	 * Return the identification of the application
	 * @return 
	 */
	public int getID(){
		return programID;
	}
	/**
	 * Return Width of the window in pixels
	 * @return int
	 */
	public int getWidth() {
		return display.getwidth();
	}

	/**
	 * Return Height of the window in pixels
	 * @return int
	 */
	public int getHeight() {

		return display.getHeight();
	}
	/**
	 * Return if the window is Minimized
	 * @return boolean
	 */
	public boolean isMinimizable(){
		return isMinimizable;
	}
	/**
	 * Return if the window is Maximized
	 * @return boolean
	 */
	public boolean isMaximizable(){
		return isMaximizable;
	}
	/**
	 * Return if the window is now Minimized
	 * @return boolean
	 */
	public boolean isMinimized(){
		return minimized;
	}
	/**
	 * Return if the window is now Maximized
	 * @return boolean
	 */
	public boolean isMaximized(){
		return maximized;
	}
	/**
	 * Set size of the window
	 * @param height in pixels
	 * @param width in pixels
	 */
	public  void setSize(int width, int height){
		
		display.setSize(width, height);
	}
	
}
