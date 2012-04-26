package net.thesocialos.shared;

public class App {
	
	private String name;
	private String image;
	private String url;
	
	public App(String appName, String appImageURL, String appURL) {
		name = appName;
		image = appImageURL;
		url = appURL;
	}
	
	public String getImage() {
		return image;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
