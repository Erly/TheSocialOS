package net.thesocialos.client.desktop;

import java.util.Iterator;
import java.util.LinkedHashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.web.bindery.event.shared.EventBus;

import net.thesocialos.client.desktop.DesktopUnit.TypeUnit;

public class DesktopManager {
	EventBus eventBus;
	
	AbsolutePanel absolutePanelScreen;
	AbsolutePanel absolutePanelDesktop;
	
	LinkedHashMap<Integer, DesktopUnit> linkedDesktopUnit; // Desde ventanas hasta formularios
	DesktopUnit lastDesktopUnit = null;
	
	public DesktopManager(EventBus eventBus, AbsolutePanel Screen, AbsolutePanel Desktop) {
		this.absolutePanelScreen = Screen;
		this.absolutePanelDesktop = Desktop;
		this.eventBus = eventBus;
		linkedDesktopUnit = new LinkedHashMap<Integer, DesktopUnit>();
		handlers();
		
		/*
		 * Click on the desktop
		 */
		ClickHandler clickHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (lastDesktopUnit != null && lastDesktopUnit.typeUnit != TypeUnit.APPLICATION) {
					
					removeUnit(lastDesktopUnit);
				}
				
			}
		};
		absolutePanelDesktop.addDomHandler(clickHandler, ClickEvent.getType());
		/*
		 * Application window resize
		 */
		Window.addResizeHandler(new ResizeHandler() {
			Timer resizeTimer = new Timer() {
				@Override
				public void run() {
					Iterator<DesktopUnit> desktopIterator = linkedDesktopUnit.values().iterator();
					
					while (desktopIterator.hasNext()) {
						checkWindowPosition(desktopIterator.next());
					}
					
				}
			};
			
			@Override
			public void onResize(ResizeEvent event) {
				
				resizeTimer.cancel();
				resizeTimer.schedule(250);
				
			}
		});
		
	}
	
	/**
	 * A�ade una unidad dada en el escritorio
	 * 
	 * @param desktopUnit
	 * @return True si se a podido abrir // False si ya estaba abierto
	 */
	public boolean addUnit(DesktopUnit desktopUnit) {
		if (linkedDesktopUnit.containsKey(desktopUnit.programID)) { return false; }
		if (lastDesktopUnit != null && lastDesktopUnit.typeUnit.equals(TypeUnit.INFO)) {
			removeUnit(lastDesktopUnit);
		}
		
		linkedDesktopUnit.put(desktopUnit.programID, desktopUnit);
		linkedDesktopUnit.get(desktopUnit.programID).open(absolutePanelScreen);
		this.lastDesktopUnit = desktopUnit;
		return true;
	}
	
	public boolean maximize(DesktopUnit desktopUnit) {
		
		return null != null;
		
	}
	
	/**
	 * Check if window position is correct
	 * 
	 * @param desktopUnit
	 *            unit to check
	 * @param xPosition
	 *            reference value. X relative position
	 * @param yPosition
	 *            reference value. Y relative position
	 */
	private void checkWindowPosition(DesktopUnit desktopUnit) {
		if (desktopUnit.isMaximized()) {
			desktopUnit.setSize(absolutePanelDesktop.getOffsetWidth() - 7, absolutePanelDesktop.getOffsetHeight() - 7);
		}
		if (desktopUnit.getAbsoluteLeft() < 0) {
			desktopUnit.setPosition(0, desktopUnit.getAbsoluteTop());
			
		} else if (desktopUnit.getAbsoluteLeft() + desktopUnit.getWidth() > Window.getClientWidth()) {
			desktopUnit.setPosition(Window.getClientWidth() - desktopUnit.getWidth(), desktopUnit.getAbsoluteTop());
		}
		if (desktopUnit.getAbsoluteTop() + desktopUnit.getHeight() > Window.getClientHeight()) {
			desktopUnit.setPosition(desktopUnit.getAbsoluteLeft(), Window.getClientHeight() - desktopUnit.getHeight());
		} else if (desktopUnit.getAbsoluteTop() < 30) {
			desktopUnit.setPosition(desktopUnit.getAbsoluteLeft(), 30);
		}
	}
	
	/**
	 * Delete a windows of the desktop
	 * 
	 * @param desktopUnit
	 * @return True si se a podido cerrar // False si no existia
	 */
	public boolean removeUnit(DesktopUnit desktopUnit) {
		if (linkedDesktopUnit.containsKey(desktopUnit.programID)) {
			desktopUnit.close(absolutePanelScreen);
			linkedDesktopUnit.remove(desktopUnit.programID);
			lastDesktopUnit = null;
			return true;
		}
		return false;
	}
	
	private void setWindowsZPositions(DesktopUnit desktopUnit) {
		Iterator<DesktopUnit> iterator = linkedDesktopUnit.values().iterator();
		while (iterator.hasNext()) {
			iterator.next().toBack();
		}
		desktopUnit.toFront();
	}
	
	private void MaximizeWindow(DesktopUnit desktopUnit) {
		
		if (desktopUnit.isMaximized()) {
			desktopUnit.setMaximized(false, 0, 0, 0, 0);
		} else {
			desktopUnit.setMaximized(true, absolutePanelDesktop.getOffsetWidth() - 7,
					absolutePanelDesktop.getOffsetHeight() - 7, absolutePanelDesktop.getAbsoluteTop(),
					absolutePanelDesktop.getAbsoluteLeft());
		}
		
	}
	
	private void handlers() {
		eventBus.addHandler(DesktopEvent.TYPE, new DesktopEventHandler() {
			
			@Override
			public void onOpen(DesktopEventOnOpen event) {
				addUnit(event.getDesktopUnit());
				
			}
			
			@Override
			public void onMinimize(DesktopEventOnMinimize event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMaximize(DesktopEventOnMaximize event) {
				MaximizeWindow(event.getDesktopUnit());
				
			}
			
			@Override
			public void onClose(DesktopEventOnClose event) {
				removeUnit(event.getDesktopUnit());
				
			}
			
			@Override
			public void onEndDrag(DesktopEventonEndDrag event) {
				
			}
			
			@Override
			public void onTop(DesktopEventOnTop event) {
				setWindowsZPositions(event.getDesktopUnit());
				
			}
		});
	}
	
}
