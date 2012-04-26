package net.thesocialos.client.desktop;

import java.util.LinkedHashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.web.bindery.event.shared.EventBus;

import net.thesocialos.client.desktop.DesktopUnit.TypeUnit;

public class DesktopManager {
	EventBus eventBus;
	
	AbsolutePanel absolutePanelScreen;
	AbsolutePanel absolutePanelDesktop;
	
	LinkedHashMap<String, DesktopUnit> linkedDesktopUnit; // Desde ventanas hasta formularios
	DesktopUnit lastDesktopUnit = null;
	
	public DesktopManager(EventBus eventBus, AbsolutePanel Screen, AbsolutePanel Desktop) {
		this.absolutePanelScreen = Screen;
		this.absolutePanelDesktop = Desktop;
		this.eventBus = eventBus;
		linkedDesktopUnit = new LinkedHashMap<String, DesktopUnit>();
		handlers();
		
		/*
		 * Event.addNativePreviewHandler(new NativePreviewHandler() {
		 * @Override public void onPreviewNativeEvent(NativePreviewEvent event) { // TODO Auto-generated method stub
		 * System.out.println("codigo evento " + event.getTypeInt()); if(event.getTypeInt() == Event.ONCLICK){
		 * //System.out.println(event.getNativeEvent().getCurrentEventTarget().getClass().getName());
		 * System.out.println(event.isFirstHandler()); } } });
		 */
		
		ClickHandler clickHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (lastDesktopUnit != null) {
					removeUnit(lastDesktopUnit);
				}
				System.out.println("Has clickeado en el escritorio");
			}
		};
		absolutePanelDesktop.addDomHandler(clickHandler, ClickEvent.getType());
		
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
	
	/**
	 * Borra una unidad dada del escritorio
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClose(DesktopEventOnClose event) {
				removeUnit(event.getDesktopUnit());
				
			}
		});
	}
	
}
