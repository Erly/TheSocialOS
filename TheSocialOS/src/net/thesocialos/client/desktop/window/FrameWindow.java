package net.thesocialos.client.desktop.window;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.app.IApplication;
import net.thesocialos.client.desktop.DesktopEventOnClose;
import net.thesocialos.client.desktop.DesktopEventOnMaximize;
import net.thesocialos.client.desktop.DesktopEventOnMinimize;
import net.thesocialos.client.desktop.DesktopEventOnTop;
import net.thesocialos.client.desktop.DesktopEventonEndDrag;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.desktop.isFrame;
import net.thesocialos.client.desktop.window.events.WindowCloseEvent;
import net.thesocialos.client.desktop.window.events.WindowEndDragEvent;
import net.thesocialos.client.desktop.window.events.WindowEventHandler;
import net.thesocialos.client.desktop.window.events.WindowMaximizeEvent;
import net.thesocialos.client.desktop.window.events.WindowMinimizeEvent;
import net.thesocialos.client.desktop.window.events.WindowOnTopEvent;
import net.thesocialos.client.desktop.window.events.WindowResizeEvent;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.WindowPanelLayout;

public class FrameWindow extends DesktopUnit implements IApplication, isFrame {
	
	String url = "";
	String urlImage;
	
	Frame frame;
	
	public FrameWindow(String url, String name, int idProgram, boolean isSubApplication) {
		this(idProgram, isSubApplication, name);
		this.url = url;
		
	}
	
	public FrameWindow(String name, String urlImage, String url, int idProgram, boolean isSubApplication) {
		this(idProgram, isSubApplication, name);
		this.url = url;
		
		setImage(urlImage);
	}
	
	public FrameWindow(String url, String name, int idProgram) {
		this(idProgram, false, name);
		this.url = url;
		
	}
	
	private FrameWindow(int idProgram, boolean isSubApplication, String name) {
		super(idProgram, name, new WindowPanelLayout(new MyCaption(), new Footer()), TypeUnit.WINDOW, isSubApplication);
		frame = new Frame();
		frame.setSize("560px", "315px");
		windowDisplay.getWindow().add(frame);
		x = Window.getClientWidth() / 2 - 280;
		y = Window.getClientHeight() / 2 - 160;
		TheSocialOS.getEventBus().fireEvent(new DesktopEventOnTop(FrameWindow.this));
		
		windowDisplay.addWindowEvents(new WindowEventHandler() {
			
			@Override
			public void onClose(WindowCloseEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnClose(FrameWindow.this));
			}
			
			@Override
			public void onEndDrag(WindowEndDragEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventonEndDrag(FrameWindow.this));
			}
			
			@Override
			public void onMaximize(WindowMaximizeEvent windowMaximizeEvent) {
				if (isMaximizable()) TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMaximize(FrameWindow.this));
				// addMedia((HashSet<? extends Media>) files.get(arrayPosition));
			}
			
			@Override
			public void onMinimize(WindowMinimizeEvent windowMinimizeEvent) {
				if (isMinimizable()) TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMinimize(FrameWindow.this));
			}
			
			@Override
			public void onTop(WindowOnTopEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnTop(FrameWindow.this));
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
		return urlImage;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	@Override
	public void setImage(String image) {
		urlImage = image;
		
	}
	
	@Override
	public void setName(String name) {
		
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
		absolutePanel.add(windowDisplay.getWindow(), x, y);
		windowDisplay.getWindow().setVisible(true);
		windowDisplay.setWindowTitle(name);
		frame.setUrl(url);
		
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
	public void setUrl(String url) {
		frame.setUrl(url);
	}
	
	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}
	
}
