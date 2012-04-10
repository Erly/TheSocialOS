package net.thesocialos.client.api;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.api.PicasaAPI.Album;
import net.thesocialos.client.oauth.OAuth;
import net.thesocialos.client.view.window.FolderWindow;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.FlickR;

public class FlickrAPI {

	public FlickrAPI() {
		// TODO Auto-generated constructor stub
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
		 * @return the title
		 */
		@Override
		public String getName() {
			return title;
		}
		
		/**
		 * @return the summary
		 */
		public String getSummary() {
			return summary;
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
	
	public void loadAlbumsInFolder(final FolderWindow folder) {
		String url = "http://api.flickr.com/services/rest/?method=flickr.photosets.getList&nojsoncallback=1&per_page=9999";
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		FlickR flickrAccount = null;
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof FlickR) {
				flickrAccount = (FlickR) account;
				break;
			}
		}
		if (null == flickrAccount)
			return;
		url = OAuth.signRequest(FlickR.CONSUMER_KEY, FlickR.CONSUMER_SECRET, flickrAccount.getToken(), flickrAccount.getTokenSecret(), url);
		send(url, new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				JSONObject object = JSONParser.parseStrict(response.getText()).isObject(); 
				HashSet<Album> albums = new HashSet<Album>();
				JSONArray array = object.get("photosets").isObject().get("photoset").isArray();
				for (int i = 0; i < array.size(); i++) {
					Album album = new Album();
					album.id = array.get(i).isObject().get("id").isString().stringValue();
					album.title = array.get(i).isObject().get("title").isObject().get("_content").isString().stringValue();
					album.summary = array.get(i).isObject().get("description").isObject().get("_content").isString().stringValue();
					album.thumbnailURL = "http://www.thesocialos.net/images/Folder.png";
					//album.thumbnailURL = array.get(i).isObject().get("media$group").isObject().get("media$thumbnail").isArray().get(0).isObject().get("url").isString().stringValue();
					album.numPhotos = Integer.parseInt(array.get(i).isObject().get("photos").isString().stringValue());
					album.commentingEnabled = array.get(i).isObject().get("can_comment").isNumber().toString().equals('1') ? true : false;
					album.commentCount = Integer.parseInt(array.get(i).isObject().get("count_comments").isString().stringValue());
					albums.add(album);
				}
				folder.addMedia(albums);
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert(exception.getMessage());
			}
		});
		/*new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(JavaScriptObject result) {
				Window.alert(result.toSource());
				Window.alert(result.toString());
				JSONObject object = new JSONObject(result);
				Window.alert(object.toString()); 
				HashSet<Album> albums = new HashSet<Album>();
				JSONArray array = object.get("photosets").isObject().get("photoset").isArray();
				for (int i = 0; i < array.size(); i++) {
					Album album = new Album();
					album.id = array.get(i).isObject().get("id").isString().stringValue();
					album.title = array.get(i).isObject().get("title").isObject().get("_content").isString().stringValue();
					Window.alert(album.title);
					album.summary = array.get(i).isObject().get("description").isObject().get("_content").isString().stringValue();
					album.thumbnailURL = "http://www.thesocialos.net/images/Folder.png";
					//album.thumbnailURL = array.get(i).isObject().get("media$group").isObject().get("media$thumbnail").isArray().get(0).isObject().get("url").isString().stringValue();
					album.numPhotos = Integer.parseInt(array.get(i).isObject().get("photos").isString().stringValue());
					album.commentingEnabled = array.get(i).isObject().get("can_comment").isNumber().toString().equals('1') ? true : false;
					album.commentCount = Integer.parseInt(array.get(i).isObject().get("count_comments").isString().stringValue());
					albums.add(album);
				}
				folder.addMedia(albums);
			}
		});*/
	}
	
	protected void send(String Url, RequestCallback cb) {
		/*JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.setTimeout(10000);
		jsonp.requestObject(Url, cb);*/
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
