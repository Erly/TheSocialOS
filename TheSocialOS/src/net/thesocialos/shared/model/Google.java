package net.thesocialos.shared.model;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class Google extends Oauth2 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Google() {
		
	}
	
	public Google(String userName) {
		super(userName);
	}
	
	@Override
	public void refresh() {
		
	}
}
