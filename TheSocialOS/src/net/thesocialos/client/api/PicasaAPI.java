package net.thesocialos.client.api;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.view.window.FolderWindow;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Google;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public class PicasaAPI {
	
	public PicasaAPI() {
		
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
	
	public void getAlbumsRequest(AsyncCallback<JavaScriptObject> cb) throws RequestException {
		String picasaAPIurl = "http://picasaweb.google.com/data/feed/api/user/";
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		Google googleAccount = null;
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Google) {
				googleAccount = (Google) account;
				break;
			}
		}
		if (null == googleAccount)
			return;
		String email = googleAccount.getUsername();
		String username = email.substring(0, email.indexOf('@'));
		
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(picasaAPIurl + username + "?alt=json&access_token=" + googleAccount.getAuthToken(), cb);
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
	
	public void loadAlbumsInFolder(final FolderWindow folder) {
		String picasaAPIurl = "http://picasaweb.google.com/data/feed/api/user/";
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		Google googleAccount = null;
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Google) {
				googleAccount = (Google) account;
				break;
			}
		}
		if (null == googleAccount)
			return;
		String email = googleAccount.getUsername();
		String username = email.substring(0, email.indexOf('@'));
		
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(picasaAPIurl + username + "?alt=json&" + googleAccount.getAuthToken(), new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(JavaScriptObject result) {
				JSONObject object = new JSONObject(result);
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
				folder.addMedia(albums);
			}
		});
	}
}