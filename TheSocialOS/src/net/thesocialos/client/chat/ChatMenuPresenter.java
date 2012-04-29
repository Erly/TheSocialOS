package net.thesocialos.client.chat;

import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ChatMenuPresenter extends DesktopUnit {
	
	public interface Display {
		Widget asWidget();
		
		CellList<User> getCellContacts();
		
		HorizontalPanel getConversationsPanel();
		
		Label getEmail();
		
		Label getState();
	}
	
	Display display;
	
	public ChatMenuPresenter(Display display) {
		super(AppConstants.CHAT, 0, null, TypeUnit.STATIC);
		
		typeUnit = TypeUnit.STATIC;
		this.display = display;
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
		absolutePanel.add(display.asWidget());
		
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
}
