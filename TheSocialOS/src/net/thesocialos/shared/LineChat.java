package net.thesocialos.shared;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;

@Cached
public class LineChat {
	@Id String user;
	String channel;
	
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	Long date;
	
	public void setDate(Long date) {
		this.date = date;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public Long getDate() {
		return date;
	}
	
	public LineChat(String user, String channel, Long date) {
		this.user = user;
		this.channel = channel;
		this.date = date;
	}
	
	public LineChat() {
		
	}
	
}
