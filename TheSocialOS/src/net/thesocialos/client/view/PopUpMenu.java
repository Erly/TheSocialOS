package net.thesocialos.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class PopUpMenu extends Composite{

	private static PopUpMenuUiBinder uiBinder = GWT
			.create(PopUpMenuUiBinder.class);

	interface PopUpMenuUiBinder extends UiBinder<Widget, PopUpMenu> {
	}

	public PopUpMenu() {
		initWidget(uiBinder.createAndBindUi(this));
	}



	public PopUpMenu(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

	}





}
