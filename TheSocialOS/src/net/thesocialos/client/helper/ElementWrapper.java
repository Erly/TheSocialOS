package net.thesocialos.client.helper;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class ElementWrapper extends Widget implements HasClickHandlers {
	
	public ElementWrapper(Element theElement) {
		setElement(theElement);
	}
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}
	
	@Override
	public void onAttach() {
		super.onAttach();
	}
}
