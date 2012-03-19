package net.thesocialos.client.api;

import java.util.HashSet;

import net.thesocialos.client.TheSocialOS;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class Picasa {
	
	public Picasa() {
		
	}
	
	public class Album implements Media {
		private String id;
		private String title;
		private String summary;
		private String thumbnailURL;
		private int numPhotos;
		private boolean commentingEnabled;
		private int commentCount;
		/**
		 * @return the id
		 */
		@Override
		public String getID() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the title
		 */
		@Override
		public String getName() {
			return title;
		}
		/**
		 * @param title the title to set
		 */
		public void setTitle(String title) {
			this.title = title;
		}
		/**
		 * @return the summary
		 */
		public String getSummary() {
			return summary;
		}
		/**
		 * @param summary the summary to set
		 */
		public void setSummary(String summary) {
			this.summary = summary;
		}
		/**
		 * @return the thumbnailURL
		 */
		@Override
		public String getThumbnailURL() {
			return thumbnailURL;
		}
		/**
		 * @param thumbnailURL the thumbnailURL to set
		 */
		public void setThumbnailURL(String thumbnailURL) {
			this.thumbnailURL = thumbnailURL;
		}
		/**
		 * @return the numPhotos
		 */
		public int getElementCount() {
			return numPhotos;
		}
		/**
		 * @param numPhotos the numPhotos to set
		 */
		public void setNumPhotos(int numPhotos) {
			this.numPhotos = numPhotos;
		}
		/**
		 * @return the commentingEnabled
		 */
		public boolean isCommentingEnabled() {
			return commentingEnabled;
		}
		/**
		 * @param commentingEnabled the commentingEnabled to set
		 */
		public void setCommentingEnabled(boolean commentingEnabled) {
			this.commentingEnabled = commentingEnabled;
		}
		/**
		 * @return the commentCount
		 */
		public int getCommentCount() {
			return commentCount;
		}
		/**
		 * @param commentCount the commentCount to set
		 */
		public void setCommentCount(int commentCount) {
			this.commentCount = commentCount;
		}		
	}
	
	public void getAlbumsRequest(AsyncCallback<JavaScriptObject> cb) throws RequestException {
		String picasaAPIurl = "http://picasaweb.google.com/data/feed/api/user/";
		// Temporal way of obtaining the username until it is included in the UserDTO
		String email = TheSocialOS.get().getCurrentUser().getEmail();
		String username = email.substring(0, email.indexOf('@'));
				
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(picasaAPIurl + username + "?alt=json-in-script", cb);
	}
	
	public HashSet<Album> getAlbums(JSONObject object) {
		HashSet<Album> albums = new HashSet<Album>();
		JSONArray array = object.get("feed").isObject().get("entry").isArray();
		for (int i = 0; i < array.size(); i++) {
			Album album = new Album();
			album.id = array.get(i).isObject().get("id").isObject().get("$t").isString().stringValue();
			album.title = array.get(i).isObject().get("title").isObject().get("$t").isString().stringValue();
			album.summary = array.get(i).isObject().get("summary").isObject().get("$t").isString().stringValue();
			album.thumbnailURL = array.get(i).isObject().get("media$group").isObject().get("media$thumbnail").isArray().get(0).isObject().get("url").isString().stringValue();
			album.numPhotos = Integer.parseInt(array.get(i).isObject().get("gphoto$numphotos").isObject().get("$t").isNumber().toString());
			album.commentingEnabled = Boolean.parseBoolean(array.get(i).isObject().get("gphoto$commentingEnabled").isObject().get("$t").isString().stringValue());
			album.commentCount = Integer.parseInt(array.get(i).isObject().get("gphoto$commentCount").isObject().get("$t").isNumber().toString());
			albums.add(album);
		}
		return albums;
	}
}
