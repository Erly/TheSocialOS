package net.thesocialos.shared.model;

import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindexed;

@Subclass
public abstract class Oauth1 extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Unindexed private String token; // A value used by the Consumer to gain access to the Protected Resources on behalf
										// of the User, instead of using the User�s Service Provider credentials.
	
	@Unindexed private String tokenSecret; // A secret used by the Consumer to establish ownership of a given Token.
	
	public Oauth1() {
		
	}
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	/**
	 * @return the tokenSecret
	 */
	public String getTokenSecret() {
		return tokenSecret;
	}
	
	/**
	 * @param tokenSecret
	 *            the tokenSecret to set
	 */
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	
}
