package net.thesocialos.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Facebook;
import net.thesocialos.shared.model.Google;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.User;

public class Oauth2Response extends HttpServlet {

	public Oauth2Response() {
		// TODO Auto-generated constructor stub
	}
	
	public void service(HttpServletRequest request, HttpServletResponse response) {
		String authToken = request.getParameter("authToken");
		String refreshToken = request.getParameter("refreshToken");
		String serviceName = request.getParameter("serviceName");
		//String uid = request.getParameter("uid");
		Objectify ofy = ObjectifyService.begin();
		Session session = UserHelper.getSessionfromSession(request.getSession());
		User user = UserHelper.getUserWithSession(session, ofy);
		if ("google".equalsIgnoreCase(serviceName)) {
			Google googleAccount = new Google();
			googleAccount.setAuthToken(authToken);
			googleAccount.setRefreshToken(refreshToken);
			user.addAccount(ofy.put(googleAccount));
		} else if ("facebook".equals(serviceName)) {
			Facebook facebookAccount = new Facebook();
			facebookAccount.setAuthToken(authToken);
			facebookAccount.setRefreshToken(refreshToken);
			user.addAccount(ofy.put(facebookAccount));
		}
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(user);
		try {
			PrintWriter writer = response.getWriter();
			writer.write("<Button onClick='javascript:window.close();'>Close window</Button>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
