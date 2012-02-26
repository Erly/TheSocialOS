package net.thesocialos.shared.model;


import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
@Unindexed
public class UserDetails {
	
	@Parent Key<User> owner;
	
	String address;
	String mobilePhone;
	String job;
	String firstName;
	String lasName;
	

	public UserDetails(){
		
	}
}
