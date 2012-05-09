package net.thesocialos.client.chat.view;

import java.util.Date;

import net.thesocialos.client.chat.ChatConversationPresenter.Display;
import net.thesocialos.shared.model.Lines;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
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
			if (value == null) return;
			DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM | HH:mm");
			Date date = new Date(value.getDate());
			
			if (value.getUserOwner() != null) {
				/*
				 * sb.appendHtmlConstant("<table  align='left'>"); sb.appendHtmlConstant("<tr><td>");
				 * sb.appendHtmlConstant("<div class='speech-1'> " + value.getText() + "<div class='speech-1a'> " +
				 * fmt.format(date, com.google.gwt.i18n.client.TimeZone.createTimeZone(60)) + "</div>  </div>");
				 * sb.appendHtmlConstant("</td></tr></table>");
				 */
				sb.appendHtmlConstant("<table  align='right'>");
				sb.appendHtmlConstant("	<tr>");
				sb.appendHtmlConstant("	<td><div class='speech-2'>");
				
				sb.appendEscapedLines(value.getText());
				sb.appendHtmlConstant("<div class='speech-2a'> " + fmt.format(date) + "</div> </div>  </td>");
				sb.appendHtmlConstant("</tr>");
				sb.appendHtmlConstant("</table>");
			} else {
				/*
				 * sb.appendHtmlConstant("<table  align='right'>"); sb.appendHtmlConstant("<tr><td>");
				 * sb.appendHtmlConstant("<div class='speech-2'> " + value.getText() + "<div class='speech-2a'> " +
				 * fmt.format(date, com.google.gwt.i18n.client.TimeZone.createTimeZone(60)) + "</div> </div>");
				 * sb.appendHtmlConstant("</td></tr></table>");
				 */
				/*
				 * sb.appendHtmlConstant("<table  align='left'>"); sb.appendHtmlConstant("	<tr>");
				 * sb.appendHtmlConstant(
				 * "	<td><div class='speech-2'>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis,"
				 * ); sb.appendHtmlConstant(
				 * " ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim.<div class='speech-2a'>15 Mar / 22:40</div> </div>  </td>"
				 * ); sb.appendHtmlConstant("</tr>"); sb.appendHtmlConstant("</table>");
				 */
				sb.appendHtmlConstant("<table  align='left'>");
				sb.appendHtmlConstant("	<tr>");
				sb.appendHtmlConstant("	<td><div class='speech-1'>");
				
				sb.appendEscapedLines(value.getText());
				sb.appendHtmlConstant("<div class='speech-1a'> " + fmt.format(date) + "</div> </div>  </td>");
				sb.appendHtmlConstant("</tr>");
				sb.appendHtmlConstant("</table>");
				
			}
			sb.appendHtmlConstant("<br /><br /><br /><br />");
			
		}
	});
	@UiField TextArea lblTextToSend;
	@UiField ScrollPanel scroll;
	@UiField Label lblSize;
	
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
	
	@Override
	public ScrollPanel getScrollPanel() {
		// TODO Auto-generated method stub
		return scroll;
	}
	
	@Override
	public Label lblCharacters() {
		// TODO Auto-generated method stub
		return lblSize;
	}
	
}
