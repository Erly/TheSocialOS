package net.thesocialos.server;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserDisconnect;
import net.thesocialos.shared.model.User;

import com.google.appengine.api.channel.ChannelPresence;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

@SuppressWarnings("serial")
public class ChannelDisconnect extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		ChannelPresence presence = channelService.parsePresence(req);
		Objectify ofy = ObjectifyService.begin();
		User user;
		try {
			// System.out.println(presence.clientId() + " disconnect");
			user = UserHelper.getUserWithEmail(ChannelApiHelper.getUserForAppkey(presence.clientId()), ofy);
		} catch (Exception e) {
			return;
		}
		user.isConnected = false;
		user.chatState = STATETYPE.OFFLINE;
		ofy.put(user);
		sendConnectionToContacts(ofy.get(user.getContacts()).values().iterator(), user.getEmail());
	}
	
	private void sendConnectionToContacts(Iterator<User> contacts, String email) {
		while (contacts.hasNext()) {
			User user = contacts.next();
			if (user.isConnected)
				ChannelApiHelper.sendMessage(user.getEmail(),
						ChannelApiHelper.encodeMessage(new ChApiChatUserDisconnect(email)));
			ChannelApiHelper.sendMessage(user.getEmail(),
					ChannelApiHelper.encodeMessage(new ChApiChatUserChngState(STATETYPE.OFFLINE, null, email)));
		}
	}
}
