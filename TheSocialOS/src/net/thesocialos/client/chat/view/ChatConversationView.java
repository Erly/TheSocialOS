package net.thesocialos.client.chat.view;

import net.thesocialos.client.chat.ChatConversationPresenter.Display;
import net.thesocialos.shared.model.Lines;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ChatConversationView extends Composite implements Display {
	
	interface PanelUiBinder extends UiBinder<Widget, ChatConversationView> {
	}
	
	private static PanelUiBinder uiBinder = GWT.create(PanelUiBinder.class);
	@UiField Button btnSend;
	@UiField(provided = true) CellList<Lines> cellList = new CellList<Lines>(new AbstractCell<Lines>() {
		@Override
		public void render(Context context, Lines value, SafeHtmlBuilder sb) {
			// TODO
		}
	});
	@UiField TextArea lblTextToSend;
	
	public ChatConversationView() {
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	public ChatConversationView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	@Override
	public ChatConversationView getChatPanel() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public Button getSendButton() {
		// TODO Auto-generated method stub
		return btnSend;
	}
	
	@Override
	public TextArea getSendText() {
		// TODO Auto-generated method stub
		return lblTextToSend;
	}
	
	@Override
	public CellList<Lines> getConversation() {
		// TODO Auto-generated method stub
		return cellList;
	}
	
}
