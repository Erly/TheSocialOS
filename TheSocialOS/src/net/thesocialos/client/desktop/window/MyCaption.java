package net.thesocialos.client.desktop.window;

import net.thesocialos.client.desktop.window.events.WindowCloseEvent;
import net.thesocialos.client.desktop.window.events.WindowMaximizeEvent;
import net.thesocialos.client.desktop.window.events.WindowMinimizeEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.WindowPanelLayout.Caption;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class MyCaption extends SimplePanel implements Caption {
	
	HorizontalPanel panel = new HorizontalPanel();
	
	Label label = new Label();
	Menu menu = new Menu();
	SimpleEventBus windowEventBus;
	
	HandlerRegistration reg;
	
	public MyCaption() {
		setStyleName("Caption");
		
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		panel.setSize("100%", "100%");
		this.add(panel);
		
		label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		// fPanel.setSize("100%", "100%");
		
		// fPanel.add(label);
		panel.add(label);
		label.setSize("100%", "15px");
		panel.setCellWidth(label, "100%");
		panel.setCellVerticalAlignment(label, HasVerticalAlignment.ALIGN_MIDDLE);
		// panel.setCellHorizontalAlignment(fPanel, HasHorizontalAlignment.ALIGN_CENTER);
		// panel.setCellVerticalAlignment(fPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		
		panel.add(menu);
		menu.setWidth("1");
		panel.setCellHorizontalAlignment(menu, HasHorizontalAlignment.ALIGN_RIGHT);
		
		menu.setWidth("1");
		menu.getBtnMin().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				windowEventBus.fireEvent(new WindowMinimizeEvent());
			}
		});
		
		menu.getBtnMax().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				windowEventBus.fireEvent(new WindowMaximizeEvent());
			}
		});
		
		menu.getBtnClose().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				windowEventBus.fireEvent(new WindowCloseEvent());
				
			}
		});
		
	}
	
	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		// TODO Auto-generated method stub
		// return addMouseDownHandler(handler);//fPanel.addMouseDownHandler(handler);
		return label.addMouseDownHandler(handler);
	}
	
	@Override
	public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
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
		return null;
	}
	
	@Override
	public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Add a window EventBus
	 * 
	 * @param windowEventBus
	 */
	@Override
	public void addWindowEventBus(SimpleEventBus windowEventBus) {
		this.windowEventBus = windowEventBus;
	}
	
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.getOffsetHeight();
	}
	
	@Override
	public String getHTML() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getText() {
		return label.getText();
	}
	
	@Override
	public void setHTML(SafeHtml html) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setHTML(String html) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setText(String text) {
		label.setText(text);
	}
}
