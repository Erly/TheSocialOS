package net.thesocialos.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.thesocialos.server.json.JSONException;
import net.thesocialos.server.json.JSONObject;
import net.thesocialos.shared.model.FlickR;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.Twitter;
import net.thesocialos.shared.model.User;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class OauthCallback extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7384724356709314551L;
	private static final int TWITTER = 0;
	private static final int FLICKR = 1;
	
	OAuthService service = null;
	Twitter twitterAccount = null;
	FlickR flickrAccount = null;
	
	public OauthCallback() {
		// TODO Auto-generated constructor stub
	}
	
	private String getFlickrUsername(Token accessToken) {
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.flickr.com/services/rest");
		request.addQuerystringParameter("method", "flickr.test.login");
		request.addQuerystringParameter("format", "json");
		request.addQuerystringParameter("nojsoncallback", "1");
		service.signRequest(accessToken, request);
		Response resp = request.send();
		String body = resp.getBody();
		try {
			JSONObject js = new JSONObject(body);
			JSONObject userjs = js.getJSONObject("user");
			return userjs.getJSONObject("username").getString("_content");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private String getUsername(int type, Token accessToken) {
		OAuthRequest request = null;
		String params = null;
		switch (type) {
		case TWITTER:
			request = new OAuthRequest(Verb.GET, "http://api.twitter.com/1/account/verify_credentials.json");
			params = "screen_name";
			break;
		default:
			return "";
		}
		service.signRequest(accessToken, request);
		Response resp = request.send();
		String body = resp.getBody();
		try {
			JSONObject js = new JSONObject(body);
			return js.getString(params);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		String oauthToken = request.getParameter("oauth_token");
		String oauthVerifier = request.getParameter("oauth_verifier");
		
		service = (OAuthService) request.getSession().getAttribute("OAuthService");
		
		Token requestToken = (Token) request.getSession().getAttribute("OAuthRequestToken");
		Verifier verifier = new Verifier(oauthVerifier);
		Token accessToken = service.getAccessToken(requestToken, verifier);
		
		Objectify ofy = ObjectifyService.begin();
		
		Session session = UserHelper.getSesssionHttpSession(request.getSession());
		User user = UserHelper.getUserSession(request.getSession(), ofy);
		if (request.getServletPath().contains("twitter")) {
			twitterAccount = new Twitter(accessToken.getToken(), accessToken.getSecret());
			twitterAccount.setUsername(getUsername(TWITTER, accessToken));
			user.addAccount(ofy.put(twitterAccount));
		} else if (request.getServletPath().contains("flickr")) {
			flickrAccount = new FlickR(accessToken.getToken(), accessToken.getSecret());
			flickrAccount.setUsername(getFlickrUsername(accessToken));
			user.addAccount(ofy.put(flickrAccount));
		} else {
			try {
				PrintWriter writer = response.getWriter();
				writer.write("There wan an error fulfilling the request. Please contact with support@thesocialos.net so it can be fixed");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		ofy.put(user);
		
		try {
			PrintWriter writer = response.getWriter();
			response.setContentType("text/html");
			writer.println("<html>");
			writer.println("<head>");
			writer.println("<TITLE>");
			writer.println("Account Added");
			writer.println("</TITLE>");
			writer.println("<SCRIPT LANGUAGE=javascript>");
			writer.println("<!--");
			writer.println("function window_onload() { window.opener.location.hash='account-added'; window.close(); } ");
			writer.println("//-->");
			writer.println("</SCRIPT>");
			writer.println("</head>");
			writer.println("<body onload=window_onload()>");
			writer.println("</body>");
			writer.println("</html>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
