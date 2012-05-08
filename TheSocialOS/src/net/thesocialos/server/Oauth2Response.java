package net.thesocialos.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.thesocialos.server.json.JSONException;
import net.thesocialos.server.json.JSONObject;
import net.thesocialos.shared.model.Facebook;
import net.thesocialos.shared.model.Google;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.User;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class Oauth2Response extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int GOOGLE = 0;
	private static final int FACEBOOK = 1;
	
	public Oauth2Response() {
		// TODO Auto-generated constructor stub
	}
	
	private String getUsername(int type, String authToken) {
		// TODO Auto-generated method stub
		String urlString = "", params = "", jsonParameter = "", username = "";
		switch (type) {
		case GOOGLE:
			urlString = "https://www.googleapis.com/oauth2/v2/userinfo";
			params = "access_token=" + authToken;
			jsonParameter = "email";
			break;
		case FACEBOOK:
			urlString = "https://graph.facebook.com/me";
			params = "access_token=" + authToken;
			jsonParameter = "username";
			break;
		}
		try {
			URL url = new URL(urlString + "?" + params);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			// get the results
			conn.connect();
			int responseCode = conn.getResponseCode(); // 200, 404, etc
			String responseMsg = conn.getResponseMessage(); // OK, Forbidden, etc
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer results = new StringBuffer();
			String oneline;
			while ((oneline = br.readLine()) != null)
				results.append(oneline);
			br.close();
			JSONObject js = new JSONObject(results.toString());
			username = js.getString(jsonParameter);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return username;
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		String authToken = request.getParameter("authToken");
		String refreshToken = request.getParameter("refreshToken");
		String serviceName = request.getParameter("serviceName");
		// String uid = request.getParameter("uid");
		Objectify ofy = ObjectifyService.begin();
		Session session = UserHelper.getSesssionHttpSession(request.getSession());
		User user = UserHelper.getUserWithSession(session, ofy);
		if ("google".equalsIgnoreCase(serviceName)) {
			int expires_in = Integer.parseInt(request.getParameter("expires_in"));
			Google googleAccount = new Google();
			// We use expires_in - 10 to compensate the delay
			googleAccount.setExpireDate(new Date(System.currentTimeMillis() + (expires_in - 10) * 1000));
			googleAccount.setAuthToken(authToken);
			googleAccount.setRefreshToken(refreshToken);
			googleAccount.setUsername(getUsername(GOOGLE, authToken));
			user.addAccount(ofy.put(googleAccount));
		} else if ("facebook".equalsIgnoreCase(serviceName)) {
			Facebook facebookAccount = new Facebook();
			facebookAccount.setExpireDate(new Date(System.currentTimeMillis() + 60 * 24 * 60 * 60 * 1000));
			facebookAccount.setAuthToken(authToken);
			// facebookAccount.setRefreshToken(refreshToken);
			facebookAccount.setUsername(getUsername(FACEBOOK, authToken));
			user.addAccount(ofy.put(facebookAccount));
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
			// TheSocialOS.get().getEventBus().fireEvent(new AccountAddedEvent());
			// writer.write("<script>window.opener.location.hash='account-added'</script>");
			// writer.write("<Button onClick=\"javascript:window.opener.location.hash='account-added';window.close();\">Close window</Button>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
