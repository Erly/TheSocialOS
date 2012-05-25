package net.thesocialos.client.i18n;

import com.google.gwt.i18n.client.Constants;

public interface SocialOSConstants extends Constants {
	
	@DefaultStringValue("Loading...")
	String loading();
	
	@DefaultStringValue("Cancel")
	String cancel();
	
	/**
	 * User Constants
	 */
	@DefaultStringValue("Contact")
	String contact();
	
	@DefaultStringValue("Contacts")
	String contacts();
	
	@DefaultStringValue("Group")
	String group();
	
	@DefaultStringValue("Groups")
	String groups();
	
	/**
	 * UserMenu Constants
	 */
	
	@DefaultStringValue("Edit profile")
	String editProfile();
	
	@DefaultStringValue("Logout")
	String logout();
	
	/**
	 * Error messages Constants
	 */
	
	@DefaultStringValue("The app may be out of date. Reload this page in your browser.")
	String error_AppOutOfDate();
	
	@DefaultStringValue("The email you entered is not valid")
	String error_Email();
	
	@DefaultStringValue("You must fill all the textboxes")
	String error_emptyTxt();
	
	@DefaultStringValue("The email you entered is not registered or the password is incorrect")
	String error_login();
	
	@DefaultStringValue("The password you entered is not valid. It must have at least 6 characters")
	String error_Password();
	
	@DefaultStringValue("The 2 passwords you entered don't match. Please try again")
	String error_Password2();
	
	@DefaultStringValue("A serialization error ocurred. Try again.")
	String error_Serialization();
	
	@DefaultStringValue("This is taking too long, try again")
	String error_Timeout();
	
	/**
	 * LoginView Constants
	 */
	
	@DefaultStringValue("Log in")
	String login();
	
	@DefaultStringValue("Keep me logged in")
	String keepLogged();
	
	@DefaultStringValue("Password")
	String password();
	
	@DefaultStringValue("Forgot your password?")
	String forgotPassword();
	
	@DefaultStringValue("Don't have an account? Register now")
	String registerNow();
	
	/**
	 * Register Constants
	 */
	
	@DefaultStringValue("Repeat your password")
	String password2();
	
	@DefaultStringValue("Register")
	String register();
	
	@DefaultStringValue("User registered correctly")
	String registerSuccesful();
	
	/**
	 * Folder name Constants
	 */
	
	@DefaultStringValue("Pictures")
	String pictures();
	
	@DefaultStringValue("Music")
	String music();
	
	@DefaultStringValue("Other")
	String other();
	
	@DefaultStringValue("Videos")
	String videos();
	
	/**
	 * Profile Constants
	 */
	
	@DefaultStringValue("Address")
	String address();
	
	@DefaultStringValue("Mobile")
	String mobile();
	
	@DefaultStringValue("Name")
	String name();
	
	@DefaultStringValue("Last Name")
	String lastName();
	
	@DefaultStringValue("Bio")
	String Bio();
	
	@DefaultStringValue("Title")
	String title();
	
	@DefaultStringValue("Facebook Account")
	String facebookAccount();
	
	@DefaultStringValue("Flickr Account")
	String flickrAccount();
	
	@DefaultStringValue("Google Account")
	String googleAccount();
	
	@DefaultStringValue("Twitter Account")
	String twitterAccount();
	
	@DefaultStringValue("Upload")
	String upload();
}
