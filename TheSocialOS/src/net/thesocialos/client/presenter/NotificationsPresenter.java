package net.thesocialos.client.presenter;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.AbsolutePanel;

import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.view.LabelText;
import net.thesocialos.shared.model.User;

public class NotificationsPresenter extends DesktopUnit{

	
	public NotificationsPresenter(){
		
	}
	
	public interface Display{
		
		LabelText getContactsLabelText();
		
		LabelText getGroupsLabelText();
		
		CellList<User> getContactsCellList();
	}
	@Override
	public void toFront() {
		// TODO Auto-generated method stub
		
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
	public void setPosition(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getXPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getYPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close(AbsolutePanel absolutePanel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void open(AbsolutePanel absolutePanel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void minimize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void maximize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isMinimized() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getID() {
		// TODO Auto-generated method stub
		
	}

}
