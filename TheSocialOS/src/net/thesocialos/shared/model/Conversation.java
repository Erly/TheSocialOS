package net.thesocialos.shared.model;

import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Entity
public class Conversation {
	
	private @Id Long id;
	private int date; // The date of the conversation created
	private @Unindexed List<Key<User>> ConversationAdmins;
	
	public Conversation() {
		
	}
	
	public boolean addConversationAdmin(Key<User> userToAdmin) {
		return ConversationAdmins.add(userToAdmin);
	}
	
	public List<Key<User>> getConversationAdmins() {
		return ConversationAdmins;
	}
	
	public int getDate() {
		return date;
	}
	
	public Long getId() {
		return id;
	}
	
	public boolean removeConversationAdmin(Key<User> userAdmin) {
		return ConversationAdmins.remove(userAdmin);
	}
	
	public void setDate(int date) {
		this.date = date;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
