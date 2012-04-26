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

public class Oauth2CallbackFB extends HttpServlet {
	
	public Oauth2CallbackFB() {
		// TODO Auto-generated constructor stub
	}
	
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			String authCode = (String) request.getParameter("code");
			String urlString = "";
			String params = "";
			String serviceName = "";
			
			urlString = "https://graph.facebook.com/oauth/access_token";
			params = "canvas=1&fbconnect=0&type=user_agent&client_id=124427357682835&" + "code=" + authCode + "&"
					+ "client_secret=fc7f73e8c2bc2ca3bc2f143b2d58582a&"
					+ "redirect_uri=http://www.thesocialos.net/oauth2callbackFB";
			serviceName = "facebook";
			
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
			
			String authToken = results.substring(results.indexOf("=") + 1);
			request.getRequestDispatcher("oauth2response?authToken=" + authToken + "&serviceName=" + serviceName)
					.forward(request, response);
			
			/*
			 * try { //writer.println("Empieza parseo JSON"); JSONObject js = new JSONObject(results.toString());
			 * //JSONArray js2 = js.getJSONArray("values"); String authToken = null, refreshToken = null; authToken =
			 * js.getString("access_token"); refreshToken = js.getString("refresh_token");
			 * //writer.println("Final parseo JSON"); request.getRequestDispatcher("oauth2response?authToken=" +
			 * authToken +"&refreshToken=" + refreshToken + "&serviceName=" + serviceName).forward(request, response); }
			 * catch (JSONException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
			 * (ServletException e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
