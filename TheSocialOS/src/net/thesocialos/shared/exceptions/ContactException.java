package net.thesocialos.shared.exceptions;

@SuppressWarnings("serial")
public class ContactException extends Exception {

public ContactException(){
		
	}
	public ContactException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ContactException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ContactException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ContactException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		//super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
}
