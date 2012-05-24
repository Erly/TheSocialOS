package net.thesocialos.client.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.desktop.IsTypeInfo;
import net.thesocialos.client.event.ContactsPetitionChangeEvent;
import net.thesocialos.client.view.LabelText;
import net.thesocialos.client.view.PopAsker;
import net.thesocialos.client.view.PopUpInfoContact;
import net.thesocialos.client.view.PopUpMenu;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class NotificationsBoxPresenter extends DesktopUnit implements IsTypeInfo {
	
	public interface Display {
		
		Widget asWidget();
		
		CellList<User> getContactsCellList();
		
		LabelText getContactsLabelText();
		
		LabelText getGroupsLabelText();
		
		StackLayoutPanel getStackLayoutPanel();
	}
	
	Display display;
	SingleSelectionModel<User> selectionModel;
	ListDataProvider<User> dataProvider;
	PopUpMenu UserPopUpMenu;
	/*
	 * Los modelos de la cajas de seleccion de los usuarios
	 */
	ProvidesKey<User> KEY_USERS_PROVIDER;
	
	List<User> usersList = new ArrayList<User>();
	
	public NotificationsBoxPresenter(Display display) {
		super(AppConstants.NOTIFICATIONS, "Notifications", null, TypeUnit.INFO, false);
		this.display = display;
		
		KEY_USERS_PROVIDER = new ProvidesKey<User>() {
			@Override
			public Object getKey(User item) {
				return item == null ? null : item.getEmail();
			}
		};
		/*
		 * Inicializado el adaptador de la cellList de usuario
		 */
		selectionModel = new SingleSelectionModel<User>(KEY_USERS_PROVIDER);
		display.getContactsCellList().setSelectionModel(selectionModel);
		dataProvider = new ListDataProvider<User>(usersList);
		dataProvider.addDataDisplay(display.getContactsCellList());
		display.getContactsLabelText().setText("0");
		// display.getGroupsLabelText().setText("0");
		handlers();
		getContactPetitions(true);
	}
	
	@Override
	public void close(AbsolutePanel absolutePanel) {
		absolutePanel.remove(display.asWidget());
		
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
	
	private void getContactPetitions(Boolean cached) {
		
		usersList.clear();
		dataProvider.flush();
		dataProvider.refresh();
		
		CacheLayer.ContactCalls.getContactPetitions(cached, new AsyncCallback<Map<String, User>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(Map<String, User> result) {
				// TODO Auto-generated method stub
				display.getStackLayoutPanel().setHeaderText(0, "Contact:     " + result.size());
				display.getContactsLabelText().setText(Integer.toString(result.size()));
				usersList.addAll(result.values());
				dataProvider.flush();
				dataProvider.refresh();
				TheSocialOS.getEventBus().fireEvent(new ContactsPetitionChangeEvent());
			}
		});
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private void handlers() {
		
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				
			}
		});
		display.getContactsCellList().addCellPreviewHandler(new CellPreviewEvent.Handler<User>() {
			
			@Override
			public void onCellPreview(CellPreviewEvent<User> event) {
				
				System.out.println(event.getNativeEvent().getClientX());
				if (event.getNativeEvent().getClientX() != 0) {
					UserPopUpMenu = new PopUpMenu();
					popupHandlers(event.getValue());
					UserPopUpMenu.show(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
					selectionModel.setSelected(event.getValue(), true);
				}
				
			}
			
		});
		
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		absolutePanel.add(display.asWidget(), x, y);
		display.asWidget().setVisible(true);
		
	}
	
	private void popupHandlers(final User user) {
		UserPopUpMenu.getMenuIAccept().setCommand(new Command() {
			
			@Override
			public void execute() {
				CacheLayer.ContactCalls.acceptAContact(user, new AsyncCallback<Boolean>() {
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSuccess(Boolean result) {
						// TODO Auto-generated method stub
						getContactPetitions(false);
						CacheLayer.ContactCalls.updateContacts(null);
						
					}
				});
				UserPopUpMenu.hide();
			}
		});
		UserPopUpMenu.getMenuIDeny().setCommand(new Command() {
			
			@Override
			public void execute() {
				CacheLayer.ContactCalls.denyAContact(user, new AsyncCallback<Boolean>() {
					
					@Override
					public void onFailure(Throwable caught) {
						
					}
					
					@Override
					public void onSuccess(Boolean result) {
						getContactPetitions(false);
						
					}
				});
				UserPopUpMenu.hide();
			}
		});
		UserPopUpMenu.getMenuISendPriv().setCommand(new Command() {
			
			@Override
			public void execute() {
				new PopAsker("Not implemented yet");
				UserPopUpMenu.hide();
				
			}
		});
		UserPopUpMenu.getMenuIViewPerfil().setCommand(new Command() {
			
			@Override
			public void execute() {
				PopUpInfoContact contactInfoPopup = new PopUpInfoContact(user.getEmail(), user.getName(), user
						.getLastName());
				contactInfoPopup.setGlassEnabled(true);
				contactInfoPopup.center();
				contactInfoPopup.show();
				UserPopUpMenu.hide();
			}
		});
	}
	
	@Override
	public void setPosition(int x, int y) {
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
		// TODO Auto-generated method stub
		
	}
	
}
