package net.thesocialos.client.i18n;

import com.google.gwt.i18n.client.Messages;

public interface SocialOSMessages extends Messages {
	@DefaultMessage("The email {0} is already in use")
	String error_UserExists(String email);
	
	@DefaultMessage("You have no {0}, try adding a third party account (Google, Facebook...) to your profile.")
	String folder_NoContent(String contentType);
}
