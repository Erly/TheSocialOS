package net.thesocialos.client.view.window;

import net.thesocialos.client.TheSocialOS;

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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.DialogBox.Caption;

public class MyCaption extends SimplePanel implements Caption {
	
	HorizontalPanel panel = new HorizontalPanel();
	FocusPanel fPanel = new FocusPanel();
	Label label = new Label();
	Menu menu = new Menu();
	
	HandlerRegistration reg;
	
	public MyCaption() {
		setStyleName("Caption");
		panel.setWidth("100%");
		this.add(panel);
		fPanel.setWidth("100%");
		fPanel.add(label);
		panel.add(fPanel);
		panel.setCellWidth(fPanel, "100%");
		panel.setCellHorizontalAlignment(fPanel, HasHorizontalAlignment.ALIGN_CENTER);
		panel.setCellVerticalAlignment(fPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		
		panel.add(menu);
		menu.setWidth("1");
		panel.setCellHorizontalAlignment(menu, HasHorizontalAlignment.ALIGN_RIGHT);
		
		final PopupPanel errorWindow = new PopupPanel(false, true);
		errorWindow.setGlassEnabled(true);
		VerticalPanel vPanel = new VerticalPanel();
		Label errorMessage = new Label("This functionality is not implemented yet.");
		final Button closeButton = new Button("Close");
		vPanel.add(errorMessage);
		vPanel.add(closeButton);
		vPanel.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_CENTER);
		closeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				((PopupPanel) closeButton.getParent().getParent()).hide();
			}
		});
		errorWindow.add(vPanel);
		
		menu.getBtnMin().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				errorWindow.center();
			}
		});
		
		menu.getBtnMax().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				errorWindow.center();
			}
		});
		
		menu.getBtnClose().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				((DialogBoxExt) MyCaption.this.getParent()).hide();
				
			}
		});
		
	}
	
	@Override
	public String getHTML() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setHTML(String html) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getText() {
		return label.getText();
	}
	
	@Override
	public void setText(String text) {
		label.setText(text);
	}
	
	@Override
	public void setHTML(SafeHtml html) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
		// TODO Auto-generated method stub
		return null;
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
		return null;
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
}
