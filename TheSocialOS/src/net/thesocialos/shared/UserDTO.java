package net.thesocialos.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserDTO implements Serializable {

	private Long id;
	private String email;
	private String pass;
	private String name;
	private String lastName;
	
	
	public UserDTO() {
		
	}


	public UserDTO(Long id, String email, String password, String name, String lastName) {
		super();
		this.id = id;
		this.email = email;
		this.setPass(password);
		this.name = name;
		this.lastName = lastName;
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


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
