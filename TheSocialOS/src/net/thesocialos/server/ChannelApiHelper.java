package net.thesocialos.server;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.ChannelApiEvents.ChApiEvent;
import net.thesocialos.shared.model.User;

import com.google.appengine.api.channel.ChannelFailureException;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.SerializationPolicy;

public class ChannelApiHelper {
	private static Properties props = System.getProperties();
	// private static final boolean USE_CHANNEL_API = setChannelAPIEnabled();
	private static final String APP_KEY = "SOS-";
	private static final Method dummyMethod = getDummyMethod();
	// final names;
	private final static String sessionN = "session";
	private final static String userN = "user";
	private final static String OBJECITIFY = "objetify";
	
	/**
	 * Create a channel for a user. Returns the channel id that the client must use to connect for receiving push
	 * messages.
	 * 
	 * @param userUniqueId
	 * @return the client channel id
	 */
	public static String createChannel(HttpSession httpsession) throws ChannelFailureException {
		
		String userEmail = UserHelper.getUserHttpSession(httpsession);
		return getChannelService().createChannel(APP_KEY + userEmail);
	}
	
	/**
	 * Send a message to one specific user
	 * 
	 * @param userEmail
	 *            The user to send the message to
	 * @param encodedString
	 *            The message codified
	 * @throws ChannelFailureException
	 */
	public static void sendMessage(String userEmail, String encodedString) throws ChannelFailureException {
		getChannelService().sendMessage(new ChannelMessage(getAppKeyForUser(userEmail), encodedString));
	}
	
	public static String encodeMessage(ChApiEvent channelApiEvent) {
		
		try {
			return RPC.encodeResponseForSuccess(dummyMethod, channelApiEvent, new SerializationPolicy() {
				
				@Override
				public boolean shouldDeserializeFields(Class<?> clazz) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean shouldSerializeFields(Class<?> clazz) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public void validateDeserialize(Class<?> clazz) throws SerializationException {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void validateSerialize(Class<?> clazz) throws SerializationException {
					// TODO Auto-generated method stub
					
				}
				
			});
			// return RPC.encodeResponseForSuccess(dummyMethod, channelApiEvent,
			// MergedSerializationPolicy.createPushSerializationPolicy());
		} catch (SerializationException e) {
			throw new RuntimeException("Unable to encode a message for push.\n" + channelApiEvent, e);
		}
		
	}
	
	private static boolean setChannelAPIEnabled() {
		String skey = props.getProperty("com.metadot.connectr.enable-channelapi");
		if (skey != null) {
			if (skey.equalsIgnoreCase("true")) // logger.info("channel API is enabled");
				return true;
			if (skey.equalsIgnoreCase("false")) return false;
		}
		return false;
	}
	
	private static ChannelService getChannelService() {
		return ChannelServiceFactory.getChannelService();
	}
	
	private static String getAppKeyForUser(String userUniqueId) {
		return APP_KEY + userUniqueId;
	}
	
	/**
	 * Split the String in two parts
	 * 
	 * @param appKeyUser
	 * @return
	 */
	public static String getUserForAppkey(String appKeyUser) {
		String[] stringSplit = appKeyUser.split("-");
		return stringSplit[1];
	}
	
	/**
	 * Send the user state for all contacts if are connected
	 * 
	 * @param contacts
	 * @param state
	 * @param custom
	 * @param email
	 */
	public static void sendStateToContacts(Iterator<User> contacts, STATETYPE state, String custom, String email) {
		while (contacts.hasNext()) {
			User user = contacts.next();
			if (user.isConnected)
				ChannelApiHelper.sendMessage(user.getEmail(),
						ChannelApiHelper.encodeMessage(new ChApiChatUserChngState(state, custom, email)));
		}
	}
	
	public static Method getDummyMethod() {
		try {
			return ChannelApiHelper.class.getDeclaredMethod("dummyMethod");
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Unable to find the dummy RPC method.");
		}
	}
	
	/**
	 * This method exists to make GWT RPC happy.
	 * <p>
	 * {@link RPC#encodeResponseForSuccess(java.lang.reflect.Method, Object)} insists that we pass it a Method that has
	 * a return type equal to the object we're encoding. What we really want to use is
	 * {@link RPC#encodeResponse(Class, Object, boolean, int, com.google.gwt.user.server.rpc.SerializationPolicy)} , but
	 * it is unfortunately private.
	 */
	@SuppressWarnings("unused")
	private ChApiEvent dummyMethod() {
		throw new UnsupportedOperationException("This should never be called.");
	}
}
