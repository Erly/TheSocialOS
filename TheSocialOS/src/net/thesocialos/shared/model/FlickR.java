package net.thesocialos.shared.model;

import com.googlecode.objectify.annotation.NotSaved;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.condition.IfDefault;

@Subclass
public class FlickR extends Oauth1 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotSaved(IfDefault.class) public final static String CONSUMER_KEY = "cc01bd2671d139d49a97d10179ff6341";
	@NotSaved(IfDefault.class) public final static String CONSUMER_SECRET = "3d016edebfd20a11";
	
	public FlickR() {
		
	}
	
	public FlickR(String token, String secret) {
		this.setToken(token);
		this.setTokenSecret(secret);
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	
}
