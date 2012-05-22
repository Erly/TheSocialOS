package net.thesocialos.shared.exceptions;

@SuppressWarnings("serial")
public class UserUpdateException extends Exception {
	public UserUpdateException() {
		
	}
	
	public UserUpdateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public UserUpdateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	public UserUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		// super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	
	public UserUpdateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
