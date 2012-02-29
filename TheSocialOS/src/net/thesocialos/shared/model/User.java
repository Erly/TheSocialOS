package net.thesocialos.shared.model;

import java.io.Serializable;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Unindexed;

@SuppressWarnings("serial")
@Cached
public class User implements Serializable {

	@Id Long id;
	String email; //Email of the user
	@Unindexed String password; //Password of the user
	Blob picture; //Avatar of the user
	Blob background; //Background of the user
	
	Key<Group> groups[];
	
	Key<Conversation> conversations[];
	
	Key<OutConversation> offlineConversations[];
	
	Key<Session> sessions[];
	
	public User(){
		
	}
}
