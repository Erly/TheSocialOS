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
	
	@Unindexed private String authToken; //Token to login
	
	@Unindexed private String refreshToken; //Token to refresh the authToken
	
	public Oauth2(){
		
	}

	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * @param authToken the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
