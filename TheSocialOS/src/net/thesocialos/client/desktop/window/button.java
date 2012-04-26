package net.thesocialos.client.desktop.window;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

public class button extends Button {
	Image img;
	String imgOffDir;
	String imgOnDir;
	String styleDefault;
	
	public button() {
		super();
	}
	
	public void setImgSrc(String imgSrc) {
		imgOffDir = imgSrc;
		img = new Image(imgSrc);
		String definedStyles = img.getElement().getAttribute("style");
		img.getElement().setAttribute("style", definedStyles + "; vertical-align:top;");
		DOM.insertBefore(getElement(), img.getElement(), DOM.getFirstChild(getElement()));
	}
	
	@Override
	public void setText(String text) {
		com.google.gwt.user.client.Element span = DOM.createElement("span");
		span.setInnerText(text);
		
		span.getStyle().setPaddingLeft(5, Unit.PX);
		span.getStyle().setPaddingRight(3, Unit.PX);
		span.getStyle().setVerticalAlign(VerticalAlign.MIDDLE);
		span.getStyle().setColor("black");
		
		span.setAttribute("class", "arial12R6D6D6D");
		DOM.insertChild(getElement(), span, 0);
	}
}