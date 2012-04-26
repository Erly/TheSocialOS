package net.thesocialos.client.desktop;

import com.google.gwt.user.client.ui.AbsolutePanel;

public abstract class DesktopUnit {
	
	public enum TypeUnit {
		APPLICATION, INFO, POPUP
	}
	protected String programID;
	protected int x;
	protected int y;
	protected Boolean isHidden;
	protected int zPosition;
	
	protected boolean isMinimized;
	
	protected TypeUnit typeUnit;
	
	public abstract void close(AbsolutePanel absolutePanel);
	
	public abstract void getID();
	
	public abstract int getXPosition();
	
	public abstract int getYPosition();
	
	public abstract int getZposition();
	
	public abstract void isMinimized();
	
	public abstract void maximize();
	
	public abstract void minimize();
	
	public abstract void open(AbsolutePanel absolutePanel);
	
	public abstract void setPosition(int x, int y);
	
	public abstract void toFront();
	
	public abstract void toZPosition(int position);
	
}
