package net.thesocialos.client.app;

import org.apache.catalina.startup.SetAllPropertiesRule;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;

public class FrameApp extends Application{
	

	private String url;
	private Frame frame;
	private String height;
	private String width;
	public FrameApp(){
		
	}

	@Override
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

	@Override
	public void setSize(String width, String height) {
		// TODO Auto-generated method stub
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


}
