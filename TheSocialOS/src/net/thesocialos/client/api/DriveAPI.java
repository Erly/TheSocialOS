package net.thesocialos.client.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.desktop.window.FolderWindow;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Google;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public class DriveAPI {
	
	public class File implements Media {
		private String id;
		private String title;
		private String parent;
		
		public File() {
		}
		
		public File(String id, String title, String parent) {
			this.id = id;
			this.title = title;
			this.parent = parent;
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
			return "http://www.thesocialos.net/images/File.png";
		}
	}
	
	public DriveAPI() {
		// TODO Auto-generated constructor stub
	}
	
	private Google getGoogleAccount() {
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Google) return (Google) account;
		}
		return null;
	}
	
	public void loadFoldersInFolder(final FolderWindow folder) {
		Google googleAccount = getGoogleAccount();
		if (null == googleAccount) return;
		String url = "https://docs.google.com/feeds/default/private/full?v=3&alt=json&showroot=true&access_token="
				+ googleAccount.getAuthToken();
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(url, new AsyncCallback<JavaScriptObject>() {
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onSuccess(JavaScriptObject result) {
				JSONObject js = new JSONObject(result);
				// Window.alert(js.toString());
				JSONArray array = js.get("feed").isObject().get("entry").isArray();
				HashSet<DriveAPI.File> files = new HashSet<DriveAPI.File>();
				// Window.alert(array.size() + "");
				for (int i = 0; i < array.size(); i++) {
					JSONObject parentLink = array.get(i).isObject().get("link").isArray().get(0).isObject();
					if (parentLink.get("rel").isString().stringValue().contains("parent")) {
						String id = array.get(i).isObject().get("id").isObject().get("$t").isString().stringValue();
						String title = array.get(i).isObject().get("title").isObject().get("$t").isString()
								.stringValue();
						String parentName = parentLink.get("title").isString().stringValue();
						files.add(new File(id, title, parentName));
					}
				}
				folder.addMedia(files);
				// loadFilesInFolder(files);
			}
		});
	}
	
	protected void loadFilesInFolder(ArrayList<File> files) {
		/*
		 * Node root = new Node("My Drive"); ArrayList<Node> open = new ArrayList<Node>(); open.add(root); while
		 * (!open.isEmpty()) { Node parent = open.get(0); open.remove(0); for (File f : files) if
		 * (f.parent.equalsIgnoreCase(parent.getName())) { Node node = new Node(f.title, parent); parent.addChild(node);
		 * open.add(node); } } open.clear(); open.add(root); while (open.size() > 0) { Node node = open.get(0);
		 * open.remove(0); if (node.getChildren().isEmpty()) { String s = node.getName(); Node parent =
		 * node.getParent(); while (null != parent) { s = parent.getName() + " -> " + s; parent = parent.getParent(); }
		 * } else for (Node n : node.getChildren()) open.add(n); }
		 */
	}
}
