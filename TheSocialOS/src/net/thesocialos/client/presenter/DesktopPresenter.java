package net.thesocialos.client.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.thesocialos.client.CacheLayer;
import net.thesocialos.client.TheSocialOS;
import net.thesocialos.shared.model.User;
import net.thesocialos.client.app.AppConstants;
import net.thesocialos.client.app.FrameApp;
import net.thesocialos.client.app.IApplication;
import net.thesocialos.client.chat.view.ChatMenuPresenter;
import net.thesocialos.client.chat.view.ChatMenuView;
import net.thesocialos.client.desktop.DesktopEventOnOpen;
import net.thesocialos.client.desktop.DesktopManager;
import net.thesocialos.client.desktop.window.FolderWindow;
import net.thesocialos.client.desktop.window.Footer;
import net.thesocialos.client.desktop.window.MyCaption;
import net.thesocialos.client.event.ContactsPetitionChangeEvent;
import net.thesocialos.client.event.LogoutEvent;
import net.thesocialos.client.helper.AppIconHelper;
import net.thesocialos.client.helper.Node;
import net.thesocialos.client.resources.Resources;
import net.thesocialos.client.view.ContactsView;
import net.thesocialos.client.view.DesktopBar;
import net.thesocialos.client.view.NotificationsBoxView;
import net.thesocialos.client.view.SearchBoxView;
import net.thesocialos.client.view.StartMenu;
import net.thesocialos.client.view.StartMenuItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WindowPanelLayout;
import com.google.gwt.user.client.ui.WindowPanelLayout;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class DesktopPresenter implements Presenter {
	
	private SimpleEventBus eventBus;
	private SimpleEventBus chatEventBus;
	/*
	 * All declarations of the desktop 
	 */
	ContactsPresenter contacsPresenter;
	SearchBoxPresenter searchBoxPresenter;
	NotificationsBoxPresenter notificationBoxPresenter;
	
	private AbsolutePanel desktop;
	private boolean startMenuFocused = false;
	private boolean userMenuFocused = false;
	DesktopManager desktopManager;
	
	Resources imageResources = GWT.create(Resources.class);
	PopupPanel programPanel = new PopupPanel(true, true); //List of run programs
	
	public interface Display {
		AbsolutePanel getDesktop();
		
		AbsolutePanel getScreen();
		
		Widget getUserMenu();
		
		HasBlurHandlers getUserMenuPanel();
		
		Widget getStartMenu();
		
		HasBlurHandlers getStartMenuPanel();
		
		HasClickHandlers getStartButton();
		
		HasClickHandlers getUserButton();
		
		HasClickHandlers getSocialOSButton();
		
		HasText getClock();
		
		HasText getUserName();
		
		Image getAvatar();

		HasText getNameLabel();

		HasText getTitleLabel();

		HasText getEmail();

		HasText getEditProfile();

		HasText getLogout();

		HasClickHandlers getEditProfilePanel();
		
		FocusPanel getLogoutPanel();
		
		Widget asWidget();
		
		DesktopBar getDesktopBar();
	}
	
	private final Display display;
	
	
	public DesktopPresenter(SimpleEventBus[] eventBus, Display display) {
		
		this.eventBus = eventBus[0];
		this.chatEventBus = eventBus[1];
		this.display = display;
	}

	/**
	 * Binds this presenter to its view loading it with information and adding all the necessary handlers.
	 */
	public void bind() {
		desktop = this.display.getDesktop();
		desktop.getElement().getStyle().setPosition(Position.ABSOLUTE);
		//desktop.getElement().getStyle().clearPosition();
		TheSocialOS.get().setDesktop(desktop);
		//Run the desktopManager
		this.desktopManager = new DesktopManager(eventBus, display.getScreen(), display.getDesktop());
		User user = CacheLayer.UserCalls.getUser();
		
		bindDesktopBar(user);
		bindUserMenu(user);
		bindSocialOS();
		bindContacts();
		bindSearchBox();
		bindPetitionsBox();
		bindProgramMenu();
		refreshData();

		// Populate the Star Menu		
		ArrayList<IApplication> appsData = new ArrayList<IApplication>();
		appsData.add(new FrameApp("Bitlet", "http://imagenes.es.sftcdn.net/es/scrn/251000/251956/bitlet-13.png", "http://www.bitlet.org/"
				,"1024px", "600px"));
		appsData.add(new FrameApp("Grooveshark Player", "http://img696.imageshack.us/img696/1622/11groovesharkicon256x25.png", "http://www.grooveshark.com"
				,"1024px", "600px"));
		appsData.add(new FrameApp("Sketchpad", "http://profile.ak.fbcdn.net/hprofile-ak-snc4/23295_128946130463344_2641_n.jpg", "http://mugtug.com/sketchpad"
				,"1024px", "600px"));
		//appsData.add(new ChatApp("Xmpp","http://www.bitrix.es/upload/iblock/e03/xmpp.gif",chatEventBus, new ChatPanel(),
			//	"450px", "300px"));
		bindStartMenu(appsData);
		
		

		AppIconHelper.populateDesktopWithIcons(desktop);

	}

	/**
	 * Binds the DesktopBar elements and adds its handlers.
	 * @param user The data transfer object of the user that is logged in the system.
	 */
	private void bindDesktopBar(User user) {
		// Create and initialize a timer for the clock refreshing
		new Timer() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				int min = new Date().getMinutes();
				display.getClock().setText(new Date().getHours() + ":" + (min < 10 ? "0" + min : min));
			}
		}.scheduleRepeating(1000);
		
		// Populate the desktopBar and the userMenu with the user data.
		this.display.getUserName().setText(user.getEmail());
		this.display.getUserButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.getUserMenu().setVisible(true);
				((FocusPanel)display.getUserButton()).addStyleName("userbar-focused");
				((FocusPanel)display.getUserButton()).removeStyleName("userbar");
				//((FocusPanel)display.getUserButton()).setStylePrimaryName("userbar-focused");
				((FocusPanel)display.getUserMenuPanel()).setFocus(true);
			}
		});

		this.display.getStartButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.getStartMenu().setVisible(true);
				((FocusPanel)display.getStartButton()).addStyleName("startbutton-focused");
				((FocusPanel)display.getStartButton()).removeStyleName("startbutton");
				((FocusPanel)display.getStartMenuPanel()).setFocus(true);
			}
		});
	}
	
	/**
	 * Bind the user menu elements and adds its handlers.
	 * @param user The data transfer object of the user that is logged on.
	 */
	private void bindUserMenu(User user) { 
		if (user.getAvatar() == null){
				this.display.getAvatar().setUrl("./images/anonymous_avatar.png");
		}else{
			this.display.getAvatar().setUrl(user.getAvatar());
		}		
		
		this.display.getNameLabel().setText(user.getName() + " " + user.getLastName());
		this.display.getTitleLabel().setText("User");
		this.display.getEmail().setText(""); // TODO the email is already at the top-right corner
		this.display.getUserMenu().setVisible(false);
		
		((FocusPanel)this.display.getEditProfilePanel()).addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				userMenuFocused = true;
			}
		});
		
		((FocusPanel)this.display.getEditProfilePanel()).addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				userMenuFocused = false;
			}
		});
		
		((FocusPanel)this.display.getEditProfilePanel()).addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				if (!userMenuFocused)
					loseUserFocus();
			}
		});
		
		this.display.getEditProfilePanel().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loseUserFocus();
				History.newItem("profile");
			}
		});
		
		this.display.getLogoutPanel().addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				userMenuFocused = true;
			}
		});
		
		this.display.getLogoutPanel().addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				userMenuFocused = false;
			}
		});
		
		this.display.getLogoutPanel().addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				if (!userMenuFocused)
					loseUserFocus();
			}
		});
		
		this.display.getLogoutPanel().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				TheSocialOS.get().getEventBus().fireEvent(new LogoutEvent());
			}
		});

		this.display.getUserMenuPanel().addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				if (!userMenuFocused)
					loseUserFocus();
			}
		});
		
	}
	
	private void bindStartMenu(ArrayList<IApplication> appsData) {
		this.display.getStartMenu().setVisible(false);
		VerticalPanel vPanel = ((StartMenu) this.display.getStartMenu()).getStartVPanel();
		for (int i = 0; i < appsData.size(); i++) {
			StartMenuItem appItem = new StartMenuItem(appsData.get(i));
			vPanel.add(appItem);
			appItem.getFocusPanel().addMouseDownHandler(new MouseDownHandler() {
				
				@Override
				public void onMouseDown(MouseDownEvent event) {
					startMenuFocused = true;
				}
			});
			appItem.getFocusPanel().addMouseOutHandler(new MouseOutHandler() {
				
				@Override
				public void onMouseOut(MouseOutEvent event) {
					startMenuFocused = false;
				}
			});
			appItem.getFocusPanel().addBlurHandler(new BlurHandler() {
				
				@Override
				public void onBlur(BlurEvent event) {
					if (!startMenuFocused)
						loseStartFocus();
				}
			});
			appItem.getFocusPanel().addMouseUpHandler(new MouseUpHandler() {
				
				@Override
				public void onMouseUp(MouseUpEvent event) {
					loseStartFocus();
				}
			});
		}
		
		this.display.getStartMenuPanel().addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				if (!startMenuFocused)
					loseStartFocus();
			}
		});
	}
	
	private void bindSocialOS() {
		final PopupPanel errorWindow = new PopupPanel(false, true);
		errorWindow.setGlassEnabled(true);
		VerticalPanel vPanel = new VerticalPanel();
		Label errorMessage = new Label("This functionality is not implemented yet.");
		final Button closeButton = new Button("Close");
		vPanel.add(errorMessage);
		vPanel.add(closeButton);
		vPanel.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_CENTER);
		closeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				((PopupPanel)closeButton.getParent().getParent()).hide();
			}
		});
		errorWindow.add(vPanel);
		
		this.display.getSocialOSButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				errorWindow.center();
			}
		});
	}
	
	private void bindContacts(){
		display.getDesktopBar().getFocusContact().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
	
			
				if (contacsPresenter == null){
					contacsPresenter = new ContactsPresenter(new ContactsView());
				}
				eventBus.fireEvent(new DesktopEventOnOpen(contacsPresenter));

			}
		});
	}
	
	private void bindSearchBox(){
		
		display.getDesktopBar().getSearchBox().addClickHandler(new ClickHandler() {
		
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (searchBoxPresenter == null){
					searchBoxPresenter = new SearchBoxPresenter(new SearchBoxView());
				}
				int x = display.getDesktopBar().getSearchBox().getAbsoluteLeft() - (searchBoxPresenter.display.getSearchBoxPanel().getOffsetWidth());
				int y = display.getDesktopBar().getSearchBox().getOffsetHeight();
				searchBoxPresenter.setPosition(x, y);
				eventBus.fireEvent(new DesktopEventOnOpen(searchBoxPresenter));
			}
		});
	}

	private void bindPetitionsBox(){
		display.getDesktopBar().getPetitionsButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (notificationBoxPresenter == null){
					notificationBoxPresenter = new NotificationsBoxPresenter(new NotificationsBoxView());
				}
				int x = display.getDesktopBar().getPetitionsButton().getAbsoluteLeft();
				int y = display.getDesktopBar().getPetitionsButton().getOffsetHeight();
				notificationBoxPresenter.setPosition(x, y);
				eventBus.fireEvent(new DesktopEventOnOpen(notificationBoxPresenter));
				
			}
		});
		//WindowPanelexmp test = new WindowPanelexmp(false, false);
		//display.getDesktop().add(test, 50, 50);
		//test.show();
		//FolderWindow prueba = new FolderWindow(new WindowPanelLayout(false, false,new MyCaption(),new Footer()),AppConstants.NOTHING);
		//prueba.setPosition(20, 20);
		//eventBus.fireEvent(new DesktopEventOnOpen(prueba));
		ChatMenuPresenter chatMenu = new ChatMenuPresenter(new ChatMenuView());
		eventBus.fireEvent(new DesktopEventOnOpen(chatMenu));
	}
	
	private void bindProgramMenu(){
		
		display.getDesktopBar().getProgramsButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				
				
				MenuBar menuBar = new MenuBar(true);
				
				menuBar.addItem(createMenuItem("Program1", AbstractImagePrototype.create(imageResources.logo()), null));
				menuBar.addItem(createMenuItem("Program2", AbstractImagePrototype.create(imageResources.logo()), null));
				MenuItem menuItem; //createMenuItem("Program3",  AbstractImagePrototype.create(imageResources.logo()));
				
				MenuBar menuBar1 = new MenuBar(true);
				menuBar1.addItem(createMenuItem("Program3.1",  AbstractImagePrototype.create(imageResources.logo()), null));
				menuBar1.addItem(createMenuItem("Program3.2",  AbstractImagePrototype.create(imageResources.logo()), null));
				menuBar1.addItem(createMenuItem("Program3.3",  AbstractImagePrototype.create(imageResources.logo()), null));
				
				menuBar.addItem(getStringMenuparse("Program3", AbstractImagePrototype.create(imageResources.logo())),true,menuBar1);
				menuBar.addItem(createMenuItem("Program4", AbstractImagePrototype.create(imageResources.logo()), null));
				menuBar.addItem(createMenuItem("Program5", AbstractImagePrototype.create(imageResources.logo()), null));
				programPanel.setPopupPosition(event.getClientX(), event.getClientY());
				programPanel.add(menuBar);
				programPanel.show();
				//MenuItem me
				
			}
		});
	}
	
	
	

  
    
    private MenuItem createMenuItem(String menuLabel,
			AbstractImagePrototype menuImage,Command command) {
			   
			    MenuItem menuItem = new MenuItem(menuImage.getHTML() + "&nbsp;"+
			menuLabel, true, command);
			    return menuItem;
			}
    private String getStringMenuparse(String name, AbstractImagePrototype menuImage){
    	return menuImage.getHTML() + "&nbsp;" + name;
    }
    /**
     * Makes the run programs visible in the bar
     * @param nodes
     */
	public void makeProgramsPanel(List<Node> nodes){
		MenuBar menuBar = new MenuBar(true);
		Iterator<Node> nodesIterator = nodes.iterator();
		while (nodesIterator.hasNext()){
			Node node = nodesIterator.next();
			Iterator<Node> nodeIterator = node.getNodeIterator();
			MenuBar subMenuBar = new MenuBar(true);
			if (nodeIterator.hasNext()){
				while (nodeIterator.hasNext()){
					Node subnode = nodeIterator.next();
					subMenuBar.addItem(createMenuItem(subnode.getName(),  AbstractImagePrototype.create(imageResources.logo()),subnode.getCommand()));
				}
				menuBar.addItem(getStringMenuparse(node.getName(),AbstractImagePrototype.create(imageResources.logo())),true,subMenuBar);
			}else{
				menuBar.addItem(createMenuItem(node.getName(),  AbstractImagePrototype.create(imageResources.logo()),node.getCommand()));
			}
		}
	}
	
	/**
	 * Refresh all information of the desktop: Group Petitions, User Petitions, Chat...
	 */
	private void refreshData(){
		CacheLayer.ContactCalls.getContactPetitions(false, new AsyncCallback<Map<String,User>>() {
			
			@Override
			public void onSuccess(Map<String, User> result) {
				// TODO Auto-generated method stub
				TheSocialOS.getEventBus().fireEvent(new ContactsPetitionChangeEvent());
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				TheSocialOS.getEventBus().fireEvent(new ContactsPetitionChangeEvent());
				
			}
		});
	}
	@Override
	public void go(final HasWidgets container) {
		container.clear(); // Clear the screen
		container.add(display.asWidget()); // Print the desktop screen
		bind();
	}

	/**
	 * makes the UserMenu invisible and restores the UserButtons default style. To be called when the focus of the UserMenu and its elements is lost. 
	 */
	protected void loseUserFocus() {
		userMenuFocused = false;
		display.getUserMenu().setVisible(false);
		((FocusPanel)display.getUserButton()).addStyleName("userbar");
		((FocusPanel)display.getUserButton()).removeStyleName("userbar-focused");
	}

	protected void loseStartFocus() {
		startMenuFocused = false;
		display.getStartMenu().setVisible(false);
		((FocusPanel)display.getStartButton()).addStyleName("startbutton");
		((FocusPanel)display.getStartButton()).removeStyleName("startbutton-focused");
	}
}
