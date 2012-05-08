package net.thesocialos.client.api;

public class MediaParent implements Media {
	
	public static final String PICTURES = "PICTURES";
	public static final String VIDEOS = "VIDEOS";
	public static final String MUSIC = "MUSIC";
	public static final String OTHER = "OTHER";
	
	private String id;
	private String name;
	
	public MediaParent(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getID() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getThumbnailURL() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
