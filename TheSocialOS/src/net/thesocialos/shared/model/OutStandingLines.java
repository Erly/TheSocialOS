package net.thesocialos.shared.model;

import java.util.Date;

import com.googlecode.objectify.Key;

public class OutStandingLines extends Lines {
	
	private int receptionDate;
	
	public OutStandingLines(String text, Key<User> userOwner, Date date) {
		super(text, userOwner, date);
	}
	
	public int getReceptionDate() {
		return receptionDate;
	}
	
	public void setReceptionDate(int receptionDate) {
		this.receptionDate = receptionDate;
	}
	
}
