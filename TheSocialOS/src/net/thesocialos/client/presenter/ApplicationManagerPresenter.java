package net.thesocialos.client.presenter;

import java.util.HashMap;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.desktop.AppManagerCloseEvent;
import net.thesocialos.client.desktop.AppManagerEvent;
import net.thesocialos.client.desktop.AppManagerEventHandler;
import net.thesocialos.client.desktop.AppManagerOpenEvent;
import net.thesocialos.client.desktop.DesktopEventOnMinimize;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.desktop.IsTypeInfo;
import net.thesocialos.client.view.Aplication;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationManagerPresenter extends DesktopUnit implements IsTypeInfo {
	Display display;
	
	HashMap<Aplication, DesktopUnit> applications = new HashMap<Aplication, DesktopUnit>();
	
	public ApplicationManagerPresenter(int programID, Display display, TypeUnit typeUnit, boolean isSubApplication) {
		super(programID, "Aplication Manager", null, typeUnit, isSubApplication);
		this.display = display;
		handler();
	}
	
	public interface Display {
		Widget asWidget();
		
		SimplePanel getHtmlPanel();
		
		boolean addApplication(Aplication aplication);
	}
	
	private void handler() {
		TheSocialOS.getEventBus().addHandler(AppManagerEvent.TYPE, new AppManagerEventHandler() {
			
			@Override
			public void onOpen(AppManagerOpenEvent event) {
				addUnit(event.getDesktopUnit());
				
			}
			
			@Override
			public void onClose(AppManagerCloseEvent event) {
				removeUnit(event.getDesktopUnit());
				
			}
		});
	}
	
	private void addUnit(DesktopUnit desktopUnit) {
		if (!applications.containsValue(desktopUnit)) {
			Aplication aplication = new Aplication(desktopUnit.name, null, this);
			
			display.addApplication(aplication);
			applications.put(aplication, desktopUnit);
			
		}
	}
	
	public void showHideApp(Aplication aplication) {
		if (applications.containsKey(aplication))
			TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMinimize(applications.get(aplication)));
		
	}
	
	private void removeUnit(DesktopUnit desktopUnit) {
		
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		if (absolutePanel.getWidgetIndex(display.asWidget()) == -1) {
			absolutePanel.add(display.asWidget(), x, y);
			display.asWidget().setVisible(true);
		} else
			absolutePanel.setWidgetPosition(display.asWidget(), x, 0);
		display.getHtmlPanel().setStyleName("appManager_open", true);
		display.getHtmlPanel().setStyleName("appManager_close", false);
		
		// display.asWidget().getElement().setAttribute("position", "absolute");
		
	}
	
	@Override
	public void close(final AbsolutePanel absolutePanel) {
		
		// alert(display.asWidget().getElement(), listener(display.asWidget().getElement()));
		
		// display.getHtmlPanel().setStyleName("appManager_open", false);
		// absolutePanel.remove(display.asWidget());
		// absolutePanel.add(display.asWidget(), x, -360);
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				absolutePanel.remove(display.asWidget());
			}
			
		};
		display.getHtmlPanel().setStyleName("appManager_close", true);
		timer.schedule(1000);
		// absolutePanel.remove(display.asWidget());
		
	}
	
	public static native JavaScriptObject listener(Element element) /*-{

		switch (element.type) {
		case "animationstart":
			$wnd.alert("Termino");
			break;
		case "animationend":
			$wnd.alert("Termino");
			break;
		case "animationiteration":
			$wnd.alert("Termino");
			break;
		}
		$wnd.alert(element.type);
		return this;
	}-*/;
	
	public static native void alert(Element element, JavaScriptObject javascriptObject) /*-{

		element.addEventListener("animationiteration", javascriptObject, false);
	}-*/;
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getWidth() {
		
		return display.asWidget().getOffsetWidth();
		
	}
	
	@Override
	public int getHeight() {
		
		return display.asWidget().getOffsetHeight();
	}
	
	@Override
	public int getAbsoluteLeft() {
		
		return display.asWidget().getAbsoluteLeft();
	}
	
	@Override
	public int getAbsoluteTop() {
		
		return display.asWidget().getAbsoluteTop();
	}
	
	@Override
	public void toBack() {
		
	}
	
	@Override
	public void toFront() {
		
	}
	
	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
}
