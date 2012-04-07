package net.thesocialos.shared.model;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class FlickR extends Oauth1 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	void refresh() {
		// TODO Auto-generated method stub

	}
	
	public FlickR(){
		
	}

	public FlickR(String token, String secret) {
		this.token = token;
		this.tokenSecret = secret;
	}

}
