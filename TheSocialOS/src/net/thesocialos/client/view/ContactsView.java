package net.thesocialos.client.view;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.presenter.ContactsPresenter.Display;
import net.thesocialos.shared.model.Group;
import net.thesocialos.shared.model.User;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ContactsView extends Composite implements Display {
	
	interface ContactsUiBinder extends UiBinder<Widget, ContactsView> {
	}
	
	private static ContactsUiBinder uiBinder = GWT.create(ContactsUiBinder.class);
	@UiField LabelText lblName;
	@UiField LabelText lblSurname;
	@UiField TextArea lblFriendBio;
	@UiField AccountText accountGoogle;
	@UiField AccountText accountFacebook;
	@UiField AccountText accountFlickR;
	@UiField AccountText accountTwitter;
	@UiField Image imgFriend;
	@UiField DecoratedTabPanel dTPUserGroups;
	@UiField HorizontalPanel ContactsMenu;
	@UiField TextBox txtSearch;
	
	@UiField(provided = true) CellList<User> listContacts = new CellList<User>(new AbstractCell<User>() {
		@Override
		public void render(Context context, User value, SafeHtmlBuilder sb) {
			// Value can be null, so do a null check..
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
	@UiField Label lblAccounts;
	@UiField HTMLPanel htmlPanel;
	
	public ContactsView() {
		
		initWidget(uiBinder.createAndBindUi(this));
		dTPUserGroups.selectTab(0);
		// Set a key provider that provides a unique key for each contact. If key is
		// used to identify contacts when fields (such as the name and address)
		// change.
		
		listContacts.setPageSize(30);
		listContacts.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		listContacts.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		// Add a selection model so we can select cells.
		// final SingleSelectionModel<User> selectionModel = new SingleSelectionModel<User>(
		// ContactDatabase.ContactInfo.KEY_PROVIDER);
		// cellList.setSelectionModel(selectionModel);
		// selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
		// public void onSelectionChange(SelectionChangeEvent event) {
		// contactForm.setContact(selectionModel.getSelectedObject());
		// }
		// });
		lblName.lblLabel.setText(TheSocialOS.getConstants().name());
		lblSurname.lblLabel.setText(TheSocialOS.getConstants().lastName());
		lblAccounts.setText(TheSocialOS.getConstants().accounts());
		listContacts.setTitle(TheSocialOS.getConstants().contacts());
		
	}
	
	@Override
	public Button GetBtnUserPrivateMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Button GetBtnUserSearchPrivateMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public LabelText getContactName() {
		// TODO Auto-generated method stub
		return lblName;
	}
	
	@Override
	public LabelText getContactSearchName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public LabelText getContactSearchSurname() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public LabelText getContactSurname() {
		// TODO Auto-generated method stub
		return lblSurname;
	}
	
	@Override
	public HorizontalPanel getContatcsMenu() {
		// TODO Auto-generated method stub
		return ContactsMenu;
	}
	
	@Override
	public CellList<Group> getGroupListBox() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Label getGroupName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Label getGroupSearchName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Label getGroupSize() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Label GetGroupSizeCount() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Label GetGroupSizeCountSearch() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Label GetGroupSizeSearch() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public DecoratedTabPanel getGroupUsersPanel() {
		
		return dTPUserGroups;
	}
	
	@Override
	public Image getImageFriend() {
		// TODO Auto-generated method stub
		return imgFriend;
	}
	
	@Override
	public Image getImageGroup() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Image getImageSearchContact() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Image getImageSearchGroup() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TextBox getSearchBox() {
		// TODO Auto-generated method stub
		return txtSearch;
	}
	
	@Override
	public CellList<User> getUserListBox() {
		
		return listContacts;
	}
	
	@Override
	public TextArea getBio() {
		// TODO Auto-generated method stub
		return lblFriendBio;
	}
	
	@Override
	public AccountText getAccountFlickR() {
		// TODO Auto-generated method stub
		return accountFlickR;
	}
	
	@Override
	public AccountText getAccountFacebook() {
		// TODO Auto-generated method stub
		return accountFacebook;
	}
	
	@Override
	public AccountText getAccountTwitter() {
		// TODO Auto-generated method stub
		return accountTwitter;
	}
	
	@Override
	public AccountText getAccountGoogle() {
		// TODO Auto-generated method stub
		return accountGoogle;
	}
	
	@Override
	public HTMLPanel getHtmlPanel() {
		// TODO Auto-generated method stub
		return htmlPanel;
	}
	
}
