package net.thesocialos.shared.model;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class FlickR extends Oauth1 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}
	
	public FlickR(){
		
	}

	public FlickR(String token, String secret) {
		this.setToken(token);
		this.setTokenSecret(secret);
	}

}
