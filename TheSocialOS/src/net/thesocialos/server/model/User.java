package net.thesocialos.server.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import net.thesocialos.server.utils.Base64Utilities;
import net.thesocialos.shared.UserDTO;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class User {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	// Id that autogenerates when a user is inserted in the Datastore
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true") //Ad encoded key
	private String key;

	@Persistent
	@Extension(vendorName="datanucleus", key="gae.pk-id", value="true")
	private Long keyID;
	
	@Persistent
	private String email;
	
	@Persistent
	private String password;

	@Persistent
	private String name;

	@Persistent
	private String lastName;
	
	@Persistent
	private String mobile = "";
	
	@Persistent
	private String address = "";
	
	@Persistent(mappedBy= "user")
	@Element (dependent = "true")
	private Set<Session> sessions = new HashSet<Session>();
	@Persistent
	private Date lastActive = new Date();

	@Persistent
	private Blob image;	// Avatar
	
	@Persistent
	private Blob background; // Background
	
	@Persistent
	private Vector<Account> accounts;
	
	@Persistent
	private String channelID;
	
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}

	public String getChannelID() {
		return channelID;
	}

	@Element (dependent = "true")
	private  ArrayList<Group> grupos = new ArrayList<Group>();


	public User() {
		
	}

	public User(String email, String password, String name, String lastName) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		
	}

	public String getKey() {
		return key;
	}

	public Long getKeyID() {
		return keyID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	public void setBasicInfo(String email, String name, String lastName) {
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}
	
	public static UserDTO toDTO(User user) {
	    if (user == null) {
	      return null;
	    }
	    return new UserDTO(user.getKeyID(), user.getEmail(), user.getName(), user.getLastName(), user.getClass().getSimpleName(), user.getMobile(), 
	    		user.getAddress(), "data:image/png;base64," + Base64Utilities.toBase64(user.getImage()), "data:image/png;base64," + Base64Utilities.toBase64(user.getBackground()),user.getChannelID());
	  }


	public byte[] getImage() {
		if (image == null)
			return null;
		return image.getBytes();
	}
	
	public byte[] getBackground() {
		if (background == null)
			return null;
		return background.getBytes();
	}
	
	public final Session searchSession(String attribute){
		java.util.Iterator<Session> iterator = getSessions().iterator();
		Session session;
		while (iterator.hasNext()){
			session =  iterator.next();
			if (session.getSessionID().equals(attribute)){
				return session;
			}
		}
		return null;
		
	}

	public void setImage(byte[] image) {
		this.image = new Blob(image);
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastActive() {
		return lastActive;
	}

	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}


	public Set<Session> getSessions() {
		return sessions;
	}

	public ArrayList<Group> getGrupos() {
		return grupos;
	}

	public Vector<Account> getAccounts() {
		return accounts;
	}

	public void addAccount(Account account) {
		this.accounts.addElement(account);
	}


}
