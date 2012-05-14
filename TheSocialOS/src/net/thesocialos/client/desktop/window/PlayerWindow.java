package net.thesocialos.client.desktop.window;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.app.IApplication;
import net.thesocialos.client.desktop.DesktopEventOnClose;
import net.thesocialos.client.desktop.DesktopEventOnMaximize;
import net.thesocialos.client.desktop.DesktopEventOnMinimize;
import net.thesocialos.client.desktop.DesktopEventOnTop;
import net.thesocialos.client.desktop.DesktopEventonEndDrag;
import net.thesocialos.client.desktop.DesktopUnit;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.WindowPanelLayout;

public class PlayerWindow extends DesktopUnit implements IApplication {
	
	String url = "";
	
	public PlayerWindow(String url, int idProgram) {
		this(idProgram);
		this.url = url;
	}
	
	public PlayerWindow(int idProgram) {
		super(idProgram, new WindowPanelLayout(new MyCaption(), new Footer()), TypeUnit.WINDOW, false);
		x = Window.getClientWidth() / 2 - 280;
		y = Window.getClientHeight() / 2 - 160;
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnTop(PlayerWindow.this));
		
		windowDisplay.addWindowEvents(new WindowEventHandler() {
			
			@Override
			public void onClose(WindowCloseEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnClose(PlayerWindow.this));
			}
			
			@Override
			public void onEndDrag(WindowEndDragEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventonEndDrag(PlayerWindow.this));
			}
			
			@Override
			public void onMaximize(WindowMaximizeEvent windowMaximizeEvent) {
				if (isMaximizable())
					TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMaximize(PlayerWindow.this));
				// addMedia((HashSet<? extends Media>) files.get(arrayPosition));
			}
			
			@Override
			public void onMinimize(WindowMinimizeEvent windowMinimizeEvent) {
				if (isMinimizable())
					TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMinimize(PlayerWindow.this));
			}
			
			@Override
			public void onTop(WindowOnTopEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnTop(PlayerWindow.this));
			}
			
			@Override
			public void onResize(WindowResizeEvent event) {
				// addMedia((HashSet<? extends Media>) files.get(arrayPosition));
			}
		});
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
		absolutePanel.remove(windowDisplay.getWindow());
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		Frame frame = new Frame(url);
		frame.setSize("560px", "315px");
		windowDisplay.getWindow().add(frame);
		absolutePanel.add(windowDisplay.getWindow(), x, y);
		windowDisplay.getWindow().setVisible(true);
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
}
