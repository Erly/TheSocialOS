package net.thesocialos.client.desktop.window;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.api.FacebookAPI;
import net.thesocialos.client.api.FlickrAPI;
import net.thesocialos.client.api.Media;
import net.thesocialos.client.api.MediaParent;
import net.thesocialos.client.api.MediaPicture;
import net.thesocialos.client.api.PicasaAPI;
import net.thesocialos.client.api.YoutubeAPI;
import net.thesocialos.client.app.IApplication;
import net.thesocialos.client.desktop.DesktopEventOnClose;
import net.thesocialos.client.desktop.DesktopEventOnMaximize;
import net.thesocialos.client.desktop.DesktopEventOnMinimize;
import net.thesocialos.client.desktop.DesktopEventOnTop;
import net.thesocialos.client.desktop.DesktopEventonEndDrag;
import net.thesocialos.client.desktop.DesktopUnit;
import net.thesocialos.client.helper.DblClickHandlerHelper;
import net.thesocialos.client.helper.MediaHelper;
import net.thesocialos.client.view.Thumbnail;
import net.thesocialos.client.view.Thumbnail.SERVICE;
import net.thesocialos.client.view.Thumbnail.TYPE;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.WindowPanelLayout;

public class FolderWindow extends DesktopUnit implements IApplication {
	
	private class TypeAndService {
		
		TYPE type;
		SERVICE service;
		
		public TypeAndService(TYPE type, SERVICE service) {
			this.type = type;
			this.service = service;
		}
	}
	
	public final static String PICTURES = TheSocialOS.getConstants().pictures();
	public final static String VIDEOS = TheSocialOS.getConstants().videos();
	public final static String MUSIC = TheSocialOS.getConstants().music();
	public final static String OTHER = TheSocialOS.getConstants().other();
	
	InfoPanel infoPanel = new InfoPanel();
	FlexTable table = new FlexTable();
	public String title = "";
	private int i = 0;
	private int j = 0;
	private boolean hasAlbums = false;
	
	private String contentType = null;
	private List<Media> parent = new ArrayList<Media>();
	private List<Set<Media>> files = new ArrayList<Set<Media>>();
	private int arrayPosition = 0;
	
	/**
	 * @deprecated Use the constructor that doesn't pass a HashSet and add the folders dynamically using addMedia
	 *             methods
	 * @param title
	 *            The folder name
	 * @param mediaSet
	 *            The media to load in the folder
	 * @param idProgram
	 *            The program id
	 */
	@Deprecated
	public FolderWindow(String title, HashSet<? extends Media> mediaSet, int idProgram) {
		this(idProgram);
		this.title = title;
		int width = display.getWidth();
		int col = width / 152;
		Iterator<? extends Media> iterator = mediaSet.iterator();
		i = 0;
		j = 0;
		while (iterator.hasNext()) {
			Media media = iterator.next();
			printMedia(media, col);
		}
	}
	
	public FolderWindow(String title, String contentType, int idProgram) {
		this(idProgram);
		this.title = title;
		this.contentType = contentType;
		
	}
	
	public FolderWindow(int idProgram) {
		files.add(new HashSet<Media>());
		display = new WindowPanelLayout(false, false, new MyCaption(), new Footer());
		programID = idProgram;
		typeUnit = TypeUnit.APPLICATION;
		x = 1;
		y = 30;
		display.addWindowEvents(new WindowEventHandler() {
			
			@Override
			public void onClose(WindowCloseEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnClose(FolderWindow.this));
			}
			
			@Override
			public void onEndDrag(WindowEndDragEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventonEndDrag(FolderWindow.this));
			}
			
			@Override
			public void onMaximize(WindowMaximizeEvent windowMaximizeEvent) {
				if (isMaximizable()) {
					TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMaximize(FolderWindow.this));
					clearMedia();
					addMedia((HashSet<? extends Media>) files.get(arrayPosition));
				}
			}
			
			@Override
			public void onMinimize(WindowMinimizeEvent windowMinimizeEvent) {
				if (isMinimizable())
					TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMinimize(FolderWindow.this));
			}
			
			@Override
			public void onTop(WindowOnTopEvent event) {
				TheSocialOS.getEventBus().fireEvent(new DesktopEventOnTop(FolderWindow.this));
			}
			
			@Override
			public void onResize(WindowResizeEvent event) {
				clearMedia();
				addMedia((HashSet<? extends Media>) files.get(arrayPosition));
			}
		});
	}
	
	private void printMedia(Media media, int col) {
		TypeAndService typeAndService = getTypeAndService(media);
		// If it a picture prefetch it, so the popup loads in the correct position (and the image loads faster ;) )
		if (typeAndService.type == TYPE.PICTURE) Image.prefetch(((MediaPicture) media).getUrl());
		Thumbnail thumb = new Thumbnail(media.getThumbnailURL(), media.getName(), typeAndService.type,
				typeAndService.service);
		thumb.addDoubleClickHandler(new DblClickHandlerHelper(this, media).getDoubleClickHandler());
		thumb.setTitle(media.getDescription());
		table.setWidget(j, i, thumb);
		table.getFlexCellFormatter().setVerticalAlignment(j, i, HasVerticalAlignment.ALIGN_TOP);
		i++;
		if (i % col == 0) {
			j++;
			i = 0;
		}
	}
	
	public void addMedia(HashSet<? extends Media> mediaSet) {
		if (!hasAlbums) {
			hasAlbums = true;
			infoPanel.removeFromParent();
		}
		int width = display.getWidth();
		int col = width / 152;
		Iterator<? extends Media> iterator = mediaSet.iterator();
		while (iterator.hasNext()) {
			Media media = iterator.next();
			files.get(arrayPosition).add(media);
			printMedia(media, col);
		}
	}
	
	public void addMedia(Media media) {
		if (!hasAlbums) {
			hasAlbums = true;
			infoPanel.removeFromParent();
		}
		int width = display.getWidth();
		int col = width / 152;
		files.get(arrayPosition).add(media);
		printMedia(media, col);
	}
	
	public void clearMedia() {
		table.clear();
		i = 0;
		j = 0;
	}
	
	@Override
	public void close(AbsolutePanel absolutePanel) {
		absolutePanel.remove(display.getWindow());
		
	}
	
	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private TypeAndService getTypeAndService(Media media) {
		TypeAndService typeAndService = null;
		if (media instanceof PicasaAPI.Album) typeAndService = new TypeAndService(TYPE.ALBUM, SERVICE.PICASA);
		else if (media instanceof PicasaAPI.Picture) typeAndService = new TypeAndService(TYPE.PICTURE, SERVICE.PICASA);
		else if (media instanceof FacebookAPI.Album) typeAndService = new TypeAndService(TYPE.ALBUM, SERVICE.FACEBOOK);
		else if (media instanceof FacebookAPI.Picture) typeAndService = new TypeAndService(TYPE.PICTURE,
				SERVICE.FACEBOOK);
		else if (media instanceof FlickrAPI.Album) typeAndService = new TypeAndService(TYPE.ALBUM, SERVICE.FLICKR);
		else if (media instanceof FlickrAPI.Picture) typeAndService = new TypeAndService(TYPE.PICTURE, SERVICE.FLICKR);
		else if (media instanceof YoutubeAPI.Album) typeAndService = new TypeAndService(TYPE.ALBUM, SERVICE.YOUTUBE);
		else if (media instanceof YoutubeAPI.Video) typeAndService = new TypeAndService(TYPE.VIDEO, SERVICE.YOUTUBE);
		else if (media instanceof YoutubeAPI.Folder) typeAndService = new TypeAndService(TYPE.FOLDER, SERVICE.YOUTUBE);
		return typeAndService;
	}
	
	@Override
	public int getZposition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void open(AbsolutePanel absolutePanel) {
		show();
		absolutePanel.add(display.getWindow(), x, y);
		display.setSize(800, 480);
		display.getWindow().setVisible(true);
		
	}
	
	@Override
	public void setImage(String image) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
	
	private void show() {
		display.setWindowTitle(title);
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		vPanel.setSize("100%", "100%");
		bindToolbar(vPanel);
		
		display.getWindow().add(vPanel);
		if (!hasAlbums && null != contentType) {
			infoPanel.setText(TheSocialOS.getMessages().folder_NoContent(contentType));
			infoPanel.getElement().getStyle().setPosition(Position.RELATIVE);
			vPanel.add(infoPanel);
		}
		
		ScrollPanel panel = new ScrollPanel();
		panel.add(table);
		vPanel.add(panel);
		// setSize(800, 480);
		panel.setSize("100%", "100%");
	}
	
	public void setTitle(String title) {
		display.setWindowTitle(title);
	}
	
	private void bindToolbar(VerticalPanel vPanel) {
		Toolbar toolbar = new Toolbar();
		vPanel.add(toolbar);
		toolbar.setHeight("30px");
		vPanel.setCellHeight(toolbar, "30px");
		// toolbar.setBackImage("./themes/default/backward.png");
		// toolbar.setForwardImage("./themes/default/forward.png");
		// toolbar.setRefreshImage("./themes/default/refresh.png");
		FocusPanel backButton = toolbar.getBackButton();
		FocusPanel forwardButton = toolbar.getForwardButton();
		FocusPanel refreshButton = toolbar.getRefreshButton();
		backButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (arrayPosition > 0) {
					clearMedia();
					arrayPosition--;
					addMedia((HashSet<Media>) files.get(arrayPosition));
					setTitle(parent.get(arrayPosition).getName());
				}
			}
		});
		forwardButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (arrayPosition < files.size() - 1) {
					clearMedia();
					arrayPosition++;
					addMedia((HashSet<Media>) files.get(arrayPosition));
					setTitle(parent.get(arrayPosition).getName());
				}
			}
		});
		refreshButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Media mediaP = parent.get(arrayPosition);
				clearMedia();
				if (mediaP instanceof MediaParent) {
					if (mediaP.getID().equalsIgnoreCase(MediaParent.PICTURES)) MediaHelper
							.reloadPictureAlbums(FolderWindow.this);
					else if (mediaP.getID().equalsIgnoreCase(MediaParent.VIDEOS)) MediaHelper
							.reloadVideoFolders(FolderWindow.this);
					else if (mediaP.getID().equalsIgnoreCase(MediaParent.MUSIC)) {
						
					} else if (mediaP.getID().equalsIgnoreCase(MediaParent.OTHER)) {
						
					}
				} else
					new DblClickHandlerHelper(FolderWindow.this, mediaP).simulateDblClick();
			}
		});
	}
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Media parent) {
		try {
			if (null == this.parent.get(arrayPosition)) this.parent.add(parent);
			else
				this.parent.set(arrayPosition, parent);
		} catch (IndexOutOfBoundsException ex) {
			this.parent.add(parent);
		}
	}
	
	public void next() {
		while (arrayPosition < files.size() - 1) {
			files.remove(files.size() - 1);
			parent.remove(parent.size() - 1);
		}
		arrayPosition++;
		files.add(new HashSet<Media>());
	}
	
}
