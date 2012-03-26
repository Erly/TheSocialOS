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
	
	
	public UserDTO() {}


	public UserDTO(Long id, String email, String name, String lastName, String title, String mobile, String address, String avatar, String background) {
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


	public UserDTO(Long id, String email, String name, String lastName, String title, String mobile, String address, String avatar, String background, String channelID) {
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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


	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public String getBackground() {
		return background;
	}


	public void setBackground(String background) {
		this.background = background;
	}


	public String getChannelID() {
		return channelID;
	}


	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
	
}
