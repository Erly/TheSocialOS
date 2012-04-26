package net.thesocialos.server.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import net.thesocialos.shared.messages.Message;
import net.thesocialos.shared.model.User;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;

public class ChannelServer {
	private static Properties props = System.getProperties();
	// private static final boolean USE_CHANNEL_API = setChannelAPIEnabled();
	private static final String APP_KEY = "SOS-";
	private static final Method dummyMethod = getDummyMethod();
	
	/**
	 * Create a channel for a user. Returns the channel id that the client must use to connect for receiving push
	 * messages.
	 * 
	 * @param userUniqueId
	 * @return the client channel id
	 */
	public static String createChannel(String userUniqueId) {
		/*
		 * if(!USE_CHANNEL_API){ // logger.log(Level.WARNING,
		 * "Channel API disabled in appengine-web.xml. Not creating."); return null; }
		 */
		
		String channelId = getChannelService().createChannel(APP_KEY + userUniqueId);
		
		// logger.info("Created new channel: " + channelId);
		return channelId;
	}
	
	private static String encodeMessage(Message msg) {
		
		try {
			return RPC.encodeResponseForSuccess(dummyMethod, msg,
					MergedSerializationPolicy.createPushSerializationPolicy());
		} catch (SerializationException e) {
			throw new RuntimeException("Unable to encode a message for push.\n" + msg, e);
		}
		
	}
	
	private static ChannelService getChannelService() {
		
		return ChannelServiceFactory.getChannelService();
	}
	
	private static Method getDummyMethod() {
		try {
			return ChannelServer.class.getDeclaredMethod("dummyMethod");
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Unable to find the dummy RPC method.");
		}
	}
	
	public static void PushallUsers(List<String> userUniqueIds, Message msg) {
		/*
		 * String encodedMessage = encodeMessage(msg); for (String userUniqueId : userUniqueIds) { String key =
		 * getAppKeyForUser(userUniqueId); //logger.info("Pushing msg to " + key); try {
		 * getChannelService().sendMessage( new ChannelMessage(key, encodedMessage)); } catch (Exception e) { // [The
		 * original google example code notes here: // A bug in the dev_appserver causes an exception to be // thrown
		 * when no users are connected yet.] // logger.log(Level.SEVERE, "Failed to push the message " + msg //+
		 * " to client " + key, e); } }
		 */
	}
	
	/**
	 * Sends a message to one specific user.
	 * 
	 * @param user
	 *            The user to send the message to.
	 * @param msg
	 *            The message to be sent.
	 */
	public static void pushMessage(User user, Message msg) {
		/*
		 * if(user.getChannelID() == null){ //logger.log(Level.SEVERE,
		 * "Can't push a message to a null channeID. Maybe the Channel API is disabled in Connectr."); return; }
		 * pushMessageById(Arrays.asList(user.getEmail()), msg); } private static String getAppKeyForUser(String
		 * userUniqueId) { return APP_KEY + userUniqueId;
		 */}
	
	private static void pushMessageById(List<String> userUniqueIds, Message msg) {
		/*
		 * String encodedMessage = encodeMessage(msg); for (String userUniqueId : userUniqueIds) { String key =
		 * getAppKeyForUser(userUniqueId); //logger.info("Pushing msg to " + key); try {
		 * getChannelService().sendMessage( new ChannelMessage(key, encodedMessage)); } catch (Exception e) { // [The
		 * original google example code notes here: // A bug in the dev_appserver causes an exception to be // thrown
		 * when no users are connected yet.] // logger.log(Level.SEVERE, "Failed to push the message " + msg //+
		 * " to client " + key, e); } }
		 */
	}
	
	private static boolean setChannelAPIEnabled() {
		String skey = props.getProperty("com.metadot.connectr.enable-channelapi");
		if (skey != null) {
			if (skey.equalsIgnoreCase("true")) {
				// logger.info("channel API is enabled");
				return true;
			}
			if (skey.equalsIgnoreCase("false")) { return false; }
		}
		return false;
	}
	
	// private static SerializationPolicy serializationPolicy =
	// MergedSerializationPolicy.createPushSerializationPolicy();
	public ChannelServer() {
		
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
	private Message dummyMethod() {
		throw new UnsupportedOperationException("This should never be called.");
	}
	
}
