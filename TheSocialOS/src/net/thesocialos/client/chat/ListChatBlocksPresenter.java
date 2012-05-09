package net.thesocialos.client.chat;

import java.util.HashMap;
import java.util.Iterator;

import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.chat.view.ChatBlockView;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.objectify.Key;

public class ListChatBlocksPresenter extends DesktopUnit {
	
	Display display;
	HashMap<Key<User>, ChatBlockView> conversationsBlock = new HashMap<Key<User>, ChatBlockView>();
	
	public ListChatBlocksPresenter(Display display) {
		super(AppConstants.CHAT, null, TypeUnit.STATIC, true);
		this.display = display;
	}
	
	public interface Display {
		Widget asWidget();
		
		HorizontalPanel getHConverPanel();
	}
	
	@Override
	public void close(AbsolutePanel absolutePanel) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		absolutePanel.add(display.asWidget());
		
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Add a conversationBlock in the horizontalPanel
	 * 
	 * @param userKey
	 */
	public void addConvesationBlock(Key<User> userKey) {
		
		if (conversationsBlock.containsKey(userKey)) return;
		conversationsBlock.put(userKey, new ChatBlockView(userKey));
		display.getHConverPanel().add(conversationsBlock.get(userKey));
		
	}
	
	/**
	 * Remove a ConversationBlock in the panel
	 * 
	 * @param userKey
	 * @return true if success or false if has a problem
	 */
	public boolean removeConversationBlock(Key<User> userKey) {
		if (!conversationsBlock.containsKey(userKey)) return false;
		boolean close = display.getHConverPanel().remove(conversationsBlock.get(userKey));
		conversationsBlock.remove(userKey);
		return close;
		
	}
	
	/**
	 * Activate the animation of the userKey conversation
	 * 
	 * @param userKey
	 */
	public void messagePending(Key<User> userKey) {
		if (!conversationsBlock.containsKey(userKey)) return;
		conversationsBlock.get(userKey).messagesPending(true);
	}
	
	/**
	 * Activate the ConversationBlock for animation
	 * 
	 * @param userKey
	 * @param activation
	 *            animation on if true
	 */
	public void setActivateConversationBlock(Key<User> userKey, boolean activation) {
		if (!conversationsBlock.containsKey(userKey)) return;
		conversationsBlock.get(userKey).setActivated(activation);
		
	}
	
	/**
	 * Modify the conversations block to true except one
	 * 
	 * @param userKey
	 */
	public void modifyConversationsBlock(Key<User> userKey) {
		Iterator<ChatBlockView> iterator = conversationsBlock.values().iterator();
		while (iterator.hasNext())
			iterator.next().setActivated(true);
		conversationsBlock.get(userKey).setActivated(false);
	}
	
	@Override
	public void toBack() {
		
	}
	
	@Override
	public void toFront() {
		
	}
	
}
