package net.thesocialos.server.model;

public class Account {

	public static final String GPLUS = "Google+";
	
	public static final String PICASSA = "Picassa";
	
	public static final String YOUTUBE = "Youtube";
	
	public static final String FACEBOOK = "Facebook";
	
	public static final String TWITTER = "Twitter";
	
	public static final String FLICKR = "FlickR";
	
	public String type;
	
	public String token;
	
	public Account() {}
	
	public Account(String type, String token) {
		this.type = type;
		this.token = token;
	}
}
