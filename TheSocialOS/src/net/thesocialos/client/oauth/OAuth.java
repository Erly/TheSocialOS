package net.thesocialos.client.oauth;

import com.google.gwt.core.client.JavaScriptObject;

public class OAuth {
	
	/* Functions needed to avoid Same Origin Policy (In twitter calls for example) */
	
	public interface JSONHandler {
		public void handleJSON(JavaScriptObject obj);
	}
	
	public static void dispatchJSON(JavaScriptObject jsonObj, JSONHandler handler) {
		handler.handleJSON(jsonObj);
	}
	
	public native static void makeJSONRequest(int i, String url, JSONHandler handler) /*-{
																						if ($wnd.jsonCallback == null) {
																						var jsonCallbacks = {};
																						} else {
																						jsonCallbacks = $wnd.jsonCallback;
																						}

																						jsonCallbacks[i] = function(jsonObj) {
																						@net.thesocialos.client.oauth.OAuth::dispatchJSON(Lcom/google/gwt/core/client/JavaScriptObject;Lnet/thesocialos/client/oauth/OAuth$JSONHandler;)(jsonObj, handler);
																						}

																						$wnd.jsonCallback = jsonCallbacks;

																						// create SCRIPT tag, and set SRC attribute equal to JSON feed URL + callback function name
																						var script = $wnd.document.createElement("script");
																						script.setAttribute("src", url);
																						script.setAttribute("type", "text/javascript");

																						$wnd.document.getElementsByTagName("head")[0].appendChild(script);

																						}-*/;
	
	/* SIGN THE REQUEST METHODS (for all OAuth1 requests) */
	
	@Deprecated
	public native static String signRequest(String key, String secret, String url) /*-{
																					var accessor = {
																					consumerSecret : secret
																					};
																					var message = {
																					method : "GET",
																					action : url,
																					parameters : [ [ 'oauth_signature_method', 'HMAC-SHA1' ],
																					[ 'oauth_consumer_key', key ], [ 'oauth_version', '1.0a' ],
																					[ 'xoauth_requestor_id', guid ], [ 'format', 'json' ] ]
																					};

																					OAuth.setTimestampAndNonce(message);
																					OAuth.setParameter(message, "oauth_timestamp", OAuth.timestamp());
																					OAuth.SignatureMethod.sign(message, accessor);
																					var finalUrl = OAuth.addToURL(message.action, message.parameters);
																					return finalUrl;
																					}-*/;
	
	// return $wnd.makeSignedRequest(key, secret, tokenSecret, url);
	
	public native static String signRequest(String key, String secret, String accessToken, String accessTokenSecret,
			String url) /*-{
						return $wnd.makeSignedRequest(key, secret, accessToken,
						accessTokenSecret, url);
						}-*/;
}
