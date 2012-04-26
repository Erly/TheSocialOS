package net.thesocialos.client.api;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.api.YoutubeAPI.Folder.TYPE;
import net.thesocialos.client.desktop.window.FolderWindow;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Google;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public class YoutubeAPI {
	
	public class Album implements MediaAlbum {
		
		private String id;
		private String title;
		private String description;
		private String thumbnailURL;
		private int numVideos;
		private boolean commentingEnabled;
		private int commentCount;
		
		/**
		 * @return the commentCount
		 */
		public int getCommentCount() {
			return commentCount;
		}
		
		/**
		 * @return the description
		 */
		@Override
		public String getDescription() {
			return description;
		}
		
		/**
		 * @return the numPhotos
		 */
		@Override
		public int getElementCount() {
			return numVideos;
		}
		
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
		 * @return the thumbnailURL
		 */
		@Override
		public String getThumbnailURL() {
			return thumbnailURL;
		}
		
		/**
		 * @return the commentingEnabled
		 */
		public boolean isCommentingEnabled() {
			return commentingEnabled;
		}
	}
	
	public static class Folder implements Media {
		
		public enum TYPE {
			UPLOADS("Uploads"), PLAYLIST("Playlists"), FAVORITES("Favorites");
			
			private String title;
			
			private TYPE(String title) {
				this.title = title;
			}
			
			public String getTitle() {
				return title;
			}
		}
		
		private String id;
		private String title;
		private String thumbnailURL;
		private TYPE type;
		
		public Folder(TYPE type) {
			title = type.getTitle();
			this.type = type;
			thumbnailURL = "http://www.thesocialos.net/images/Folder.png";
		}
		
		public Folder(TYPE type, String thumbnailURL) {
			title = type.getTitle();
			this.type = type;
			this.thumbnailURL = thumbnailURL;
		}
		
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
		
		public TYPE getType() {
			return type;
		}
		
	}
	
	public class Video implements MediaPicture {
		
		private String id;
		private String title;
		private String description;
		private String url;
		private String thumbnailURL;
		private boolean commentingEnabled;
		private int commentCount;
		
		public int getCommentCount() {
			return commentCount;
		}
		
		@Override
		public String getDescription() {
			return description;
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
		
		public boolean isCommentingEnabled() {
			return commentingEnabled;
		}
	}
	
	public YoutubeAPI() {
		// TODO Auto-generated constructor stub
	}
	
	/* FOLDER LOADERS */
	
	public Folder createFolder(TYPE type) {
		return new Folder(type);
	}
	
	public Folder createFolder(TYPE type, String thumbnailUrl) {
		return new Folder(type, thumbnailUrl);
	}
	
	/* VIDEO LOADERS */
	
	private Google getGoogleAccount() {
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Google) return (Google) account;
		}
		return null;
	}
	
	public void loadFavoritesInFolder(final FolderWindow folder) {
		String youtubeAPIurl = "https://gdata.youtube.com/feeds/api/users/default/favorites";
		Google googleAccount = getGoogleAccount();
		if (null == googleAccount) return;
		
		youtubeAPIurl += "?alt=json&access_token=" + googleAccount.getAuthToken();
		loadVideosInFolder(youtubeAPIurl, folder);
	}
	
	private void loadFoldersInFolder(String requestUrl, final FolderWindow folder) {
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(requestUrl, new AsyncCallback<JavaScriptObject>() {
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(JavaScriptObject result) {
				JSONObject object = new JSONObject(result);
				HashSet<Album> playlists = new HashSet<Album>();
				JSONArray array = object.get("feed").isObject().get("entry").isArray();
				for (int i = 0; i < array.size(); i++) {
					Album playlist = new Album();
					playlist.id = array.get(i).isObject().get("yt$playlistId").isObject().get("$t").isString()
							.stringValue();
					playlist.title = array.get(i).isObject().get("title").isObject().get("$t").isString().stringValue();
					playlist.description = array.get(i).isObject().get("yt$description").isObject().get("$t")
							.isString().stringValue();
					playlist.thumbnailURL = array.get(i).isObject().get("media$group").isObject()
							.get("media$thumbnail").isArray().get(0).isObject().get("url").isString().stringValue();
					playlist.numVideos = Integer.parseInt(array.get(i).isObject().get("gd$feedLink").isArray().get(0)
							.isObject().get("countHint").isNumber().toString());
					playlists.add(playlist);
				}
				folder.addMedia(playlists);
			}
		});
	}
	
	public void loadPlaylistsInFolder(final FolderWindow folder) {
		String youtubeAPIurl = "https://gdata.youtube.com/feeds/api/users/default/playlists";
		Google googleAccount = getGoogleAccount();
		if (null == googleAccount) return;
		
		youtubeAPIurl += "?alt=json&access_token=" + googleAccount.getAuthToken();
		loadFoldersInFolder(youtubeAPIurl, folder);
	}
	
	public void loadPlaylistVideosInFolder(Album playlist, final FolderWindow folder) {
		String youtubeAPIurl = "https://gdata.youtube.com/feeds/api/playlists/";
		Google googleAccount = getGoogleAccount();
		if (null == googleAccount) return;
		
		youtubeAPIurl += playlist.getID() + "?alt=json&access_token=" + googleAccount.getAuthToken();
		loadVideosInFolder(youtubeAPIurl, folder);
	}
	
	public void loadUploadsInFolder(final FolderWindow folder) {
		String youtubeAPIurl = "https://gdata.youtube.com/feeds/api/users/default/uploads";
		Google googleAccount = getGoogleAccount();
		if (null == googleAccount) return;
		
		youtubeAPIurl += "?alt=json&access_token=" + googleAccount.getAuthToken();
		loadVideosInFolder(youtubeAPIurl, folder);
	}
	
	private void loadVideosInFolder(String requestUrl, final FolderWindow folder) {
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(requestUrl, new AsyncCallback<JavaScriptObject>() {
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(JavaScriptObject result) {
				JSONObject object = new JSONObject(result);
				HashSet<Video> videos = new HashSet<Video>();
				JSONArray array = object.get("feed").isObject().get("entry").isArray();
				for (int i = 0; i < array.size(); i++) {
					JSONValue v = null;
					if ((v = array.get(i).isObject().get("app$control")) != null)
						if (null != v.isObject().get("app$draft")) continue;
					Video video = new Video();
					video.id = array.get(i).isObject().get("id").isObject().get("$t").isString().stringValue();
					video.title = array.get(i).isObject().get("title").isObject().get("$t").isString().stringValue();
					video.url = array.get(i).isObject().get("link").isArray().get(0).isObject().get("href").isString()
							.stringValue();
					video.url = video.url.replace("watch?v=", "embed/");
					video.url = video.url.substring(0, video.url.indexOf('&')) + "?autoplay=1";
					video.description = array.get(i).isObject().get("media$group").isObject().get("media$description")
							.isObject().get("$t").isString().stringValue();
					video.thumbnailURL = array.get(i).isObject().get("media$group").isObject().get("media$thumbnail")
							.isArray().get(1).isObject().get("url").isString().stringValue();
					// video.commentingEnabled =
					// Boolean.parseBoolean(array.get(i).isObject().get("gphoto$commentingEnabled").isObject().get("$t").isString().stringValue());
					video.commentCount = Integer.parseInt(array.get(i).isObject().get("gd$comments").isObject()
							.get("gd$feedLink").isObject().get("countHint").isNumber().toString());
					videos.add(video);
				}
				folder.addMedia(videos);
			}
		});
	}
}
