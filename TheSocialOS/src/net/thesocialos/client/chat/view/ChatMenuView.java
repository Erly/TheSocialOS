package net.thesocialos.client.chat.view;

import net.thesocialos.client.chat.view.ChatMenuPresenter.Display;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ChatMenuView extends Composite implements Display {
	
	private static ChatMenuViewUiBinder uiBinder = GWT.create(ChatMenuViewUiBinder.class);
	
	@UiField Label lblEmail;
	@UiField(provided = true) CellList<User> cellList = new CellList<User>(new AbstractCell<User>() {
		@Override
		public void render(Context context, User value, SafeHtmlBuilder sb) {
			// TODO
		}
	});
	@UiField HorizontalPanel ConversationsPanel;
	@UiField Label lblState;
	
	interface ChatMenuViewUiBinder extends UiBinder<Widget, ChatMenuView> {
	}
	
	public ChatMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
		com.google.gwt.user.client.Element element = this.asWidget().getElement();
		ListChatBlocks chatBlocks = new ListChatBlocks();
		// chatBlocks.setVisible(true);
		// DOM.appendChild(element, chatBlocks.asWidget().getElement());
	}
	
	@Override
	public CellList<User> getCellContacts() {
		// TODO Auto-generated method stub
		return cellList;
	}
	
	@Override
	public Label getState() {
		// TODO Auto-generated method stub
		return lblState;
	}
	
	@Override
	public Label getEmail() {
		// TODO Auto-generated method stub
		return lblEmail;
	}
	
	@Override
	public HorizontalPanel getConversationsPanel() {
		// TODO Auto-generated method stub
		return ConversationsPanel;
		
	}
	
}