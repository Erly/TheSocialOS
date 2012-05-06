package net.thesocialos.client.chat.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TestChat extends Composite {
	
	private static TestChatUiBinder uiBinder = GWT.create(TestChatUiBinder.class);
	
	interface TestChatUiBinder extends UiBinder<Widget, TestChat> {
	}
	
	public TestChat() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
}
