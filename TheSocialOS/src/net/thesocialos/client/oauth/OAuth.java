package net.thesocialos.client.oauth;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

public class OAuth {
	
	public native static String signRequest(String key, String secret, String url) /*-{
		var accessor = {consumerSecret: secret};
		var message = {
			method: "GET",
			action: url,
			parameters: [
				['oauth_signature_method', 'HMAC-SHA1'],
				['oauth_consumer_key', key],
				['oauth_version', '1.0a'],
				['xoauth_requestor_id', guid],
				['format', 'json']
			]
		};
		
		OAuth.setTimestampAndNonce(message);
		OAuth.setParameter(message, "oauth_timestamp", OAuth.timestamp());
		OAuth.SignatureMethod.sign(message, accessor);
		var finalUrl = OAuth.addToURL(message.action, message.parameters);
		return finalUrl;
	}-*/;
	
	// return $wnd.makeSignedRequest(key, secret, tokenSecret, url);
	
	public native static String signRequest(String key, String secret, String accessToken, String accessTokenSecret, String url) /*-{
		return $wnd.makeSignedRequest(key, secret, accessToken, accessTokenSecret, url);
	}-*/;
	
	protected void send(String Url, RequestCallback cb) {
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
