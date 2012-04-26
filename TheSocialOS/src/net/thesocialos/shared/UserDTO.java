package net.thesocialos.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserDTO implements Serializable {
	
	private Long id;
	private String email;
	private String name;
	private String lastName;
	private String title;
	private String mobile;
	private String address;
	private String avatar;
	private String background;
	private String channelID;
	
	public UserDTO() {
	}
	
	public UserDTO(Long id, String email, String name, String lastName, String title, String mobile, String address,
			String avatar, String background) {
		super();
		setId(id);
		setEmail(email);
		setName(name);
		setLastName(lastName);
		setTitle(title);
		setMobile(mobile);
		setAddress(address);
		setAvatar(avatar);
		setBackground(background);
	}
	
	public UserDTO(Long id, String email, String name, String lastName, String title, String mobile, String address,
			String avatar, String background, String channelID) {
		super();
		setId(id);
		setEmail(email);
		setName(name);
		setLastName(lastName);
		setTitle(title);
		setMobile(mobile);
		setAddress(address);
		setAvatar(avatar);
		setBackground(background);
		setChannelID(channelID);
	}
	
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public String getBackground() {
		return background;
	}
	
	public String getChannelID() {
		return channelID;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public void setBackground(String background) {
		this.background = background;
	}
	
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
}
