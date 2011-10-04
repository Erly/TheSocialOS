package net.thesocialos.shared;

public class UserSummaryDTO {

	private String id;
	private String displayName;
	
	public UserSummaryDTO() {
		
	}

	public UserSummaryDTO(String id, String displayName) {
		super();
		this.id = id;
		this.displayName = displayName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
		
}
