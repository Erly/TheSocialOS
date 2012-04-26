package net.thesocialos.client.app;

import com.google.gwt.user.client.ui.Widget;

public interface IApplication {
	
	public String getHeight();
	
	public String getImage();
	
	public String getName();
	
	public String getWidth();
	
	public Widget run();
	
	public void setImage(String image);
	
	public void setName(String name);
	
	public void setSize(String width, String height);
}
