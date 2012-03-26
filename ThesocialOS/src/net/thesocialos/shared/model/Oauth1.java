package net.thesocialos.shared.model;

import com.googlecode.objectify.annotation.NotSaved;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindexed;
import com.googlecode.objectify.condition.IfDefault;

@SuppressWarnings("serial")
@Subclass
public abstract class Oauth1 extends Account {
	@NotSaved(IfDefault.class) static String CONSUMER_KEY;
	@NotSaved(IfDefault.class) static String CONSUMER_SECRET;
	
	@Unindexed String token; //A value used by the Consumer to gain access to the Protected Resources on behalf of the User, instead of using the User’s Service Provider credentials.
	
	@Unindexed String tokenSecret; //A secret used by the Consumer to establish ownership of a given Token.

	public Oauth1(){
		
	}
}
