package net.thesocialos.client.advanced;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

public class AdvFocusPanel extends FocusPanel implements AdvClickNotifier {
	
	private AdvClickListener listener = null;
	
	public AdvFocusPanel() {
		super();
		sinkEvents(Event.ONMOUSEUP | Event.ONCONTEXTMENU);
	}
	
	public AdvFocusPanel(Widget child) {
		super(child);
		sinkEvents(Event.ONMOUSEUP | Event.ONCONTEXTMENU);
	}
	
	@Override
	public void onBrowserEvent(Event e) {
		GWT.log("onBrowserEvent", null);
		e.cancelBubble(true);
		e.stopPropagation();
		e.preventDefault();
		switch (DOM.eventGetType(e)) {
		case Event.ONMOUSEUP:
			if (DOM.eventGetButton(e) == NativeEvent.BUTTON_LEFT) {
				GWT.log("Event.BUTTON_LEFT", null);
				listener.onClick(this, e);
			}
			
			if (DOM.eventGetButton(e) == NativeEvent.BUTTON_RIGHT) GWT.log("Event.BUTTON_RIGHT", null);
			break;
		
		case Event.ONCONTEXTMENU:
			GWT.log("Event.ONCONTEXTMENU", null);
			listener.onRightClick(this, e);
			break;
		
		default:
			super.onBrowserEvent(e);
		}// end switch
	}
	
	@Override
	public void addClickListener(AdvClickListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void removeClickListener(AdvClickListener listener) {
		this.listener = null;
	}
}