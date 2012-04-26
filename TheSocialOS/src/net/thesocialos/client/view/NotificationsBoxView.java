package net.thesocialos.client.view;

import net.thesocialos.client.presenter.NotificationsBoxPresenter.Display;
import net.thesocialos.shared.model.Group;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.StackLayoutPanel;

public class NotificationsBoxView extends Composite implements Display {
	
	interface NotificationsBoxUiBinder extends UiBinder<Widget, NotificationsBoxView> {
	}
	private static NotificationsBoxUiBinder uiBinder = GWT.create(NotificationsBoxUiBinder.class);
	@UiField(provided = true) CellList<User> cellListFriends = new CellList<User>(new AbstractCell<User>() {
		@Override
		public void render(Context context, User value, SafeHtmlBuilder sb) {
			
			if (value == null) { return; }
			sb.appendHtmlConstant("<table>");
			
			// Add the contact image.
			sb.appendHtmlConstant("<tr><td rowspan='3'>");
			sb.appendHtmlConstant("<img src='./images/anonymous_avatar.png' width='30' height='35' />");
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
	@UiField LabelText labelTextGroups;
	@UiField(provided = true) CellList<Group> cellListGroups = new CellList<Group>(new AbstractCell<Group>() {
		@Override
		public void render(Context context, Group value, SafeHtmlBuilder sb) {
			// TODO
		}
	});
	
	@UiField StackLayoutPanel stackLayoutPanel;
	
	public NotificationsBoxView() {
		initWidget(uiBinder.createAndBindUi(this));
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
		return labelTextGroups;
	}
	
	@Override
	public StackLayoutPanel getStackLayoutPanel() {
		// TODO Auto-generated method stub
		return stackLayoutPanel;
	}
	
}
