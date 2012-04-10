package net.thesocialos.client.presenter;

import java.util.ArrayList;
import java.util.List;

import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.desktop.DesktopUnit.TypeUnit;
import net.thesocialos.shared.model.Group;
import net.thesocialos.shared.model.User;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;


public class SearchBoxPresenter extends DesktopUnit {
	
	SingleSelectionModel<Object> selectionModel;
	ListDataProvider<Object> dataProvider;
	/*
	 * Los modelos de la cajas de seleccion de los usuarios
	 */
	ProvidesKey<Object> KEY_USERS_PROVIDER;
	List<Object> usersList = new ArrayList<Object>();
	
	/*
	 * Los modelos de la cajas de seleccion de los grupos
	 */
	ProvidesKey<Object> KEY_GROUPS_PROVIDER;
	List<Object> groupsList = new ArrayList<Object>();
	
	Display display;
	
	public interface Display{
		
		Widget asWidget();
		
		Label getLabelFriends();
		
		Label getLabelGroups();
		
		CellList<Object> getComponentsList();
		
		void setComponentsList(CellList<?> cellList);
	}
	
	public SearchBoxPresenter(Display display){
		programID = "002";
		typeUnit = TypeUnit.INFO;
		this.display = display;
	}
	/*
	 * Eventos de la interfaz
	 */
	private void handlers(){
		
		display.getLabelFriends().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				display.setComponentsList(new CellList<User>(UsersCell()));
				
			}
		});
		
		display.getLabelGroups().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private AbstractCell<User> UsersCell(){
		KEY_USERS_PROVIDER = new ProvidesKey<Object>() {
			public Object getKey(Object item) {
				return item == null ? null : ((User) item).getEmail();
			}
		};
		selectionModel = new SingleSelectionModel<Object>(KEY_USERS_PROVIDER);
		display.getComponentsList().setSelectionModel(selectionModel);
		dataProvider = new ListDataProvider<Object>(usersList);
		dataProvider.addDataDisplay(display.getComponentsList());

		
		return new AbstractCell<User>() {
			
			@Override
			public void render(Context context, User value, SafeHtmlBuilder sb) {
				
				 if (value == null) {
				        return;
				      }
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
		};
		
	}
	private AbstractCell<Group> groupsCell(){
		KEY_GROUPS_PROVIDER = new ProvidesKey<Object>() {
			public Object getKey(Object item) {
				return item == null ? null : ((Group) item).getId();
			}
		};
		selectionModel = new SingleSelectionModel<Object>(KEY_GROUPS_PROVIDER);
		display.getComponentsList().setSelectionModel(selectionModel);
		dataProvider = new ListDataProvider<Object>(groupsList);
		return new AbstractCell<Group>() {
			
			@Override
			public void render(Context context, Group value, SafeHtmlBuilder sb) {
				
				if (value == null){
					return;
				}
				
				 sb.appendHtmlConstant("<table>");
			
			      // Add the contact image.
			      sb.appendHtmlConstant("<tr><td rowspan='3'>"  );
			      sb.appendHtmlConstant("<img src='./images/anonymous_avatar.png' width='30' height='35' />");
			      sb.appendHtmlConstant("</td>");

			      // Add the name and address.
			      sb.appendHtmlConstant("<td style='font-size:95%;'>");
			      sb.appendEscaped(value.getName());
			      sb.appendHtmlConstant("</td></tr><tr><td>");
			      sb.appendEscaped(value.getId().toString());
			      sb.appendHtmlConstant("</td></tr></table>");
			}
		};
	}
	@Override
	public void Tofront() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void Toback(int position) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void close(AbsolutePanel absolutePanel) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void open(AbsolutePanel absolutePanel) {
		absolutePanel.add(display.asWidget());
		display.asWidget().setVisible(true);
		
		
	}
	@Override
	public void minimize() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void maximize() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void isMinimized() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getID() {
		// TODO Auto-generated method stub
		
	}

}
