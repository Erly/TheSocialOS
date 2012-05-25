package net.thesocialos.server;

import javax.servlet.http.HttpServlet;

import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Admin;
import net.thesocialos.shared.model.Columns;
import net.thesocialos.shared.model.Conversation;
import net.thesocialos.shared.model.Facebook;
import net.thesocialos.shared.model.FlickR;
import net.thesocialos.shared.model.Google;
import net.thesocialos.shared.model.Group;
import net.thesocialos.shared.model.ImageUpload;
import net.thesocialos.shared.model.Lines;
import net.thesocialos.shared.model.Oauth1;
import net.thesocialos.shared.model.Oauth2;
import net.thesocialos.shared.model.Session;
import net.thesocialos.shared.model.Twitter;
import net.thesocialos.shared.model.User;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

@SuppressWarnings("serial")
public class LoadOnStart extends HttpServlet {
	
	@Override
	public void init() {
		
		// User
		ObjectifyService.register(Session.class);
		ObjectifyService.register(Account.class);
		ObjectifyService.register(Admin.class);
		ObjectifyService.register(Group.class);
		ObjectifyService.register(Twitter.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(Columns.class);
		
		// Chat
		ObjectifyService.register(Lines.class);
		ObjectifyService.register(Conversation.class);
		
		// SocialOS
		ObjectifyService.register(Oauth1.class);
		ObjectifyService.register(Oauth2.class);
		ObjectifyService.register(Facebook.class);
		ObjectifyService.register(FlickR.class);
		ObjectifyService.register(Google.class);
		
		ObjectifyService.register(ImageUpload.class);
		
		setAllUsertoOffline();
		
	}
	
	private void setAllUsertoOffline() {
		Objectify ofy = ObjectifyService.begin();
		Query<User> queryusers = ofy.query(User.class).filter("isConnected =", true);
		for (User user : queryusers) {
			user.isConnected = false;
			user.chatState = STATETYPE.OFFLINE;
			ofy.put(user);
		}
		
	}
}
