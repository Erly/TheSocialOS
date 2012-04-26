package net.thesocialos.client.desktop.window;

import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class Footer extends SimplePanel implements com.google.gwt.user.client.ui.WindowPanelLayout.Footer {
	
	HorizontalPanel horizontalPanel = new HorizontalPanel();
	FocusPanel fPanelResize = new FocusPanel();
	SimpleEventBus windowEventBus;
	HandlerRegistration reg;
	Label lblTitle = new Label();
	Menu menu = new Menu();
	
	public Footer() {
		setStyleName("Footer");
		this.add(horizontalPanel);
		// this.windowEventBus = windowEventBus;
		FocusPanel focusPanel = new FocusPanel();
		horizontalPanel.setWidth("100%");
		focusPanel.setWidth("100%");
		focusPanel.add(lblTitle);
		horizontalPanel.add(focusPanel);
		fPanelResize.setStyleName("Footer-resize");
		
		fPanelResize.setWidth("15px");
		fPanelResize.setHeight("15px");
		Image image = new Image("/themes/default/border_resize.png");
		image.setSize("15px", "15px");
		fPanelResize.add(image);
		
		horizontalPanel.add(fPanelResize);
		horizontalPanel.setCellHorizontalAlignment(fPanelResize, HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel.setCellVerticalAlignment(fPanelResize, HasVerticalAlignment.ALIGN_BOTTOM);
		
	}
	
	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		// TODO Auto-generated method stub
		return fPanelResize.addMouseDownHandler(handler);
	}
	
	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		// TODO Auto-generated method stub
		return addDomHandler(handler, MouseOverEvent.getType());
	}
	
	@Override
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getHTML() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Sets the html string inside this class
	 * 
	 * Use {@link #setWidget(Widget)} to set the contents inside the {@link DialogBox}.
	 * 
	 * @param html
	 *            the object's new HTML
	 */
	@Override
	public void setHTML(String html) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Get the title of the footer
	 * 
	 * @return The String representation of the text
	 */
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Set the title of the footer
	 * 
	 * @param The
	 *            String representation of the text
	 */
	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setHTML(SafeHtml html) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return getOffsetHeight();
	}
	
}
