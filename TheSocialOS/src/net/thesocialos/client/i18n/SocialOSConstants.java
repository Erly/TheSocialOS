package net.thesocialos.client.i18n;

import com.google.gwt.i18n.client.Constants;

public interface SocialOSConstants extends Constants {
	
	@DefaultStringValue("Address")
	String address();
	
	@DefaultStringValue("Cancel")
	String cancel();
	
	/**
	 * User Constantas
	 */
	@DefaultStringValue("Contact")
	String contact();
	
	@DefaultStringValue("Contacts")
	String contacts();
	
	/**
	 * UserMenu Constants
	 */
	@DefaultStringValue("Edit profile")
	String editProfile();
	
	/**
	 * Error messages Constants
	 */
	@DefaultStringValue("The app may be out of date. Reload this page in your browser.")
	String error_AppOutOfDate();
	
	@DefaultStringValue("The email you entered is not valid")
	String error_Email();
	
	@DefaultStringValue("You must fill all the textboxes")
	String error_emptyTxt();
	
	@DefaultStringValue("The password you entered is not valid. It must have at least 6 characters")
	String error_Password();
	
	@DefaultStringValue("The 2 passwords you entered don't match. Please try again")
	String error_Password2();
	
	@DefaultStringValue("A serialization error ocurred. Try again.")
	String error_Serialization();
	
	@DefaultStringValue("This is taking too long, try again")
	String error_Timeout();
	
	@DefaultStringValue("Facebook Account")
	String facebookAccount();
	
	@DefaultStringValue("Flickr Account")
	String flickrAccount();
	
	@DefaultStringValue("Forgot your password?")
	String forgotPassword();
	
	@DefaultStringValue("Google Account")
	String googleAccount();
	
	@DefaultStringValue("Group")
	String group();
	
	@DefaultStringValue("Groups")
	String groups();
	
	@DefaultStringValue("Keep me logged in")
	String keepLogged();
	
	@DefaultStringValue("Last Name")
	String lastName();
	
	@DefaultStringValue("Loading...")
	String loading();
	
	@DefaultStringValue("Log in")
	String login();
	
	@DefaultStringValue("Logout")
	String logout();
	
	@DefaultStringValue("Mobile")
	String mobile();
	
	@DefaultStringValue("Music")
	String music();
	
	@DefaultStringValue("Name")
	String name();
	
	@DefaultStringValue("Other")
	String other();
	
	/**
	 * LoginView Constants
	 */
	@DefaultStringValue("Password")
	String password();
	
	/**
	 * Register Constants
	 */
	@DefaultStringValue("Repeat your password")
	String password2();
	
	/**
	 * Folder name Constants
	 */
	@DefaultStringValue("Pictures")
	String pictures();
	
	@DefaultStringValue("Register")
	String register();
	
	@DefaultStringValue("Don't have an account? Register now")
	String registerNow();
	
	@DefaultStringValue("User registered correctly")
	String registerSuccesful();
	
	/**
	 * Profile Constants
	 */
	@DefaultStringValue("Title")
	String title();
	
	@DefaultStringValue("Twitter Account")
	String twitterAccount();
	
	@DefaultStringValue("Upload")
	String upload();
	
	@DefaultStringValue("Videos")
	String videos();
}
