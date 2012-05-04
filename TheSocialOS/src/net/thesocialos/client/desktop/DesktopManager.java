// $codepro.audit.disable unnecessaryImport
package net.thesocialos.client.desktop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import net.thesocialos.client.desktop.DesktopUnit.TypeUnit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.web.bindery.event.shared.EventBus;

public class DesktopManager {
	EventBus eventBus;
	
	AbsolutePanel absolutePanelScreen;
	AbsolutePanel absolutePanelDesktop;
	
	LinkedHashMap<Integer, ArrayList<DesktopUnit>> linkedDesktopUnit; // Desde ventanas hasta formularios
	
	DesktopUnit lastDesktopUnit = null;
	
	UnitsManager unitsManager = new UnitsManager();
	
	public DesktopManager(EventBus eventBus, AbsolutePanel Screen, AbsolutePanel Desktop) {
		absolutePanelScreen = Screen;
		absolutePanelDesktop = Desktop;
		this.eventBus = eventBus;
		linkedDesktopUnit = new LinkedHashMap<Integer, ArrayList<DesktopUnit>>();
		handlers();
		
		/*
		 * Click on the desktop
		 */
		ClickHandler clickHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (lastDesktopUnit != null && lastDesktopUnit.typeUnit != TypeUnit.WINDOW)
					unitsManager.removeUnit(lastDesktopUnit);
				
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
					Iterator<ArrayList<DesktopUnit>> desktopIterator = linkedDesktopUnit.values().iterator();
					while (desktopIterator.hasNext()) {
						Iterator<DesktopUnit> desktopUnitIterator = desktopIterator.next().iterator();
						while (desktopUnitIterator.hasNext())
							checkWindowPosition(desktopUnitIterator.next());
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
	
	class UnitsManager {
		
		/**
		 * A�ade una unidad dada en el escritorio
		 * 
		 * @param desktopUnit
		 * @return True si se a podido abrir // False si ya estaba abierto
		 */
		public boolean addUnit(DesktopUnit desktopUnit) {
			if (lastDesktopUnit != null && lastDesktopUnit.typeUnit.equals(TypeUnit.INFO)) removeUnit(lastDesktopUnit);
			if (linkedDesktopUnit.containsKey(desktopUnit.getProgramID())) {
				if (desktopUnit.isSubApplication()) {
					ArrayList<DesktopUnit> hashDesktopUnits = linkedDesktopUnit.get(desktopUnit.getProgramID());
					if (hashDesktopUnits.contains(desktopUnit)) {
						setWindowsZPositions(desktopUnit);
						return true;
					} else
						hashDesktopUnits.add(desktopUnit);
					
				} else
					setWindowsZPositions(desktopUnit);
				
			} else {
				if (desktopUnit.isSubApplication()) return false;
				ArrayList<DesktopUnit> desktopUnits = new ArrayList<DesktopUnit>();
				
				desktopUnits.add(desktopUnit);
				linkedDesktopUnit.put(desktopUnit.getID(), desktopUnits);
			}
			if (desktopUnit.typeUnit.equals(TypeUnit.INFO)) lastDesktopUnit = desktopUnit;
			
			desktopUnit.open(absolutePanelScreen);
			return true;
		}
		
		/**
		 * Delete a windows of the desktop
		 * 
		 * @param desktopUnit
		 * @return True si se a podido cerrar // False si no existia
		 */
		public boolean removeUnit(DesktopUnit desktopUnit) {
			
			if (linkedDesktopUnit.containsKey(desktopUnit.getProgramID())) {
				if (desktopUnit.isSubApplication()) {
					if (linkedDesktopUnit.get(desktopUnit.getProgramID()).contains(desktopUnit)) {
						linkedDesktopUnit.get(desktopUnit.getProgramID()).remove(desktopUnit);
						desktopUnit.close(absolutePanelScreen);
					}
				} else {
					Iterator<DesktopUnit> iDesktopUnit = linkedDesktopUnit.get(desktopUnit.getProgramID()).iterator();
					while (iDesktopUnit.hasNext()) {
						DesktopUnit deskUnit = iDesktopUnit.next();
						deskUnit.close(absolutePanelScreen);
						
					}
					linkedDesktopUnit.remove(desktopUnit.getProgramID());
				}
				
				lastDesktopUnit = null;
				return true;
			}
			return false;
		}
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
		if (desktopUnit.typeUnit == TypeUnit.WINDOW) {
			if (desktopUnit.isMaximized())
				desktopUnit.setSize(absolutePanelDesktop.getOffsetWidth() - 7,
						absolutePanelDesktop.getOffsetHeight() - 7);
			if (desktopUnit.getAbsoluteLeft() < 0) desktopUnit.setPosition(0, desktopUnit.getAbsoluteTop());
			else if (desktopUnit.getAbsoluteLeft() + desktopUnit.getWidth() > Window.getClientWidth())
				desktopUnit.setPosition(Window.getClientWidth() - desktopUnit.getWidth(), desktopUnit.getAbsoluteTop());
			if (desktopUnit.getAbsoluteTop() + desktopUnit.getHeight() > Window.getClientHeight()) desktopUnit
					.setPosition(desktopUnit.getAbsoluteLeft(), Window.getClientHeight() - desktopUnit.getHeight());
			else if (desktopUnit.getAbsoluteTop() < 30) desktopUnit.setPosition(desktopUnit.getAbsoluteLeft(), 30);
		}
		
	}
	
	private void handlers() {
		eventBus.addHandler(DesktopEvent.TYPE, new DesktopEventHandler() {
			
			@Override
			public void onClose(DesktopEventOnClose event) {
				unitsManager.removeUnit(event.getDesktopUnit());
				
			}
			
			@Override
			public void onEndDrag(DesktopEventonEndDrag event) {
				
			}
			
			@Override
			public void onMaximize(DesktopEventOnMaximize event) {
				MaximizeWindow(event.getDesktopUnit());
				
			}
			
			@Override
			public void onMinimize(DesktopEventOnMinimize event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onOpen(DesktopEventOnOpen event) {
				unitsManager.addUnit(event.getDesktopUnit());
				
			}
			
			@Override
			public void onTop(DesktopEventOnTop event) {
				setWindowsZPositions(event.getDesktopUnit());
				
			}
		});
	}
	
	public boolean maximize(DesktopUnit desktopUnit) {
		
		return null != null;
		
	}
	
	private void MaximizeWindow(DesktopUnit desktopUnit) {
		setWindowsZPositions(desktopUnit);
		if (desktopUnit.isMaximized()) desktopUnit.setMaximized(false, 0, 0, 0, 0);
		else
			desktopUnit.setMaximized(true, absolutePanelDesktop.getOffsetWidth() - 7,
					absolutePanelDesktop.getOffsetHeight() - 7, absolutePanelDesktop.getAbsoluteTop(),
					absolutePanelDesktop.getAbsoluteLeft());
		
	}
	
	private void setWindowsZPositions(DesktopUnit desktopUnit) {
		Iterator<ArrayList<DesktopUnit>> desktopIterator = linkedDesktopUnit.values().iterator();
		while (desktopIterator.hasNext()) {
			Iterator<DesktopUnit> desktopUnitIterator = desktopIterator.next().iterator();
			while (desktopUnitIterator.hasNext())
				desktopUnitIterator.next().toBack();
		}
		desktopUnit.toFront();
		
	}
	
}
