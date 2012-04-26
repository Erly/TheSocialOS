package net.thesocialos.client.chat.view;

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

public class ChatMenuView extends Composite {
	
	private static ChatMenuViewUiBinder uiBinder = GWT.create(ChatMenuViewUiBinder.class);
	
	interface ChatMenuViewUiBinder extends UiBinder<Widget, ChatMenuView> {
	}
	
	public ChatMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
}