package net.thesocialos.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.thesocialos.client.service.SocialService;
import net.thesocialos.shared.model.Facebook;
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
	
	@Override
	public String postOnFacebook(Facebook facebookAccount, String message) {
		String urlString = "https://graph.facebook.com/me/feed";
		String params = "access_token=" + facebookAccount.getAuthToken() + "&message=" + message;
		
		URL url;
		try {
			url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			// post the parameters
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(params);
			wr.flush();
			wr.close();
			
			// get the results
			conn.connect();
			int responseCode = conn.getResponseCode(); // 200, 404, etc
			String responseMsg = conn.getResponseMessage(); // OK, Forbidden, etc
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer results = new StringBuffer();
			String oneline;
			while ((oneline = br.readLine()) != null)
				results.append(oneline);
			// writer.println(oneline);
			// System.out.println(oneline);
			br.close();
			
			return results.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
