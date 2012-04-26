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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
