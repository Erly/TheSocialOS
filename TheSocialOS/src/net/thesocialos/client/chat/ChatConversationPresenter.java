package net.thesocialos.client.chat;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.app.IApplication;
import net.thesocialos.client.chat.events.ChatSendMessage;
import net.thesocialos.client.chat.view.ChatConversationView;
import net.thesocialos.client.desktop.DesktopEventOnClose;
import net.thesocialos.client.desktop.DesktopEventOnTop;
import net.thesocialos.client.desktop.DesktopEventonEndDrag;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.desktop.window.WindowCloseEvent;
import net.thesocialos.client.desktop.window.WindowDisplay;
import net.thesocialos.client.desktop.window.WindowEndDragEvent;
import net.thesocialos.client.desktop.window.WindowEventHandler;
import net.thesocialos.client.desktop.window.WindowMaximizeEvent;
import net.thesocialos.client.desktop.window.WindowMinimizeEvent;
import net.thesocialos.client.desktop.window.WindowOnTopEvent;
import net.thesocialos.shared.model.Lines;
import net.thesocialos.shared.model.User;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.objectify.Key;

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
	
	private Display display;
	private Key<User> userWithChat;
	
	public ChatConversationPresenter(int programID, String appName, String appImageURL, Key<User> userWithChat,
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
		initWindow();
	}
	
	private void bindHandlers() {
		display.getSendButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				TheSocialOS.getEventBus().fireEvent(new ChatSendMessage(userWithChat, display.getSendText().getText()));
				
			}
		});
		windowDisplay.addWindowEvents(new WindowEventHandler() {
			
			@Override
			public void onTop(WindowOnTopEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnTop(ChatConversationPresenter.this));
				
			}
			
			@Override
			public void onMinimize(WindowMinimizeEvent windowMinimizeEvent) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMaximize(WindowMaximizeEvent windowMaximizeEvent) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onEndDrag(WindowEndDragEvent event) {
				// TODO Auto-generated method stub
				TheSocialOS.getEventBus().fireEvent(new DesktopEventonEndDrag(ChatConversationPresenter.this));
			}
			
			@Override
			public void onClose(WindowCloseEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnClose(ChatConversationPresenter.this));
				
			}
		});
	}
	
	/**
	 * Write a messageChat in the window
	 * 
	 * @param message
	 *            to write in the chat window
	 */
	public void writeMessage(Lines line) {
		display.getSendText().setText(
				"User:" + line.getUserOwner().getName() + " text: " + line.getText() + " Date " + line.getDate());
	}
	
	/**
	 * Make the window have go on top of the desktop
	 */
	public void setOnTop() {
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnTop(this));
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
		absolutePanel.remove(windowDisplay.getWindow());
		
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		
		absolutePanel.add(windowDisplay.getWindow(), 20, 50);
		windowDisplay.getWindow().setVisible(true);
		
		// absolutePanel.add(display.asWidget(), 20, 50);
		
	}
	
	private void initWindow() {
		
		windowDisplay.setResizable(false);
		windowDisplay.setWindowTitle(userWithChat.getName());
		display.asWidget().setSize("447px", "323px");
		windowDisplay.getWindow().add(display.asWidget());
		
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