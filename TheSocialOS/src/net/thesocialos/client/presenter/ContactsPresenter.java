package net.thesocialos.client.presenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.service.UserService;
import net.thesocialos.client.service.UserServiceAsync;
import net.thesocialos.client.view.LabelText;

import net.thesocialos.shared.model.Group;
import net.thesocialos.shared.model.User;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.googlecode.objectify.Key;

public class ContactsPresenter extends DesktopUnit {
	
	private final UserServiceAsync userService = GWT.create(UserService.class);
	
	Display display;
	
	List<String> ListContacts;
	List<String> ListGroups;
	
	/*
	 * Los modelos de la cajas de seleccion de los contactos
	 */
	ProvidesKey<User> KEY_PROVIDER;
	SingleSelectionModel<User> contactSelectionModel;
	ListDataProvider<User> contactDataProvider;
	List<User> contactList;
	
	public interface Display {
		
		DecoratedTabPanel getGroupUsersPanel();
		
		CellList<User> getUserListBox();
		
		CellList<Group> getGroupListBox();
		
		TextBox getSearchBox();
		
		// Users
		
		Image getImageFriend();
		
		Image getImageGroup();
		
		Image getImageSearchContact();
		
		Image getImageSearchGroup();
		
		LabelText getContactName();
		
		LabelText getContactSurname();
		
		LabelText getContactSearchName();
		
		LabelText getContactSearchSurname();
		
		Label getGroupName();
		
		Label getGroupSearchName();
		
		Label getGroupSize();
		
		Label GetGroupSizeCount();
		
		Label GetGroupSizeSearch();
		
		Label GetGroupSizeCountSearch();
		
		Button GetBtnUserPrivateMessage();
		
		Button GetBtnUserSearchPrivateMessage();
		
		HorizontalPanel getContatcsMenu();
		
		Widget asWidget();
	}
	
	public ContactsPresenter(Display display) {
		programID = AppConstants.CONTACTS;
		typeUnit = TypeUnit.INFO;
		this.display = display;
		KEY_PROVIDER = new ProvidesKey<User>() {
			public Object getKey(User item) {
				return item == null ? null : item.getEmail();
			}
		};
		contactSelectionModel = new SingleSelectionModel<User>(KEY_PROVIDER);
		
	}
	
	/*
	 * Rpc Secction
	 */
	
	private void getFriends() {
		
		// Indica la variable que ser� seleccionada como key
		
		// Crea una clase SingleSelectionModel y la inicializas con la key anterior
		
		// Le indicas a la cellList el tipo de seleccion que vas a usar
		display.getUserListBox().setSelectionModel(contactSelectionModel);
		contactList = new ArrayList<User>();
		contactDataProvider = new ListDataProvider<User>(contactList);
		
		CacheLayer.ContactCalls.getContacts(false, new AsyncCallback<Map<Key<User>, User>>() {
			
			@Override
			public void onSuccess(Map<Key<User>, User> result) {
				// TODO Auto-generated method stub
				Iterator<User> iterator = result.values().iterator();
				
				while (iterator.hasNext()) {
					
					contactList.add(iterator.next());
					System.out.println(contactList.get(0).getName());
				}
				contactDataProvider.addDataDisplay(display.getUserListBox());
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				contactList.add(new User("vssnake@thesocialos.net", null, null, "juan", "palomo", "ni uno"));
				contactList.add(new User("vssnake1@thesocialos.net", null, null, "juan", "palomo", "ni uno"));
				contactList.add(new User("vssnake2@thesocialos.net", null, null, "juan", "palomo", "ni uno"));
				contactDataProvider.addDataDisplay(display.getUserListBox());
				
			}
		});
		
		handlers();
		
	}
	
	private void handlers() {
		// Handler de la CellList de Contacts
		contactSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				display.getContactName().setText(contactSelectionModel.getSelectedObject().getName());
				display.getContactSurname().setText(contactSelectionModel.getSelectedObject().getLastName());
			}
		});
		display.getSearchBox().addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
				System.out.println(display.getSearchBox().getText());
				/*
				 * new RPCXSRF<List<User>>(userService) {
				 * @Override protected void XSRFcallService(AsyncCallback<List<User>> cb) {
				 * userService.getFriendsSuggestionList(display.getSearchBox().getText(), cb); } public void
				 * onSuccess(List<User> returnUsers){ System.out.println(returnUsers.size()); } public void
				 * onFailure(Throwable caught){ } }.retry(3);
				 */
				CacheLayer.ContactCalls.getContacts(false, new AsyncCallback<Map<Key<User>, User>>() {
					
					@Override
					public void onSuccess(Map<Key<User>, User> result) {
						
						String text[] = display.getSearchBox().getText().split(" ");
						
						if (text != null && text.length > 0) {
							// display.getUserListBox().
							contactList.clear();
							Iterator<User> iterator = result.values().iterator();
							while (iterator.hasNext()) {
								User contact = iterator.next();
								if (contact.getName().contains(text[0])) {
									contactList.add(contact);
								}
							}
							contactDataProvider.flush();
							contactDataProvider.refresh();
						}
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
	}
	
	public HorizontalPanel getContactsPresenter() {
		return display.getContatcsMenu();
	}
	
	@Override
	public void toFront() {
		
	}
	
	@Override
	public void toZPosition(int position) {
		
	}
	
	@Override
	public int getZposition() {
		return 0;
	}
	
	@Override
	public void close(AbsolutePanel absolutepanel) {
		absolutepanel.remove(display.asWidget());
		
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
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void setSize(int height, int width) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getAbsoluteLeft() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getAbsoluteTop() {
		// TODO Auto-generated method stub
		return 0;
	}
}
