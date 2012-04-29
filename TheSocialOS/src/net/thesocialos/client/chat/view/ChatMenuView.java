package net.thesocialos.client.chat.view;

import net.thesocialos.client.chat.ChatMenuPresenter.Display;
import net.thesocialos.client.helper.RPCXSRF;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.shared.ChannelApiEvents.ChApiContactNew;
import net.thesocialos.shared.model.User;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ChatMenuView extends Composite implements Display {
	
	interface ChatMenuViewUiBinder extends UiBinder<Widget, ChatMenuView> {
	}
	
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
	@UiField Button button;
	
	public ChatMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
		com.google.gwt.user.client.Element element = asWidget().getElement();
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
	public HorizontalPanel getConversationsPanel() {
		// TODO Auto-generated method stub
		return ConversationsPanel;
		
	}
	
	@Override
	public Label getEmail() {
		// TODO Auto-generated method stub
		return lblEmail;
	}
	
	@Override
	public Label getState() {
		// TODO Auto-generated method stub
		return lblState;
	}
	
	@UiHandler("button")
	void onButtonClick(ClickEvent event) {
		final UserServiceAsync userService = GWT.create(UserService.class);
		new RPCXSRF<Void>(userService) {
			
			@Override
			protected void XSRFcallService(AsyncCallback<Void> cb) {
				userService.checkChannel(new ChApiContactNew("topota"), cb);
				
			};
			
			@Override
			public void onSuccess(Void nada) {
				
			}
			
		}.retry(3);
		
	}
}