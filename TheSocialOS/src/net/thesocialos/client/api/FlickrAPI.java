package net.thesocialos.client.api;

import java.util.Iterator;
import java.util.Map;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.googlecode.objectify.Key;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.desktop.window.FolderWindow;
import net.thesocialos.client.oauth.OAuth;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.FlickR;

public class FlickrAPI {
	
	public FlickrAPI() {
		// TODO Auto-generated constructor stub
	}
	
	public class Album implements MediaAlbum {
		private String id;
		private String title;
		private String description;
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
		 * @return the title
		 */
		@Override
		public String getName() {
			return title;
		}
		
		/**
		 * @return the description
		 */
		@Override
		public String getDescription() {
			return description;
		}
		
		/**
		 * @return the thumbnailURL
		 */
		@Override
		public String getThumbnailURL() {
			return thumbnailURL;
		}
		
		/**
		 * @return the numPhotos
		 */
		@Override
		public int getElementCount() {
			return numPhotos;
		}
		
		/**
		 * @return the commentingEnabled
		 */
		public boolean isCommentingEnabled() {
			return commentingEnabled;
		}
		
		/**
		 * @return the commentCount
		 */
		public int getCommentCount() {
			return commentCount;
		}
	}
	
	public class Picture implements MediaPicture {
		
		private String id;
		private String title;
		private String url;
		private String thumbnailURL;
		private boolean commentingEnabled;
		private int commentCount;
		
		@Override
		public String getID() {
			return id;
		}
		
		@Override
		public String getName() {
			return title;
		}
		
		@Override
		public String getThumbnailURL() {
			return thumbnailURL;
		}
		
		@Override
		public String getUrl() {
			return url;
		}
		
		@Override
		public String getDescription() {
			return "";
		}
		
	}
	
	public void loadAlbumsInFolder(final FolderWindow folder) {
		String url = "http://api.flickr.com/services/rest/?method=flickr.photosets.getList&nojsoncallback=1&per_page=9999";
		final FlickR flickrAccount = getFlickrAccount();
		if (null == flickrAccount) return;
		
		url = OAuth.signRequest(FlickR.CONSUMER_KEY, FlickR.CONSUMER_SECRET, flickrAccount.getToken(),
				flickrAccount.getTokenSecret(), url);
		send(url, new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				JSONObject object = JSONParser.parseStrict(response.getText()).isObject();
				JSONArray array = object.get("photosets").isObject().get("photoset").isArray();
				for (int i = 0; i < array.size(); i++) {
					Album album = new Album();
					album.id = array.get(i).isObject().get("id").isString().stringValue();
					album.title = array.get(i).isObject().get("title").isObject().get("_content").isString()
							.stringValue();
					album.description = array.get(i).isObject().get("description").isObject().get("_content")
							.isString().stringValue();
					album.numPhotos = Integer.parseInt(array.get(i).isObject().get("photos").isString().stringValue());
					album.commentingEnabled = array.get(i).isObject().get("can_comment").isNumber().toString()
							.equals('1') ? true : false;
					album.commentCount = Integer.parseInt(array.get(i).isObject().get("count_comments").isString()
							.stringValue());
					String cover_photo_id = array.get(i).isObject().get("primary").isString().stringValue();
					loadAlbumInFolder(album, cover_photo_id, folder, flickrAccount);
				}
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert(exception.getMessage());
			}
		});
	}
	
	private void loadAlbumInFolder(final Album album, String cover_photo_id, final FolderWindow folder,
			FlickR flickrAccount) {
		String url = "http://api.flickr.com/services/rest/?method=flickr.photos.getSizes&nojsoncallback=1&per_page=9999";
		url += "&photo_id=" + cover_photo_id;
		
		url = OAuth.signRequest(FlickR.CONSUMER_KEY, FlickR.CONSUMER_SECRET, flickrAccount.getToken(),
				flickrAccount.getTokenSecret(), url);
		send(url, new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				JSONObject object = JSONParser.parseStrict(response.getText()).isObject();
				JSONArray array = object.get("sizes").isObject().get("size").isArray();
				album.thumbnailURL = array.get(1).isObject().get("source").isString().stringValue();
				folder.addMedia(album);
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert(exception.getMessage());
			}
		});
	}
	
	public void loadPicturesInFolder(Album album, final FolderWindow folder) {
		String url = "http://api.flickr.com/services/rest/?method=flickr.photosets.getPhotos&nojsoncallback=1&per_page=9999";
		url += "&photoset_id=" + album.getID();
		final FlickR flickrAccount = getFlickrAccount();
		if (null == flickrAccount) return;
		
		url = OAuth.signRequest(FlickR.CONSUMER_KEY, FlickR.CONSUMER_SECRET, flickrAccount.getToken(),
				flickrAccount.getTokenSecret(), url);
		send(url, new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				JSONObject object = JSONParser.parseStrict(response.getText()).isObject();
				JSONArray array = object.get("photoset").isObject().get("photo").isArray();
				for (int i = 0; i < array.size(); i++) {
					Picture picture = new Picture();
					picture.id = array.get(i).isObject().get("id").isString().stringValue();
					picture.title = array.get(i).isObject().get("title").isString().stringValue();
					loadPictureInFolder(picture, folder, flickrAccount);
				}
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert(exception.getMessage());
			}
		});
	}
	
	private void loadPictureInFolder(final Picture picture, final FolderWindow folder, FlickR flickrAccount) {
		String url = "http://api.flickr.com/services/rest/?method=flickr.photos.getSizes&nojsoncallback=1&per_page=9999";
		url += "&photo_id=" + picture.getID();
		
		url = OAuth.signRequest(FlickR.CONSUMER_KEY, FlickR.CONSUMER_SECRET, flickrAccount.getToken(),
				flickrAccount.getTokenSecret(), url);
		send(url, new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				JSONObject object = JSONParser.parseStrict(response.getText()).isObject();
				// HashSet<Picture> pictures = new HashSet<Picture>();
				JSONArray array = object.get("sizes").isObject().get("size").isArray();
				picture.url = array.get(array.size() - 1).isObject().get("source").isString().stringValue();
				picture.thumbnailURL = array.get(1).isObject().get("source").isString().stringValue();
				folder.addMedia(picture);
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert(exception.getMessage());
			}
		});
	}
	
	private FlickR getFlickrAccount() {
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof FlickR) { return (FlickR) account; }
		}
		return null;
	}
	
	protected void send(String Url, RequestCallback cb) {
		/*
		 * JsonpRequestBuilder jsonp = new JsonpRequestBuilder(); jsonp.setTimeout(10000); jsonp.requestObject(Url, cb);
		 */
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Url);
		builder.setTimeoutMillis(10000);
		builder.setCallback(cb);
		
		Request req = null;
		try {
			req = builder.send();
		} catch (RequestException e) {
			cb.onError(req, e);
		}
	}
}
