package net.thesocialos.client.view.window;

import java.util.HashSet;
import java.util.Iterator;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import net.thesocialos.client.TheSocialOS;
import net.thesocialos.client.api.FacebookAPI;
import net.thesocialos.client.api.FlickrAPI;
import net.thesocialos.client.api.Media;
import net.thesocialos.client.api.PicasaAPI;
import net.thesocialos.client.helper.DblClickHandlerHelper;
import net.thesocialos.client.view.Thumbnail;
import net.thesocialos.client.view.Thumbnail.SERVICE;
import net.thesocialos.client.view.Thumbnail.TYPE;

public class FolderWindow {

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
	
	public FolderWindow() {
		// TODO Auto-generated constructor stub
	}
	
	public FolderWindow(String title) {
		this.title = title;
	}
	
	public FolderWindow(String title, String contentType) {
		this.title = title;
		this.contentType = contentType;
	}
	
	public FolderWindow(String title, HashSet<? extends Media> mediaSet) {
		this.title = title;
		Iterator<? extends Media> iterator = mediaSet.iterator();
		i = 0; j = 0;
		while (iterator.hasNext()) {
			Media media = iterator.next();
			TypeAndService typeAndService = getTypeAndService(media);
			Thumbnail thumb = new Thumbnail(media.getThumbnailURL(), media.getName(), typeAndService.type, typeAndService.service);
			table.setWidget(j, i, thumb);
			//table.getFlexCellFormatter().setWidth(j, i, "150px");
			table.getFlexCellFormatter().setVerticalAlignment(j, i, HasVerticalAlignment.ALIGN_TOP);
			i++;
			if (i % 4 == 0) {
				j++;
				i = 0;
			}
		}
	}

	public void show() {
		DialogBoxExt window = new DialogBoxExt(false, false, new MyCaption());
		window.setText(title);
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		window.add(vPanel);
		if (!hasAlbums && null != contentType) {
			infoPanel.setText(TheSocialOS.getMessages().folder_NoContent(contentType));
			infoPanel.getElement().getStyle().setPosition(Position.RELATIVE);
			vPanel.add(infoPanel);
		}
		ScrollPanel panel = new ScrollPanel();
		panel.add(table);
		vPanel.add(panel);
		panel.setSize("800px", "480px");
		window.show();
		window.setPopupPosition(10, 30);
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
			Thumbnail thumb = new Thumbnail(media.getThumbnailURL(), media.getName(), typeAndService.type, typeAndService.service);
			thumb.addDoubleClickHandler(new DblClickHandlerHelper(media).getDoubleClickHandler());
			table.setWidget(j, i, thumb);
			table.getFlexCellFormatter().setVerticalAlignment(j, i, HasVerticalAlignment.ALIGN_TOP);
			i++;
			if (i % 4 == 0) {
				j++;
				i = 0;
			}
		}
	}

	private TypeAndService getTypeAndService(Media media) {
		TypeAndService typeAndService = null;
		if (media instanceof PicasaAPI.Album) {
			typeAndService = new TypeAndService(TYPE.ALBUM, SERVICE.PICASA);
		} else if (media instanceof PicasaAPI.Picture) {
			typeAndService = new TypeAndService(TYPE.PICTURE, SERVICE.PICASA);
			Image.prefetch(((PicasaAPI.Picture)media).getUrl());
		} else if (media instanceof FacebookAPI.Album) {
			typeAndService = new TypeAndService(TYPE.ALBUM, SERVICE.FACEBOOK);
		} else if (media instanceof FlickrAPI.Album) {
			typeAndService = new TypeAndService(TYPE.ALBUM, SERVICE.FLICKR);
		}
		return typeAndService;
	}
	
	private class TypeAndService {
		
		TYPE type;
		SERVICE service;
		public TypeAndService(TYPE type, SERVICE service) {
			this.type = type;
			this.service = service;
		}
	}
}
