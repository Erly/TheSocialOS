package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.presenter.NotificationsBoxPresenter.Display;
import net.thesocialos.shared.model.User;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NotificationsBoxView extends Composite implements Display {
	
	interface NotificationsBoxUiBinder extends UiBinder<Widget, NotificationsBoxView> {
	}
	
	private static NotificationsBoxUiBinder uiBinder = GWT.create(NotificationsBoxUiBinder.class);
	@UiField(provided = true) CellList<User> cellListFriends = new CellList<User>(new AbstractCell<User>() {
		@Override
		public void render(Context context, User value, SafeHtmlBuilder sb) {
			
			if (value == null) return;
			sb.appendHtmlConstant("<table>");
			
			// Add the contact image.
			sb.appendHtmlConstant("<tr><td rowspan='3'>");
			if (value.getUrlAvatar() == null) sb
					.appendHtmlConstant("<img src='./images/anonymous_avatar.png' width='30' height='35' />");
			else
				sb.appendHtmlConstant("<img src=" + value.getUrlAvatar() + " width='30' height='35' />");
			sb.appendHtmlConstant("</td>");
			
			// Add the name and address.
			sb.appendHtmlConstant("<td style='font-size:95%;'>");
			sb.appendEscaped(value.getName() + " " + value.getLastName());
			sb.appendHtmlConstant("</td></tr><tr><td>");
			sb.appendEscaped(value.getEmail());
			sb.appendHtmlConstant("</td></tr></table>");
		}
	});
	@UiField LabelText labelTextFriends;
	
	@UiField StackLayoutPanel stackLayoutPanel;
	@UiField VerticalPanel panel;
	
	public NotificationsBoxView() {
		initWidget(uiBinder.createAndBindUi(this));
		labelTextFriends.lblLabel.setText(TheSocialOS.getConstants().contacts());
		cellListFriends.setTitle(TheSocialOS.getConstants().contacts());
		stackLayoutPanel.setHeaderHTML(0, TheSocialOS.getConstants().contacts());
	}
	
	@Override
	public CellList<User> getContactsCellList() {
		// TODO Auto-generated method stub
		return cellListFriends;
	}
	
	@Override
	public LabelText getContactsLabelText() {
		// TODO Auto-generated method stub
		return labelTextFriends;
	}
	
	@Override
	public LabelText getGroupsLabelText() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public StackLayoutPanel getStackLayoutPanel() {
		// TODO Auto-generated method stub
		return stackLayoutPanel;
	}
	
	@Override
	public VerticalPanel getPanel() {
		// TODO Auto-generated method stub
		return panel;
	}
	
}
