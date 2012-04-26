package net.thesocialos.client.desktop.window;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Menu extends HorizontalPanel {
	
	private button btnMin = new button();
	
	private button btnMax = new button();
	
	private button btnClose = new button();
	
	public Menu() {
		
		setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		setStyleName("window-menu");
		setBorderWidth(0);
		
		setSize("72px", "26px");
		
		btnMin.setImgSrc("/themes/default/min-btn.gif");
		btnMin.setStyleName("window-menu-btn-left");
		// btnMin.addStyleName("window-menu-btn-left:hover");
		// btnMin.setStyleOver("window-menu-min-on","/themes/default/min-btn-on.gif");
		add(btnMin);
		btnMin.setSize("24px", "26px");
		
		btnMax.setImgSrc("/themes/default/max-btn.gif");
		btnMax.setStyleName("window-menu-btn");
		// btnMax.addStyleName("window-menu-btn:hover");
		add(btnMax);
		btnMax.setSize("24px", "26px");
		
		btnClose.setImgSrc("/themes/default/close-btn.gif");
		btnClose.setStyleName("window-menu-btn");
		// btnClose.addStyleName("window-menu-btn:hover");
		add(btnClose);
		btnClose.setSize("24px", "26px");
		setStyleName("window-menu");
	}
	
	public button getBtnClose() {
		return btnClose;
	}
	public button getBtnMax() {
		return btnMax;
	}
	
	public button getBtnMin() {
		return btnMin;
	}
	
}
