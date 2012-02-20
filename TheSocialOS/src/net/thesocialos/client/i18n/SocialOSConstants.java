package net.thesocialos.client.i18n;

import com.google.gwt.i18n.client.Constants;

public interface SocialOSConstants extends Constants {
	
	@DefaultStringValue("Loading...")
	String loading();
	
	/**
	 * Error messages Constants
	 */
	@DefaultStringValue("The app may be out of date. Reload this page in your browser.")
	String error_AppOutOfDate();
	
	@DefaultStringValue("A serialization error ocurred. Try again.")
	String error_Serialization();
	
	@DefaultStringValue("This is taking too long, try again")
	String error_Timeout();
	
	@DefaultStringValue("The 2 passwords you entered don't match. Please try again")
	String error_Password2();
	
	/**
	 * LoginView Constants
	 */
	@DefaultStringValue("Password")
	String password();
	
	@DefaultStringValue("Repeat your password")
	String password2();
	
	@DefaultStringValue("Keep me logged in")
	String keepLogged();
	
	@DefaultStringValue("Forgot your password?")
	String forgotPassword();
	
	@DefaultStringValue("Don't have an account? Register now")
	String registerNow();
	
	@DefaultStringValue("Log in")
	String login();
	
	/**
	 * Register Constants
	 */
	@DefaultStringValue("Register")
	String register();
	
	@DefaultStringValue("User registered correctly")
	String registerSuccesful();

}
