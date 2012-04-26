package net.thesocialos.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import net.thesocialos.server.json.JSONException;
import net.thesocialos.server.json.JSONObject;
import net.thesocialos.shared.model.Google;

public class RefreshTokens {
	
	public static Google refreshGoogle(Google googleAccount) {
		try {
			String urlString = "https://accounts.google.com/o/oauth2/token";
			String params = "client_id=398121744591.apps.googleusercontent.com&"
					+ "client_secret=WByUe_YHFd07JWEzMpWZ6cGf&" + "grant_type=refresh_token&" + "refresh_token="
					+ googleAccount.getRefreshToken();
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
			}
			br.close();
			try {
				JSONObject js = new JSONObject(results.toString());
				String authToken = js.getString("access_token");
				int expires_in = js.getInt("expires_in");
				googleAccount.setAuthToken(authToken);
				// We use expires_in - 10 to compensate the delay
				googleAccount.setExpireDate(new Date(System.currentTimeMillis() + (expires_in - 10) * 1000));
				return googleAccount;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return googleAccount;
	}
}
