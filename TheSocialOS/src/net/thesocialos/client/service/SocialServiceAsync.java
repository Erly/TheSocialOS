package net.thesocialos.client.service;

import net.thesocialos.shared.model.Facebook;
import net.thesocialos.shared.model.Twitter;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SocialServiceAsync extends ServiceAsync {
	void tweet(Twitter twitterAccount, String message, AsyncCallback<String> callback);
	
	void postOnFacebook(Facebook facebookAccount, String message, AsyncCallback<String> callback);
}
