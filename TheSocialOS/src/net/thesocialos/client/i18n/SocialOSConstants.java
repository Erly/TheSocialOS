package net.thesocialos.client.i18n;

import com.google.gwt.i18n.client.Constants;

public interface SocialOSConstants extends Constants {
	
	@DefaultStringValue("Loading...")
	String loading();

	@DefaultStringValue("Cancel")
	String cancel();

	@DefaultStringValue("Upload")
	String upload();
	
	/**
	 * Error messages Constants
	 */
	@DefaultStringValue("The app may be out of date. Reload this page in your browser.")
	String error_AppOutOfDate();
	
	@DefaultStringValue("A serialization error ocurred. Try again.")
	String error_Serialization();
	
	@DefaultStringValue("This is taking too long, try again")
	String error_Timeout();

	@DefaultStringValue("The email you entered is not valid")
	String error_Email();

	@DefaultStringValue("The password you entered is not valid. It must have at least 6 characters")
	String error_Password();

	@DefaultStringValue("The 2 passwords you entered don't match. Please try again")
	String error_Password2();

	@DefaultStringValue("You must fill all the textboxes")
	String error_emptyTxt();
	
	/**
	 * LoginView Constants
	 */
	@DefaultStringValue("Password")
	String password();
	
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
	@DefaultStringValue("Repeat your password")
	String password2();
	
	@DefaultStringValue("Name")
	String name();
	
	@DefaultStringValue("Last Name")
	String lastName();
	
	@DefaultStringValue("Register")
	String register();
	
	@DefaultStringValue("User registered correctly")
	String registerSuccesful();
	
	/**
	 * UserMenu Constants
	 */
	@DefaultStringValue("Edit profile")
	String editProfile();
	
	@DefaultStringValue("Logout")
	String logout();

	/**
	 * Profile Constants
	 */
	@DefaultStringValue("Title")
	String title();

	@DefaultStringValue("Mobile")
	String mobile();

	@DefaultStringValue("Address")
	String address();

	@DefaultStringValue("Google Account")
	String googleAccount();

	@DefaultStringValue("Facebook Account")
	String facebookAccount();
	
	/**
	 * User Constantas
	 */
	@DefaultStringValue("Contact")
	String  contact();
	@DefaultStringValue("Contacts")
	String  contacts();
	@DefaultStringValue("Group")
	String group();
	@DefaultStringValue("Groups")
	String groups();

}
