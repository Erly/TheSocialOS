package net.thesocialos.client.chat;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.app.IApplication;
import net.thesocialos.client.chat.events.ChatSendMessage;
import net.thesocialos.client.chat.view.ChatConversationView;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.desktop.window.WindowDisplay;
import net.thesocialos.shared.model.Lines;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ChatConversationPresenter extends DesktopUnit implements IApplication {
	
	public interface Display {
		
		ChatConversationView getChatPanel();
		
		Button getSendButton();
		
		TextArea getSendText();
		
		CellList<Lines> getConversation();
		
		Widget asWidget();
	}
	
	private String name;
	private String image;
	String message = "hellow World";
	
	private Display display;
	private String userWithChat;
	
	public ChatConversationPresenter(int programID, String appName, String appImageURL, String userWithChat,
			WindowDisplay windoDisplay, Display display) {
		super(programID, windoDisplay, TypeUnit.WINDOW, true);
		setName(appName);
		setImage(appImageURL);
		
		this.display = display;
		this.userWithChat = userWithChat;
		
		init();
	}
	
	private void init() {
		bindHandlers();
	}
	
	private void bindHandlers() {
		display.getSendButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				TheSocialOS.getEventBus().fireEvent(new ChatSendMessage(userWithChat, display.getSendText().getText()));
				
			}
		});
	}
	
	/**
	 * Write a messageChat in the window
	 * 
	 * @param message
	 *            to write in the chat window
	 */
	public void writeMessage(String message) {
		
	}
	
	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return image;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	@Override
	public void setImage(String image) {
		this.image = image;
		
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
		
	}
	
	@Override
	public void close(AbsolutePanel absolutePanel) {
		absolutePanel.remove(display.asWidget());
		
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		absolutePanel.add(display.asWidget(), 20, 50);
		
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void toFront() {
		windowDisplay.toFront();
	}
}