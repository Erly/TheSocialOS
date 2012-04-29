package net.thesocialos.client.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.shared.ChannelApiEvents.ChApiChatUserChngState.STATETYPE;
import net.thesocialos.shared.model.User;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
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
		
		Label getState();
	}
	
	Display display;
	
	SingleSelectionModel<User> selectionModel;
	ListDataProvider<User> dataProvider;
	/*
	 * Los modelos de la cajas de seleccion de los usuarios
	 */
	ProvidesKey<User> KEY_USERS_PROVIDER;
	
	List<User> usersList = new ArrayList<User>();
	
	public ChatMenuPresenter(Display display) {
		super(AppConstants.CHAT, 0, null, TypeUnit.STATIC);
		typeUnit = TypeUnit.STATIC;
		this.display = display;
		
		selectionModel = new SingleSelectionModel<User>(KEY_USERS_PROVIDER);
		display.getCellContacts().setSelectionModel(selectionModel);
		dataProvider = new ListDataProvider<User>(usersList);
		dataProvider.addDataDisplay(display.getCellContacts());
		display.getName().setText(CacheLayer.UserCalls.getUser().getName());
		display.getSurname().setText(CacheLayer.UserCalls.getUser().getLastName());
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
	
}
