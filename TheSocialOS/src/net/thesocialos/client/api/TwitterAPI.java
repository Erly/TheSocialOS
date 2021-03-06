package net.thesocialos.client.api;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.oauth.OAuth;
import net.thesocialos.client.oauth.OAuth.JSONHandler;
import net.thesocialos.client.presenter.SocialDeckPresenter.Display;
import net.thesocialos.client.service.SocialService;
import net.thesocialos.client.service.SocialServiceAsync;
import net.thesocialos.client.view.deck.DeckColumn;
import net.thesocialos.client.view.deck.TimelinePost;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Columns;
import net.thesocialos.shared.model.Twitter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.googlecode.objectify.Key;

public class TwitterAPI {
	
	private static final SocialServiceAsync socialService = GWT.create(SocialService.class);
	
	public class Tweet {
		private String id;
		private String user_id;
		private String user_name;
		private String screen_name;
		private Date created_at;
		private String profile_image_url;
		private boolean user_verified;
		private String text;
		private String source;
		
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
		 * @return the profile_image_url
		 */
		public String getProfile_image_url() {
			return profile_image_url;
		}
		
		/**
		 * @return the screen_name
		 */
		public String getScreen_name() {
			return screen_name;
		}
		
		/**
		 * @return the source
		 */
		public String getSource() {
			return source;
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
		
		/**
		 * @return the user_verified
		 */
		public boolean isUser_verified() {
			return user_verified;
		}
	}
	
	public static Timer timer = null;
	
	private Set<DeckColumn> deckColumns = new HashSet<DeckColumn>();
	
	private int i = 0;
	
	public TwitterAPI() {
		// TODO Auto-generated constructor stub
	}
	
	private String getHomeTimelineSignedUrl(Twitter twitterAccount, String lastId) {
		String url = "https://api.twitter.com/1/statuses/home_timeline.json";
		
		if (null != lastId && !lastId.isEmpty()) url += "?since_id=" + lastId;
		return OAuth.signRequest(Twitter.CONSUMER_KEY, Twitter.CONSUMER_SECRET, twitterAccount.getToken(),
				twitterAccount.getTokenSecret(), url);
	}
	
	private String getMentionsTimelineSignedUrl(Twitter twitterAccount, String lastId) {
		String url = "https://api.twitter.com/1/statuses/mentions.json";
		
		if (null != lastId && !lastId.isEmpty()) url += "?since_id=" + lastId;
		return OAuth.signRequest(Twitter.CONSUMER_KEY, Twitter.CONSUMER_SECRET, twitterAccount.getToken(),
				twitterAccount.getTokenSecret(), url);
	}
	
	private String getSearchTimelineSignedUrl(Twitter twitterAccount, String query, String lastId) {
		if (null == query || query.isEmpty()) return "";
		if (query.contains("#")) query = query.replace("#", "%23");
		String url = "https://search.twitter.com/search.json?q=" + query;
		if (null != lastId && !lastId.isEmpty()) url += "&since_id=" + lastId;
		return OAuth.signRequest(Twitter.CONSUMER_KEY, Twitter.CONSUMER_SECRET, twitterAccount.getToken(),
				twitterAccount.getTokenSecret(), url);
	}
	
	private Twitter getTwitterAccount() {
		Map<Key<Account>, Account> accounts = CacheLayer.UserCalls.getAccounts();
		Iterator<Account> it = accounts.values().iterator();
		while (it.hasNext()) {
			Account account = it.next();
			if (account instanceof Twitter) return (Twitter) account;
		}
		return null;
	}
	
	private String getUserTimelineSignedUrl(Twitter twitterAccount, String screen_name, String lastId) {
		if (null == screen_name || screen_name.isEmpty()) screen_name = twitterAccount.getUsername();
		String url = "https://api.twitter.com/1/statuses/user_timeline.json?screen_name=" + screen_name;
		if (null != lastId && !lastId.isEmpty()) url += "&since_id=" + lastId;
		return OAuth.signRequest(Twitter.CONSUMER_KEY, Twitter.CONSUMER_SECRET, twitterAccount.getToken(),
				twitterAccount.getTokenSecret(), url);
	}
	
	public void loadColumns(final Display display, Set<Columns> columnsSet) {
		final Twitter twitterAccount = getTwitterAccount();
		if (null == twitterAccount) return;
		
		if (null == deckColumns || deckColumns.isEmpty()) for (Columns c : columnsSet) {
			DeckColumn col = new DeckColumn();
			col.setColumns(c);
			deckColumns.add(col);
			display.getAllPostColumnsPanel().add(col);
			col.getParent().setWidth("300px");
		}
		loadColumns(twitterAccount);
		
		timer = new Timer() {
			
			@Override
			public void run() {
				loadColumns(twitterAccount, display);
			}
		};
		timer.scheduleRepeating(50000);
	}
	
	public void loadColumns(DeckColumn column) {
		final Twitter twitterAccount = getTwitterAccount();
		if (null == twitterAccount) return;
		deckColumns.add(column);
		loadColumns(twitterAccount);
	}
	
	private void loadColumns(Twitter twitterAccount, final Display display) {
		Map<Key<Columns>, Columns> columns = CacheLayer.UserCalls.getColumns();
		Set<Columns> columnsSet = new HashSet<Columns>(columns.values());
		if (columnsSet.size() != deckColumns.size()) {
			deckColumns.clear();
			display.getAllPostColumnsPanel().clear();
			for (Columns c : columnsSet) {
				DeckColumn col = new DeckColumn();
				col.setColumns(c);
				deckColumns.add(col);
				display.getAllPostColumnsPanel().add(col);
				col.getParent().setWidth("300px");
			}
		}
		for (DeckColumn col : deckColumns) {
			Columns c = col.getColumns();
			String url = "";
			if (c.getType() == Columns.TYPE.TIMELINE) {
				if (c.getValue().equals(Columns.HOME)) {
					col.setTitle("Timeline");
					new FacebookAPI().loadWall(col);
					url = getHomeTimelineSignedUrl(twitterAccount, c.getLastTweetId());
				} else if (c.getValue().equals(Columns.MENTIONS)) {
					col.setTitle("Mentions");
					url = getMentionsTimelineSignedUrl(twitterAccount, c.getLastTweetId());
				} else if (c.getValue().equals(Columns.USER)) {
					col.setTitle("Me");
					url = getUserTimelineSignedUrl(twitterAccount, twitterAccount.getUsername(), c.getLastTweetId());
				} else {
					col.setTitle(c.getValue());
					url = getUserTimelineSignedUrl(twitterAccount, c.getValue(), c.getLastTweetId());
				}
			} else if (c.getType() == Columns.TYPE.SEARCH) {
				col.setTitle(c.getValue());
				url = getSearchTimelineSignedUrl(twitterAccount, c.getValue(), c.getLastTweetId());
			} else if (c.getType() == Columns.TYPE.LIST) col.setTitle(c.getValue());
			// new TwitterAPI().loadUserTimelineInPanel(col);
			loadTweetsInPanel(url, col);
			// display.getAllPostColumnsPanel().add(col);
		}
	}
	
	private void loadColumns(Twitter twitterAccount) {
		for (DeckColumn col : deckColumns) {
			Columns c = col.getColumns();
			String url = "";
			if (c.getType() == Columns.TYPE.TIMELINE) {
				if (c.getValue().equals(Columns.HOME)) {
					col.setTitle("Timeline");
					new FacebookAPI().loadWall(col);
					url = getHomeTimelineSignedUrl(twitterAccount, c.getLastTweetId());
				} else if (c.getValue().equals(Columns.MENTIONS)) {
					col.setTitle("Mentions");
					url = getMentionsTimelineSignedUrl(twitterAccount, c.getLastTweetId());
				} else if (c.getValue().equals(Columns.USER)) {
					col.setTitle("Me");
					url = getUserTimelineSignedUrl(twitterAccount, twitterAccount.getUsername(), c.getLastTweetId());
				} else {
					col.setTitle(c.getValue());
					url = getUserTimelineSignedUrl(twitterAccount, c.getValue(), c.getLastTweetId());
				}
			} else if (c.getType() == Columns.TYPE.SEARCH) {
				col.setTitle(c.getValue());
				url = getSearchTimelineSignedUrl(twitterAccount, c.getValue(), c.getLastTweetId());
			} else if (c.getType() == Columns.TYPE.LIST) col.setTitle(c.getValue());
			// new TwitterAPI().loadUserTimelineInPanel(col);
			loadTweetsInPanel(url, col);
			// display.getAllPostColumnsPanel().add(col);
		}
	}
	
	public synchronized void loadHomeTimelineInPanel(final HasWidgets panel) {
		int j = i++;
		Twitter twitterAccount = getTwitterAccount();
		if (null == twitterAccount) return;
		
		String url = "https://api.twitter.com/1/statuses/home_timeline.json";
		
		url = OAuth.signRequest(Twitter.CONSUMER_KEY, Twitter.CONSUMER_SECRET, twitterAccount.getToken(),
				twitterAccount.getTokenSecret(), url);
		loadTweetsInPanel(url, panel, j);
	}
	
	private void loadTweetsInPanel(String url, final DeckColumn panel) {
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		jsonp.requestObject(url, new AsyncCallback<JavaScriptObject>() {
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
			}
			
			@Override
			public void onSuccess(JavaScriptObject result) {
				JSONArray array;
				Columns c = panel.getColumns();
				if (c.getType() == Columns.TYPE.SEARCH) {
					JSONObject json = new JSONObject(result);
					array = json.get("results").isArray();
				} else
					array = new JSONArray(result);
				Set<Tweet> tweets = new HashSet<Tweet>();
				for (int i = 0; i < array.size(); i++) {
					Tweet tweet = new Tweet();
					tweet.id = array.get(i).isObject().get("id").isNumber().toString();
					if (c.getType() == Columns.TYPE.SEARCH) {
						tweet.user_id = array.get(i).isObject().get("from_user_id").isNumber().toString();
						tweet.user_name = array.get(i).isObject().get("from_user_name").isString().stringValue();
						tweet.screen_name = array.get(i).isObject().get("from_user").isString().stringValue();
						tweet.profile_image_url = array.get(i).isObject().get("profile_image_url").isString()
								.stringValue();
					} else {
						JSONObject user = array.get(i).isObject().get("user").isObject();
						tweet.user_id = user.get("id").isNumber().toString();
						tweet.user_name = user.get("name").isString().stringValue();
						tweet.screen_name = user.get("screen_name").isString().stringValue();
						tweet.profile_image_url = user.get("profile_image_url").isString().stringValue();
						tweet.user_verified = user.get("verified").isBoolean().booleanValue();
					}
					
					tweet.created_at = new Date(array.get(i).isObject().get("created_at").isString().stringValue());
					
					tweet.text = array.get(i).isObject().get("text").isString().stringValue();
					tweet.source = array.get(i).isObject().get("source").isString().stringValue();
					// panel.addTweet(tweet);
					tweets.add(tweet);
				}
				Set<Tweet> oldTweets = panel.getTweets();
				if (null != oldTweets && !oldTweets.isEmpty()) tweets.addAll(tweets);
				else
					panel.setTweets(tweets);
				// panel.clearTweets();
				panel.loadAll();
			}
		});
		/*
		 * OAuth.makeJSONRequest(j, url, new JSONHandler() {
		 * @Override public void handleJSON(JavaScriptObject obj) { } });
		 */
	}
	
	private synchronized void loadTweetsInPanel(String url, final HasWidgets panel, int j) {
		OAuth.makeJSONRequest(j, url, new JSONHandler() {
			
			@Override
			public void handleJSON(JavaScriptObject obj) {
				JSONArray array = new JSONArray(obj);
				for (int i = 0; i < array.size(); i++) {
					Tweet tweet = new Tweet();
					tweet.id = array.get(i).isObject().get("id").isNumber().toString();
					JSONObject user = array.get(i).isObject().get("user").isObject();
					tweet.user_id = user.get("id").isNumber().toString();
					tweet.user_name = user.get("name").isString().stringValue();
					tweet.screen_name = user.get("screen_name").isString().stringValue();
					
					tweet.created_at = new Date(array.get(i).isObject().get("created_at").isString().stringValue());
					
					tweet.profile_image_url = user.get("profile_image_url").isString().stringValue();
					tweet.user_verified = user.get("verified").isBoolean().booleanValue();
					tweet.text = array.get(i).isObject().get("text").isString().stringValue();
					tweet.source = array.get(i).isObject().get("source").isString().stringValue();
					panel.add(new TimelinePost(tweet));
				}
			}
		});
	}
	
	public void post(final String message) {
		final Twitter twitterAccount = getTwitterAccount();
		new RPCXSRF<String>(socialService) {
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			
			@Override
			public void onSuccess(String result) {
				// Window.alert(result);
				timer.schedule(1000);
			}
			
			@Override
			protected void XSRFcallService(AsyncCallback<String> cb) {
				socialService.tweet(twitterAccount, message, cb);
			}
		}.retry(3);
	}
	
	/*
	 * int j = i++; if (i == 100) i = 0; String url =
	 * "https://api.twitter.com/1/statuses/update.json?callback=jsonCallback[" + j + "]"; url =
	 * OAuth.signPostRequest(Twitter.CONSUMER_KEY, Twitter.CONSUMER_SECRET, twitterAccount.getToken(),
	 * twitterAccount.getTokenSecret(), url, message); postTweet(url, j); } private void postTweet(String url, int j) {
	 * OAuth.makeJSONRequest(j, url, new JSONHandler() {
	 * @Override public void handleJSON(JavaScriptObject obj) { JSONObject js = new JSONObject(obj);
	 * Window.alert(js.toString()); } }); }
	 */
	
	public synchronized void loadUserTimelineInPanel(final HasWidgets panel) {
		int j = i++;
		
		Twitter twitterAccount = getTwitterAccount();
		if (null == twitterAccount) return;
		
		String url = "https://api.twitter.com/1/statuses/user_timeline.json?screen_name="
				+ twitterAccount.getUsername() + "&callback=jsonCallback[" + j + "]";
		
		url = OAuth.signRequest(Twitter.CONSUMER_KEY, Twitter.CONSUMER_SECRET, twitterAccount.getToken(),
				twitterAccount.getTokenSecret(), url);
		loadTweetsInPanel(url, panel, j);
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
