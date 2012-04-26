package net.thesocialos.shared.model;

public class OutStandingLines extends Lines {
	
	private int receptionDate;
	
	public void setReceptionDate(int receptionDate) {
		this.receptionDate = receptionDate;
	}
	
	public OutStandingLines() {
		super();
	}
	
	public int getReceptionDate() {
		return receptionDate;
	}
	
}
