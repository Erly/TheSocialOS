package net.thesocialos.client.helper;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.channelApi.Channel;
import net.thesocialos.client.channelApi.ChannelFactory;
import net.thesocialos.client.channelApi.ChannelFactory.ChannelCreatedCallback;
import net.thesocialos.client.channelApi.SocketError;
import net.thesocialos.client.channelApi.SocketListener;
import net.thesocialos.client.event.ChannelClose;
import net.thesocialos.client.event.ChannelOpen;
import net.thesocialos.client.service.ChannelService;
import net.thesocialos.shared.ChannelApiEvents.ChApiEvent;
import net.thesocialos.shared.messages.ChannelTextMessage;
import net.thesocialos.shared.messages.Message;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;

public class ChannelApiHelper {
	// private Channel channel;
	static SerializationStreamFactory pushServiceStreamFactory;
	
	static int retry = 3;
	
	public static boolean isChannelOpen = false;
	
	public ChannelApiHelper() {
		
	}
	
	/**
	 * Extract the Object information of the encodedString
	 * 
	 * @param encodedString
	 *            the string coded
	 * @return a ChApiEvent Object
	 */
	private static ChApiEvent decodedString(String encodedString) {
		
		try {
			pushServiceStreamFactory = (SerializationStreamFactory) ChannelService.App.getInstance();
			SerializationStreamReader reader = pushServiceStreamFactory.createStreamReader(encodedString);
			ChApiEvent message = (ChApiEvent) reader.readObject();
			return message;
			/*
			 * Window.alert("Creando clase de compilacion"); SerializationStreamFactory ssf =
			 * (SerializationStreamFactory) GWT.create(ChannelService.class); Window.alert("Deserializando objeto");
			 * ChApiEvent event = (ChApiEvent) ssf.createStreamReader(encodedString).readObject();
			 * Window.alert("Objeto deserializado"); return event;
			 */
		} catch (SerializationException e) {
			e.printStackTrace();
			Window.alert("Excepcion serializacion");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			Window.alert("Excepcion" + e.getCause().toString());
			return null;
		}
		
	}
	
	private static void fireEvent(String encodedString) {
		// Window.alert("Descodificando String");
		System.out.println(encodedString);
		ChApiEvent channelEvent = decodedString(encodedString);
		System.out.println(channelEvent.getClass());
		// System.out.println(c)
		// Window.alert("String descodificado:" + channelEvent.getAssociatedType());;
		if (channelEvent != null) TheSocialOS.getEventBus().fireEvent(channelEvent);
		
	}
	
	/**
	 * Handle messages pushed from the server.
	 */
	public static void handleMessage(Message msg) {
		switch (msg.getType()) {
		
		case NEW_CHATMSG_AVAILABLE:
			GWT.log("Pushed msg received: NEW_CONTENT_AVAILABLE");
			
			// eventBus.fireEvent(new MessageChatAvailableEvent(((MessageChatAvailableMessage) msg).getMessage()));
			break;
		
		case TEXT_MESSAGE:
			String ttext = ((ChannelTextMessage) msg).get();
			GWT.log("Pushed msg received: TEXT_MESSAGE: " + ttext);
			break;
		
		default:
			Window.alert("Unknown message type: " + msg.getType());
		}
	}
	
	private static void updateTokenChannel() {
		if (retry == 0) return;
		CacheLayer.UserCalls.getNewChannelId(new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				listenToChannel(CacheLayer.UserCalls.getUser());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught);
				
			}
		});
	}
	
	public static void listenToChannel(User user) {
		
		if (user.getTokenChannel() == null) {
			updateTokenChannel();
			return;
		}
		
		ChannelFactory.createChannel(user.getTokenChannel(), new ChannelCreatedCallback() {
			
			@Override
			public void onChannelCreated(Channel channel) {
				channel.open(new SocketListener() {
					
					@Override
					public void onClose() {
						TheSocialOS.getEventBus().fireEvent(new ChannelClose());
						isChannelOpen = false;
						// Window.alert("Socket close");
					}
					
					@Override
					public void onError(SocketError error) {
						// System.out.println(error.getDescription());
						ChannelFactory.deleteChannel();
						updateTokenChannel();
						retry--;
						// Window.alert("Socket error");
					}
					
					@Override
					public void onMessage(String encodedString) {
						// .alert("Socket onMessage " + encodedString);
						fireEvent(encodedString);
					}
					
					@Override
					public void onOpen() {
						TheSocialOS.getEventBus().fireEvent(new ChannelOpen());
						System.out.println("Canal abierto " + CacheLayer.UserCalls.getUser().getEmail());
						isChannelOpen = true;
						retry = 3;
						// Window.alert("Socket open");
						
					}
				});
				
			}
		});
		
	}
	
}
