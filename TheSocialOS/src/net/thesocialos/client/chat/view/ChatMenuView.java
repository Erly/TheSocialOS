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
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ChatMenuView extends Composite implements Display {
	
	interface ChatMenuViewUiBinder extends UiBinder<Widget, ChatMenuView> {
	}
	
	private static ChatMenuViewUiBinder uiBinder = GWT.create(ChatMenuViewUiBinder.class);
	@UiField(provided = true) CellList<User> cellList = new CellList<User>(new AbstractCell<User>() {
		@Override
		public void render(Context context, User value, SafeHtmlBuilder sb) {
			if (value == null) return;
			switch (value.chatState) {
			case ONLINE:
				sb.appendHtmlConstant("<table class='chat_state_online'  width= '100%'>");
				break;
			case BUSY:
				sb.appendHtmlConstant("<table class='chat_state_busy'  width= '100%'>");
				break;
			case AFK:
				sb.appendHtmlConstant("<table class='chat_state_away'  width= '100%'>");
				break;
			case OFFLINE:
				sb.appendHtmlConstant("<table class='chat_state_offline'  width= '100%'>");
				break;
			default:
				break;
			}
			
			// Add the contact image.
			sb.appendHtmlConstant("<tr><td rowspan='3'>");
			sb.appendHtmlConstant("<img src='./images/anonymous_avatar.png' width='30' height='35' />");
			sb.appendHtmlConstant("</td>");
			
			// Add the name and address.
			sb.appendHtmlConstant("<td style='font-size:95%;' align='left'>");
			sb.appendEscaped(value.chatState.toString());
			sb.appendHtmlConstant("</td></tr><tr><td>");
			sb.appendEscaped(value.getName() + " " + value.getLastName());
			
			sb.appendHtmlConstant("</td></tr></table>");
		}
	});
	@UiField HorizontalPanel ConversationsPanel;
	
	@UiField Label lblState;
	@UiField Button button;
	@UiField Label lblName;
	@UiField Label lblSurname;
	@UiField HTMLPanel htmlState;
	@UiField FocusPanel FocusPanelState;
	
	public ChatMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
		// com.google.gwt.user.client.Element element = asWidget().getElement();
		// ListChatBlocks chatBlocks = new ListChatBlocks();
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
	
	@Override
	public Label getName() {
		// TODO Auto-generated method stub
		return lblName;
	}
	
	@Override
	public Label getSurname() {
		// TODO Auto-generated method stub
		return lblSurname;
	}
	
	@Override
	public Label getStateLabel() {
		// TODO Auto-generated method stub
		return lblState;
	}
	
	@Override
	public FocusPanel getStateFocus() {
		// TODO Auto-generated method stub
		return FocusPanelState;
	}
	
	@Override
	public HTMLPanel getStateCircle() {
		// TODO Auto-generated method stub
		return htmlState;
	}
}