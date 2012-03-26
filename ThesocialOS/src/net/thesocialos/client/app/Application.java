package net.thesocialos.client.app;

import net.thesocialos.client.desktop.DesktopUnit;

public abstract class Application  implements IApplication {
	private String image;
	private String name;
	private String height;
	private String widght;

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
	
	public Application(){
		
	}
	
}
