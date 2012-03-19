package net.thesocialos.client.app;


import com.google.gwt.user.client.ui.Widget;

public interface IApplication {
	
	public Widget run();
	
	public void setSize(String width, String height);
	
	public String getName();
	
	public String getImage();
	
	public String getWidth();
	
	public String getHeight();
}
