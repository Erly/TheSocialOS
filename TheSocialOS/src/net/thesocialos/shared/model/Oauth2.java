package net.thesocialos.shared.model;

import com.googlecode.objectify.annotation.NotSaved;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindexed;
import com.googlecode.objectify.condition.IfDefault;

@SuppressWarnings("serial")
@Subclass
public abstract class Oauth2 extends Account {
	@NotSaved(IfDefault.class) static String CLIENTID;
	@NotSaved(IfDefault.class) static final String PRODUCTNAME ="TheSocialOS";
	
	@Unindexed String authToken; //Token to login
	
	@Unindexed String refreshToken; //Token to refresh the authToken
	
	public Oauth2(){
		
	}
}
