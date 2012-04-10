package net.thesocialos.shared.model;

import com.googlecode.objectify.annotation.NotSaved;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.condition.IfDefault;

@Subclass
public class Twitter extends Oauth1 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotSaved(IfDefault.class) public static String CONSUMER_KEY = "KQ6iX3bbpfDDpKGYtx2X8A";
	@NotSaved(IfDefault.class) public static String CONSUMER_SECRET = "VKwB7Ro0oCvePk1YnqlXLK9b1hdliEDF0qClr9U8w";

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	public Twitter(){
		
	}

	public Twitter(String token, String tokenSecret) {
		this.setToken(token);
		this.setTokenSecret(tokenSecret);
	}
}
