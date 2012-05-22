package net.thesocialos.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.thesocialos.server.utils.ChannelServer;
import net.thesocialos.shared.LineChat;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatRecvMessage;
import net.thesocialos.shared.messages.MessageChat;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class ChatServiceImpl extends RemoteServiceServlet implements net.thesocialos.client.service.ChatService {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private void sendEvent(Objectify ofy) {
		MessageChat message = new MessageChat();
		
		Iterable<Key<LineChat>> allKeys = ofy.query(LineChat.class).fetchKeys();
		Map<Key<LineChat>, LineChat> qChat = ofy.get(allKeys);
		List<String> usersEmails = new ArrayList<String>();
		for (LineChat chat : qChat.values())
			usersEmails.add(chat.getUser());
		ChannelServer.PushallUsers(usersEmails, message);
		
	}
	
	@Override
	public Long sendText(Key<User> contactUser, String message) {
		Objectify ofy = ObjectifyService.begin();
		
		if (UserHelper.isYourFriend(perThreadRequest.get().getSession(), ofy, contactUser)) {
			Long time = Calendar.getInstance().getTimeInMillis();
			ChannelApiHelper.sendMessage(
					contactUser.getName(),
					ChannelApiHelper.encodeMessage(new ChApiChatRecvMessage(time, Key.create(User.class,
							UserHelper.getUserHttpSession(perThreadRequest.get().getSession())), message)));
			return time;
		}
		return null;
		
	}
}
