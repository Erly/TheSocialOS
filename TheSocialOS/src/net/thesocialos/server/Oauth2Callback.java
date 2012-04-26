package net.thesocialos.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.thesocialos.server.json.JSONException;
import net.thesocialos.server.json.JSONObject;

public class Oauth2Callback extends HttpServlet {
	
	public Oauth2Callback() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			String authCode = request.getParameter("code");
			String urlString = "https://accounts.google.com/o/oauth2/token";
			String params = "code=" + authCode + "&" + "client_id=398121744591.apps.googleusercontent.com&"
					+ "client_secret=WByUe_YHFd07JWEzMpWZ6cGf&"
					+ "redirect_uri=http://www.thesocialos.net/oauth2callback&" + "grant_type=authorization_code";
			String serviceName = "google";
			
			URL url = new URL(urlString);
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
			while ((oneline = br.readLine()) != null) {
				results.append(oneline);
				// writer.println(oneline);
				// System.out.println(oneline);
			}
			br.close();
			try {
				JSONObject js = new JSONObject(results.toString());
				// JSONArray js2 = js.getJSONArray("values");
				String authToken = null, refreshToken = null;
				authToken = js.getString("access_token");
				refreshToken = js.getString("refresh_token");
				int expires_in = js.getInt("expires_in");
				request.getRequestDispatcher(
						"oauth2response?authToken=" + authToken + "&refreshToken=" + refreshToken + "&expires_in="
								+ expires_in + "&serviceName=" + serviceName).forward(request, response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
