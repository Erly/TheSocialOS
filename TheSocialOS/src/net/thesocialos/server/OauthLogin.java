package net.thesocialos.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

public class OauthLogin extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1286811952202620598L;
	
	public OauthLogin() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		
		String serviceType = request.getParameter("serviceType");
		OAuthService service = null;
		
		if (serviceType.equalsIgnoreCase("twitter")) {
			service = new ServiceBuilder().provider(TwitterApi.class).apiKey("KQ6iX3bbpfDDpKGYtx2X8A")
					.apiSecret("VKwB7Ro0oCvePk1YnqlXLK9b1hdliEDF0qClr9U8w")
					.callback("http://www.thesocialos.net/twittercallback").build();
		} else if (serviceType.equalsIgnoreCase("flickr")) {
			service = new ServiceBuilder().provider(FlickrApi.class).apiKey("cc01bd2671d139d49a97d10179ff6341")
					.apiSecret("3d016edebfd20a11").callback("http://www.thesocialos.net/flickrcallback").build();
		} else {
			PrintWriter writer;
			try {
				writer = response.getWriter();
				writer.write("There was an error with the request. Please contact support@thesocialos.net "
						+ "to inform of this bug so it can be fixed as soon as possible");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		Token requestToken = service.getRequestToken();
		
		String authUrl = service.getAuthorizationUrl(requestToken);
		
		request.getSession().setAttribute("OAuthService", service);
		request.getSession().setAttribute("OAuthRequestToken", requestToken);
		
		try {
			response.sendRedirect(authUrl + "&perms=delete");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
