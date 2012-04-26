package net.thesocialos.client.api;

import java.util.Iterator;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Google;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public class Plus {
	
	public class Activities {
		public class List {
			String userid = null;
			String collection = null;
			int maxResults = 0;
			String pageToken = null;
			
			public List() {
			}
			
			public void execute(AsyncCallback<JavaScriptObject> cb) {
				Google googleAccount = getGoogleAccount();
				if (null == getGoogleAccount()) return;
				
				String url = "https://www.googleapis.com/plus/v1/people/" + userid + "/activities/" + collection
						+ "?access_token=" + googleAccount.getAuthToken();
				if (0 != maxResults) url += "&maxResults=" + maxResults;
				if (null != pageToken) url += "&pageToken=" + pageToken;
				
				JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
				jsonp.requestObject(url, cb);
			}
			
			public void setCollection(String collection) {
				this.collection = collection;
			}
			
			public void setMaxResults(int maxResults) {
				this.maxResults = maxResults;
			}
			
			public void setPageToken(String pageToken) {
				this.pageToken = pageToken;
			}
			
			public void setUserId(String userid) {
				this.userid = userid;
			}
		}
		
		public List list(String userid) {
			List list = new List();
			list.setUserId(userid);
			return list;
		}
		
		public List list(String userid, String collection) {
			List list = list(userid);
			list.setCollection(collection);
			return list;
		}
		
		public List list(String userid, String collection, int maxResults) {
			List list = list(userid, collection);
			list.setMaxResults(maxResults);
			return list;
		}
		
		public List list(String userid, String collection, int maxresults, String pageToken) {
			List list = list(userid, collection, maxresults);
			list.setPageToken(pageToken);
			return list;
		}
		
		public List list(String userid, String collection, String pageToken) {
			List list = list(userid, collection);
			list.setPageToken(pageToken);
			return list;
		}
	}
	
	public class Comments {
		
	}
	
	public class People {
		
	}
	
	public Plus() {
		// TODO Auto-generated constructor stub
	}
	
	public Activities activities() {
		return new Activities();
	}
	
	private Google getGoogleAccount() {
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Google) { return (Google) account; }
		}
		return null;
	}
}
