package net.thesocialos.client.desktop.window;

import java.util.HashSet;
import java.util.Iterator;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.api.FacebookAPI;
import net.thesocialos.client.api.FlickrAPI;
import net.thesocialos.client.api.Media;
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
import net.thesocialos.client.view.Thumbnail;
import net.thesocialos.client.view.Thumbnail.SERVICE;
import net.thesocialos.client.view.Thumbnail.TYPE;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
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
	
	public FolderWindow(String title, HashSet<? extends Media> mediaSet, WindowPanelLayout display, int idProgram) {
		this(display, idProgram);
		this.title = title;
		Iterator<? extends Media> iterator = mediaSet.iterator();
		i = 0;
		j = 0;
		while (iterator.hasNext()) {
			Media media = iterator.next();
			TypeAndService typeAndService = getTypeAndService(media);
			Thumbnail thumb = new Thumbnail(media.getThumbnailURL(), media.getName(), typeAndService.type,
					typeAndService.service);
			table.setWidget(j, i, thumb);
			// table.getFlexCellFormatter().setWidth(j, i, "150px");
			table.getFlexCellFormatter().setVerticalAlignment(j, i, HasVerticalAlignment.ALIGN_TOP);
			i++;
			if (i % 4 == 0) {
				j++;
				i = 0;
			}
		}
	}
	
	public FolderWindow(String title, String contentType, WindowPanelLayout display, int idProgram) {
		this(display, idProgram);
		this.title = title;
		this.contentType = contentType;
		
	}
	
	public FolderWindow(String title, WindowPanelLayout display, int idProgram) {
		this(display, idProgram);
		this.title = title;
		
	}
	
	public FolderWindow(WindowPanelLayout display, int idProgram) {
		super(idProgram, 0, display, TypeUnit.WINDOW);
		title = "Prueba";
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
				if (isMaximizable())
					TheSocialOS.getEventBus().fireEvent(new DesktopEventOnMaximize(FolderWindow.this));
				
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
		});
		
	}
	
	public void addMedia(HashSet<? extends Media> mediaSet) {
		if (!hasAlbums) {
			hasAlbums = true;
			infoPanel.removeFromParent();
		}
		Iterator<? extends Media> iterator = mediaSet.iterator();
		while (iterator.hasNext()) {
			Media media = iterator.next();
			TypeAndService typeAndService = getTypeAndService(media);
			// If it a picture prefetch it, so the popup loads in the correct position (and the image loads faster ;) )
			if (typeAndService.type == TYPE.PICTURE) Image.prefetch(((MediaPicture) media).getUrl());
			Thumbnail thumb = new Thumbnail(media.getThumbnailURL(), media.getName(), typeAndService.type,
					typeAndService.service);
			thumb.addDoubleClickHandler(new DblClickHandlerHelper(media).getDoubleClickHandler());
			thumb.setTitle(media.getDescription());
			table.setWidget(j, i, thumb);
			table.getFlexCellFormatter().setVerticalAlignment(j, i, HasVerticalAlignment.ALIGN_TOP);
			i++;
			if (i % 4 == 0) {
				j++;
				i = 0;
			}
		}
	}
	
	public void addMedia(Media media) {
		if (!hasAlbums) {
			hasAlbums = true;
			infoPanel.removeFromParent();
		}
		TypeAndService typeAndService = getTypeAndService(media);
		// If it a picture prefetch it, so the popup loads in the correct position (and the image loads faster ;) )
		if (typeAndService.type == TYPE.PICTURE) Image.prefetch(((MediaPicture) media).getUrl());
		Thumbnail thumb = new Thumbnail(media.getThumbnailURL(), media.getName(), typeAndService.type,
				typeAndService.service);
		thumb.addDoubleClickHandler(new DblClickHandlerHelper(media).getDoubleClickHandler());
		thumb.setTitle(media.getDescription());
		table.setWidget(j, i, thumb);
		table.getFlexCellFormatter().setVerticalAlignment(j, i, HasVerticalAlignment.ALIGN_TOP);
		i++;
		if (i % 4 == 0) {
			j++;
			i = 0;
		}
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
	
	@Override
	public void toZPosition(int position) {
		// TODO Auto-generated method stub
		
	}
	
}
