package net.thesocialos.client.api;

import net.thesocialos.client.TheSocialOS;

import com.google.gwt.dev.util.collect.HashSet;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class Picasa {
	
	public Picasa() {
		
	}
	
	public class Album {
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
		public String getId() {
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
		public String getTitle() {
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
		public int getNumPhotos() {
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
	
	public void getAlbumsRequest(RequestCallback cb) throws RequestException {
		String picasaAPIurl = "http://picasaweb.google.com/data/feed/api/user/";
		// Temporal way of obtaining the username until it is included in the UserDTO
		String email = TheSocialOS.get().getCurrentUser().getEmail();
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, picasaAPIurl + email.substring(0, email.indexOf('@')));
		builder.setCallback(cb);
		Request request = builder.send();
	}
	
	public HashSet<Album> getAlbums(Response response) {
		String text = response.getText();
		Document xml = XMLParser.parse(text);
		NodeList entries = xml.getElementsByTagName("entry");
		HashSet<Album> albums = new HashSet<Album>();
		for (int i = 0; i < entries.getLength(); i++) {
			NodeList list = entries.item(i).getChildNodes();
			Album album = new Album();
			for (int j = 0; j < list.getLength(); j++) {
				String name = list.item(j).getNodeName();
				String value = list.item(j).getNodeValue();
				System.out.println(list.item(j).toString());
				if ("id".equals(name)) {
					album.setId(value);
				} else if ("title".equals(name)) {
					album.setTitle(value);
				} else if ("summary".equals(name)) {
					album.setSummary(value);
				} else if ("media:thumbail".equals(name)) {
					String url = ((Element)list.item(j)).getAttribute("url");
					album.setThumbnailURL(url);
				} else if ("numphotos".equals(name)) {
					album.setNumPhotos(Integer.parseInt(value));
				} else if ("gphoto:commentingEnabled".equals(name)) {
					album.setCommentingEnabled(Boolean.parseBoolean(value));
				} else if ("commentCount".equals(name)) {
					album.setCommentCount(Integer.parseInt(value));
				}
			}
			albums.add(album);
		}
		return albums;
	}
}
