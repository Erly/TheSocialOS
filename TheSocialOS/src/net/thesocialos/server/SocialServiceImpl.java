package net.thesocialos.server;

import net.thesocialos.client.service.SocialService;
import net.thesocialos.shared.model.Twitter;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;

public class SocialServiceImpl extends XsrfProtectedServiceServlet implements SocialService {
	
	OAuthService service = null;
	
	@Override
	public String tweet(Twitter twitterAccount, String message) {
		service = new ServiceBuilder().provider(TwitterApi.class).apiKey(Twitter.CONSUMER_KEY)
				.apiSecret(Twitter.CONSUMER_SECRET).build();
		OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1/statuses/update.json");
		request.addQuerystringParameter("status", message);
		service.signRequest(new Token(twitterAccount.getToken(), twitterAccount.getTokenSecret()), request);
		Response response = request.send();
		return response.getBody();
	}
	
}
