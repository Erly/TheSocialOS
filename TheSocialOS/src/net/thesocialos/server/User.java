package net.thesocialos.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

import net.thesocialos.shared.UserDTO;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class User {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String email;
	
	@Persistent
	private String password;

	@Persistent
	private String name;

	@Persistent
	private String lastName;
	
	@Persistent
	List<Long> friends;
	
	@Persistent
	private Date lastActive = new Date();

	@Persistent
	private Blob image;
	
	@Persistent
	private Blob background;
	
	public User() {
		friends = new ArrayList<Long>();
	}
	
	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public User(String email, String password, String name, String lastName) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
	}
	
	/*
	public static User getDefaultUser() {
		String defaultEmail = "erlantz@thesocialos.net";
	    
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    User oneResult = null, detached = null;
	    Query q = pm.newQuery(User.class, "email == :email");
	    q.setUnique(true);
	    try {
	      oneResult = (User) q.execute(defaultEmail);
	      if (oneResult != null) {
	        detached = pm.detachCopy(oneResult);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      pm.close();
	      q.closeAll();
	    }
	    return detached;
	}

	public static User getDefaultUser(PersistenceManager pm) {
		String defaultEmail = "erlantz@thesocialos.net";
	    
	    User oneResult = null;
	    Query q = pm.newQuery(User.class, "email == :email");
	    q.setUnique(true);
	    try {
	      oneResult = (User) q.execute(defaultEmail);
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      q.closeAll();
	    }
	    return oneResult;
	}

*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Long> getFriends() {
		return friends;
	}

	public void addFriend(Long id) {
		friends.add(id);
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
	    return new UserDTO(user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getLastName());
	  }

	public byte[] getBackground() {
		if (background == null)
			return null;
		return background.getBytes();
	}

	public void setBackground(byte[] bytes) {
		this.background = new Blob(bytes);
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
}
