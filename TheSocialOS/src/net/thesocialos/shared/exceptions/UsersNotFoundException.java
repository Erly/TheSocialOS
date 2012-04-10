package net.thesocialos.shared.exceptions;

@SuppressWarnings("serial")
public class UsersNotFoundException extends Exception {

public UsersNotFoundException(){
		
	}
	public UsersNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UsersNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public UsersNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UsersNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		//super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
}
