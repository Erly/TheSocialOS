package net.thesocialos.shared;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;



@SuppressWarnings("serial")
public class Chat implements Serializable{

	@Id Long id;
	String text;
	public String getText() {
		return text;
	}

	long date;
	public void setDate(long date) {
		this.date = date;
	}

	String user;
	public String getUser() {
		return user;
	}





	public long getDate() {
		return date;
	}


	
	
	
	public Chat (String text, String owner){
		
		this.date = new Date().getTime();
		this.text = text;
		//this.user = new Key<User>(User.class, owner.getEmail());
		this.user = owner;
		
	}
	
	public Chat(){
		
	}
	
}
