package net.thesocialos.client.app;

import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;

public class FrameApp implements IApplication{
	
	private String name;
	private String image;
	private String url;
	private Frame frame;
	private String height;
	private String width;
	
	public FrameApp(){
		
	}

	public Widget run() {
		// TODO Auto-generated method stub
		return frame = new Frame(url);
	}
	
	public FrameApp(String appName, String appImageURL, String appURL,String width,String height) {
		setName(appName);
		setImage(appImageURL);
		this.setHeight(height);
		this.setWidth(width);
		
		url = appURL;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setSize(String width, String height) {
		frame.setSize(width, height);
	}
	
	public String getHeight() {
		return height;
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getWidth() {
		return width;
	}
	
	public void setWidth(String width) {
		this.width = width;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return image;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	@Override
	public void setImage(String image) {
		// TODO Auto-generated method stub
		this.image = image;
	}
}