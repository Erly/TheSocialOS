package net.thesocialos.client.helper;

import net.thesocialos.client.channel.Channel;
import net.thesocialos.client.channel.ChannelFactory;
import net.thesocialos.client.channel.SocketListener;
import net.thesocialos.client.event.MessageChatAvailableEvent;

import net.thesocialos.shared.UserDTO;
import net.thesocialos.shared.messages.ChannelTextMessage;
import net.thesocialos.shared.messages.Message;
import net.thesocialos.shared.messages.Message.Type;
import net.thesocialos.shared.messages.MessageChat;


import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.web.bindery.event.shared.SimpleEventBus;


public class Comet {
	private Channel channel;
	SerializationStreamFactory pushServiceStreamFactory;
	 private SimpleEventBus eventBus;
	 
	 public Comet(SimpleEventBus eventBus) {
		 this.eventBus = eventBus;
		 
	 }
	
	public void listenToChannel(UserDTO user){
		//channel = ChannelFactory.createChannel(user.getChannelID());
		pushServiceStreamFactory=  (SerializationStreamFactory) PushService.App.getInstance();
		channel = ChannelFactory.createChannel(user.getChannelID());
		channel.open(new SocketListener() {
			
			@Override
			public void onOpen() {
				System.out.println("Conectado");
				
			}
			
			@Override
			public void onMessage(String encodedData) {
				SerializationStreamReader reader;
				try {
					System.out.println(encodedData);
					reader = pushServiceStreamFactory.createStreamReader(encodedData);
					Message message = (Message) reader.readObject();
					
					if (message.getType() == Type.NEW_CHATMSG_AVAILABLE){
						MessageChat messageChat =  (MessageChat) message;
						System.out.println(messageChat.getTypeChat());
						eventBus.fireEvent(new MessageChatAvailableEvent(messageChat)); //Fire a event to AppController
					}
					System.out.println(message.toString());
				} catch (SerializationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
	}
	
	 /**
	   * Handle messages pushed from the server.
	   */
	  public void handleMessage(Message msg) {
	    switch (msg.getType()) {

	    case NEW_CHATMSG_AVAILABLE:
	      GWT.log("Pushed msg received: NEW_CONTENT_AVAILABLE");
	      
	   //   eventBus.fireEvent(new MessageChatAvailableEvent(((MessageChatAvailableMessage) msg).getMessage()));
	      break;

	    case TEXT_MESSAGE:
	      String ttext = ((ChannelTextMessage) msg).get();
	      GWT.log("Pushed msg received: TEXT_MESSAGE: " + ttext);
	      break;

	    default:
	      Window.alert("Unknown message type: " + msg.getType());
	    }
	  }
	  
	  
}
