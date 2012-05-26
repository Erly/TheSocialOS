package net.thesocialos.shared.model;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class Facebook extends Oauth2 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Facebook() {
		
	}
	
	public Facebook(String nick) {
		super(nick);
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
