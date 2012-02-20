package net.thesocialos.server.model;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Session {
	
@PrimaryKey
@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true") //Ad encoded key
private String key;

@Persistent
private String sessionID; // Session ID

@Persistent
private Date expireDate; // Session expire date

@Persistent
private User user; // User of the session

	public Session(String sessionID, User user, Date expireDate){
		this.sessionID = sessionID;
		this.user = user;
		this.expireDate = expireDate;
	}

	public String getSessionID() {
		return sessionID;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getKey() {
		return key;
	}

	public User getUser() {
		return user;
	}
}
