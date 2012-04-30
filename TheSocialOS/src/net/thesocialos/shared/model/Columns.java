package net.thesocialos.shared.model;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Unindexed;

public class Columns implements Serializable {
	
	public enum TYPE {
		TIMELINE, SEARCH, LIST;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1246235297743607751L;
	
	public static final String HOME = "HOME";
	public static final String MENTIONS = "MENTIONS";
	public static final String USER = "USER";
	
	@Id Long id;
	
	@Unindexed private TYPE type;
	@Unindexed private String value;
	@Unindexed private String lastTweetId = "";
	
	public Columns() {
	}
	
	/**
	 * The constructor of the column
	 * 
	 * @param type
	 *            A constant from the enum TYPE, it can be: TIMELINE, SEARCH or LIST
	 * @param value
	 *            If it is a TIMELINE type it ca be the HOME or USER constant or screen_name of a user. If it is a
	 *            SEARCH it must be the query. And if it is a LIST it must be the list name.
	 */
	public Columns(TYPE type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * @return the lastTweetId
	 */
	public String getLastTweetId() {
		return lastTweetId;
	}
	
	/**
	 * @return the type
	 */
	public TYPE getType() {
		return type;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @param lastTweetId
	 *            the lastTweetId to set
	 */
	public void setLastTweetId(String lastTweetId) {
		this.lastTweetId = lastTweetId;
	}
	
}
