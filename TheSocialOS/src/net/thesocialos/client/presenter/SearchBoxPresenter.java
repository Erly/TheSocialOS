package net.thesocialos.client.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.desktop.IsTypeInfo;
import net.thesocialos.client.helper.SearchArrayList;
import net.thesocialos.client.view.PopUpInfoContact;
import net.thesocialos.shared.model.User;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class SearchBoxPresenter extends DesktopUnit implements IsTypeInfo {
	
	public interface Display {
		
		Widget asWidget();
		
		Image getAvatarIMG();
		
		CellList<User> getComponentsList();
		
		Label getLabelFriends();
		
		Label getLabelGroups();
		
		Label getLabelInfo();
		
		Label getLabelInvite();
		
		VerticalPanel getSearchBoxPanel();
		
		StackLayoutPanel getStackLayout();
		
		TextBox getSearchBox();
		
		HTMLPanel getHtmlPanel();
		
		void setComponentsList(CellList<User> cellList);
	}
	
	SingleSelectionModel<User> selectionModel;
	ListDataProvider<User> dataProvider;
	/*
	 * Los modelos de la cajas de seleccion de los usuarios
	 */
	ProvidesKey<User> KEY_USERS_PROVIDER;
	
	SearchArrayList usersList = new SearchArrayList();
	/*
	 * Los modelos de la cajas de seleccion de los grupos
	 */
	ProvidesKey<Object> KEY_GROUPS_PROVIDER;
	
	List<Object> groupsList = new ArrayList<Object>();
	
	Display display;
	
	public SearchBoxPresenter(Display display) {
		super(AppConstants.SEARCHBOX, "Contacts Search", null, TypeUnit.INFO, false);
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
		display.getComponentsList().setSelectionModel(selectionModel);
		dataProvider = new ListDataProvider<User>(usersList);
		dataProvider.addDataDisplay(display.getComponentsList());
		handlers();
	}
	
	private void addPetitionContact(User contactUser) {
		CacheLayer.ContactCalls.addPetitionContact(contactUser, new AsyncCallback<Boolean>() {
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
				
			}
			
			@Override
			public void onSuccess(Boolean result) {
				System.out.println(result);
				
			}
		});
	}
	
	@Override
	public void close(final AbsolutePanel absolutePanel) {
		Timer timer = new Timer() {
			@Override
			public void run() {
				absolutePanel.remove(display.asWidget());
				display.asWidget().setVisible(false);
			}
		};
		
		display.getHtmlPanel().setStyleName("contactsBoxes_close", true);
		display.getHtmlPanel().setStyleName("appManager_open", false);
		timer.schedule(500);
		// absolutePanel.remove(display.asWidget());
		
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
	
	/**
	 * Obtiene todos los usuarios del servidor
	 */
	private void getUsers() {
		
		usersList.clear();
		dataProvider.flush();
		dataProvider.refresh();
		CacheLayer.ContactCalls.getUsers(true, new AsyncCallback<Map<String, User>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(Map<String, User> result) {
				
				// display.setComponentsList(new CellList<User>(usersCell()));
				result.remove(CacheLayer.UserCalls.getUser().getEmail());
				usersList.addAll(result.values());
				dataProvider.flush();
				dataProvider.refresh();
				
			}
		});
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				if (CacheLayer.ContactCalls.isContact(selectionModel.getSelectedObject().getOwnKey())) display
						.getLabelInvite().setVisible(false);
				else
					display.getLabelInvite().setVisible(true);
				if (selectionModel.getSelectedObject().getUrlAvatar() == null) display.getAvatarIMG().setUrl(
						"./images/anonymous_avatar.png");
				else
					display.getAvatarIMG().setUrl(selectionModel.getSelectedObject().getUrlAvatar());
			}
		});
		display.getComponentsList().addDomHandler(new ContextMenuHandler() {
			
			@Override
			public void onContextMenu(ContextMenuEvent event) {
				
				DomEvent.fireNativeEvent(
						Document.get().createClickEvent(0, event.getNativeEvent().getScreenX(),
								event.getNativeEvent().getScreenY(), event.getNativeEvent().getClientX(),
								event.getNativeEvent().getClientY(), false, false, false, false),
						display.getComponentsList());
				
			}
		}, ContextMenuEvent.getType());
		
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*
	 * Eventos de la interfaz
	 */
	private void handlers() {
		
		display.getLabelFriends().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				display.getStackLayout().showWidget(0);
				
			}
		});
		
		/*
		 * display.getLabelGroups().addClickHandler(new ClickHandler() {
		 * @Override public void onClick(ClickEvent event) { display.getStackLayout().showWidget(1); } });
		 */
		display.getLabelInvite().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				User contactUser;
				if ((contactUser = selectionModel.getSelectedObject()) != null) addPetitionContact(contactUser);
				
			}
		});
		display.getLabelInfo().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				User contactUser;
				if ((contactUser = selectionModel.getSelectedObject()) != null) {
					PopUpInfoContact contactInfoPopup = new PopUpInfoContact(contactUser.getEmail(), contactUser
							.getName(), contactUser.getLastName(), contactUser.getUrlAvatar());
					contactInfoPopup.setGlassEnabled(true);
					contactInfoPopup.center();
					contactInfoPopup.show();
				}
				
			}
		});
		display.getSearchBox().addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
				// System.out.println(display.getSearchBox().getText());
				// ArrayList<User> lista = ;
				dataProvider.setList(usersList.getSearchUsers(display.getSearchBox().getText()));
				// System.out.println(lista.size());
				dataProvider.flush();
				dataProvider.refresh();
			}
		});
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		if (!display.asWidget().isAttached()) {
			absolutePanel.add(display.asWidget(), x, y);
			display.asWidget().setVisible(true);
			display.getHtmlPanel().setStyleName("contactsBoxes_open", true);
			display.getHtmlPanel().setStyleName("contactsBoxes_close", false);
			
			getUsers();
		}
		
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
