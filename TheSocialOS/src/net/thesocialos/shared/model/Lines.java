package net.thesocialos.shared.model;

import javax.persistence.Id;

import com.google.gwt.i18n.shared.impl.cldr.DateTimeFormatInfoImpl_es;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;

public class Lines implements IsSerializable {
	
	private @Id Long id;
	private long date; // The date that has been written
	private @Unindexed String text; // The text of the line
	private @Parent Key<Conversation> converOwner;
	private Key<User> userOwner;
	
	public Lines(String text, Key<User> userOwner, long date) {
		this.text = text;
		this.userOwner = userOwner;
		this.date = date;
		new DateTimeFormatInfoImpl_es();
	}
	
	public Lines() {
		
	}
	
	public Key<Conversation> getConverOwner() {
		return converOwner;
	}
	
	public long getDate() {
		return date;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public Key<User> getUserOwner() {
		return userOwner;
	}
	
}
