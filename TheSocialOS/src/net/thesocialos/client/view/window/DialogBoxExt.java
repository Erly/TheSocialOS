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
//	private Caption caption;

	public DialogBoxExt(boolean autoHide, boolean modal,
			Caption captionWidget) {
		super(autoHide, modal,captionWidget);
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
	protected void beginDragging(MouseDownEvent event) {
		
		
	}
	
	protected void continueDragging(MouseMoveEvent event) {
	
		 onMouseMove(caption2.asWidget(), event.getX(), event.getY());
	  }
	
	protected void endDragging(MouseUpEvent event) {
		TheSocialOS.get().getDesktop().remove(panel);

		onMouseUp(caption2.asWidget(), event.getX(), event.getY());
		
		if (this.getAbsoluteLeft() < 0) {
			this.setPopupPosition(0, this.getAbsoluteTop());
		}
		if (this.getAbsoluteLeft() + this.getOffsetWidth() > Window.getClientWidth()) {
			this.setPopupPosition(Window.getClientWidth() - this.getOffsetWidth(), this.getAbsoluteTop());
		}
		if (this.getAbsoluteTop() + this.getOffsetHeight() > Window.getClientHeight()) {
			this.setPopupPosition(this.getAbsoluteLeft(), Window.getClientHeight() - this.getOffsetHeight());
		}
		if (this.getAbsoluteTop() < 30) {
			this.setPopupPosition(this.getAbsoluteLeft(), 30);
		}
	}
	
	 
}
