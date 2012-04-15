package net.thesocialos.client.api;

import java.util.Iterator;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.oauth.OAuth;
import net.thesocialos.client.oauth.OAuth.JSONHandler;
import net.thesocialos.client.view.profile.TimelinePost;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Twitter;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.googlecode.objectify.Key;

public class TwitterAPI {

	public class Tweet {
		private String id;
		private String user_id;
		private String user_name;
		private String screen_name;
		private String profile_image_url;
		private boolean user_verified;
		private String text;
		private String source;
		
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
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
		/**
		 * @return the screen_name
		 */
		public String getScreen_name() {
			return screen_name;
		}
		/**
		 * @return the profile_image_url
		 */
		public String getProfile_image_url() {
			return profile_image_url;
		}
		/**
		 * @return the user_verified
		 */
		public boolean isUser_verified() {
			return user_verified;
		}
		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}
		/**
		 * @return the source
		 */
		public String getSource() {
			return source;
		}
	}
	
	public TwitterAPI() {
		// TODO Auto-generated constructor stub
	}
	
	public void loadHomeTimelineInPanel(final HasWidgets panel) {
		String url = "https://api.twitter.com/1/statuses/home_timeline.json?callback=jsonCallback";
		Twitter twitterAccount = getTwitterAccount();
		if (null == twitterAccount)
			return;
		
		url = OAuth.signRequest(Twitter.CONSUMER_KEY, Twitter.CONSUMER_SECRET, twitterAccount.getToken(), twitterAccount.getTokenSecret(), url);
		OAuth.makeJSONRequest(url, new JSONHandler() {
			
			public void handleJSON(JavaScriptObject obj) {
				JSONArray array = new JSONArray(obj);
				for (int i = 0; i < array.size(); i++) {
					Tweet tweet = new Tweet();
					tweet.id = array.get(i).isObject().get("id").isNumber().toString();
					JSONObject user = array.get(i).isObject().get("user").isObject();
					tweet.user_id = user.get("id").isNumber().toString();
					tweet.user_name = user.get("name").isString().stringValue();
					tweet.screen_name =  user.get("screen_name").isString().stringValue();
					tweet.profile_image_url = user.get("profile_image_url").isString().stringValue();
					tweet.user_verified = user.get("verified").isBoolean().booleanValue();
					tweet.text = array.get(i).isObject().get("text").isString().stringValue();
					tweet.source = array.get(i).isObject().get("source").isString().stringValue();
					panel.add(new TimelinePost(tweet));
				}
			}
		});
		/*send(url, new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				JSONArray array = JSONParser.parseStrict(response.getText()).isArray();
				Window.alert(array.size()+ "");
				for (int i = 0; i < array.size(); i++) {
					Tweet tweet = new Tweet();
					tweet.id = array.get(i).isObject().get("id").isNumber().toString();
					Window.alert(tweet.id);
					JSONObject user = array.get(i).isObject().get("user").isObject();
					tweet.user_id = user.get("id").isNumber().toString();
					Window.alert(tweet.user_id);
					tweet.screen_name =  user.get("screen_name").isString().toString();
					Window.alert(tweet.screen_name);
					tweet.profile_image_url = user.get("profile_image_url").isString().toString();
					Window.alert(tweet.profile_image_url);
					tweet.user_verified = user.get("verified").isBoolean().booleanValue();
					Window.alert(tweet.user_verified+"");
					tweet.text = array.get(i).isObject().get("text").isString().stringValue();
					Window.alert(tweet.text);
					tweet.source = array.get(i).isObject().get("source").isString().stringValue();
					Window.alert(tweet.source);
					panel.add(new TimelinePost(tweet));
				}
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				// TODO Auto-generated method stub
				
			}
		});*/
	}

	private Twitter getTwitterAccount() {
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Twitter) {
				return (Twitter)account;
			}
		}
		return null;
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
