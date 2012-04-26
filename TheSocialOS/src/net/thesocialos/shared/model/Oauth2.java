package net.thesocialos.shared.model;

import java.util.Date;

import com.googlecode.objectify.annotation.NotSaved;
import com.googlecode.objectify.annotation.Subclass;
import com.googlecode.objectify.annotation.Unindexed;
import com.googlecode.objectify.condition.IfDefault;

@Subclass
public abstract class Oauth2 extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotSaved(IfDefault.class) static String CLIENTID;
	@NotSaved(IfDefault.class) static final String PRODUCTNAME = "TheSocialOS";
	
	@Unindexed private String authToken; // Token to login
	
	@Unindexed private String refreshToken; // Token to refresh the authToken
	
	@Unindexed private Date expireDate;
	
	public Oauth2() {
		
	}
	
	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}
	
	/**
	 * @param authToken
	 *            the authToken to set
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
	 * @param refreshToken
	 *            the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	/**
	 * @return the expireDate
	 */
	public Date getExpireDate() {
		return expireDate;
	}
	
	/**
	 * @param expireDate
	 *            the expireDate to set
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
}
