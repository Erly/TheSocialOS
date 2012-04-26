package net.thesocialos.client.app;

import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;

public class FrameApp implements IApplication {
	
	private String name;
	private String image;
	private String url;
	private Frame frame;
	private String height;
	private String width;
	
	public FrameApp() {
		
	}
	
	public FrameApp(String appName, String appImageURL, String appURL, String width, String height) {
		setName(appName);
		setImage(appImageURL);
		this.setHeight(height);
		this.setWidth(width);
		
		url = appURL;
	}
	
	public String getHeight() {
		return height;
	}
	
	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return image;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getWidth() {
		return width;
	}
	
	public Widget run() {
		// TODO Auto-generated method stub
		return frame = new Frame(url);
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	
	@Override
	public void setImage(String image) {
		// TODO Auto-generated method stub
		this.image = image;
	}
	
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}
	
	public void setSize(String width, String height) {
		frame.setSize(width, height);
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setWidth(String width) {
		this.width = width;
	}
}