package net.thesocialos.client.view;

import net.thesocialos.client.presenter.SearchBoxPresenter.Display;
import net.thesocialos.shared.model.User;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SearchBoxView extends Composite implements Display {
	
	interface SearchBoxUiBinder extends UiBinder<Widget, SearchBoxView> {
	}
	
	private static SearchBoxUiBinder uiBinder = GWT.create(SearchBoxUiBinder.class);
	@UiField(provided = true) CellList<User> cellList = new CellList<User>(new AbstractCell<User>() {
		@Override
		public void render(Context context, User value, SafeHtmlBuilder sb) {
			
			if (value == null) return;
			
			sb.appendHtmlConstant("<table class='chat_state_offline'  width= '100%'>");
			
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
	
	@UiField Label lblFriends;
	@UiField Label lblGroups;
	@UiField VerticalPanel searchPanel;
	@UiField(provided = true) CellList<Object> cellList_1 = new CellList<Object>(new AbstractCell<Object>() {
		@Override
		public void render(Context context, Object value, SafeHtmlBuilder sb) {
			// TODO
		}
	});
	@UiField TextBox txtSearch;
	@UiField Label lblInvite;
	@UiField Label lblInfo;
	@UiField Image imgAvatar;
	
	@UiField StackLayoutPanel stackLayout;
	
	public SearchBoxView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Image getAvatarIMG() {
		// TODO Auto-generated method stub
		return imgAvatar;
	}
	
	@Override
	public CellList<User> getComponentsList() {
		// TODO Auto-generated method stub
		return cellList;
	}
	
	@Override
	public Label getLabelFriends() {
		// TODO Auto-generated method stub
		return lblFriends;
	}
	
	@Override
	public Label getLabelGroups() {
		// TODO Auto-generated method stub
		return lblGroups;
	}
	
	@Override
	public Label getLabelInfo() {
		// TODO Auto-generated method stub
		return lblInfo;
	}
	
	@Override
	public Label getLabelInvite() {
		// TODO Auto-generated method stub
		return lblInvite;
	}
	
	@Override
	public VerticalPanel getSearchBoxPanel() {
		// TODO Auto-generated method stub
		return searchPanel;
	}
	
	@Override
	public StackLayoutPanel getStackLayout() {
		// TODO Auto-generated method stub
		return stackLayout;
	}
	
	@Override
	public void setComponentsList(CellList<User> cellList) {
		// TODO Auto-generated method stub
		this.cellList = cellList;
	}
	
}
