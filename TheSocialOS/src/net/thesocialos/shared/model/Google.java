package net.thesocialos.shared.model;

@SuppressWarnings("serial")
public class Google extends Oauth2 {
	
	private String email;
	
	public Google(){
		
	}
	
	@Override
	void refresh() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
