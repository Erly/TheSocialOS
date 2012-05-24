package net.thesocialos.shared.model;

public class ApplicationManager {
	private int type;
	private String name;
	
	public ApplicationManager(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
}
