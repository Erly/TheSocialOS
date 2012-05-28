package net.thesocialos.client.presenter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.desktop.IsTypeInfo;
import net.thesocialos.client.helper.SearchArrayList;
import net.thesocialos.client.view.AccountText;
import net.thesocialos.client.view.LabelText;
import net.thesocialos.shared.model.Account;
import net.thesocialos.shared.model.Facebook;
import net.thesocialos.shared.model.FlickR;
import net.thesocialos.shared.model.Google;
import net.thesocialos.shared.model.Group;
import net.thesocialos.shared.model.Twitter;
import net.thesocialos.shared.model.User;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class ContactsPresenter extends DesktopUnit implements IsTypeInfo {
	
	public interface Display {
		
		Widget asWidget();
		
		Button GetBtnUserPrivateMessage();
		
		Button GetBtnUserSearchPrivateMessage();
		
		LabelText getContactName();
		
		// Users
		
		LabelText getContactSearchName();
		
		LabelText getContactSearchSurname();
		
		LabelText getContactSurname();
		
		TextArea getBio();
		
		HorizontalPanel getContatcsMenu();
		
		CellList<Group> getGroupListBox();
		
		Label getGroupName();
		
		Label getGroupSearchName();
		
		Label getGroupSize();
		
		Label GetGroupSizeCount();
		
		Label GetGroupSizeCountSearch();
		
		Label GetGroupSizeSearch();
		
		DecoratedTabPanel getGroupUsersPanel();
		
		Image getImageFriend();
		
		Image getImageGroup();
		
		Image getImageSearchContact();
		
		Image getImageSearchGroup();
		
		TextBox getSearchBox();
		
		CellList<User> getUserListBox();
		
		AccountText getAccountFlickR();
		
		AccountText getAccountFacebook();
		
		AccountText getAccountTwitter();
		
		AccountText getAccountGoogle();
		
	}
	
	Display display;
	List<String> ListContacts;
	
	List<String> ListGroups;
	/*
	 * Los modelos de la cajas de seleccion de los contactos
	 */
	ProvidesKey<User> KEY_PROVIDER;
	SingleSelectionModel<User> contactSelectionModel;
	ListDataProvider<User> contactDataProvider;
	
	SearchArrayList contactList;
	
	public ContactsPresenter(Display display) {
		super(AppConstants.CONTACTS, "Contacts", null, TypeUnit.INFO, false);
		this.display = display;
		KEY_PROVIDER = new ProvidesKey<User>() {
			@Override
			public Object getKey(User item) {
				return item == null ? null : item.getEmail();
			}
		};
		contactSelectionModel = new SingleSelectionModel<User>(KEY_PROVIDER);
		
	}
	
	/*
	 * Rpc Secction
	 */
	
	@Override
	public void close(AbsolutePanel absolutepanel) {
		absolutepanel.remove(display.asWidget());
		
	}
	
	@Override
	public int getWidth() {
		
		return display.asWidget().getOffsetWidth();
		
	}
	
	@Override
	public int getHeight() {
		
		return display.asWidget().getOffsetHeight();
	}
	
	@Override
	public int getAbsoluteLeft() {
		
		return display.asWidget().getAbsoluteLeft();
	}
	
	@Override
	public int getAbsoluteTop() {
		
		return display.asWidget().getAbsoluteTop();
	}
	
	public HorizontalPanel getContactsPresenter() {
		return display.getContatcsMenu();
	}
	
	private void getFriends() {
		
		// Indica la variable que ser� seleccionada como key
		
		// Crea una clase SingleSelectionModel y la inicializas con la key anterior
		
		// Le indicas a la cellList el tipo de seleccion que vas a usar
		display.getUserListBox().setSelectionModel(contactSelectionModel);
		contactList = new SearchArrayList();
		contactDataProvider = new ListDataProvider<User>(contactList);
		
		CacheLayer.ContactCalls.getContactsWithoutKey(false, new AsyncCallback<Map<String, User>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				contactDataProvider.addDataDisplay(display.getUserListBox());
				
			}
			
			@Override
			public void onSuccess(Map<String, User> result) {
				// TODO Auto-generated method stub
				Iterator<User> iterator = result.values().iterator();
				
				while (iterator.hasNext())
					contactList.add(iterator.next());
				contactDataProvider.addDataDisplay(display.getUserListBox());
				
			}
		});
		
		handlers();
		
	}
	
	@Override
	public int getZposition() {
		return 0;
	}
	
	private void handlers() {
		// Handler de la CellList de Contacts
		contactSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				display.getAccountFacebook().setAccounts(TheSocialOS.getConstants().empty());
				display.getAccountGoogle().setAccounts(TheSocialOS.getConstants().empty());
				display.getAccountTwitter().setAccounts(TheSocialOS.getConstants().empty());
				display.getAccountFacebook().setAccounts(TheSocialOS.getConstants().empty());
				display.getAccountGoogle().disableAccount();
				display.getAccountFlickR().disableAccount();
				display.getAccountTwitter().disableAccount();
				display.getContactName().setText(contactSelectionModel.getSelectedObject().getName());
				display.getContactSurname().setText(contactSelectionModel.getSelectedObject().getLastName());
				display.getBio().setText(contactSelectionModel.getSelectedObject().getBio());
				if (contactSelectionModel.getSelectedObject().getUrlAvatar() != null) display.getImageFriend().setUrl(
						contactSelectionModel.getSelectedObject().getUrlAvatar());
				else
					display.getImageFriend().setUrl("/images/anonymous_avatar.png");
				CacheLayer.ContactCalls.getContactAccounts(contactSelectionModel.getSelectedObject().getOwnKey(),
						false, new AsyncCallback<List<Account>>() {
							
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onSuccess(List<Account> result) {
								Iterator<Account> iterator = result.iterator();
								while (iterator.hasNext()) {
									Account account = iterator.next();
									if (account instanceof Facebook)
										display.getAccountFacebook().setAccounts(account.getUsername());
									if (account instanceof FlickR)
										display.getAccountFlickR().setAccounts(account.getUsername());
									if (account instanceof Google)
										display.getAccountGoogle().setAccounts(account.getUsername());
									if (account instanceof Twitter)
										display.getAccountTwitter().setAccounts(account.getUsername());
									
								}
								
							}
						});
			}
		});
		display.getSearchBox().addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
				
				/*
				 * new RPCXSRF<List<User>>(userService) {
				 * @Override protected void XSRFcallService(AsyncCallback<List<User>> cb) {
				 * userService.getFriendsSuggestionList(display.getSearchBox().getText(), cb); } public void
				 * onSuccess(List<User> returnUsers){ System.out.println(returnUsers.size()); } public void
				 * onFailure(Throwable caught){ } }.retry(3);
				 */
				/*
				 * CacheLayer.ContactCalls.getContactsWithoutKey(false, new AsyncCallback<Map<String, User>>() {
				 * @Override public void onFailure(Throwable caught) { // TODO Auto-generated method stub }
				 * @Override public void onSuccess(Map<String, User> result) { String text[] =
				 * display.getSearchBox().getText().split(" "); if (text != null && text.length > 0) { //
				 * display.getUserListBox(). contactList.clear(); Iterator<User> iterator = result.values().iterator();
				 * while (iterator.hasNext()) { User contact = iterator.next(); if (contact.getName().contains(text[0]))
				 * contactList.add(contact); } contactDataProvider.flush(); contactDataProvider.refresh(); } } });
				 */
				contactDataProvider.setList(contactList.getSearchUsers(display.getSearchBox().getText()));
				// System.out.println(lista.size());
				contactDataProvider.flush();
				contactDataProvider.refresh();
			}
		});
		
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		
		absolutePanel.add(display.asWidget());
		display.asWidget().setVisible(true);
		getFriends();
	}
	
	@Override
	public void setPosition(int x, int y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
		
	}
	
	@Override
	public void setSize(int height, int width) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void toBack() {
		
	}
	
	@Override
	public void toFront() {
		
	}
	
	@Override
	public void toZPosition(int position) {
		
	}
}
