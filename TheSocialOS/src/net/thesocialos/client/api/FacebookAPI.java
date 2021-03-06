package net.thesocialos.client.api;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.desktop.window.FolderWindow;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.SocialService;
import net.thesocialos.client.service.SocialServiceAsync;
import net.thesocialos.client.view.deck.DeckColumn;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Facebook;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public class FacebookAPI {
	
	private static final SocialServiceAsync socialService = GWT.create(SocialService.class);
	
	public class Album implements MediaAlbum {
		
		private String id;
		private String name;
		private String thumbnailURL;
		private int numPhotos;
		
		@Override
		public String getDescription() {
			return "";
		}
		
		@Override
		public int getElementCount() {
			return numPhotos;
		}
		
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
		
		public void setThumbnailUrl(String url) {
			thumbnailURL = url;
		}
		
	}
	
	public class Picture implements MediaPicture {
		
		private String id;
		private String title;
		private String url;
		private String thumbnailURL;
		
		@Override
		public String getDescription() {
			return "";
		}
		
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
	
	public class Post {
		private String id;
		private String user_id;
		private String user_name;
		private Date created_at;
		private String text;
		
		/**
		 * @return the created_at date
		 */
		public Date getCreated_at() {
			return created_at;
		}
		
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		
		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}
		
		/**
		 * @return the user_id
		 */
		public String getUser_id() {
			return user_id;
		}
		
		/**
		 * @return the user_name
		 */
		public String getUser_name() {
			return user_name;
		}
	}
	
	private Facebook getFacebookAccount() {
		// Map<Key<Account>, Account> accounts =
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Facebook) return (Facebook) account;
		}
		return null;
	}
	
	private void loadAlbumInFolder(final Album album, String cover_photo_id, final FolderWindow folder,
			Facebook facebookAccount) {
		String facebookAPIurl = "https://graph.facebook.com/" + cover_photo_id + "?access_token="
				+ facebookAccount.getAuthToken();
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(facebookAPIurl, new AsyncCallback<JavaScriptObject>() {
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println(caught.getMessage());
				caught.printStackTrace();
			}
			
			@Override
			public void onSuccess(JavaScriptObject result) {
				JSONObject object = new JSONObject(result);
				album.setThumbnailUrl(object.get("picture").isString().stringValue());
				folder.addMedia(album);
			}
		});
		
	}
	
	public void loadAlbumsInFolder(final FolderWindow folder) {
		String facebookAPIurl = "https://graph.facebook.com/";
		Facebook facebookAccount = getFacebookAccount();
		
		if (null == facebookAccount) return;
		String username = facebookAccount.getUsername();
		facebookAPIurl += username + "/albums?access_token=" + facebookAccount.getAuthToken();
		loadAlbumsInFolder(folder, facebookAPIurl, facebookAccount);
	}
	
	private void loadAlbumsInFolder(final FolderWindow folder, String facebookUrl, final Facebook facebookAccount) {
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
				JSONArray array = object.get("data").isArray();
				for (int i = 0; i < array.size(); i++) {
					Album album = new Album();
					album.id = array.get(i).isObject().get("id").isString().stringValue();
					album.name = array.get(i).isObject().get("name").isString().stringValue();
					album.numPhotos = Integer.parseInt(array.get(i).isObject().get("count").isNumber().toString());
					String cover_photo_id = array.get(i).isObject().get("cover_photo").isString().stringValue();
					loadAlbumInFolder(album, cover_photo_id, folder, facebookAccount);
				}
				JSONValue js = object.get("paging");
				if (null != js) {
					JSONString nextAlbumsUrl = js.isObject().get("next").isString();
					if (null != nextAlbumsUrl && !"".equals(nextAlbumsUrl))
						loadAlbumsInFolder(folder, nextAlbumsUrl.stringValue(), facebookAccount);
				}
			}
		});
	}
	
	public void loadPicturesInFolder(Album album, final FolderWindow folder) {
		String facebookAPIurl = "https://graph.facebook.com/";
		Facebook facebookAccount = getFacebookAccount();
		
		if (null == facebookAccount) return;
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
					if ((s = array.get(i).isObject().get("name")) != null) picture.title = s.isString().stringValue();
					else
						picture.title = picture.getID();
					picture.url = array.get(i).isObject().get("source").isString().stringValue();
					picture.thumbnailURL = array.get(i).isObject().get("picture").isString().stringValue();
					// album.thumbnailURL =
					// array.get(i).isObject().get("media$group").isObject().get("media$thumbnail").isArray().get(0).isObject().get("url").isString().stringValue();
					
					pictures.add(picture);
				}
				if (pictures.size() > 0) folder.addMedia(pictures);
				JSONValue js = object.get("paging");
				if (null != js) {
					JSONString nextPicturesUrl = js.isObject().get("next").isString();
					if (null != nextPicturesUrl && !"".equals(nextPicturesUrl))
						loadPicturesInFolder(folder, nextPicturesUrl.stringValue());
				}
			}
		});
	}
	
	public void post(final String message) {
		final Facebook facebookAccount = getFacebookAccount();
		new RPCXSRF<String>(socialService) {
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			
			@Override
			public void onSuccess(String result) {
				// Window.alert(result);
				new Timer() {
					
					@Override
					public void run() {
						// loadColumns(facebookAccount);
					}
				}.schedule(1000);
			}
			
			@Override
			protected void XSRFcallService(AsyncCallback<String> cb) {
				socialService.postOnFacebook(facebookAccount, message, cb);
			}
		}.retry(3);
	}
	
	public void loadWall(final DeckColumn panel) {
		Facebook facebookAccount = getFacebookAccount();
		if (null == facebookAccount) return;
		String urlString = "https://graph.facebook.com/me/home?access_token=" + facebookAccount.getAuthToken();
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(urlString, new AsyncCallback<JavaScriptObject>() {
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
			}
			
			@Override
			public void onSuccess(JavaScriptObject result) {
				JSONObject object = new JSONObject(result);
				HashSet<Post> posts = new HashSet<Post>();
				JSONArray array = object.get("data").isArray();
				for (int i = 0; i < array.size(); i++) {
					Post post = new Post();
					post.id = array.get(i).isObject().get("id").isString().stringValue();
					post.user_id = array.get(i).isObject().get("from").isObject().get("id").isString().stringValue();
					post.user_name = array.get(i).isObject().get("from").isObject().get("name").isString()
							.stringValue();
					String text = "";
					if (null != array.get(i).isObject().get("message")) text = array.get(i).isObject().get("message")
							.isString().stringValue();
					else if (null != array.get(i).isObject().get("story")) text = array.get(i).isObject().get("story")
							.isString().stringValue();
					else if (null != array.get(i).isObject().get("caption")) text = array.get(i).isObject()
							.get("caption").isString().stringValue();
					else if (null != array.get(i).isObject().get("description"))
						text = array.get(i).isObject().get("description").isString().stringValue();
					post.text = text;
					post.created_at = new Date(array.get(i).isObject().get("created_time").isString().stringValue());
					posts.add(post);
				}
				Set<Post> oldPosts = panel.getPosts();
				if (null != oldPosts && !oldPosts.isEmpty()) posts.addAll(posts);
				else
					panel.setPosts(posts);
				// panel.clearPosts();
				panel.loadAll();
			}
		});
	}
}
