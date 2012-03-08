package net.thesocialos.server;

import java.security.acl.Group;

import javax.servlet.http.HttpServlet;

import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Admin;
import net.thesocialos.shared.model.Conversation;
import net.thesocialos.shared.model.Facebook;
import net.thesocialos.shared.model.FlickR;
import net.thesocialos.shared.model.Google;
import net.thesocialos.shared.model.Lines;
import net.thesocialos.shared.model.Oauth1;
import net.thesocialos.shared.model.Oauth2;
import net.thesocialos.shared.model.OutConversation;
import net.thesocialos.shared.model.Twitter;

import com.google.appengine.api.users.User;
import com.google.gwt.dev.ModuleTabPanel.Session;
import com.googlecode.objectify.ObjectifyService;


@SuppressWarnings("serial")
public class LoadOnStart extends HttpServlet {

	public void init(){
		/*
		//User
		ObjectifyService.register(Session.class);
		ObjectifyService.register(Account.class);
		ObjectifyService.register(Admin.class);
		ObjectifyService.register(Group.class);
		ObjectifyService.register(Twitter.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(UserDetails.class);
		//Chat
		ObjectifyService.register(Lines.class);
		ObjectifyService.register(Conversation.class);
		ObjectifyService.register(OutConversation.class);
		//SocialOS
		ObjectifyService.register(Oauth1.class);
		ObjectifyService.register(Oauth2.class);
		ObjectifyService.register(Facebook.class);
		ObjectifyService.register(FlickR.class);
		ObjectifyService.register(Google.class);
		
		
		*/
		
	}
}
