package net.thesocialos.client.chat.view;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.shared.model.User;

public class ChatMenuPresenter extends DesktopUnit {
	
	Display display;
	
	public ChatMenuPresenter(Display display) {
		programID = AppConstants.CHAT;
		typeUnit = TypeUnit.STATIC;
		this.display = display;
	}
	
	public interface Display {
		CellList<User> getCellContacts();
		
		Label getState();
		
		Label getEmail();
		
		HorizontalPanel getConversationsPanel();
		
		Widget asWidget();
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void close(AbsolutePanel absolutePanel) {
		absolutePanel.remove(display.asWidget());
		
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		absolutePanel.add(display.asWidget());
		
	}
	
}
