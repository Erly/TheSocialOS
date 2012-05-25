package net.thesocialos.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

@SuppressWarnings("serial")
public class ImageUpload implements Serializable {
	@Id String key;
	String servingUrl;
	Date createdAt;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getServingUrl() {
		return servingUrl;
	}
	
	public void setServingUrl(String servingUrl) {
		this.servingUrl = servingUrl;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
