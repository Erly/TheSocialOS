package net.thesocialos.client.chat.view;

import net.thesocialos.client.chat.ListChatBlockPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ListChatBlocks extends Composite implements Display {
	
	interface ListChatBlocksUiBinder extends UiBinder<Widget, ListChatBlocks> {
	}
	
	private static ListChatBlocksUiBinder uiBinder = GWT.create(ListChatBlocksUiBinder.class);
	@UiField HorizontalPanel horizontalConversationsPanel;
	
	public ListChatBlocks() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public HorizontalPanel getHConverPanel() {
		// TODO Auto-generated method stub
		return horizontalConversationsPanel;
	}
	
}
