package net.thesocialos.client.desktop;

import com.google.gwt.user.client.ui.AbsolutePanel;

public abstract class DesktopUnit {

	protected String programID;
	protected int x;
	protected int y;
	protected Boolean isHidden;
	protected int zPosition;
	protected boolean isMinimized;
	public enum TypeUnit  {APPLICATION,INFO,POPUP}
	protected  TypeUnit typeUnit;
	public abstract void toFront();
	
	public abstract void toZPosition(int position);
	
	public abstract int getZposition();
	
	public abstract void setPosition(int x, int y);
	
	public abstract int getXPosition();
	
	public abstract int getYPosition();
	
	public abstract void close(AbsolutePanel absolutePanel);
	
	public abstract void open(AbsolutePanel absolutePanel);
	
	public abstract void minimize();
	
	public abstract void maximize();
	
	public abstract void isMinimized();
	
	public abstract void getID();
	
	
	
}
