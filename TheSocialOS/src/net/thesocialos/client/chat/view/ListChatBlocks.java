package net.thesocialos.client.chat.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ListChatBlocks extends Composite {
	
	private static ListChatBlocksUiBinder uiBinder = GWT.create(ListChatBlocksUiBinder.class);
	
	interface ListChatBlocksUiBinder extends UiBinder<Widget, ListChatBlocks> {
	}
	
	public ListChatBlocks() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
}
