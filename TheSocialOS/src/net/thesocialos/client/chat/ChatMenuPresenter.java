package net.thesocialos.client.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.chat.events.ChatOpenConversation;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.model.User;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

public class ChatMenuPresenter extends DesktopUnit {
	
	public interface Display {
		Widget asWidget();
		
		CellList<User> getCellContacts();
		
		HorizontalPanel getConversationsPanel();
		
		Label getName();
		
		Label getSurname();
		
		Label getStateLabel();
		
		FocusPanel getStateFocus();
		
		HTMLPanel getStateCircle();
	}
	
	Display display;
	
	private static Integer clickCount = new Integer(0);
	SingleSelectionModel<User> selectionModel;
	ListDataProvider<User> dataProvider;
	/*
	 * Los modelos de la cajas de seleccion de los usuarios
	 */
	ProvidesKey<User> KEY_USERS_PROVIDER;
	
	List<User> usersList = new ArrayList<User>();
	
	ChatManager chatManager;
	POPUPMenu popUPMenu;
	
	public ChatMenuPresenter(Display display, ChatManager chatManager) {
		
		super(AppConstants.CHAT, null, TypeUnit.STATIC, false);
		typeUnit = TypeUnit.STATIC;
		this.display = display;
		this.chatManager = chatManager;
		selectionModel = new SingleSelectionModel<User>(KEY_USERS_PROVIDER);
		display.getCellContacts().setSelectionModel(selectionModel);
		dataProvider = new ListDataProvider<User>(usersList);
		dataProvider.addDataDisplay(display.getCellContacts());
		display.getName().setText(CacheLayer.UserCalls.getUser().getName());
		display.getSurname().setText(CacheLayer.UserCalls.getUser().getLastName());
		init();
	}
	
	public void refeshContacts(Map<String, User> result) {
		usersList.addAll(result.values());
		dataProvider.flush();
		dataProvider.refresh();
		
	}
	
	public void changeContactState(String userEmail, STATETYPE stateType, String customMSG) {
		for (int a = 0; a < dataProvider.getList().size(); a++)
			if (dataProvider.getList().get(a).getEmail().equalsIgnoreCase(userEmail))
				dataProvider.getList().get(a).chatState = stateType;
		dataProvider.flush();
		dataProvider.refresh();
		
	}
	
	public void changeUserState(STATETYPE StateType) {
		display.getStateLabel().setText(StateType.toString());
		switch (StateType) {
		case ONLINE:
			
			display.getStateCircle().setStyleName("chatMenu_circle_online");
			break;
		case AFK:
			display.getStateCircle().setStyleName("chatMenu_circle_away");
			break;
		case BUSY:
			display.getStateCircle().setStyleName("chatMenu_circle_busy");
			break;
		case OFFLINE:
			display.getStateCircle().setStyleName("chatMenu_circle_offline");
			break;
		
		default:
			break;
		}
		
	}
	
	private void init() {
		popUPMenu = new POPUPMenu();
		handlers();
		
		final Timer t = new Timer() {
			@Override
			public void run() {
				System.out.println("click Reseteado");
				clickCount = 0;
				cancel();
			}
		};
		display.getCellContacts().addCellPreviewHandler(new Handler<User>() {
			
			@Override
			public void onCellPreview(CellPreviewEvent<User> event) {
				// System.out.println(event.getValue().getOwnKey());
				if (event.getNativeEvent().getType().equalsIgnoreCase("click")) {
					
					clickCount++;
					t.schedule(500);
					if (clickCount > 1) // System.out.println("double click");
						TheSocialOS.getEventBus().fireEvent(new ChatOpenConversation(event.getValue().getOwnKey()));
					
				}
				
			}
		});
	}
	
	private void handlers() {
		display.getStateFocus().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				popUPMenu.show(event.getClientX(), event.getClientY());
				
			}
		});
	}
	
	@Override
	public void close(AbsolutePanel absolutePanel) {
		absolutePanel.remove(display.asWidget());
		
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		absolutePanel.add(display.asWidget());
		
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void toBack() {
		
	}
	
	@Override
	public void toFront() {
		
	}
	
	private class POPUPMenu {
		
		MenuBar menuBar = new MenuBar(true);
		PopupPanel popUpPanel = new PopupPanel(true);
		
		private POPUPMenu() {
			
			menuBar.addItem(new MenuItem(STATETYPE.ONLINE.toString(), new Command() {
				@Override
				public void execute() {
					chatManager.changeState(STATETYPE.ONLINE, null);
					popUpPanel.hide();
				}
			}));
			menuBar.addItem(new MenuItem(STATETYPE.BUSY.toString(), new Command() {
				@Override
				public void execute() {
					chatManager.changeState(STATETYPE.BUSY, null);
					popUpPanel.hide();
				}
			}));
			menuBar.addItem(new MenuItem(STATETYPE.AFK.toString(), new Command() {
				@Override
				public void execute() {
					chatManager.changeState(STATETYPE.AFK, null);
					popUpPanel.hide();
				}
			}));
			menuBar.addItem(new MenuItem(STATETYPE.OFFLINE.toString(), new Command() {
				@Override
				public void execute() {
					chatManager.changeState(STATETYPE.OFFLINE, null);
					popUpPanel.hide();
				}
			}));
			
			popUpPanel.add(menuBar);
			
		}
		
		public void show(int left, int top) {
			popUpPanel.setPopupPosition(left, top);
			popUpPanel.show();
		}
		
	}
}