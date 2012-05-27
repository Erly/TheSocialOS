package net.thesocialos.client.service;

import net.thesocialos.shared.model.Facebook;
import net.thesocialos.shared.model.Twitter;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.XsrfProtect;

@RemoteServiceRelativePath("socialService")
@XsrfProtect
public interface SocialService extends RemoteService {
	String tweet(Twitter twitterAccount, String message);
	
	String postOnFacebook(Facebook facebookAccount, String message);
}
