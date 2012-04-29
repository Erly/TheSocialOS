package net.thesocialos.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.thesocialos.server.utils.ChannelServer;
import net.thesocialos.shared.Chat;
import net.thesocialos.shared.LineChat;
import net.thesocialos.shared.messages.MessageChat;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class ChatServiceImpl extends RemoteServiceServlet implements net.thesocialos.client.service.ChatService {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public List<Chat> examplePush(String text) {
		/*
		 * HttpSession session = perThreadRequest.get().getSession(); Objectify ofy = ObjectifyService.begin();
		 * List<Chat> list; String uid = (String) session.getAttribute("uid"); PersistenceManager pm =
		 * PMF.get().getPersistenceManager(); // User user = pm.getObjectById(User.class,uid); ofy.put(new
		 * Chat(text,user.getEmail())); LineChat lineChat; if ((lineChat =
		 * ofy.get(LineChat.class,user.getKey()))==null){ lineChat = new
		 * LineChat(user.getKey(),user.getEmail(),Long.parseLong("0")); ofy.put(lineChat); } Query<Chat> chatQ; if
		 * ((chatQ = ofy.query(Chat.class).filter("date >=",lineChat.getDate()))!=null){ list = chatQ.list();
		 * lineChat.setDate(list.get(list.size()-1).getDate()); ofy.put(lineChat); }else{ return null; } //if ((!=
		 * null)) // TODO Auto-generated method stub // User user = new User("demo@thesocialos.net", null, null, null);
		 * //user.setChannelID("demo@thesocialos.net"); MessageChat mesage = new MessageChat(); // mesage.setMessage(new
		 * MessageChatTextMessage()); ChannelServer.pushMessage(user, mesage); //ChannelService channelService =
		 * ChannelServiceFactory.getChannelService(); //channelService.sendMessage(new
		 * ChannelMessage("demo@thesocialos.net", mesage.toString())); //ChannelServer.pushMessage(user,mesage); return
		 * list; //Channel channelService; //channelService.sendMessage(new ChannelMessage(channelKey,
		 * getMessageString())); // ChannelServer.pushMessage(user,new MessageChatAvailableMessage())
		 */
		return null;
	}
	
	@Override
	public List<Chat> getText() {
		
		/*
		 * Query<Chat> chatQ; LineChat lineChat; HttpSession session = perThreadRequest.get().getSession(); Objectify
		 * ofy = ObjectifyService.begin(); List<Chat> list; String uid = (String) session.getAttribute("uid");
		 * PersistenceManager pm = PMF.get().getPersistenceManager(); User user = pm.getObjectById(User.class,uid);
		 * System.out.println(ofy.get(Chat.class).size()); try { lineChat = ofy.get(LineChat.class,user.getEmail()); }
		 * catch (Exception e) { return null; } if ((chatQ =
		 * ofy.query(Chat.class).filter("date >",lineChat.getDate()))!=null){ list = chatQ.list(); if (!list.isEmpty());
		 * lineChat.setDate(list.get(list.size()-1).getDate()); ofy.put(lineChat); return list; }else{ return null; } }
		 * @Override public Boolean sendText(String text) { String uid = (String)
		 * perThreadRequest.get().getSession().getAttribute("uid"); //ObjectifyOpts opts = new
		 * ObjectifyOpts().setSessionCache(true); PersistenceManager pm = PMF.get().getPersistenceManager(); User user =
		 * pm.getObjectById(User.class,uid); Objectify ofy = ObjectifyService.begin(); Chat chat = new
		 * Chat(text,user.getEmail()); ofy.put(chat); sendEvent(ofy); return true;
		 */
		return null;
	}
	
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
	public void sendText(String contactEmail, String message) {
		// TODO Auto-generated method stub
		
	}
	
}
