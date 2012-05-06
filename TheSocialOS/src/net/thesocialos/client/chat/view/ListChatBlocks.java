package net.thesocialos.client.chat.view;

import net.thesocialos.client.chat.ListChatBlocksPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ListChatBlocks extends Composite implements Display {
	
	interface ListChatBlocksUiBinder extends UiBinder<Widget, ListChatBlocks> {
	}
	
	private static ListChatBlocksUiBinder uiBinder = GWT.create(ListChatBlocksUiBinder.class);
	
	public ListChatBlocks() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
}
