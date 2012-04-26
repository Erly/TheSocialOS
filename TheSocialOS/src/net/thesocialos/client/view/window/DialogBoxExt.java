package net.thesocialos.client.view.window;

import net.thesocialos.client.TheSocialOS;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.SimplePanel;

public class DialogBoxExt extends DialogBox {
	
	private static final String DEFAULT_STYLENAME = "sos-Window";
	SimplePanel panel = new SimplePanel();
	MyCaption caption2;
	
	// private Caption caption;
	
	public DialogBoxExt(boolean autoHide, boolean modal, Caption captionWidget) {
		super(autoHide, modal, captionWidget);
		setStyleName(DEFAULT_STYLENAME); // Set Style
		panel.setStyleName("invisiblePanel");
		caption2 = (MyCaption) getCaption().asWidget();
		
		caption2.fPanel.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				beginD(event);
			}
		});
		
	}
	
	private void beginD(MouseDownEvent event) {
		TheSocialOS.get().getDesktop().add(panel);
		onMouseDown(caption2.asWidget(), event.getX(), event.getY());
	}
	
	/**
	 * 
	 */
	@Override
	protected void beginDragging(MouseDownEvent event) {
		
	}
	
	@Override
	protected void continueDragging(MouseMoveEvent event) {
		onMouseMove(caption2.asWidget(), event.getX(), event.getY());
	}
	
	@Override
	protected void endDragging(MouseUpEvent event) {
		TheSocialOS.get().getDesktop().remove(panel);
		
		onMouseUp(caption2.asWidget(), event.getX(), event.getY());
		
		if (getAbsoluteLeft() < 0) setPopupPosition(0, getAbsoluteTop());
		if (getAbsoluteLeft() + getOffsetWidth() > Window.getClientWidth())
			setPopupPosition(Window.getClientWidth() - getOffsetWidth(), getAbsoluteTop());
		if (getAbsoluteTop() + getOffsetHeight() > Window.getClientHeight())
			setPopupPosition(getAbsoluteLeft(), Window.getClientHeight() - getOffsetHeight());
		if (getAbsoluteTop() < 30) setPopupPosition(getAbsoluteLeft(), 30);
	}
	
}
