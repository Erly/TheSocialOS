package net.thesocialos.client.desktop.window;

import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.app.IApplication;
import net.thesocialos.client.desktop.DesktopUnit;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.WindowPanelLayout;

public class SharedWindow extends DesktopUnit implements IApplication {
	
	public SharedWindow(TypeUnit typeUnit) {
		super(AppConstants.SHAREDMANAGER, "Shared Manager", new WindowPanelLayout(new MyCaption(), new Footer()),
				typeUnit, false);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setImage(String image) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
}
