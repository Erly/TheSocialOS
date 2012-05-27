package net.thesocialos.client.presenter;

import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.app.IApplication;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.desktop.window.Footer;
import net.thesocialos.client.desktop.window.MyCaption;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WindowPanelLayout;

public class SharedUnit extends DesktopUnit implements IApplication {
	
	Display display;
	
	public SharedUnit(Display display) {
		super(AppConstants.SHAREDMANAGER, "SharedManager", new WindowPanelLayout(new MyCaption(), new Footer()),
				TypeUnit.WINDOW, false);
		this.display = display;
	}
	
	public interface Display {
		Widget asWidget();
		
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
		absolutePanel.add(display.asWidget(), x, y);
		
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	@Override
	public void setImage(String image) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
	
}
