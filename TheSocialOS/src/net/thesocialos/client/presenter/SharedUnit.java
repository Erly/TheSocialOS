package net.thesocialos.client.presenter;

import java.util.ArrayList;
import java.util.List;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.app.IApplication;
import net.thesocialos.client.desktop.DesktopEventOnClose;
import net.thesocialos.client.desktop.DesktopEventOnMaximize;
import net.thesocialos.client.desktop.DesktopEventOnMinimize;
import net.thesocialos.client.desktop.DesktopEventOnTop;
import net.thesocialos.client.desktop.DesktopEventonEndDrag;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.desktop.window.Footer;
import net.thesocialos.client.desktop.window.MyCaption;
import net.thesocialos.client.desktop.window.events.WindowCloseEvent;
import net.thesocialos.client.desktop.window.events.WindowEndDragEvent;
import net.thesocialos.client.desktop.window.events.WindowEventHandler;
import net.thesocialos.client.desktop.window.events.WindowMaximizeEvent;
import net.thesocialos.client.desktop.window.events.WindowMinimizeEvent;
import net.thesocialos.client.desktop.window.events.WindowOnTopEvent;
import net.thesocialos.client.desktop.window.events.WindowResizeEvent;
import net.thesocialos.client.event.ShareHistoryChangEvent;
import net.thesocialos.client.event.ShareHistoryChngeEventHandler;
import net.thesocialos.shared.model.SharedHistory;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WindowPanelLayout;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class SharedUnit extends DesktopUnit implements IApplication {
	
	Display display;
	
	SingleSelectionModel<SharedHistory> selectionModel;
	ListDataProvider<SharedHistory> dataProvider;
	ArrayList<SharedHistory> historyList = new ArrayList<SharedHistory>();
	/*
	 * Los modelos de la cajas de seleccion de los usuarios
	 */
	ProvidesKey<SharedHistory> KEY_USERS_PROVIDER;
	
	Image image = new Image();
	Frame frame = new Frame();
	
	public SharedUnit(Display display) {
		super(AppConstants.SHAREDMANAGER, "SharedManager", new WindowPanelLayout(false, false, new MyCaption(),
				new Footer()), TypeUnit.WINDOW, false);
		this.display = display;
		handlers();
		windowDisplay.getWindow().add(display.asWidget());
		
		setMaximizable(false);
		windowDisplay.setResizable(false);
		KEY_USERS_PROVIDER = new ProvidesKey<SharedHistory>() {
			@Override
			public Object getKey(SharedHistory item) {
				return item == null ? null : item.getKey();
			}
		};
		
		selectionModel = new SingleSelectionModel<SharedHistory>(KEY_USERS_PROVIDER);
		display.getComponentsList().setSelectionModel(selectionModel);
		dataProvider = new ListDataProvider<SharedHistory>(historyList);
		dataProvider.addDataDisplay(display.getComponentsList());
		frame.setWidth("382px");
		frame.setHeight("297px");
		image.setWidth("382px");
		image.setHeight("297px");
		initCellList();
	}
	
	private void populateCellList(boolean cached) {
		/**
		 * Populate the List
		 */
		historyList.clear();
		dataProvider.flush();
		dataProvider.refresh();
		CacheLayer.UserCalls.getShareHistory(cached, new AsyncCallback<List<SharedHistory>>() {
			
			@Override
			public void onSuccess(List<SharedHistory> result) {
				// TODO Auto-generated method stub
				
				historyList = (ArrayList<SharedHistory>) result;
				dataProvider.setList(historyList);
				dataProvider.flush();
				dataProvider.refresh();
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void initCellList() {
		
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				display.getSimplePanel().clear();
				switch (selectionModel.getSelectedObject().getMessageType()) {
				case IMAGE:
					image.setUrl(selectionModel.getSelectedObject().getData());
					display.getSimplePanel().add(image);
					break;
				case VIDEO:
					frame.setUrl(selectionModel.getSelectedObject().getData());
					display.getSimplePanel().add(frame);
					break;
				
				default:
					break;
				}
				
			}
		});
	}
	
	public interface Display {
		Widget asWidget();
		
		CellList<SharedHistory> getComponentsList();
		
		SimplePanel getSimplePanel();
		
	}
	
	@Override
	public void close(AbsolutePanel absolutePanel) {
		absolutePanel.remove(windowDisplay.getWindow());
		windowDisplay.getWindow().setVisible(false);
		
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		if (!windowDisplay.getWindow().isAttached()) {
			windowDisplay.setWindowTitle(getName());
			absolutePanel.add(windowDisplay.getWindow(), x, y);
			windowDisplay.setSize(605, 358);
			windowDisplay.getWindow().setVisible(true);
			populateCellList(true);
		}
		
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	@Override
	public void setImage(String image) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
	
	private void handlers() {
		windowDisplay.addWindowEvents(new WindowEventHandler() {
			
			@Override
			public void onClose(WindowCloseEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnClose(SharedUnit.this));
			}
			
			@Override
			public void onEndDrag(WindowEndDragEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventonEndDrag(SharedUnit.this));
			}
			
			@Override
			public void onMaximize(WindowMaximizeEvent windowMaximizeEvent) {
				if (isMaximizable()) TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMaximize(SharedUnit.this));
			}
			
			@Override
			public void onMinimize(WindowMinimizeEvent windowMinimizeEvent) {
				if (isMinimizable())
					TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMinimize(SharedUnit.this, false));
			}
			
			@Override
			public void onTop(WindowOnTopEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnTop(SharedUnit.this));
			}
			
			@Override
			public void onResize(WindowResizeEvent event) {
				
			}
		});
		TheSocialOS.getEventBus().addHandler(ShareHistoryChangEvent.TYPE, new ShareHistoryChngeEventHandler() {
			
			@Override
			public void onHistoryChange(ShareHistoryChangEvent event) {
				populateCellList(false);
				
			}
		});
		
	}
}
