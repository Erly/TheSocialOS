package net.thesocialos.client.api;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.api.PicasaAPI.Album;
import net.thesocialos.client.view.window.FolderWindow;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Facebook;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public class FacebookAPI {
	
	public class Album implements Media {

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
	
	public void loadAlbumsInFolder(FolderWindow folder) {
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
		loadAlbumInFolder(folder, facebookAPIurl + username + "/albums?access_token=" + facebookAccount.getAuthToken());
	}
	
	private void loadAlbumInFolder(final FolderWindow folder, String facebookUrl) {
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(facebookUrl, new AsyncCallback<JavaScriptObject>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
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
					album.thumbnailURL = array.get(i).isObject().get("media$group").isObject().get("media$thumbnail").isArray().get(0).isObject().get("url").isString().stringValue();
					album.numPhotos = Integer.parseInt(array.get(i).isObject().get("count").isNumber().toString());
					albums.add(album);
				}
				folder.addMedia(albums);
				
				JSONObject js = object.get("paging").isObject();
				JSONString nextAlbumsUrl = js.get("next").isString();
				if (null != nextAlbumsUrl && !"".equals(nextAlbumsUrl))
					loadAlbumInFolder(folder, nextAlbumsUrl.stringValue());
			}
		});
	}
}
