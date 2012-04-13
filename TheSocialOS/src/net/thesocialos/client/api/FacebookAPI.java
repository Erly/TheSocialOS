package net.thesocialos.client.api;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.view.window.FolderWindow;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Facebook;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public class FacebookAPI {
	
	public class Album implements MediaAlbum {

		private String id;
		private String name;
		private String thumbnailURL;
		private int numPhotos;

		@Override
		public String getID() {
			return id;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getThumbnailURL() {
			return thumbnailURL;
		}

		@Override
		public int getElementCount() {
			return numPhotos;
		}
		
	}
	
	public class Picture implements MediaPicture {

		private String id;
		private String title;
		private String url;
		private String thumbnailURL;
		
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
		
	}

	
	public void getAlbumsRequest(AsyncCallback<JavaScriptObject> cb) {
		String facebookAPIurl = "https://graph.facebook.com/";
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		Facebook facebookAccount = null;
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Facebook) {
				facebookAccount = (Facebook) account;
				break;
			}
		}
		if (null == facebookAccount)
			return;
		String username = facebookAccount.getUsername();
		
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(facebookAPIurl + username + "/albums?" + facebookAccount.getAuthToken(), cb);
	}
	
	public HashSet<Album> getAlbums(JSONObject object) {
		HashSet<Album> albums = new HashSet<Album>();
		JSONArray array = object.get("data").isArray();
		for (int i = 0; i < array.size(); i++) {
			Album album = new Album();
			album.id = array.get(i).isObject().get("id").isString().stringValue();
			album.name = array.get(i).isObject().get("name").isString().stringValue();
			album.thumbnailURL = array.get(i).isObject().get("media$group").isObject().get("media$thumbnail").isArray().get(0).isObject().get("url").isString().stringValue();
			album.numPhotos = Integer.parseInt(array.get(i).isObject().get("count").isNumber().toString());
			albums.add(album);
		}
		/* This method only loads the first page of albums for now
		 * JSONObject js = object.get("paging").isObject();
		 */
		return albums;
	}
	
	public void loadAlbumsInFolder(final FolderWindow folder) {
		String facebookAPIurl = "https://graph.facebook.com/";
		Facebook facebookAccount = getFacebookAccount();
		
		if (null == facebookAccount)
			return;
		String username = facebookAccount.getUsername();
		facebookAPIurl += username + "/albums?access_token=" + facebookAccount.getAuthToken();
		loadAlbumInFolder(folder, facebookAPIurl);
	}

	private void loadAlbumInFolder(final FolderWindow folder, String facebookUrl) {
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(facebookUrl, new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getMessage());
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(JavaScriptObject result) {
				JSONObject object = new JSONObject(result);
				HashSet<Album> albums = new HashSet<Album>();
				JSONArray array = object.get("data").isArray();
				for (int i = 0; i < array.size(); i++) {
					Album album = new Album();
					album.id = array.get(i).isObject().get("id").isString().stringValue();
					album.name = array.get(i).isObject().get("name").isString().stringValue();
					album.thumbnailURL = "http://www.thesocialos.net/images/Folder.png";
					//album.thumbnailURL = array.get(i).isObject().get("media$group").isObject().get("media$thumbnail").isArray().get(0).isObject().get("url").isString().stringValue();
					album.numPhotos = Integer.parseInt(array.get(i).isObject().get("count").isNumber().toString());
					albums.add(album);
				}
				if (albums.size() > 0)
					folder.addMedia(albums);
				JSONValue js = object.get("paging").isObject();
				if (null != js) {
					JSONString nextAlbumsUrl = js.isObject().get("next").isString();
					if (null != nextAlbumsUrl && !"".equals(nextAlbumsUrl))
						loadAlbumInFolder(folder, nextAlbumsUrl.stringValue());
				}
			}
		});
	}
	
	public void loadPicturesInFolder(Album album, final FolderWindow folder) {
		String facebookAPIurl = "https://graph.facebook.com/";
		Facebook facebookAccount = getFacebookAccount();
		
		if (null == facebookAccount)
			return;
		facebookAPIurl += album.getID() + "/photos?access_token=" + facebookAccount.getAuthToken();
		loadPicturesInFolder(folder, facebookAPIurl);
	}
	
	private void loadPicturesInFolder(final FolderWindow folder, String facebookUrl) {
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(facebookUrl, new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getMessage());
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(JavaScriptObject result) {
				JSONObject object = new JSONObject(result);
				HashSet<Picture> pictures = new HashSet<Picture>();
				JSONArray array = object.get("data").isArray();
				for (int i = 0; i < array.size(); i++) {
					Picture picture = new Picture();
					picture.id = array.get(i).isObject().get("id").isString().stringValue();
					JSONValue s = null;
					if ((s = array.get(i).isObject().get("name")) != null)
						picture.title = s.isString().stringValue();
					else
						picture.title = picture.getID();
					picture.url = array.get(i).isObject().get("source").isString().stringValue();
					picture.thumbnailURL = array.get(i).isObject().get("picture").isString().stringValue();
					//album.thumbnailURL = array.get(i).isObject().get("media$group").isObject().get("media$thumbnail").isArray().get(0).isObject().get("url").isString().stringValue();
					
					pictures.add(picture);
				}
				if (pictures.size() > 0)
					folder.addMedia(pictures);
				JSONValue js = object.get("paging");
				if (null != js) {
					JSONString nextPicturesUrl = js.isObject().get("next").isString();
					if (null != nextPicturesUrl && !"".equals(nextPicturesUrl))
						loadPicturesInFolder(folder, nextPicturesUrl.stringValue());
				}
			}
		});
	}
	
	private Facebook getFacebookAccount() {
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Facebook) {
				return (Facebook)account;
			}
		}
		return null;
	}
}
